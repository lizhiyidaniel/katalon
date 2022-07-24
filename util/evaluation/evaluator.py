#need to preprocess the test_cases to extract only the code parts without Webcommment
#The format the test case should be like:
#["WebUI.setText(findTestObject('Page_Login/tbx_Email'), GlobalVariable.G_Email)",
# "WebUI.setText(findTestObject('Page_Login/tbx_Password'), GlobalVariable.G_Password)",
# "WebUI.click(findTestObject('Page_Login/btn_Login'))",
# "WebUI.waitForElementPresent(findTestObject('Module_Navigation/txt_title'), 30)",
# 'WebUI.takeScreenshotAsCheckpoint("Successful Login")']
import math
def preprocess_test_case(case):
    '''Takes a case string and preprocess it and remove precondition, verification and comments part of the test case'''
    try:
        if math.isnan(case):
            return None
    except:
        pass
    output = []
    steps = case.split('\n')
    for step in steps:
        step = step.strip()
        if step.startswith("def"):
            if step.startswith("def actualinvalidMessage"):
                output.append(step)
            elif step.startswith("def actualEmailErrorMessage"):
                output.append(step)
            elif step.startswith("def actualPasswordErrorMessage"):
                output.append(step)
            elif step.startswith("def actualinvalidMessage"):
                output.append(step)
            elif step.startswith("def actualSuccessfullMessage"):
                output.append(step)
            else:
                continue
        if step.startswith("WebUI.waitForPageLoad"):
            continue
        if not step.startswith("WebUI"):
            continue
        if step.startswith("WebUI.comment"):
            continue
        if step.startswith("WebUI.takeScreenshotAsCheckpoint"):
            continue
        if step.startswith("WebUI.callTestCase"):
            continue
        if step.startswith("WebUI.verifyMatch"):
            continue
        if step.startswith("WebUI.verifyTextPresent"):
            continue
        if step.startswith("WebUI.waitForPageLoad"):
            continue
        if step.startswith("WebUI.verifyElementPresent"):
            continue
        if step.startswith("WebUI.verifyElementVisible"):
            continue
        if step.startswith("WebUI.waitForElementVisible"):
            continue
        if step.startswith("WebUI.verifyElementText"):
            continue
        if step.startswith("WebUI.verifyElementNotPresent"):
            continue
        else:
            output.append(step)
    return output
import re
#!pip install re
#you may need to uncomment to install re for regex expressions in Python
#The groovy code should be split by [., /, (, )] marks into smaller chunks to evaluate in BLEU score
#The output would be like ['WebUI.setText','findTestObject',"'Page_Login","tbx_Email'"," 'admin1@mail.com'"]
def groovy_parser(code):
    '''Take input of code and make it into smaller chunks for evaluation by regex'''
    output = []
    result = re.split(r'[\[\],()/]', code)
    for item in result:
        item = item.strip()
        if len(item) >= 1:
            output.append(item)
    return output
#Evaluation method1: exact match
#the function to calculate f1 score
def f1_score(reference, test):
    correct = 0
    for chunk in reference:
        if chunk in test:
            correct += 1
    recall = correct / len(reference)
    precision = correct /len(test)
    f1 = 2 * recall * precision / (recall + precision)
    return f1
import nltk
from nltk.translate.bleu_score import sentence_bleu
#!pip install nltk
#uncomment the above to install the nltk libary for usage of bleu_score
#smoothing function got from https://www.psych.mcgill.ca/labs/mogillab/anaconda2/pkgs/nltk-3.2.2-py27_0/lib/python2.7/site-packages/nltk/translate/bleu_score.py
#Smoothing is used in case of division by zero, refer to the different smoothing methods for reference
class SmoothingFunction:
    """
    This is an implementation of the smoothing techniques
    for segment-level BLEU scores that was presented in
    Boxing Chen and Collin Cherry (2014) A Systematic Comparison of
    Smoothing Techniques for Sentence-Level BLEU. In WMT14.
    http://acl2014.org/acl2014/W14-33/pdf/W14-3346.pdf
    """
    def __init__(self, epsilon=0.1, alpha=5, k=5):
        """
        This will initialize the parameters required for the various smoothing
        techniques, the default values are set to the numbers used in the
        experiments from Chen and Cherry (2014).

        >>> hypothesis1 = ['It', 'is', 'a', 'guide', 'to', 'action', 'which', 'ensures',
        ...                 'that', 'the', 'military', 'always', 'obeys', 'the',
        ...                 'commands', 'of', 'the', 'party']
        >>> reference1 = ['It', 'is', 'a', 'guide', 'to', 'action', 'that', 'ensures',
        ...               'that', 'the', 'military', 'will', 'forever', 'heed',
        ...               'Party', 'commands']

        >>> chencherry = SmoothingFunction()
        >>> print (sentence_bleu([reference1], hypothesis1)) # doctest: +ELLIPSIS
        0.4118...
        >>> print (sentence_bleu([reference1], hypothesis1, smoothing_function=chencherry.method0)) # doctest: +ELLIPSIS
        0.4118...
        >>> print (sentence_bleu([reference1], hypothesis1, smoothing_function=chencherry.method1)) # doctest: +ELLIPSIS
        0.4118...
        >>> print (sentence_bleu([reference1], hypothesis1, smoothing_function=chencherry.method2)) # doctest: +ELLIPSIS
        0.4489...
        >>> print (sentence_bleu([reference1], hypothesis1, smoothing_function=chencherry.method3)) # doctest: +ELLIPSIS
        0.4118...
        >>> print (sentence_bleu([reference1], hypothesis1, smoothing_function=chencherry.method4)) # doctest: +ELLIPSIS
        0.4118...
        >>> print (sentence_bleu([reference1], hypothesis1, smoothing_function=chencherry.method5)) # doctest: +ELLIPSIS
        0.4905...
        >>> print (sentence_bleu([reference1], hypothesis1, smoothing_function=chencherry.method6)) # doctest: +ELLIPSIS
        0.4135...
        >>> print (sentence_bleu([reference1], hypothesis1, smoothing_function=chencherry.method7)) # doctest: +ELLIPSIS
        0.4905...

        :param epsilon: the epsilon value use in method 1
        :type epsilon: float
        :param alpha: the alpha value use in method 6
        :type alpha: int
        :param k: the k value use in method 4
        :type k: int
        """
        self.epsilon = epsilon
        self.alpha = alpha
        self.k = k

    def method0(self, p_n, *args, **kwargs):
        """ No smoothing. """
        p_n_new = []
        for i, p_i in enumerate(p_n):
            if p_i.numerator != 0:
                p_n_new.append(p_i)
            else:
                _msg = str("\nCorpus/Sentence contains 0 counts of {}-gram overlaps.\n"
                           "BLEU scores might be undesirable; "
                           "use SmoothingFunction().").format(i+1)
                warnings.warn(_msg)
                # If this order of n-gram returns 0 counts, the higher order
                # n-gram would also return 0, thus breaking the loop here.
                break
        return p_n_new

    def method1(self, p_n, *args, **kwargs):
        """
        Smoothing method 1: Add *epsilon* counts to precision with 0 counts.
        """
        return [(p_i.numerator + self.epsilon)/ p_i.denominator
                if p_i.numerator == 0 else p_i for p_i in p_n]

    def method2(self, p_n, *args, **kwargs):
        """
        Smoothing method 2: Add 1 to both numerator and denominator from
        Chin-Yew Lin and Franz Josef Och (2004) Automatic evaluation of
        machine translation quality using longest common subsequence and
        skip-bigram statistics. In ACL04.
        """
        return [Fraction(p_i.numerator + 1, p_i.denominator + 1, _normalize=False) for p_i in p_n]

    def method3(self, p_n, *args, **kwargs):
        """
        Smoothing method 3: NIST geometric sequence smoothing
        The smoothing is computed by taking 1 / ( 2^k ), instead of 0, for each
        precision score whose matching n-gram count is null.
        k is 1 for the first 'n' value for which the n-gram match count is null/
        For example, if the text contains:
         - one 2-gram match
         - and (consequently) two 1-gram matches
        the n-gram count for each individual precision score would be:
         - n=1  =>  prec_count = 2     (two unigrams)
         - n=2  =>  prec_count = 1     (one bigram)
         - n=3  =>  prec_count = 1/2   (no trigram,  taking 'smoothed' value of 1 / ( 2^k ), with k=1)
         - n=4  =>  prec_count = 1/4   (no fourgram, taking 'smoothed' value of 1 / ( 2^k ), with k=2)
        """
        incvnt = 1 # From the mteval-v13a.pl, it's referred to as k.
        for i, p_i in enumerate(p_n):
            if p_i.numerator == 0:
                p_n[i] = 1 / (2**incvnt * p_i.denominator)
                incvnt+=1
        return p_n

    def method4(self, p_n, references, hypothesis, hyp_len):
        """
        Smoothing method 4:
        Shorter translations may have inflated precision values due to having
        smaller denominators; therefore, we give them proportionally
        smaller smoothed counts. Instead of scaling to 1/(2^k), Chen and Cherry
        suggests dividing by 1/ln(len(T)), where T is the length of the translation.
        """
        for i, p_i in enumerate(p_n):
            if p_i.numerator == 0 and hyp_len != 0:
                incvnt = i+1 * self.k / math.log(hyp_len) # Note that this K is different from the K from NIST.
                p_n[i] = 1 / incvnt
        return p_n


    def method5(self, p_n, references, hypothesis, hyp_len):
        """
        Smoothing method 5:
        The matched counts for similar values of n should be similar. To a
        calculate the n-gram matched count, it averages the nâˆ’1, n and n+1 gram
        matched counts.
        """
        m = {}
        # Requires an precision value for an addition ngram order.
        p_n_plus1 = p_n + [modified_precision(references, hypothesis, 5)]
        m[-1] = p_n[0] + 1
        for i, p_i in enumerate(p_n):
            p_n[i] = (m[i-1] + p_i + p_n_plus1[i+1]) / 3
            m[i] = p_n[i]
        return p_n

    def method6(self, p_n, references, hypothesis, hyp_len):
        """
        Smoothing method 6:
        Interpolates the maximum likelihood estimate of the precision *p_n* with
        a prior estimate *pi0*. The prior is estimated by assuming that the ratio
        between pn and pnâˆ’1 will be the same as that between pnâˆ’1 and pnâˆ’2; from
        Gao and He (2013) Training MRF-Based Phrase Translation Models using
        Gradient Ascent. In NAACL.
        """
        # This smoothing only works when p_1 and p_2 is non-zero.
        # Raise an error with an appropriate message when the input is too short
        # to use this smoothing technique.
        assert p_n[2], "This smoothing method requires non-zero precision for bigrams."
        for i, p_i in enumerate(p_n):
            if i in [0,1]: # Skips the first 2 orders of ngrams.
                continue
            else:
                pi0 = 0 if p_n[i-2] == 0 else p_n[i-1]**2 / p_n[i-2]
                # No. of ngrams in translation that matches the reference.
                m = p_i.numerator
                # No. of ngrams in translation.
                l = sum(1 for _ in ngrams(hypothesis, i+1))
                # Calculates the interpolated precision.
                p_n[i] = (m + self.alpha * pi0) / (l + self.alpha)
        return p_n

    def method7(self, p_n, references, hypothesis, hyp_len):
        """
        Smoothing method 6:
        Interpolates the maximum likelihood estimate of the precision *p_n* with
        a prior estimate *pi0*. The prior is estimated by assuming that the ratio
        between pn and pnâˆ’1 will be the same as that between pnâˆ’1 and pnâˆ’2.
        """
        p_n = self.method4(p_n, references, hypothesis, hyp_len)
        p_n = self.method5(p_n, references, hypothesis, hyp_len)
        return p_n
def f1_evaluation(gold_scripts, scripts):
    #Takes the gold scripts and generated scripts to print F1 scores
    scores = 0
    for i in range(len(gold_scripts)):
        if scripts[i][0] == '':
            continue
        chunks_test = []
        for step in scripts[i]:
            chunks_test.extend(groovy_parser(step))
        chunks_gold = []
        for step in gold_scripts[i]:
            chunks_gold.extend(groovy_parser(step))
        scores += f1_score(chunks_gold, chunks_test)
    print(scores/ (len(gold_scripts)))
    
def BLEU_evaluation(gold_scripts, scripts):
    #Takes the gold scripts and generated scripts to print BLEU 1-4 scores
    chencherry = SmoothingFunction()
    weights_1gram = (1, 0, 0, 0)
    weights_2gram = (1/2, 1/2, 0, 0)
    weights_3gram = (1/3, 1/3, 1/3, 0)
    weights_4gram = (1/4, 1/4, 1/4, 1/4)
    BLEU_1 = 0
    BLEU_2 = 0
    BLEU_3 = 0
    BLEU_4 = 0
    for i in range(len(gold_scripts)):
        chunks_test = []
        for step in scripts[i]:
            chunks_test.extend(groovy_parser(step))
        chunks_gold = []
        for step in gold_scripts[i]:
             chunks_gold.extend(groovy_parser(step))
        BLEU_1 += sentence_bleu([chunks_gold], chunks_test, smoothing_function=chencherry.method1, weights=weights_1gram)
        BLEU_2 += sentence_bleu([chunks_gold], chunks_test, smoothing_function=chencherry.method1, weights=weights_2gram)
        BLEU_3 += sentence_bleu([chunks_gold], chunks_test, smoothing_function=chencherry.method1, weights=weights_3gram)
        BLEU_4 += sentence_bleu([chunks_gold], chunks_test, smoothing_function=chencherry.method1, weights=weights_4gram)                      
    print("1 gram BLEU Score is: {}".format(BLEU_1/ len(gold_scripts)))
    print("2 gram BLEU Score is: {}".format(BLEU_2/ len(gold_scripts)))
    print("3 gram BLEU Score is: {}".format(BLEU_3/ len(gold_scripts)))
    print("4 gram BLEU Score is: {}".format(BLEU_4/ len(gold_scripts)))
#A demo of how to use the bleu score
#1. need to parse the sample, and gold codes into chunks
#2. pass through the sentence_bleu along with smoothing and weights
#3. calculate the average score by the sum divide by sample numbers
# chencherry = SmoothingFunction()
# weights = (1./4, 1./4, 1./4, 1./4)
#scores = 0
#for i in range(len(GOLD_cases)):
    #SCRIPT5 is empty so it is ignored here
#    if i != 5:
#        chunks_test = []
#        for step in scripts[i]:
#            chunks_test.extend(groovy_parser(step))
#        chunks_gold = []
#        for step in GOLD_cases[i]:
#            chunks_gold.extend(groovy_parser(step))
    #print(sentence_bleu([chunks_gold], chunks_test, smoothing_function=chencherry.method1, weights=weights))
#    scores += sentence_bleu([chunks_gold], chunks_test, smoothing_function=chencherry.method1, weights=weights)
#print(scores/ (len(GOLD_cases) - 1))