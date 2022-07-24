# get required imports
import pandas as pd
import numpy as np
import pickle as pkl
from collections import *
import Levenshtein
import spacy
import re
nlp = spacy.load("en_core_web_md")
from difflib import SequenceMatcher
import warnings
warnings. filterwarnings('ignore')

# required data structures
scripts_list = pkl.load(open('pickle_file_obj_path_dicts/script_list.pkl', "rb"))
path_dict_c = pkl.load(open('pickle_file_obj_path_dicts/path_dict_c.pkl', "rb"))
alternate_dict_c = pkl.load(open('pickle_file_obj_path_dicts/alternate_dict_c.pkl', "rb"))
action_widget = pkl.load(open('pickle_file_obj_path_dicts/action_widget.pkl', "rb"))

def location_modifier(location):
    """(str) -> str
    makes string more readable
    
    Params:
       location (str) a part of object path that requires string comparison
    
    Returns:
        (str)
        A more readable and language friendly string that is easier to compare 
        against parsed test cases.
    """
    location = ' '.join(location.split('_'))
    all_list = (re.findall('([A-Z]+(?=[A-Z\n\s]|$))*([A-Z]?[a-z]*)', location)) # word seperator
    starter = ''
    for group in all_list:
        for string in group:
            if string == '':
                continue
            else:
                starter += string + ' ' # adds word seperated by space to string
    return starter.rstrip()

def get_sequence_score(string_a, string_b):
    """(str, str) -> int
    gives a cumulative longest common sequence score comparing words from one string to another
    
    Params:
       string_a (str) first string
       string_b (str) second string
       
    Returns:
        lcs (int) 
        Cumulativee longest common sequence score for words independent of order
    """
    list_a = string_a.split() # location
    list_b = string_b.split() # key
    lcs = 0
    for word_a in list_a:
        max_word_seq = -np.inf
        for word_b in list_b:
            seqMatch = SequenceMatcher(None, word_a, word_b)
            word_seq = seqMatch.find_longest_match(0, len(word_a), 0, len(word_b)).size # calculates longest sequence between two words
            if (word_seq > max_word_seq):
                max_word_seq = word_seq
        lcs += max_word_seq # adds to the final score for each word 
    return lcs

def get_location(page, location):
    """(str, str) -> str
    finds the location most similar to the location in parsed test
    cases
    
    Params:
       page (str) page key for path_dict_c
       location (str) location property of parsed test case
       
    Returns:
        min_key (str) 
        key for path_dict_c[page] most similar to location
    """
    min_score = np.inf
    min_key = ''
    for key in path_dict_c[page].keys():
        lcs = get_sequence_score(location.lower(), location_modifier(key).lower())
        # calculates score
        score = (len(location) - lcs)/len(location)
        if score < min_score:
            min_score = score
            min_key = key
        # if score is same, uses Levenshtein distance
        elif score == min_score:
            min_dist = Levenshtein.distance(location.lower(), location_modifier(min_key).lower())
            dist = Levenshtein.distance(location.lower(), location_modifier(key).lower())
            if dist < min_dist:
                min_score = score
                min_key = key
    return min_key

def get_alternate_location(location):
    """(str) -> str
    finds the location most similar to the location in parsed test
    cases
    
    Params:
       location (str) location property of parsed test case
       
    Returns:
        min_key (str) 
        key of alternate_dict_c that's most similar to location
    """
    min_score = np.inf
    min_key = ''
    for key in alternate_dict_c.keys():
        # looks for longest sequence in the two strings
        seqMatch = SequenceMatcher(None, location_modifier(key).lower(), location.lower())
        lcs = seqMatch.find_longest_match(0, len(location_modifier(key).lower()), 0, len(location.lower())).size
        # calculates score
        score = (len(location_modifier(key).lower()) - lcs)/len(location_modifier(key).lower())
        if score < min_score:
            min_score = score
            min_key = key
        # if score is same, Levenshtein distance is used
        elif score == min_score:
            min_dist = Levenshtein.distance(location.lower(), location_modifier(min_key).lower())
            dist = Levenshtein.distance(location.lower(), location_modifier(key).lower())
            if dist < min_dist:
                min_score = score
                min_key = key
    return min_key

def page_str_modifier(page):
    """(str) -> str
    makes string more readable
    
    Params:
       page (str) a part of object path that requires string comparison
    
    Returns:
        (str)
        A more readable and language friendly string that is easier to compare 
        against parsed test cases.
    """
    # if page contains sub pages (a/b) -> b
    if len(page.split('/')) > 1:
        page = ''.join(page.split('/')[1:])
        page = ''.join(page.split('_'))
    else:
        page = ''.join(page.split('_'))[1:]
    page = ' '.join(re.findall('[A-Z][a-z]+', page))
    return page

def get_page(page):
    """(str) -> str
    finds the page key most similar to the page value in parsed test
    cases
    
    Params:
       page (str) page property of parsed test case
       
    Returns:
        max_key (str) 
        key of path_dict_c that's most similar to page
    """
    max_sim = -np.inf
    max_key = ''
    for key in path_dict_c.keys():
        # semantic similarity score
        similarity = nlp(page).similarity(nlp(page_str_modifier(key)))
        if similarity > max_sim:
            max_sim = similarity
            max_key = key
    # dictionary key with maximum semantic similarity
    return max_key

def get_alternate_page(location, page):
    """(str, str) -> str
    finds the page key of alternate_dict_c[location] 
    most similar to the page value in parsed test cases
    
    Params:
       location (str) location key of alternate_dict_c
       page (str) page property of parsed test case
       
    Returns:
        max_key (str) 
        key of alternate_dict_c[location] that's most similar to page
    """
    max_sim = -np.inf
    max_key = ''
    for key in alternate_dict_c[location].keys():
        # semantic similarity score
        similarity = nlp(page.lower()).similarity(nlp(key.lower()))
        if similarity > max_sim:
            max_sim = similarity
            max_key = key
    # dictionary key with maximum semantic similarity
    return max_key

def get_object_path(parsed_test_case):
    """(dict) -> str or set
    finds the object path for a given parsed test case
    
    Params:
       parsed_test_case (dict) a parsed test case
       
    Returns:
        (str)
        The identified object path
        OR
        (set)
        A set of possible object path values
    """
    # match page of object path
    page = get_page(parsed_test_case['page'])
    # to consider cases where value property in a parsed test case is used as a location instead
    # get element of object path 
    if 'location' in parsed_test_case.keys():
        location = get_location(page, parsed_test_case['location'])
    else:
        location = get_location(page, parsed_test_case['value'])
    object_path_set = set()
    for element in path_dict_c[page][location]:
        # if path is associated with V property in parsed test case
        if element in action_widget[parsed_test_case['V']]:
            object_path_set.add(element)
    # one path found, return path       
    if len(object_path_set) == 1:
        return list(object_path_set)[0]
    # multiple paths found, return a set of paths
    elif len(object_path_set) > 1:
        return object_path_set
    # no path found, consider alternative strategy
    elif len(object_path_set) == 0:
        # get element of object path
        if 'location' in parsed_test_case.keys():
            location = get_alternate_location(parsed_test_case['location'])
        else:
            location = get_alternate_location(parsed_test_case['value'])
        # get page 
        page = get_alternate_page(location, parsed_test_case['page'])
        for element in alternate_dict_c[location][page]:
            if element in action_widget[parsed_test_case['V']]:
                object_path_set.add(element)
        # one path found, return path
        if len(object_path_set) == 1:
            return list(object_path_set)[0]
        # multiple paths found, return a set of paths
        elif len(object_path_set) > 1:
            return object_path_set
        # no path found, consider last resort
        elif len(object_path_set) == 0:
            object_path_set = set()
            page = get_page(parsed_test_case['page'])
            # returns all paths associated with the page, if they're associated with
            # the functionality given in the parsed test case ('V')
            for element in path_dict_c[page].keys():
                for path in path_dict_c[page][element]:
                    if path in action_widget[parsed_test_case['V']]:
                        object_path_set.add(path)
            return object_path_set