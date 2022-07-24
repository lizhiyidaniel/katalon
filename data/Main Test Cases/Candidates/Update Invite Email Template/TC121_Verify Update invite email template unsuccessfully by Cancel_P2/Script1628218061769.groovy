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

import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import com.kms.katalon.core.webui.common.WebUiCommonHelper
import com.kms.katalon.core.annotation.SetUp
import com.kms.katalon.core.annotation.TearDown
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.webui.common.WebUiCommonHelper
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import generator.DynamicGenerator
import groovy.transform.Field

@Field String validNameUnique = "Ontest284 TC1 " + DynamicGenerator.getInstance().getDynamicVariableByClassName(getClass())

@SetUp
def setup() {
	def testsNavItem = findTestObject('Object Repository/Module_Navigation/nav_item_tests')
	def testNameSpan = findTestObject('Object Repository/Page_SearchTests/span_Ongoing_TestName', ['testName': validNameUnique])
	def testItemByName = findTestObject('Object Repository/Page_SearchTests/item_Ongoing_Test_By_Name', ['testName': validNameUnique])
	
	WebUI.comment('There is at least one ongoing test')
	WebUI.callTestCase(findTestCase('Test Cases/Common Test Cases/Test/Create An Ongoing Test'), ['testName': validNameUnique])
	
	WebUI.comment('Back to the onogoing test list')
	WebUI.click(testsNavItem, FailureHandling.STOP_ON_FAILURE)
	
	WebUI.comment('Step 1: Click an ongoing test on the Tests listing page.')
	if (true) {
		WebUI.scrollToElement(testNameSpan, 3, FailureHandling.CONTINUE_ON_FAILURE)
		try {
			WebUI.click(testItemByName, FailureHandling.STOP_ON_FAILURE)
		}
		catch (Exception e) {
			WebDriver driver = DriverFactory.getWebDriver()
			WebElement element = WebUiCommonHelper.findWebElement(testItemByName, 5)
			JavascriptExecutor executor = ((driver) as JavascriptExecutor)
			executor.executeScript('arguments[0].click()', element)
		}
	}
}
@TearDown
def teardown() {
	WebUI.comment('Remove the ongoing tested test')
	WebUI.callTestCase(findTestCase('Test Cases/Common Test Cases/Test/Tear Down Test'), ['testName': validNameUnique])
}
def inviteCandidatesButton = findTestObject('Object Repository/Page_TestOverview/btn_Invite_Candidates')
def breadcrumb = findTestObject('Object Repository/Module_Navigation/breadcrumb')
def btn_ReviewEmailTemplate = findTestObject('Object Repository/Page_InviteCandidates/Review Invite Email Template/btn_ReviewEmailTemplate')
def inputSubject = findTestObject('Object Repository/Page_InviteCandidates/Review Invite Email Template/input_Subjective')
def inputEmailbody = findTestObject('Object Repository/Page_InviteCandidates/Review Invite Email Template/input_EmailBody')
def btnSave = findTestObject('Object Repository/Page_InviteCandidates/Review Invite Email Template/btn_SaveEmailTemplate')
def btnCancel = findTestObject('Object Repository/Page_InviteCandidates/Review Invite Email Template/btn_DiscardChanges')
def successToast = findTestObject('Object Repository/Module_Navigation/toast_Success')

WebUI.comment('Step 1: Click the Invite Candidates button')
WebUI.scrollToElement(inviteCandidatesButton, 3)
WebUI.click(inviteCandidatesButton, FailureHandling.STOP_ON_FAILURE) 

WebUI.comment('Step 2: Click Review subject and body')
WebUI.waitForElementVisible(btn_ReviewEmailTemplate,2)
WebUI.scrollToElement(btn_ReviewEmailTemplate, 5)
WebUI.delay(1)
WebUI.click(btn_ReviewEmailTemplate, FailureHandling.STOP_ON_FAILURE)
WebUI.delay(2)
WebUI.waitForElementVisible(inputSubject,1)
WebUI.scrollToElement(inputSubject, 3)
def currentSubject = WebUI.getText(inputSubject)
def currentEmailBody = WebUI.getText(inputEmailbody)

WebUI.comment('Step 3: Set the value beyond the limit of the allowed number of characters')
WebUI.scrollToElement(inputSubject, 1)
WebUI.setText(inputSubject, subjectOverLimitTest, FailureHandling.STOP_ON_FAILURE)
WebUI.scrollToElement(inputEmailbody, 1)
WebUI.setText(inputEmailbody, subjectOverLimitTest, FailureHandling.STOP_ON_FAILURE)

WebUI.comment('Step 4: Save changes')
WebUI.scrollToElement(btnSave, 1)
WebUI.click(btnSave, FailureHandling.CONTINUE_ON_FAILURE)

WebUI.comment('Step 5: Verify error message when the value beyond the limit of the allowed number of characters')

def errorMessageSubjective = findTestObject('Object Repository/Page_InviteCandidates/Review Invite Email Template/errorMessage_Subjective')
def errorMessageEmailBody = findTestObject('Object Repository/Page_InviteCandidates/Review Invite Email Template/errorMessage_EmailBody')
def givenErrorSubjective = WebUI.getText(errorMessageSubjective)
def givenErrorMessageEmailBody = WebUI.getText(errorMessageEmailBody)

WebUI.verifyMatch(givenErrorSubjective, inValidSubjectiveOverLimit, false)
WebUI.verifyMatch(givenErrorMessageEmailBody, inValidEmailBodyOverLimit, false)

WebUI.comment('Step 6: Delete value')
WebUI.scrollToElement(inputSubject, 2)
WebUI.setText(inputSubject, subjectEmptyTest, FailureHandling.STOP_ON_FAILURE)

WebUI.scrollToElement(btnSave, 3)
WebUI.sendKeys(inputEmailbody, Keys.chord(Keys.CONTROL, 'a'))
WebUI.sendKeys(inputEmailbody, Keys.chord(Keys.DELETE))

WebUI.comment('Step 7: Save changes')
WebUI.scrollToElement(btnSave, 1)
WebUI.click(btnSave, FailureHandling.CONTINUE_ON_FAILURE)

WebUI.comment('Step 8: Verify error message: This field is required.')
givenErrorSubjective = WebUI.getText(errorMessageSubjective)
givenErrorMessageEmailBody = WebUI.getText(errorMessageEmailBody)

WebUI.verifyMatch(givenErrorSubjective, inValidSubjective, false)
WebUI.verifyMatch(givenErrorMessageEmailBody, inValidEmailBody, false)

WebUI.comment('Step 9: Discard changes')
WebUI.scrollToElement(btnCancel, 3)
WebUI.click(btnCancel)

def givenCurrentSubject = WebUI.getText(inputSubject)
def givenEmailBody = WebUI.getText(inputEmailbody)

WebUI.verifyMatch(givenCurrentSubject, currentSubject, false)
WebUI.verifyMatch(givenEmailBody, currentEmailBody, false)

