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

def questionType
for(i=2; i< 5;i++) {
	switch(i) {
		case 2:
			questionType = "Subjective"
			break;
		case 3:
			questionType = "Programming"
			break;
		case 4:
			questionType = "File Upload"
			break;
	}
	
	'Click Add New Question button'
	WebUI.click(findTestObject('Page_TestQuestions/btn_AddNewQuestion'))
	
	'Verify if the Add New Question dialog is presented'
	WebUI.verifyElementPresent(findTestObject('Page_TestQuestions/dialog_Dialog'), 10)
	
	'Verify Question Type field'
	WebUI.selectOptionByValue(findTestObject('Object Repository/Page_TestQuestions/select_QuestionType'), questionType, false)
	WebUI.verifyOptionSelectedByValue(findTestObject('Object Repository/Page_TestQuestions/select_QuestionType'), questionType, false, 20)
	
	'Enter invalid value'
	if (true) {
		WebUI.click(findTestObject('Object Repository/Page_TestQuestions/wrapper_Ckeditor'))
		WebUI.sendKeys(findTestObject('Object Repository/Page_TestQuestions/ckedior_ProblemStatement'), ProblemStatement)
		
		WebUI.sendKeys(findTestObject('Object Repository/Page_TestQuestions/ckedior_ProblemStatement'), Keys.chord(Keys.TAB))
		
		WebUI.sendKeys(findTestObject('Object Repository/Page_TestQuestions/ckedior_ProblemStatement'), SuggestedAnswer)
		
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

		'Verify message error in suggested answer field'
		WebUI.waitForElementPresent(findTestObject('Object Repository/Page_TestQuestions/txt_MCQErrorMessage', ['order': 2]), 10, FailureHandling.CONTINUE_ON_FAILURE)
		message = WebUI.getText(findTestObject('Object Repository/Page_TestQuestions/txt_MCQErrorMessage', ['order': 2]), FailureHandling.CONTINUE_ON_FAILURE)
		WebUI.verifyMatch(message, suggestedAnswerError, false)
		
		'Verify message error in awarded score field'
		WebUI.waitForElementPresent(findTestObject('Object Repository/Page_TestQuestions/txt_MCQErrorMessage', ['order': 3]), 10, FailureHandling.CONTINUE_ON_FAILURE)
		message = WebUI.getText(findTestObject('Object Repository/Page_TestQuestions/txt_MCQErrorMessage', ['order': 3]), FailureHandling.CONTINUE_ON_FAILURE)
		WebUI.verifyMatch(message, awardedScoreError, false)
		
		'Verify message error in subtracted score field'
		WebUI.waitForElementPresent(findTestObject('Object Repository/Page_TestQuestions/txt_MCQErrorMessage', ['order': 4]), 10, FailureHandling.CONTINUE_ON_FAILURE)
		message = WebUI.getText(findTestObject('Object Repository/Page_TestQuestions/txt_MCQErrorMessage', ['order': 4]), FailureHandling.CONTINUE_ON_FAILURE)
		WebUI.verifyMatch(message, subtractedScoreError, false)
	}
	
	'Click cancel'
	WebUI.click(findTestObject('Object Repository/Page_TestQuestions/btn_CancelButton'))
}