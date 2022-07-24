# Parsing Instructions

## Install
```
!pip install allennlp
!pip install allennlp-models
!pip install --upgrade google-cloud-storage
!pip install cached-path==1.1.2
```

## Make ups
```
FrameParser class
    - The frame parser that creates and fills out a frame for each instruction step in a test case.
    
Frame class
    - The object that stores information for a frame
```

## Usage
```
To use the parsing module, you only need to create a new FrameParser instance, and then call .parse()
function on that FrameParser.

Two modes are available: the Rule based mode and the BERT-tagger based model. The choice of the mode
is determied by parameter logic gate at the .parse() function.
```