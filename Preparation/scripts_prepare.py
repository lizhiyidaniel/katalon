import re
from webbrowser import get
import pandas as pd
import numpy as np
import sys
import pickle

# filter out imports and WebUI.comments
def script_filter(script):
    cleaned = []
    lines = script.split('\n')
    for line in lines:
        if line.strip() == '':
            continue
        if line.startswith('import'):
            continue
        if line.strip().startswith('WebUI.comment'):
            continue
        cleaned.append(line)
    return '\n'.join(cleaned)

def filter_scripts_prepare(path):
    df = pd.read_csv(path)
    clean_scripts = []
    for script in df['test_scripts']:
        if script is np.NaN:
            clean_scripts.append(np.NaN)
            continue
        cleaned = script_filter(script)
        clean_scripts.append(cleaned)
    df['prepared_scripts'] = clean_scripts
    return df

# Create func-obj-dict
def update_func_obj_dict(script, func_obj_dict):
    lines = script.split('\n')
    func_name_pattern = re.compile('WebUI\.([a-zA-Z]*)(.*)')
    obj_pattern = re.compile('[^\']*findTestObject\((\'([^\']*)\')+.*')
    for line in lines:
        result = func_name_pattern.search(line)
        if result:
            func_name = result.group(1)
            obj_result = obj_pattern.search(result.group(2))
            if obj_result:
                obj = obj_result.group(2)
                if obj.startswith('Object Repository/'):
                    obj = obj[len('Object Repository/'):]
                if func_name in func_obj_dict:
                    func_obj_dict[func_name].add(obj)
                else:
                    obj_set = set()
                    obj_set.add(obj)
                    func_obj_dict[func_name] = obj_set
        else:
            continue

    return func_obj_dict

def get_func_obj_dict(df):
    func_obj_dict = {}
    for script in df['test_scripts']:
        if script is np.NaN:
            continue
        func_obj_dict = update_func_obj_dict(script, func_obj_dict)
    return func_obj_dict


# Standardize scripts
def combine_lines(script):
    '''Combine multiple code lines separated by comma or if blocks into one string'''

    # multiple code lines separated by comma
    multi_pattern = "(.*),\n(.*)"
    multi_result = re.search(multi_pattern, script)
    if multi_result:
        sub = ', '.join(multi_result.groups())
        script = re.sub(multi_pattern, sub, script)

    # if blocks
    if_block_pattern = "if\s*\(.*\)\s*{\n([^}]*\n)*\t*}"
    if_block_results = re.finditer(if_block_pattern, script)
    for block in if_block_results:
        sub = ''.join(block.group(0).split('\n'))
        script = script.replace(block.group(0), sub)

    return script

def code_block_clean(script):
    '''
        Process the code blocks including 
        1) setUp function,
        2) if(true) blocks
        3) if blocks
    '''

    # Clear up SetUp def
    setup_pattern = "@SetUp\(\)\ndef setUp\([^\)]*\) {([^}]*)}"
    setup_result = re.search(setup_pattern, script)
    if setup_result:
        sub = setup_result.group(1)
        script = re.sub(setup_pattern, sub, script)

    # Clear up if(true)
    if_true_pattern = "if\s*\(true\)\s*{([^}]*)}"
    if_true_result = re.finditer(if_true_pattern, script)
    for if_true_block in if_true_result:
        sub = '\n'.join(if_true_block.group(1).split('\t'))
        script = script.replace(if_true_block.group(0), sub)

    return script


def find_types(script, types_set):
    '''Find all types of variables in a script'''
    pattern = "(\n\t*|^[@a-zA-Z]*\s)([a-zA-z]+)\s[a-zA-Z]+\s*=\s*[^=]"
    results = re.finditer(pattern, script)
    for result in results:
        types_set.add(result.group(2))
    return types_set


def code_to_dict(line):
    '''Standardize one code line into a dict'''
    d = {}

    var_pattern = re.compile('(def|int|List|String|WebDriver|WebElement|JavascriptExecutor) (\w+) = (.*)')
#     obj_pattern = re.compile('findTestObject\(\'([\w\/]*)\'\)')
    func_name_pattern = re.compile('WebUI\.([a-zA-Z]*)\((.*)\)')
    entity_pattern = re.compile('[a-zA-Z0-9]+[^,]*|\[[^\]]*\]')

    var_result = var_pattern.search(line)
    # Case 1: def VAR =
    if var_result:
        var_type = var_result.group(1)
        var_name = var_result.group(2)
        result = func_name_pattern.search(var_result.group(3))
        d['var_type'] = var_type
        d['var_name'] = var_name
        if not result:
            d['value'] = var_result.group(3)

    # Case 2: WebUI.FUNCTION
    else:
        result = func_name_pattern.search(line)

    if result:
        func_name = result.group(1)
        entities = entity_pattern.findall(result.group(2))
#         entities = [obj_pattern.search(ent).group(1) if obj_pattern.search(ent) else ent for ent in entities]

        d['action'] = func_name
        d['entities'] = entities
    return d


def if_block_to_dict(block_line):
    '''Process if blocks to dict'''
    d = {}
    condition_pattern = "if\s*\((.*)\)\s*{(.*)}$"
    condition_result = re.search(condition_pattern, block_line)
    d['condition'] = condition_result.group(1)
    actions = condition_result.group(2).split('\t')
    actions_dict = []
    for action in actions:
        if action.strip() == '':
            continue
        actions_dict.append(code_to_dict(action))
    d['actions'] = actions_dict
    return d


def scripts_preprocess(df):
    '''Process all test scripts'''

    processed_scripts = []
    for old_script in df['prepared_scripts']:
        if old_script is np.NaN:
            continue

        script = code_block_clean(combine_lines(old_script))

        lines = script.split('\n')
        new_script = []
        block = []
        for line in lines:
            line = line.strip()
            # ignore empty lines
            if line == '':
                continue
            # ignore comments
            if line.startswith('\'') and line.endswith('\''):
                continue

            # process WebUI functions and def VAR = code lines
            if line.startswith('WebUI.') or ('=' in line):
                if len(block) > 0:
                    new_script.append('\n'.join(block))
                    block = []
                new_line = code_to_dict(line)
                new_script.append(new_line)

            # process If blocks
            elif line.startswith('if'):
                new_line = if_block_to_dict(line)
                new_script.append(new_line)

            else:
                block.append(line)
        processed_scripts.append(new_script)
        for line in new_script:
            print(line)
        print('\n')
    return processed_scripts



if __name__ == "__main__":

    path = sys.argv[1]
    cleaned_df = filter_scripts_prepare(path)
    cleaned_df.to_csv('preprocessed_scripts.csv')

    func_obj_dict = get_func_obj_dict(cleaned_df)
    with open('funct-obj-dict.pkl', 'wb') as f:
        pickle.dump(func_obj_dict, f)
    print(len(func_obj_dict))
    print(func_obj_dict['setText'])
    print("Function-objects dictionary is saved in funct-obj-dict.pkl")

    standard_scripts = scripts_preprocess(cleaned_df)
    with open('standard-scripts.pkl', 'wb') as f:
        pickle.dump(standard_scripts, f)
    print(len(standard_scripts))
    print("Standardized scripts are saved in standard-scripts.pkl")
