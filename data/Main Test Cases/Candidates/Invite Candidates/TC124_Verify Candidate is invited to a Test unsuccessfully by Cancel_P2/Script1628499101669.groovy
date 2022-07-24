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

@Field String validNameUnique = "Ontest273 TC1 " + DynamicGenerator.getInstance().getDynamicVariableByClassName(getClass())
@Field String validEmail = "katalontestingontest@gmail.com"
@Field String validName = "Hoa Pham"
@Field String validEmailAndFullName = validEmail + " " + validName

@SetUp
def setup() {
	def testsNavItem = findTestObject('Object Repository/Module_Navigation/nav_item_tests')
	def testNameSpan = findTestObject('Object Repository/Page_SearchTests/span_Ongoing_TestName', ['testName': validNameUnique])
	def testItemByName = findTestObject('Object Repository/Page_SearchTests/item_Ongoing_Test_By_Name', ['testName': validNameUnique])
	def inviteCandidatesButton = findTestObject('Object Repository/Page_TestOverview/btn_Invite_Candidates')
	def addCandidatesButton = findTestObject('Object Repository/Page_InviteCandidates/btn_AddCandidates')
	def addCandidatesModal = findTestObject('Object Repository/Page_InviteCandidates/modal_AddCandidates')
	def inputAddCandidates = findTestObject('Object Repository/Page_InviteCandidates/Add Candidates Modal/input_AddCandidates')
	def addButton = findTestObject('Object Repository/Page_InviteCandidates/Add Candidates Modal/btn_Add')
	
	WebUI.comment('There is at least one candidate added to the invite list')
	if (true) {
		// Create an ongoing test
		WebUI.callTestCase(findTestCase('Test Cases/Common Test Cases/Test/Create An Ongoing Test'), ['testName': validNameUnique])
		
		// Add an candidate to invite list
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
		
		WebUI.click(inviteCandidatesButton)
		
		WebUI.click(addCandidatesButton)
		WebUI.verifyElementVisible(addCandidatesModal)
		WebUI.setText(inputAddCandidates, validEmailAndFullName)
		WebUI.click(addButton)
	}
}

@TearDown()
def teardown() {
	WebUI.comment('Remove the ongoing tested test')
	if (true) {
		WebUI.callTestCase(findTestCase('Test Cases/Common Test Cases/Test/Tear Down Test'), ['testName': validNameUnique])
		WebUI.delay(3)
	}
}

def inviteButton = findTestObject('Object Repository/Page_InviteCandidates/btn_Invite')
def confirmInviteModal = findTestObject('Object Repository/Page_InviteCandidates/modal_ConfirmInvite')
def cancelButton = findTestObject('Object Repository/Page_InviteCandidates/Confirm Invite Modal/btn_Cancel')

WebUI.comment('Step 1: Click the Invite button ')
if (true) {
	WebUI.scrollToElement(inviteButton, 3)
	WebUI.click(inviteButton)
}

WebUI.comment('Step 2:  Verify the popup confirmation is displayed')
WebUI.waitForElementVisible(confirmInviteModal, 3)

WebUI.comment('Step 3: Click Cancel')
WebUI.click(cancelButton)

WebUI.comment('Step 4: Close popup and do nothing')
WebUI.waitForElementNotVisible(confirmInviteModal, 3)





