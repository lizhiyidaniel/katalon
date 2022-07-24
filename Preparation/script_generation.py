import random
import numpy as np
import spacy
from get_object_path import get_object_path

nlp = spacy.load("en_core_web_md")


# func-verb-alignment dict
func_verb_alignment = {
    'setText': {'enter', 'input'},
    'click': {'click'},
    'waitForElementPresent': {'wait'},
    'getText': {'get'},
    'verifyMatch': {'check'}
}

# verb-func alignment dict
verb_func_alignment = {}
for func, verbs in func_verb_alignment.items():
    for verb in verbs:
        verb_func_alignment[verb] = func


# Reformat output of find_obj_path
def obj_path(test_case_dict):
    '''(dict) -> str
    Get object_path

    Params:
        (dict) One test case dict from a parsed manual test case

    Returns:
        (str) Object path
    '''

    output = get_object_path(test_case_dict)

    if type(output) == 'set':
        path = random.sample(output, 1)
        return f'findTestObject(\'{path}\')'
    else:
        return f'findTestObject(\'{output}\')'


# Alignment
def align_func(test_case_dict):
    '''(dict) -> str
    Get aligned WebUI function

    Params:
        (dict) One test case dict from a parsed manual test case

    Returns:
        (str) Aligned WebUI function name
    '''

    action = test_case_dict['V']
    if action in verb_func_alignment:
        func = verb_func_alignment[action]
    else:
        max_sim = -np.inf
        for verb in verb_func_alignment:
            similarity = nlp(action).similarity(nlp(verb))
            if similarity > max_sim:
                max_sim = similarity
                func = verb_func_alignment[verb]
    return func


def align_one_line_to_script(test_case_dict):
    '''(dict) -> dict
    Generate aligned script dict

    Params:
        (dict) One test case dict from a parsed manual test case

    Returns:
        (dict) Generated aligned script dict
    '''

    # Get aligned WebUI function
    fun = align_func(test_case_dict)

    # Get arguments for the WebUI function
    args = []

    if fun == 'setText':
        arg1 = obj_path(test_case_dict)
        arg2 = f"'{test_case_dict['value']}'"
        args = [arg1, arg2]
    if fun == 'click':
        arg1 = obj_path(test_case_dict)
        args = [arg1]
    if fun == 'waitForElementPresent':
        arg1 = obj_path(test_case_dict)
        args = [arg1, str(test_case_dict['time']), 'FailureHandling.STOP_ON_FAILURE']
    if fun == 'getText':
        args = [obj_path(test_case_dict)]

    script_dict = {"action": fun, "entities": args}

    # Get variable
    if fun == 'getText':
        obj = args[-1].split('\'')[-2].split('_')[-1]
        script_dict['var_name'] = 'actual' + obj
        script_dict['var_type'] = 'def'

    return script_dict


# Script generation
def generate_script_dicts(test_case_dicts):
    '''(list) -> list
    Generate aligned list of script dicts

    Params:
        (list) parsed test case dicts for one manual test case

    Returns:
        (list) Generated aligned list of script dicts
    '''

    script_dicts = []
    for line_dict in test_case_dicts:
        if line_dict['V'] not in verb_func_alignment:
            continue
        script_dict = align_one_line_to_script(line_dict)
        script_dicts.append(script_dict)
    return script_dicts


def dicts_to_script(script_dicts):
    '''(list) -> str
    Generate aligned script from script dicts

    Params:
        (list) Generated script dicts for one manual test case

    Returns:
        (str) Generated aligned script
    '''
    script = []
    variables = {}
    for line_dict in script_dicts:
        line = f"WebUI.{line_dict['action']}({', '.join(line_dict['entities'])})"
        if line_dict['action'] == 'verifyMatch':
            actual = variables['getText']
            expected = actual.replace("actual", "expected")
            line = f"WebUI.{line_dict['action']}({actual}, {expected}, false)"

        if 'var_name' in line_dict:
            line = f"{line_dict['var_type']} {line_dict['var_name']} = {line}"
            variables[line_dict['action']] = line_dict['var_name']

        script.append(line)
    return '\n'.join(script)


def generate_script(test_case):
    '''(list) -> list
    Generate aligned script

    Params:
        (list) parsed test case dicts for one manual test case

    Returns:
        (list) Generated aligned script
    '''
    script_dicts = generate_script_dicts(test_case)
    script = dicts_to_script(script_dicts)
    return script