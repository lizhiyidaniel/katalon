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

@Field String validNameUnique = "Ontest222 TC2 " + DynamicGenerator.getInstance().getDynamicVariableByClassName(getClass())

@SetUp
def setup() {
	def testAccessibleSwitch = findTestObject('Object Repository/Page_TestOverview/switch_TestAccessible')
	def confirmToggleAccessibleSwitch = findTestObject('Object Repository/Page_TestOverview/btn_Ok_Toggle_Accessible')
	def testsNavItem = findTestObject('Object Repository/Module_Navigation/nav_item_tests')
	
	WebUI.comment('There is at least one ongoing test with Invite-only type, Test Access is ON')
	WebUI.callTestCase(findTestCase('Test Cases/Common Test Cases/Test/Create An Ongoing Test'), ['testName': validNameUnique])
	
	WebUI.comment('Back to the onogoing test list')
	WebUI.click(testsNavItem, FailureHandling.STOP_ON_FAILURE)
}

@TearDown
def teardown() {
	WebUI.comment('Delete tested test.')
	WebUI.callTestCase(findTestCase('Test Cases/Common Test Cases/Test/Tear Down Test'), ['testName': validNameUnique])
}

def testNameSpan = findTestObject('Object Repository/Page_SearchTests/span_Ongoing_TestName', ['testName': validNameUnique])
def testItemByName = findTestObject('Object Repository/Page_SearchTests/item_Ongoing_Test_By_Name', ['testName': validNameUnique])
def testOverviewContainer = findTestObject('Object Repository/Page_TestOverview/container_Test_Overview')
def testAccessibleLabel = findTestObject('Object Repository/Page_TestOverview/label_Accessible')
def pencilIconOfTestName = findTestObject('Object Repository/Page_TestOverview/icon_Pencil_By_FieldName', ['fieldName': 'name'])
def pencilIconOfTestDuration = findTestObject('Object Repository/Page_TestOverview/icon_Pencil_By_FieldName', ['fieldName': 'duration'])
def pencilIconOfTestType = findTestObject('Object Repository/Page_TestOverview/icon_Pencil_By_FieldName', ['fieldName': 'type'])
def pencilIconOfTestLink = findTestObject('Object Repository/Page_TestOverview/icon_Pencil_By_FieldName', ['fieldName': 'testLink'])
def pencilIconOfTestPracticeLink = findTestObject('Object Repository/Page_TestOverview/icon_Pencil_By_FieldName', ['fieldName': 'practiceLink'])
def pencilIconOfTestTags = findTestObject('Object Repository/Page_TestOverview/icon_Pencil_By_FieldName', ['fieldName': 'tags'])
def pencilIconOfTestDescription = findTestObject('Object Repository/Page_TestOverview/icon_Pencil_By_FieldName', ['fieldName': 'description'])
def pencilIconOfTestInstructions = findTestObject('Object Repository/Page_TestOverview/icon_Pencil_By_FieldName', ['fieldName': 'instructions'])
def toggleTakeScreenshot = findTestObject('Object Repository/Page_TestOverview/switch_TakeScreenshot')
def takeScreenshotConfirmModal = findTestObject('Object Repository/Page_TestOverview/modal_Confirm_TakeScreenshot')

WebUI.comment('Step 1: Click an ongoing test on the Tests listing page ')
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

WebUI.comment('Step 2: Verify System redirects to Test details, Overview tab')
WebUI.waitForElementVisible(testOverviewContainer, 3, FailureHandling.CONTINUE_ON_FAILURE)

WebUI.comment('Step 3: Verify Test Access: ON')
if (true) {
	WebUI.scrollToElement(testAccessibleLabel, 3, FailureHandling.CONTINUE_ON_FAILURE)
	def accessible = WebUI.getText(testAccessibleLabel, FailureHandling.STOP_ON_FAILURE)
	WebUI.verifyMatch(accessible, "On", false)
}

WebUI.comment('Step 4: Verify non-editable fields.')
if (true) {
	WebUI.comment('Test Name')
	if (true) {
		WebUI.verifyElementNotPresent(pencilIconOfTestName, 3, FailureHandling.CONTINUE_ON_FAILURE)
	}
	
	WebUI.comment('Test Duration')
	if (true) {
		WebUI.verifyElementNotPresent(pencilIconOfTestDuration, 3, FailureHandling.CONTINUE_ON_FAILURE)
	}
	
	WebUI.comment('Test Type')
	if (true) {
		WebUI.verifyElementNotPresent(pencilIconOfTestType, 3, FailureHandling.CONTINUE_ON_FAILURE)
	}
	
	WebUI.comment('Test Link')
	if (true) {
		WebUI.verifyElementNotPresent(pencilIconOfTestLink, 3, FailureHandling.CONTINUE_ON_FAILURE)
	}
	
	WebUI.comment('Practice Test Link')
	if(true) {
		WebUI.verifyElementNotPresent(pencilIconOfTestPracticeLink, 3, FailureHandling.CONTINUE_ON_FAILURE)
	}
	
	WebUI.comment('Tags')
	if(true) {
		WebUI.verifyElementNotPresent(pencilIconOfTestTags, 3, FailureHandling.CONTINUE_ON_FAILURE)
	}
	
	WebUI.comment('Test Description')
	if (true) {
		WebUI.verifyElementNotPresent(pencilIconOfTestDescription, 3, FailureHandling.CONTINUE_ON_FAILURE)
	}
	
	WebUI.comment('Test Instruction')
	if (true) {
		WebUI.verifyElementNotPresent(pencilIconOfTestInstructions, 3, FailureHandling.CONTINUE_ON_FAILURE)
	}
	
	WebUI.comment('Settings')
	if (true) {
		WebUI.scrollToElement(toggleTakeScreenshot, 3, FailureHandling.CONTINUE_ON_FAILURE)
		WebUI.click(toggleTakeScreenshot, FailureHandling.CONTINUE_ON_FAILURE)
		WebUI.verifyElementNotPresent(takeScreenshotConfirmModal, 3, FailureHandling.CONTINUE_ON_FAILURE)
	}
}
