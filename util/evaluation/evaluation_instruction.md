# Instructions

## Installations

> pip install regex <br>
> pip install nltk <br>

## Import 

1. Import the installed `regex` library

> import re

2. Import the installed `nltk` library

> import nltk

3. Import the module

> from evaluation import evaluator

## Usecase

`Preprocess the test cases`: Takes a case string and preprocess it and remove precondition, verification and comments part of the test case <br>
`preprocess_test_case(case)`


`Parse the Groovy code into chunks`: Take input of code and make it into smaller chunks for evaluation by regex <br>
`groovy_parser(code)`

`Use F1 evaluation`: Takes the gold scripts and generated scripts to print F1 scores
`f1_evaluation(gold_scripts, scripts)`


`use 1-4 grams BLEU evaluation`: Takes the gold scripts and generated scripts to print BLEU 1-4 scores <br>
BLEU_evaluation(gold_scripts, scripts)



## Detailed Demo

Check the [evaluation notebook](https://github.ubc.ca/mds-cl-2021-22/katalon_project/blob/dev/util/evaluation/evaluation.ipynb)