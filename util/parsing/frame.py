''' This module includes the Frame class that contains the frame structures with their corresponding slots.
'''

# Frame structures for the actions that we have worked on thus far
all_frames = {
    'enter': {'V': 'enter', 'value': None, 'location': None},
    'set': {'V': 'set', 'value': None, 'location': None},
    'input': {'V': 'input', 'value': None, 'location': None},
    'click': {'V': 'click', 'value': None},
    'wait': {'V': 'wait', 'value': None, 'time': None},
    'get': {'V': 'get', 'value': None},
    'check': {'V': 'check', 'value': None},
    'verify': {'V': 'verify', 'value': None}
}


class Frame:
    
    def __init__(self):
        ''' Initiate a Frame object.
        
        @Attributes:
            - action (str): The action verb for this frame
            - frame (dict): A dictionary with different slots for this frame
            - spec_vals (list of str): List of special values as indicated by the manual instruction
            - spec_names (list of str): List of object name as indicated by the manual instruction
            - spec_times (list of int): List of time values as indicated by the manual instruction
        '''
        self.action = None
        self.frame = None
        self.spec_vals = []
        self.spec_names = []
        self.spec_times = []
        
    def __str__(self):
        ''' The Frame object is printed in a format that shows all its attributes.
        '''
        return f'action: {self.action}, frame: {self.frame}, \nspec_vals: {self.spec_vals}, spec_names: {self.spec_names}, spec_times: {self.spec_times}'
        
    def update_action(self, action):
        ''' Function that updates the main action verb for this frame.
        '''
        self.action = action
        
    def init_slots(self, action):
        ''' Function that initiates the frame attribute with slots empty according to the action verb.
        '''
        if action in all_frames:
            self.frame = all_frames[action]
        else:
            print(f'Error: frame not defined for action "{action}"! Please check the Frame module under ./util/parsing')
            
    def fill_slot(self, slot_name, slot_value):
        ''' Function that updates the specified slot with some value.
        '''
        if self.frame:
            self.frame[slot_name] = slot_value
        else:
            print(f'Error: frame not initiated!')
        
    def update_spec(self, spec_vals, spec_names, spec_times):
        ''' Function that updates all special values for the frame.
        '''
        self.spec_vals = spec_vals
        self.spec_names = spec_names
        self.spec_times = spec_times
        
    def update_page(self, curr_page):
        ''' Function that updates the page for the frame (used later to find test object path).
        '''
        self.frame['page'] = curr_page
