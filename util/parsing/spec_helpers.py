''' Utility functions that help with finding or removing special characters in the manual test case instructions.
'''
import regex as re


def find_spec(step):
    ''' Find special values according to special character patterns.
    
    @Input:
        - step (str): A string instruction from a manual test case
    
    @Return:
        - val_matches (list of regex match object): List of match objects for special values
        - name_matches (list of regex match object): List of match objects for object names
        - time_matches (list of regex match object): List of match objects for time values
    '''
    val_pattern = re.compile(r'["\'](.+?)["\']')
    name_pattern = re.compile(r'\[(.+?)\]')
    time_pattern = re.compile(r'\((.+?)\)')

    val_matches = re.findall(val_pattern, step)
    name_matches = re.findall(name_pattern, step)
    time_matches = re.findall(time_pattern, step)

    return val_matches, name_matches, time_matches

def remove_spec(step):
    ''' Remove special characters from the manual test case instructions.
    
    @Input:
        - step (str): A string instruction from a manual test case
        
    @Return:
        - step (str): A string instruction from a manual test case with special characters removed
    '''
    val_pattern = re.compile(r'"(.+?)"')
    val_match = re.search(val_pattern, step)
    if val_match:
        step = step[:val_match.start()] + step[val_match.end():]

    name_pattern = re.compile(r'(\[.+?\])')
    name_match = re.search(name_pattern, step)
    if name_match:
        step = step[:name_match.start()] + step[name_match.end():]

    time_pattern = re.compile(r'(\(.+?\))')
    time_match = re.search(time_pattern, step)
    if time_match:
        step = step[:time_match.start()] + step[time_match.end():]

    return step   
