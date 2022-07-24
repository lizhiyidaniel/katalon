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

'[ON] As a Recruiter, I want to delete questions in a draft test'

@SetUp()
def setUp() {
	'Login'
	WebUI.callTestCase(findTestCase('Common Test Cases/Login/Login'), null)
	
	WebUI.comment('When I click Test on the menu,')
	WebUI.click(findTestObject('Object Repository/Module_Navigation/nav_item_tests'))
	
	'Create test for testing'
	WebUI.callTestCase(findTestCase('Common Test Cases/Test/Add Test'), null)
	
	'Add a MCQ question'
	WebUI.callTestCase(findTestCase('Common Test Cases/Test/Add MCQ Question'), null)
	
	'Dismiss the toast'
	WebUI.click(findTestObject('Object Repository/Page_TestQuestions/toast_Successfully'))
	
	'Add one more MCQ question'
	WebUI.callTestCase(findTestCase('Common Test Cases/Test/Add MCQ Question'), null)
	
	'Dismiss the toast'
	WebUI.click(findTestObject('Object Repository/Page_TestQuestions/toast_Successfully'))
}

@TearDown()
def tearDown() {
	'Delete the test Test'
	def id = WebUI.getAttribute(findTestObject('Object Repository/Page_AddTest/txt_TestName'), 'data-id')
	WebUI.callTestCase(findTestCase('Test Cases/Common Test Cases/Test/Tear Down Test'), ['id': id])
}

'Click the Multiple Choice checkbox'
WebUI.click(findTestObject('Object Repository/Page_TestQuestions/cbx_QuestionType', ['order': 1]))

'Click delete questions'
WebUI.click(findTestObject('Object Repository/Page_TestQuestions/btn_DeleteQuestion'))

'Verify if the confirm dialog is appearing'
WebUI.verifyElementPresent(findTestObject('Page_TestQuestions/dialog_Dialog'), 10)

'Verify if the message of the confirm dialog is displayed correctly'
if (true) {
	def message = WebUI.getText(findTestObject('Object Repository/Page_TestQuestions/txt_ConfirmMessage'))
	WebUI.verifyMatch(message, confirmMessage, false)
}


'Click cancel button'
WebUI.click(findTestObject('Object Repository/Page_TestQuestions/btn_ConfirmDialog_Cancel'))

'Verify if the dialog is already closed and the questions are not deleted'
if (true) {
	WebUI.verifyElementNotPresent(findTestObject('Page_TestQuestions/dialog_Dialog'), 10)
	WebUI.verifyElementPresent(findTestObject('Object Repository/Page_TestQuestions/cbx_QuestionType', ['order' : 1]), 10)
}

