import sys
import os
import pickle

def get_objects(path):
    '''
    str -> set
    Get all objects by traversing Object Repository 

    Params:
        path (str) the path to Object Repository
    Returns:
        objects (set) A set containing all extracted object names
    '''
    objects = set()

    # "../../ubc-script-generation/ontest-main/Automation/Object Repository"
    path_walk = os.walk(path)

    for root, directories, files in path_walk:
        if directories:
            continue

        # Add object names to the set
        directory = root.split('\\')[-1]
        for file in files:
            fileName = file.split('.')[0]
            obj = directory + '/' + fileName
            objects.add(obj)

    with open('objects.pkl', 'wb') as f:
        pickle.dump(objects, f)

    return objects

if __name__ == "__main__":

    path = sys.argv[1]
    objects = get_objects(path)
    print("Objects are saved in objects.pkl")