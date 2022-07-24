import re
import numpy as np

def remove_numbering(string):
    """ (str) -> str
    Strips the numerical numbering from the beginning of a string
    
    Params:
        string (str) a string
        
    Returns:
        (str)
        A string with the numerical numbering at its beginning removed
    """
    return re.sub('(\d+(\.\d+)?\.?)', '', string, count=1).strip()

def process_steps(steps_str_list):
    """(list) -> (list)
    Converts a list of string of steps into a list of list of steps
    
    Params:
        steps_str_list (list) a list of steps in string format
    
    Returns:
        steps_list (list) a list of list of steps 
    """
    # list for steps of all test cases
    steps_list = []
    for steps_str in steps_str_list:
        # list for steps of each test case
        step_list = []
        # if element in the list is null
        if isinstance(steps_str, float):
            steps_list.append(np.nan)
        else:
            # splitting the step string on \n
            for step in steps_str.splitlines():
                # removing numbering from each step and adding it to step list
                step_list.append(remove_numbering(step))
            steps_list.append(step_list)
    return steps_list