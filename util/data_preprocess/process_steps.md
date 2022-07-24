# Instructions

## Installations

> pip install regex <br>
> pip install numpy <br>
> pip install sys <br>

## Import 

1. Import the installed `sys` library

> import sys

2. Append the path to the folder where the module`process_step.py` exists

> sys.path.append('util/data_preprocess') # path from the project folder directory

3. Import the module

> from process_steps import process_steps

## Usecase

> process_steps(_a list of strings containing the numerical steps in string format_)

## Output

A list of lists where each nested list contains the separated non-numerical steps for each string in the input list.
