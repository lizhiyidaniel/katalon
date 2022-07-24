import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver

import com.kms.katalon.core.annotation.SetUp
import com.kms.katalon.core.annotation.TearDown
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import org.openqa.selenium.Keys as Keys

WebUI.comment('Story: [ON] As a Recruiter, I want to delete questions from a library')

def LibName = 'Test lib' + System.currentTimeMillis()

'Set up'
if (true) {
	'Login'
	WebUI.callTestCase(findTestCase('Common Test Cases/Login/Login'), null)
	
	'When I click Libraries on the menu'
	WebUI.click(findTestObject('Object Repository/Module_Navigation/nav_item_libraries'))
	
	'Add subjective question'
	WebUI.callTestCase(findTestCase('Common Test Cases/Library/Create New Subjective'), ['libraryName': LibName])
}

WebUI.waitForPageLoad(10)
'On MCQ need to delete: Click delete icon'
WebUI.click(findTestObject("Object Repository/Page_Libraries/btn_DeleteQuestion", ['order': 1]))
'Verify confirmation popup is displayed'
WebUI.verifyElementPresent(findTestObject("Object Repository/Page_TestQuestions/dialog_Dialog"), 30)
'Click Confirm'
WebUI.click(findTestObject("Object Repository/Page_TestQuestions/btn_ConfirmDialog_Submit"))
'Verify confirmation popup is closed'
WebUI.verifyElementNotPresent(findTestObject("Object Repository/Page_TestQuestions/dialog_Dialog"), 0)

'Verify delete question successfully'
WebUI.waitForPageLoad(10)
WebUI.takeScreenshotAsCheckpoint("Delete Subjective Question")
WebUI.verifyTextPresent("Delete question successfully.", false, FailureHandling.STOP_ON_FAILURE)

'Tear down'
if (true) {
	'Delete the test libary'
	WebUI.callTestCase(findTestCase('Common Test Cases/Library/Tear Down Test Library'), ['libraryName': LibName])
}
