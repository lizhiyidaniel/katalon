import pandas as pd
import numpy as np
from os import listdir
from os.path import isfile
import pickle as pkl

# combine script file with its title
def get_script(path, dic={}):
    """(str, dict) -> dict
    Combines script file from the given path with their corresponding title

    Params:
        path (str) path where the scripts are
        dic (dict) dict mapping script file with 
                   their test case title, initially 
                   empty

    Returns:
        dic (dict)
        dict mapping script file with their
        test case title
    """
    for dir in listdir(path):
        # if directory has a test case title
        if dir.startswith('TC'):
            for file in listdir(path+dir+'/'):
                # align test case title with the script file inside it
                dic[dir] = path+dir+'/'+file
        # if directory is not a test case title and has a nested folder
        elif not isfile(path+dir+'/'):
            # change the path to the nested folder and update the dictionary
            get_script(path+dir+'/', dic)
    return dic

def clean_dataframe(test_cases_df):
    """(pd.DataFrame) -> pd.DataFrame
    Cleans the dataframe and removes all inconsistencies from title

    Params:
        test_cases_df (pd.DataFrame) unclean dataframe

    Returns:
        test_cases_df (pd.DataFrame)
        cleaned dataframe
    """
    # remove the unnamed columns from the dataframe
    for column in test_cases_df.columns:
        if 'Unnamed: ' in column:
            test_cases_df.drop(column, inplace=True, axis=1)
    # remove first two rows
    test_cases_df.drop(test_cases_df.index[[0, 1]], inplace=True)
    # reset index
    test_cases_df.reset_index(drop=True, inplace=True)
    # manually edit the test case title
    test_cases_df.iloc[131]['Title'] = 'TC132_Verify Question is timed out_P1'

    return test_cases_df

def align_scripts(test_cases_df):
    """(pd.DataFrame) -> pd.DataFrame
    Aligns the groovy scripts corresponding to the 
    test cases and adds it to the dataframe

    Params:
        test_cases_df (pd.DataFrame) dataframe read from
                                     the provided excel file

    Returns:
        test_cases_df (pd.DataFrame)
        dataframe with scripts included
    """
    # get the path_dict dictionary from get_script function
    path_dict = get_script('data/Main Test Cases/')
    scripts = []
    # clean the dataframe
    test_cases_df = clean_dataframe(test_cases_df)
    # go through all rows in the dataframe
    for i in range(test_cases_df.shape[0]):
        # if the row is not null
        if test_cases_df.iloc[i]['Title'] in path_dict.keys():
            # use the test case title as the key to the path_dict
            with open(path_dict[test_cases_df.iloc[i]['Title']]) as f:
                # read scripts, add to the list
                scripts.append(f.read())
        else:
            scripts.append(np.nan)
    test_cases_df['test_scripts'] = scripts

    return test_cases_df