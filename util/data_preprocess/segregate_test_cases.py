import numpy as np

def segregate(case):
    """ (str) -> str, str, str
    Segregates a manual test case string into three sub-strings
    
    Params:
        case (str) a manual test case string
        
    Returns:
        pre_condition_str, steps_str, expected_result_str (str, str, str) 
        Three sub-strings corresponding to the pre-condition, steps and
        expected results section of a manual test case        
    """
    pre_condition = False
    steps = False
    expected_result = False

    pre_condition_str = ''
    steps_str = ''
    expected_result_str = ''
    
    # split the test case string into separate lines
    for line in case.splitlines():
        # if line empty
        if line.strip() == '':
            continue
        # if its a pre-condition statement
        if 'pre-condition' in line.strip().lower():
            # if pre-condition points start from the same line where pre-condition is mentioned
            if len(line.split(':')) > 1 and not ((line.split(':')[1]).strip() == ''):
                # we add that line as a pre_condition_str without 'pre-condition'
                pre_condition_str += line.split(':')[1]
            # indicates we found pre_condition
            pre_condition = True
            continue
        # if pre_condition is found or line starts with 'steps'
        if pre_condition or line.strip().lower().startswith('steps'):
            # if line starts with 'steps'
            if line.strip().lower().startswith('steps'):
                # pre_condition is set to False
                pre_condition = False
                # if 'steps' line also includes a step
                if len(line.split(':')) > 1 and not ((line.split(':')[1]).strip() == ''):
                    # we add it to the steps_str
                    steps_str += line.split(':')[1]
                # steps is set to True
                steps = True
                continue
            # if pre_condition string is empty
            if pre_condition_str != '':
                # add next line to pre_condition string
                pre_condition_str += '\n'
            # strip extra whitespace from right side from pre_condition_str
            pre_condition_str += line.rstrip()
        # if steps is true or line starts with expected result
        if steps or line.strip().lower().startswith('expected result'):
            # if line starts with expected result
            if line.strip().lower().startswith('expected result'):
                # steps is made false
                steps = False
                # if expected result also includes an expected result
                if len(line.split(':')) > 1 and not ((line.split(':')[1]).strip() == ''):
                    expected_result_str += (line.split(':')[1])
                # expected result is set as True
                expected_result = True
                continue
            # if steps_str is empty
            if steps_str != '':
                # nextLine is added
                steps_str += '\n'
            # extra whitespace from the steps_str is removed
            steps_str += line.rstrip()
        # if expected_result is true
        if expected_result:
            # if expected result string is empty
            if expected_result_str != '':
                # add nextLine
                expected_result_str += '\n'
            # removes whitespace from the expected result string
            expected_result_str += line.rstrip()   
    
    return pre_condition_str, steps_str, expected_result_str

def process_test_cases(case_list):
    """ (list) -> list, list, list
    Processes list of manual test cases into three separate lists
    
    Params:
        case_list (list) a list of the manual test cases
        
    Returns:
        pre_condition_list, steps_list, expected_result_list (list, list, list) 
        Three lists of pre-condition, steps and expected results corresponding
        to each example in a list of the manual test cases 
    """
    pre_condition_list, steps_list, expected_result_list = [], [], []
    # going through each manual test case
    for case in case_list:
        # if manual test case is null
        if isinstance(case, float):
            pre_condition_list.append(np.nan)
            steps_list.append(np.nan)
            expected_result_list.append(np.nan)
        # if manual test case is not null
        else:
            # get segregated pre_condition, steps and expected_result section
            pre_condition, steps, expected_result = segregate(case)
            # if pre_condition is empty
            if pre_condition == '':
                pre_condition_list.append(np.nan)
            # add it to the pre_condition_list
            else:
                pre_condition_list.append(pre_condition)
            # if expected_result is empty
            if expected_result == '':
                expected_result_list.append(np.nan)
            # add it to the expected_result_list
            else:
                expected_result_list.append(expected_result)
            # add it to the steps_list 
            steps_list.append(steps)
    return pre_condition_list, steps_list, expected_result_list