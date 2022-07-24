import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import org.openqa.selenium.Keys

import com.kms.katalon.core.annotation.SetUp
import com.kms.katalon.core.annotation.TearDown
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

@SetUp()
def setUp() {
	def testNavItem = findTestObject('Object Repository/Module_Navigation/nav_item_tests')
	
	WebUI.comment('User must be logged in to the system successfully ')
	WebUI.callTestCase(findTestCase('Test Cases/Common Test Cases/Login/Login'), null)
	
	WebUI.comment('There is at least one draft test')
	if (true) {
		WebUI.click(testNavItem)
		WebUI.callTestCase(findTestCase('Test Cases/Common Test Cases/Test/Add Test'), null)
	}
}

@TearDown()
def tearDown() {
	def OptionDropdownButton = findTestObject('Object Repository/Page_TestOverview/btn_OptionDropdown')
	def ConfirmButtonDeleteTest = findTestObject('Object Repository/Page_TestOverview/btn_Confirm_Delete_Test')
	
	WebUI.comment('Delete the draft test which has just been used to test.')
	if (true) {
		WebUI.scrollToElement(OptionDropdownButton, 3)
		WebUI.waitForElementVisible(OptionDropdownButton, 3)
		WebUI.click(OptionDropdownButton)
		WebUI.waitForElementVisible(findTestObject('Object Repository/Page_TestOverview/btn_Option', ['optionName': 'Delete test']), 3)
		WebUI.click(findTestObject('Object Repository/Page_TestOverview/btn_Option', ['optionName': 'Delete test']))
		WebUI.waitForElementVisible(ConfirmButtonDeleteTest, 3)
		WebUI.click(ConfirmButtonDeleteTest)
		WebUI.delay(1)
	}
}

WebUI.comment('Click on the fields and update invalid values')
if (true) {
	WebUI.comment('Edit Test Name')
	if (true) {
		WebUI.scrollToElement(findTestObject('Object Repository/Page_TestOverview/span_ShowingTextOrNumberField', ['fieldName': 'name']), 3, , FailureHandling.CONTINUE_ON_FAILURE)
		WebUI.click(findTestObject('Object Repository/Page_TestOverview/span_ShowingTextOrNumberField', ['fieldName': 'name']), FailureHandling.CONTINUE_ON_FAILURE)
		WebUI.setText(findTestObject('Object Repository/Page_TestOverview/input_TextOrNumberField', ['fieldName': 'name']), invalidTestName, FailureHandling.CONTINUE_ON_FAILURE)
		WebUI.click(findTestObject('Object Repository/Page_TestOverview/btn_Save', ['fieldName': 'name']), FailureHandling.CONTINUE_ON_FAILURE)
	}
	
	WebUI.comment('Edit Test Link')
	if (true) {
		WebUI.scrollToElement(findTestObject('Object Repository/Page_TestOverview/span_ShowingTextOrNumberField', ['fieldName': 'testLink']), 3, FailureHandling.CONTINUE_ON_FAILURE)
		WebUI.click(findTestObject('Object Repository/Page_TestOverview/span_ShowingTextOrNumberField', ['fieldName': 'testLink']), FailureHandling.CONTINUE_ON_FAILURE)
		WebUI.setText(findTestObject('Object Repository/Page_TestOverview/input_TextOrNumberField', ['fieldName': 'testLink']), invalidTestLink, FailureHandling.CONTINUE_ON_FAILURE)
		WebUI.click(findTestObject('Object Repository/Page_TestOverview/btn_Save', ['fieldName': 'testLink']), FailureHandling.CONTINUE_ON_FAILURE)
	}
	
	WebUI.comment('Edit Tags')
	if (true) {
		WebUI.scrollToElement(findTestObject('Object Repository/Page_TestOverview/span_ShowingTextOrNumberField', ['fieldName': 'tags']), 3, FailureHandling.CONTINUE_ON_FAILURE)
		WebUI.click(findTestObject('Object Repository/Page_TestOverview/span_ShowingTextOrNumberField', ['fieldName': 'tags']), FailureHandling.CONTINUE_ON_FAILURE)
		WebUI.setText(findTestObject('Object Repository/Page_TestOverview/input_TestTags'), invalidTestTag, FailureHandling.CONTINUE_ON_FAILURE)
		WebUI.sendKeys(findTestObject('Object Repository/Page_TestOverview/input_TestTags'), Keys.chord(Keys.ENTER), FailureHandling.CONTINUE_ON_FAILURE)
	}
	
	WebUI.comment('Edit Test Description')
	if (true) {
		WebUI.scrollToElement(findTestObject('Object Repository/Page_TestOverview/icon_EditRichText', ['fieldName': 'TEST DESCRIPTION']), 3, FailureHandling.CONTINUE_ON_FAILURE)
		WebUI.click(findTestObject('Object Repository/Page_TestOverview/icon_EditRichText', ['fieldName': 'TEST DESCRIPTION']), FailureHandling.CONTINUE_ON_FAILURE)
		WebUI.sendKeys(findTestObject('Object Repository/Page_TestOverview/input_RichText', ['fieldName': 'description']), invalidTestDescription, FailureHandling.CONTINUE_ON_FAILURE)
	}
	
	WebUI.comment('Edit Test Instruction')
	if (true) {
		WebUI.scrollToElement(findTestObject('Object Repository/Page_TestOverview/icon_EditRichText', ['fieldName': 'TEST INSTRUCTIONS']), 3, FailureHandling.CONTINUE_ON_FAILURE)
		WebUI.click(findTestObject('Object Repository/Page_TestOverview/icon_EditRichText', ['fieldName': 'TEST INSTRUCTIONS']), FailureHandling.CONTINUE_ON_FAILURE)
		WebUI.sendKeys(findTestObject('Object Repository/Page_TestOverview/input_RichText', ['fieldName': 'instructions']), invalidTestInstructions, FailureHandling.CONTINUE_ON_FAILURE)
	}
}

WebUI.comment('Verify error message is displayed')
if (true) {
	WebUI.comment('Verify Test Name')
	if(true) {
		WebUI.verifyElementVisible(findTestObject('Object Repository/Page_TestOverview/span_InvalidFeedbackMessage', ['fieldName': 'name']), FailureHandling.CONTINUE_ON_FAILURE)
		WebUI.scrollToElement(findTestObject('Object Repository/Page_TestOverview/span_InvalidFeedbackMessage', ['fieldName': 'name']), 3, FailureHandling.CONTINUE_ON_FAILURE)
		WebUI.verifyElementText(findTestObject('Object Repository/Page_TestOverview/span_InvalidFeedbackMessage', ['fieldName': 'name']), invalidTestNameMessage, FailureHandling.CONTINUE_ON_FAILURE)
	}
	
	
	WebUI.comment('Verify Test Link')
	if(true) {
		WebUI.verifyElementVisible(findTestObject('Object Repository/Page_TestOverview/span_InvalidFeedbackMessage', ['fieldName': 'testLink']), FailureHandling.CONTINUE_ON_FAILURE)
		WebUI.scrollToElement(findTestObject('Object Repository/Page_TestOverview/span_InvalidFeedbackMessage', ['fieldName': 'testLink']), 3, FailureHandling.CONTINUE_ON_FAILURE)
		WebUI.verifyElementText(findTestObject('Object Repository/Page_TestOverview/span_InvalidFeedbackMessage', ['fieldName': 'testLink']), invalidTestLinkMessage, FailureHandling.CONTINUE_ON_FAILURE)
	}
	
	WebUI.comment('Verify Tags')
	if (true) {
		WebUI.verifyElementVisible(findTestObject('Object Repository/Page_TestOverview/span_InvalidFeedbackMessage', ['fieldName': 'tags']), FailureHandling.CONTINUE_ON_FAILURE)
		WebUI.scrollToElement(findTestObject('Object Repository/Page_TestOverview/span_InvalidFeedbackMessage', ['fieldName': 'tags']), 3, FailureHandling.CONTINUE_ON_FAILURE)
		WebUI.verifyElementText(findTestObject('Object Repository/Page_TestOverview/span_InvalidFeedbackMessage', ['fieldName': 'tags']), invalidTestTagMessage, FailureHandling.CONTINUE_ON_FAILURE)
	}
	
	WebUI.comment('Verify Test Description')
	if (true) {
		WebUI.verifyElementVisible(findTestObject('Object Repository/Page_TestOverview/span_InvalidFeedbackMessage', ['fieldName': 'description']), FailureHandling.CONTINUE_ON_FAILURE)
		WebUI.scrollToElement(findTestObject('Object Repository/Page_TestOverview/span_InvalidFeedbackMessage', ['fieldName': 'description']), 3, FailureHandling.CONTINUE_ON_FAILURE)
		WebUI.verifyElementText(findTestObject('Object Repository/Page_TestOverview/span_InvalidFeedbackMessage', ['fieldName': 'description']), invalidTestDescriptionMessage, FailureHandling.CONTINUE_ON_FAILURE)
	}
	
	WebUI.comment('Verify Test Instruction')
	if (true) {
		WebUI.verifyElementVisible(findTestObject('Object Repository/Page_TestOverview/span_InvalidFeedbackMessage', ['fieldName': 'instructions']), FailureHandling.CONTINUE_ON_FAILURE)
		WebUI.scrollToElement(findTestObject('Object Repository/Page_TestOverview/span_InvalidFeedbackMessage', ['fieldName': 'instructions']), 3, FailureHandling.CONTINUE_ON_FAILURE)
		WebUI.verifyElementText(findTestObject('Object Repository/Page_TestOverview/span_InvalidFeedbackMessage', ['fieldName': 'instructions']), invalidTestInstructionsMessage, FailureHandling.CONTINUE_ON_FAILURE)
	}
}