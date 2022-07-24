import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testng.keyword.TestNGBuiltinKeywords as TestNGKW
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys

import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import org.openqa.selenium.By
import org.openqa.selenium.Keys
import org.openqa.selenium.WebDriver

import com.kms.katalon.core.annotation.SetUp
import com.kms.katalon.core.annotation.TearDown
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

WebUI.comment('[ON] As a Recruiter, I want to create new question to add to a test.')

@SetUp()
def setUp( ) {
	'Login'
	WebUI.callTestCase(findTestCase('Common Test Cases/Login/Login'), null)
	
	WebUI.comment('When I click Test on the menu,')
	WebUI.click(findTestObject('Object Repository/Module_Navigation/nav_item_tests'))
	
	'Create Test for test'
	WebDriver driver = DriverFactory.getWebDriver()
	def empty = driver.findElements(By.xpath(emptyXpath)).size()
	
	if(empty != 0){
		WebUI.click(findTestObject('Page_AddTest/EmptyPage/btn_CreateTest-Empty'))
	} else {
		WebUI.click(findTestObject('Object Repository/Page_AddTest/btn_CreateTest'))
	}
	
	'Click questions in the left sidebar'
	WebUI.click(findTestObject('Page_TestQuestions/btn_TestQuestion'))
}

@TearDown()
def tearDown() {
	'Delete the test Test'
	def id = WebUI.getAttribute(findTestObject('Object Repository/Page_AddTest/txt_TestName'), 'data-id')
	WebUI.callTestCase(findTestCase('Test Cases/Common Test Cases/Test/Tear Down Test'), ['id': id])
}

'Click Add New Question button'
WebUI.click(findTestObject('Page_TestQuestions/btn_AddNewQuestion'))

'Verify if the Add New Question dialog is presented'
WebUI.verifyElementPresent(findTestObject('Page_TestQuestions/dialog_Dialog'), 10)

'Verify Question Type field: Default value: Multiple Choice'
WebUI.verifyOptionSelectedByValue(findTestObject('Object Repository/Page_TestQuestions/select_QuestionType'), 'Multiple Choice', false, 20)

'Enter invalid value'
if (true) {
	WebUI.click(findTestObject('Object Repository/Page_TestQuestions/wrapper_Ckeditor'))
	WebUI.sendKeys(findTestObject('Object Repository/Page_TestQuestions/ckedior_ProblemStatement'), ProblemStatement)
	
	WebUI.click(findTestObject('Object Repository/Page_TestQuestions/label_MultipleChoice',[('CHOOSE_TYPE'):ChooseType]))
	def answers = Answers
	
	List answerTextbox = WebUI.findWebElements(findTestObject('Object Repository/Page_TestQuestions/tbx_Answer'),30)
	
	int numberOfAnswers = answerTextbox.size()
	
	if (numberOfAnswers > 0) {
		for (int i = 0; i < numberOfAnswers; i++) {
			answerTextbox.get(i).sendKeys(answers[i])
		}
	}
	
	WebUI.setText(findTestObject('Object Repository/Page_TestQuestions/tbx_MaxTime'), MaxTime)
	WebUI.selectOptionByValue(findTestObject('Object Repository/Page_TestQuestions/select_Difficulty'), Difficulty, false)
	
	WebUI.setText(findTestObject('Object Repository/Page_TestQuestions/tbx_AwardedScore'), AwardedScore)
	WebUI.setText(findTestObject('Object Repository/Page_TestQuestions/tbx_SubtractedScore'), SubtractedScore)
	
	for(tag in Tags) {
		WebUI.sendKeys(findTestObject('Object Repository/Page_TestQuestions/tbx_TagsInput'), tag)
		WebUI.sendKeys(findTestObject('Object Repository/Page_TestQuestions/tbx_TagsInput'), Keys.chord(Keys.ENTER))
	}
}

'Click Add Question'
WebUI.click(findTestObject('Object Repository/Page_TestQuestions/btn_SubmitButton'))

'Verify if error message is displayed'
if (true) {
	def message;
	'Verify message error in problem statement field'
	WebUI.waitForElementPresent(findTestObject('Object Repository/Page_TestQuestions/txt_MCQErrorMessage', ['order': 1]), 10, FailureHandling.CONTINUE_ON_FAILURE)
	message = WebUI.getText(findTestObject('Object Repository/Page_TestQuestions/txt_MCQErrorMessage', ['order': 1]), FailureHandling.CONTINUE_ON_FAILURE)
	WebUI.verifyMatch(message, statementError, false)
	
	'Verify message error in choice field'
	WebUI.waitForElementPresent(findTestObject('Object Repository/Page_TestQuestions/txt_MCQErrorMessage', ['order': 2]), 10, FailureHandling.CONTINUE_ON_FAILURE)
	message = WebUI.getText(findTestObject('Object Repository/Page_TestQuestions/txt_MCQErrorMessage', ['order': 2]), FailureHandling.CONTINUE_ON_FAILURE)
	WebUI.verifyMatch(message, choiceError, false)
	
	'Verify message error in answer field'
	WebUI.waitForElementPresent(findTestObject('Object Repository/Page_TestQuestions/txt_MCQErrorMessage', ['order': 3]), 10, FailureHandling.CONTINUE_ON_FAILURE)
	message = WebUI.getText(findTestObject('Object Repository/Page_TestQuestions/txt_MCQErrorMessage', ['order': 3]), FailureHandling.CONTINUE_ON_FAILURE)
	WebUI.verifyMatch(message, answerError, false)
	
	'Verify message error in awarded score field'
	WebUI.waitForElementPresent(findTestObject('Object Repository/Page_TestQuestions/txt_MCQErrorMessage', ['order': 4]), 10, FailureHandling.CONTINUE_ON_FAILURE)
	message = WebUI.getText(findTestObject('Object Repository/Page_TestQuestions/txt_MCQErrorMessage', ['order': 4]), FailureHandling.CONTINUE_ON_FAILURE)
	WebUI.verifyMatch(message, awardedScoreError, false)
	
	'Verify message error in subtracted score field'
	WebUI.waitForElementPresent(findTestObject('Object Repository/Page_TestQuestions/txt_MCQErrorMessage', ['order': 5]), 10, FailureHandling.CONTINUE_ON_FAILURE)
	message = WebUI.getText(findTestObject('Object Repository/Page_TestQuestions/txt_MCQErrorMessage', ['order': 5]), FailureHandling.CONTINUE_ON_FAILURE)
	WebUI.verifyMatch(message, subtractedScoreError, false)
}

'Click cancel'
WebUI.click(findTestObject('Object Repository/Page_TestQuestions/btn_CancelButton'))