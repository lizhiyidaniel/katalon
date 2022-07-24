''' This module includes the FrameParser class that parses the manual test cases into Frames with slots filled.
'''

# !pip install allennlp
# !pip install allennlp-models

from allennlp.predictors.predictor import Predictor
import allennlp_models.tagging
from util.parsing.spec_helpers import find_spec, remove_spec
from util.parsing.frame import Frame
import regex as re


class FrameParser:
    
    def __init__(self):
        ''' Initiate a FrameParser object.
        
        @Attributes:
            - predictor: An AllenNLP predictor for parsing
            - parsed_frames (list of list of Frame): The list that stores all the parsed frames 
        '''
        self.predictor = Predictor.from_path("https://storage.googleapis.com/allennlp-public-models/openie-model.2020.03.26.tar.gz")
        self.parsed_frames = []
        
    def __str__(self):
        ''' The FrameParser object is printed as its parsed frames.
        '''
        return self.parsed_frames
        
    def parse(self, testcases, testcase_pages, all_tags, rule_base=False, debug=False):
        ''' The parse function takes in a list of all testcases.
        
        @Inputs:
            - testcases (list of list of strings): The list that stores the instructions from different manual
            test cases
            - testcase_pages (list of strings): The list that stores the page information for each test case
            - all_tags (list of list of strings): The collection of all IOB tags produced by the BERT tagger
            - rule_base (bool): Indicates whether the parser uses rule-based method to process the test case
            instructions (the alternative is BERT tagger)
            - debug (bool): Indicates whether to print out intermediate results for debugging
            
        @Return:
            - parsed_frames (list of list of Frame): The list that stores all the parsed frames 
        '''
        for i, testcase in enumerate(testcases):
            curr_page = testcase_pages[i]
            if not rule_base: # Index tags if we are using the BERT tagger
                tc_tags = all_tags[i]
            self.parsed_frames.append([])
            if type(testcase) == list:
                for j, step in enumerate(testcase):
                    if not rule_base:
                        tags = tc_tags[j]

                    if debug:
                        print('current step:', step)

                    # Find the special segments from each test case step
                    spec_val, spec_name, spec_time = find_spec(step) 

                    # Use AllenNLP to perform semantic role labeling (SRL)
                    parsed_step = self.predictor.predict(sentence=remove_spec(step.lower()))

                    curr_frame = Frame()
                    if len(parsed_step['verbs']) != 0:
                        # Extract useful information from SRL result
                        parsed_info = self._description_parser(parsed_step['verbs'][0]['description'], debug)
                        # Update the Frame object with parsed information
                        curr_frame.update_action(parsed_info['V'])
                        curr_frame.init_slots(parsed_info['V'])
                        curr_frame.update_spec(spec_val, spec_name, spec_time)
                        curr_frame.update_page(curr_page)

                        if debug:
                            print(curr_frame)
                    else: # Show warning message if AllenNLP did not detect a main verb for this step
                        print(f'Action Parsing Warning: No action verb detected at test case {i+1} for step "{step}"!')

                    if rule_base: # Fill slots with rule-based methods
                        filled_frame = self._rule_based_slot_filling(parsed_info, curr_frame)
                        self.parsed_frames[-1].append(filled_frame.copy())
                    else: # Fill slots with BERT sequence tagger
                        if len(step.strip().split()) == len(tags): # Only fill slots when the tagger tags correctly
                            filled_frame = self._tagger_based_slot_filling(step, tags, curr_frame)
                            self.parsed_frames[-1].append(filled_frame.copy())
                        else:
                            print(f'BERT-tagger Warning: instruction step "{step}" was not parsed properly by the BERT tagger!')
                
            if debug:
                print()
                
        return self.parsed_frames
    
    def _tagger_based_slot_filling(self, step, tags, frame, debug=False):
        ''' Helper function that performs slot filling with BERT tagger results.
        
        @Inputs:
            - step (str): A string instruction from a manual test case
            - tags (list of strings): A list of IOB tags from the BERT tagger
            - frame (Frame): A Frame object with slots not filled
            - debug (bool): Indicates whether to print out intermediate results for debugging
            
        @Return:
            - frame (Frame): A Frame object with slots filled
        '''
        word_list = step.strip().split()
        slot_dict = {}
        for tag, word in zip(tags, word_list):
            if tag[0] != 'O' and tag[2:] in frame.frame.keys():
                if tag[2:] not in slot_dict:
                    slot_dict[tag[2:]] = word
                else:
                    slot_dict[tag[2:]] = slot_dict[tag[2:]] + ' ' + word
                    
        if debug:
            print(slot_dict)
                    
        for slot_name, slot_value in slot_dict.items():
            frame.fill_slot(slot_name, slot_value.strip())
            
        return frame.frame
    
    def _rule_based_slot_filling(self, parsed_info, frame):
        ''' Helper function that performs slot filling with rule-based methods.
        
        @Inputs:
            - parsed_info (dict): A dictionary with parsing output from AllenNLP
            - frame (Frame): A Frame object with slots not filled
            
        @Return:
            - frame (Frame): A Frame object with slots filled
        '''
        if frame.action == 'enter' or frame.action == 'input' or frame.action == 'set':
            # Fill 'value' slot
            if frame.spec_vals != []:
                frame.fill_slot('value', frame.spec_vals[0])
            elif 'ARG1' in parsed_info:
                frame.fill_slot('value', parsed_info['ARG1'])
            else:
                print(f'Error (enter & input): NO FIRST OBJECT! \n{parsed_info}')
            # Fill 'location' slot
            if frame.spec_names != []:
                frame.fill_slot('location', frame.spec_names[0])
            elif 'ARGM-LOC' in parsed_info:
                frame.fill_slot('location', parsed_info['ARGM-LOC'])
            elif 'ARG2' in parsed_info:
                frame.fill_slot('location', parsed_info['ARG2'])
            elif 'ARGM-PRP' in parsed_info:
                frame.fill_slot('location', parsed_info['ARGM-PRP'])
            elif 'ARGM-DIR' in parsed_info:
                frame.fill_slot('location', parsed_info['ARGM-DIR'])
            else:
                print(f'Error (enter & input): NO SECOND OBJECT! \n{parsed_info}') 
        elif frame.action == 'click':
            # Fill 'value' slot
            if frame.spec_names != []:
                frame.fill_slot('value', frame.spec_names[0])
            elif 'ARGM-DIS' in parsed_info:
                frame.fill_slot('value', parsed_info['ARGM-DIS'])
            elif 'ARG1' in parsed_info:
                frame.fill_slot('value', parsed_info['ARG1'])
            elif 'ARGM-LOC' in parsed_info:
                frame.fill_slot('value', parsed_info['ARGM-LOC'])
            else:
                print(f'Error (click): NO FIRST OBJECT! \n{parsed_info}')
        elif frame.action == 'wait':
            # Fill 'value' slot
            if frame.spec_names != []:
                frame.fill_slot('value', frame.spec_names[0])
            elif 'ARG1' in parsed_info:
                frame.fill_slot('value', parsed_info['ARG1'])
            elif 'ARG2' in parsed_info:
                frame.fill_slot('value', parsed_info['ARG2'])
            # Fill 'time' slot
            if frame.spec_times != []:
                frame.fill_slot('time', int(frame.spec_times[0]))
            else:
                print(f'Error (wait): No time detected! \n{parsed_info}')
        elif frame.action == 'get':
            # Fill 'value' slot
            if frame.spec_names != []:
                frame.fill_slot('value', frame.spec_names[0])
            elif 'ARG1' in parsed_info:
                frame.fill_slot('value', parsed_info['ARG1'])
            elif 'ARG2' in parsed_info:
                frame.fill_slot('value', parsed_info['ARG2'])
                
        return frame.frame
            
    def _description_parser(self, description, debug=False):
        ''' Helper function that converts raw AllenNLP output to a structured dictionary. 
        
        @Inputs:
            - description (str): Raw string output from AllenNLP
            - debug (bool): Indicates whether to print out intermediate results for debugging
            
        @Return:
            - parsed_info (dict): A dictionary with structured parsing output from AllenNLP
        '''
        des_pattern = re.compile(r'\[(.+?)\]')
        matches = re.findall(des_pattern, description)
        parsed_info = {}
        for match in matches:
            elements = match.split(':')
            parsed_info[elements[0].strip()] = elements[1].strip()
            
        if debug:
            print(parsed_info)
            
        return parsed_info
