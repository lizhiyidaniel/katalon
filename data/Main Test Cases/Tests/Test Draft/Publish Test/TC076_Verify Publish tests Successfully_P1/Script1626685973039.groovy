import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.WebDriver

import com.kms.katalon.core.annotation.SetUp
import com.kms.katalon.core.annotation.TearDown
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import generator.DynamicGenerator
import groovy.transform.Field

@Field String testName = DynamicGenerator.getInstance().getDynamicVariableByClassName(getClass())

@SetUp()
def setUp() {
	WebUI.comment('User must be logged in to the system successfully ')
	WebUI.callTestCase(findTestCase('Test Cases/Common Test Cases/Login/Login'), null)
	
	WebUI.comment('There is at least one draft test')
	if (true) {
		WebUI.click(findTestObject('Object Repository/Module_Navigation/nav_item_tests'))
		WebUI.callTestCase(findTestCase('Test Cases/Common Test Cases/Test/Add Test'), null)
	}
	
	WebUI.delay(5)
	
	// Change the name of this test
	if (true) {
		WebUI.scrollToElement(findTestObject('Object Repository/Page_TestOverview/span_ShowingTextOrNumberField', ['fieldName': 'name']), 3, , FailureHandling.CONTINUE_ON_FAILURE)
		WebUI.click(findTestObject('Object Repository/Page_TestOverview/span_ShowingTextOrNumberField', ['fieldName': 'name']), FailureHandling.CONTINUE_ON_FAILURE)
		WebUI.setText(findTestObject('Object Repository/Page_TestOverview/input_TextOrNumberField', ['fieldName': 'name']), testName, FailureHandling.CONTINUE_ON_FAILURE)
		WebUI.click(findTestObject('Object Repository/Page_TestOverview/btn_Save', ['fieldName': 'name']), FailureHandling.CONTINUE_ON_FAILURE)
	}
}

@TearDown()
def tearDown() {
	WebUI.callTestCase(findTestCase('Test Cases/Common Test Cases/Test/Tear Down Test'), ['testName': testName])
}

WebUI.comment('Step 1: Click Publish test')
WebUI.scrollToElement(findTestObject('Object Repository/Page_TestOverview/btn_Publish'), 3, FailureHandling.CONTINUE_ON_FAILURE)
WebUI.click(findTestObject('Object Repository/Page_TestOverview/btn_Publish'), FailureHandling.STOP_ON_FAILURE)

WebUI.comment('Step 2: Verify System shows a confirmation message')
WebUI.waitForElementVisible(findTestObject('Object Repository/Page_TestOverview/modal_Confirm_Publish'), 3, FailureHandling.CONTINUE_ON_FAILURE)

WebUI.comment('Step 3: Click Confirm')
WebUI.click(findTestObject('Object Repository/Page_TestOverview/btn_Ok_Publish'), FailureHandling.STOP_ON_FAILURE)

WebUI.comment('Step 4: Verify: ')
if (true) {
	WebUI.comment('Popup is closed, Show successful message and reload the current page.')
	if (true) {
		WebUI.comment('Popup is closed')
		WebUI.waitForElementNotPresent(findTestObject('Object Repository/Page_TestOverview/modal_Confirm_Publish'), 5, FailureHandling.CONTINUE_ON_FAILURE)
		
		WebUI.comment('Show successful message')
		def message = WebUI.getText(findTestObject('Object Repository/Module_Navigation/toast_Success'), FailureHandling.CONTINUE_ON_FAILURE)
		WebUI.verifyMatch(message, successMessage, false)
		WebUI.verifyElementVisible(findTestObject('Object Repository/Module_Navigation/toast_Success'), FailureHandling.CONTINUE_ON_FAILURE)
	}
	
	WebUI.comment('Change test status to “Ongoing"')
	if (true) {
		WebUI.delay(1)
		def status = WebUI.getText(findTestObject('Object Repository/Page_TestOverview/span_Test_Status'), FailureHandling.CONTINUE_ON_FAILURE)
		WebUI.verifyMatch(status, 'Ongoing', false)
	}
	
	WebUI.comment('Create a Practice.')
	WebUI.comment('WAITING THE PRACTICE TEST TICKET.')
	
	WebDriver driver = DriverFactory.getWebDriver();
	JavascriptExecutor executor = (JavascriptExecutor)driver;
	
	
	WebUI.comment('Add Email Templates (Invite, Reminder, Cancelled Invite, Thank You) to a test.')
	if (true) {
		WebUI.comment('Open Email Templates')
		WebUI.waitForElementVisible(findTestObject('Object Repository/Page_TestOverview/btn_Email_Templates'), 3, FailureHandling.STOP_ON_FAILURE)
		WebUI.click(findTestObject('Object Repository/Page_TestOverview/btn_Email_Templates'), FailureHandling.STOP_ON_FAILURE)
		
		WebUI.comment('Click to tab Invite Email')
		WebUI.waitForElementVisible(findTestObject('Object Repository/Page_EmailTemplates/tab_Invite_Email'), 3, FailureHandling.STOP_ON_FAILURE)
		WebUI.click(findTestObject('Object Repository/Page_EmailTemplates/tab_Invite_Email'), FailureHandling.STOP_ON_FAILURE)
		
		WebUI.comment('Check Invite Subjective')
		def inviteSubjective = WebUI.getAttribute(findTestObject('Object Repository/Page_EmailTemplates/content_Subjective_By_Type', ["emailType": "invite"]), "value", FailureHandling.CONTINUE_ON_FAILURE)
		WebUI.verifyMatch(inviteSubjective, validInviteSubjective, false)
		
		WebUI.comment('Check Invite Email Body')
		def inviteEmailBody = executor.executeScript("return document.evaluate('//*[@id=\"invite-email-email-body\"]//*[@aria-label=\"Rich Text Editor, main\"]', document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue.outerHTML");
		WebUI.verifyMatch(inviteEmailBody, validInviteEmailBody, false)
		
		WebUI.comment('Click to tab Reminder Email')
		WebUI.waitForElementVisible(findTestObject('Object Repository/Page_EmailTemplates/tab_Reminder_Email'), 3, FailureHandling.STOP_ON_FAILURE)
		WebUI.click(findTestObject('Object Repository/Page_EmailTemplates/tab_Reminder_Email'), FailureHandling.STOP_ON_FAILURE)
		
		WebUI.comment('Check Reminder Subjective')
		def reminderSubjective = WebUI.getAttribute(findTestObject('Object Repository/Page_EmailTemplates/content_Subjective_By_Type', ["emailType": "reminder"]), "value", FailureHandling.CONTINUE_ON_FAILURE)
		WebUI.verifyMatch(reminderSubjective, validReminderSubjective, false)
		
		WebUI.comment('Check Reminder Email Body')
		def reminderEmailBody = executor.executeScript("return document.evaluate('//*[@id=\"reminder-email-email-body\"]//*[@aria-label=\"Rich Text Editor, main\"]', document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue.outerHTML");
		WebUI.verifyMatch(reminderEmailBody, validReminderEmailBody, false)
		
		WebUI.comment('Click to tab Cancelled Email')
		WebUI.waitForElementVisible(findTestObject('Object Repository/Page_EmailTemplates/tab_Cancelled_Email'), 3, FailureHandling.STOP_ON_FAILURE)
		WebUI.click(findTestObject('Object Repository/Page_EmailTemplates/tab_Cancelled_Email'), FailureHandling.STOP_ON_FAILURE)
		
		WebUI.comment('Check Cancelled Subjective')
		def cancelledSubjective = WebUI.getAttribute(findTestObject('Object Repository/Page_EmailTemplates/content_Subjective_By_Type', ["emailType": "cancelled"]), "value", FailureHandling.CONTINUE_ON_FAILURE)
		WebUI.verifyMatch(cancelledSubjective, validCancelledSubjective, false)
		
		WebUI.comment('Check Cancelled Email Body')
		def cancelledEmailBody = executor.executeScript("return document.evaluate('//*[@id=\"cancelled-email-email-body\"]//*[@aria-label=\"Rich Text Editor, main\"]', document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue.outerHTML");
		WebUI.verifyMatch(cancelledEmailBody, validCancelledEmailBody, false)
		
		WebUI.comment('Click to tab Thank You Email')
		WebUI.waitForElementVisible(findTestObject('Object Repository/Page_EmailTemplates/tab_Thanks_Email'), 3, FailureHandling.STOP_ON_FAILURE)
		WebUI.click(findTestObject('Object Repository/Page_EmailTemplates/tab_Thanks_Email'), FailureHandling.STOP_ON_FAILURE)
		
		WebUI.comment('Check Thank You Subjective')
		def thanksSubjective = WebUI.getAttribute(findTestObject('Object Repository/Page_EmailTemplates/content_Subjective_By_Type', ["emailType": "thank"]), "value", FailureHandling.CONTINUE_ON_FAILURE)
		WebUI.verifyMatch(thanksSubjective, validThanksSubjective, false)
		
		WebUI.comment('Check Thank You Email Body')
		def thanksEmailBody = executor.executeScript("return document.evaluate('//*[@id=\"thank-email-email-body\"]//*[@aria-label=\"Rich Text Editor, main\"]', document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue.outerHTML");
		WebUI.verifyMatch(thanksEmailBody, validThanksEmailBody, false)
	}
}