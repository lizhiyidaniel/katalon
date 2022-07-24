import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement

import com.kms.katalon.core.annotation.SetUp
import com.kms.katalon.core.annotation.TearDown
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.webui.common.WebUiCommonHelper
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import generator.DynamicGenerator
import groovy.transform.Field

@Field String validNameUnique = "Ontest283 TC2 " + DynamicGenerator.getInstance().getDynamicVariableByClassName(getClass())
@Field String validEmail = DynamicGenerator.getInstance().getDynamicVariableByClassName(getClass()) + "@gmail.com"
@Field String validName = "Hoa Pham"
@Field String validEmailAndFullName = validEmail + " " + validName

@SetUp
def setup() {
	def testsNavItem = findTestObject('Object Repository/Module_Navigation/nav_item_tests')
	def testNameSpan = findTestObject('Object Repository/Page_SearchTests/span_Ongoing_TestName', ['testName': validNameUnique])
	def testItemByName = findTestObject('Object Repository/Page_SearchTests/item_Ongoing_Test_By_Name', ['testName': validNameUnique])
	def inviteCandidatesButton = findTestObject('Object Repository/Page_TestOverview/btn_Invite_Candidates')
	
	WebUI.comment('There is at least one ongoing test')
	WebUI.callTestCase(findTestCase('Test Cases/Common Test Cases/Test/Create An Ongoing Test'), ['testName': validNameUnique])
	
	WebUI.comment('Back to the onogoing test list')
	WebUI.click(testsNavItem, FailureHandling.STOP_ON_FAILURE)
	
	WebUI.comment('Click an ongoing test on the Tests listing page.')
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
	
	WebUI.comment('Click the Invite Candidates button')
	WebUI.scrollToElement(inviteCandidatesButton, 3)
	WebUI.click(inviteCandidatesButton)
}

@TearDown
def teardown() {
	WebUI.comment('Remove all candidates')
	WebUI.callTestCase(findTestCase('Test Cases/Common Test Cases/Invite List/Delete All Candidates From Invite List'), null)
	
	WebUI.comment('Remove the ongoing tested test')
	WebUI.callTestCase(findTestCase('Test Cases/Common Test Cases/Test/Tear Down Test'), ['testName': validNameUnique])
}

def addCandidatesButton = findTestObject('Object Repository/Page_InviteCandidates/btn_AddCandidates')
def addCandidatesModal = findTestObject('Object Repository/Page_InviteCandidates/modal_AddCandidates')
def inputAddCandidates = findTestObject('Object Repository/Page_InviteCandidates/Add Candidates Modal/input_AddCandidates')
def addButton = findTestObject('Object Repository/Page_InviteCandidates/Add Candidates Modal/btn_Add')
def successToast = findTestObject('Object Repository/Module_Navigation/toast_Success')
def newRowData = findTestObject('Object Repository/Page_InviteCandidates/row_CandidateInfo', ["lineNumber": "1"])
def email = findTestObject('Object Repository/Page_InviteCandidates/td_EmailByLineNumber', ["lineNumber": "1"])
def name = findTestObject('Object Repository/Page_InviteCandidates/td_NameByLineNumber',  ["lineNumber": "1"])
def checkbox = findTestObject('Object Repository/Page_InviteCandidates/checkbox_InviteListCheckBoxByLineNumber', ["lineNumber": "1"])

WebUI.comment('Step 1: On the Invite Candidates page: Click Add button')
WebUI.click(addCandidatesButton)

WebUI.comment('Step 2: Verify Add Candidate popup is displayed')
WebUI.verifyElementVisible(addCandidatesModal)

WebUI.comment('Step 3: Enter valid candidate’s details (email and full name)')
WebUI.setText(inputAddCandidates, validEmailAndFullName)

WebUI.comment('Step 4: Click Add')
WebUI.click(addButton)

WebUI.comment('Step 5: Verify Close popup, show the successful message, Create candidate tests with status “New”, Add candidates in Invite list in Invite Candidates page (ticked by default).')
if (true) {
	WebUI.comment('show the successful message')
	def givenMessage = WebUI.getText(successToast)
	WebUI.verifyMatch(givenMessage, expectedSuccessMessage, false)
	
	WebUI.comment('Verify Close popup')
	WebUI.verifyElementNotPresent(addCandidatesModal, 5)
	
	WebUI.comment('Create candidate tests with status “New”, Add candidates in Invite list in Invite Candidates page (ticked by default)')
	WebUI.verifyElementVisible(newRowData)
	def givenEmail = WebUI.getText(email)
	def givenName = WebUI.getText(name)
	def isChecked = WebUI.getAttribute(checkbox, "aria-checked")
	WebUI.verifyMatch(givenEmail, validEmail, false)
	WebUI.verifyMatch(givenName, validName, false)
	WebUI.verifyMatch(isChecked, "true", false)
}




