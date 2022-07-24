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

@Field String validNameUnique = "Ontest283 TC1 " + DynamicGenerator.getInstance().getDynamicVariableByClassName(getClass())

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
def heading = findTestObject('Object Repository/Page_InviteCandidates/title_HeadingTitle')
def breadcrumb = findTestObject('Object Repository/Module_Navigation/breadcrumb')

WebUI.comment('Step 1: Click the Invite Candidates button')
WebUI.scrollToElement(inviteCandidatesButton, 3)
WebUI.click(inviteCandidatesButton)

WebUI.comment('Step 2: System navigates to Invite Candidates page, Verify Name page, menu')
if (true) {
	def givenHeadingText = WebUI.getText(heading)
	WebUI.verifyMatch(givenHeadingText, expectedHeading, false)
	
	WebDriver driver = DriverFactory.getWebDriver();
	JavascriptExecutor executor = (JavascriptExecutor)driver;
	
	def breadcrumbPart1 = executor.executeScript("return document.evaluate('//*[@aria-label=\"breadcrumb\"]//li[2]', document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue.innerText")
	def breadcrumbPart2 = executor.executeScript("return document.evaluate('//*[@aria-label=\"breadcrumb\"]//li[3]', document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue.innerText")
	def breadcrumbPart3 = executor.executeScript("return document.evaluate('//*[@aria-label=\"breadcrumb\"]//li[4]', document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue.innerText")
	
	WebUI.verifyMatch(breadcrumbPart1, "Tests", false)
	WebUI.verifyMatch(breadcrumbPart2, validNameUnique, false)
	WebUI.verifyMatch(breadcrumbPart3, "Invite Candidates", false)
}


WebUI.comment('Step 3: Take Screenshot As Checkpoint')
WebUI.takeScreenshot()
