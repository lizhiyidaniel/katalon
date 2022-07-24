import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.google.common.collect.FilteredEntryMultimap.Keys
import org.openqa.selenium.Keys as Keys
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
	}
}

def switchTestAccessibleDisable = findTestObject('Object Repository/Page_TestOverview/switch_TestAccessible_Disable')
def practiseTestNotAvailabeMessage = findTestObject('Object Repository/Page_TestOverview/msg_PracticeTestLinkNotAvaliable')

WebUI.comment('Verify fields are not edited')
if (true) {
	WebUI.comment('Text Access')
	WebUI.verifyElementVisible(switchTestAccessibleDisable, FailureHandling.CONTINUE_ON_FAILURE)
	
	WebUI.comment('Practice Test Link')
	WebUI.verifyElementVisible(practiseTestNotAvailabeMessage, FailureHandling.CONTINUE_ON_FAILURE)
}

def templateInstruction

WebUI.comment('Click on the fields and update valid values')
if (true) {
	WebUI.comment('Edit Test Name')
	if (true) {
		WebUI.scrollToElement(findTestObject('Object Repository/Page_TestOverview/span_ShowingTextOrNumberField', ['fieldName': 'name']), 3, , FailureHandling.CONTINUE_ON_FAILURE)
		WebUI.click(findTestObject('Object Repository/Page_TestOverview/span_ShowingTextOrNumberField', ['fieldName': 'name']), FailureHandling.CONTINUE_ON_FAILURE)
		WebUI.setText(findTestObject('Object Repository/Page_TestOverview/input_TextOrNumberField', ['fieldName': 'name']), testName, FailureHandling.CONTINUE_ON_FAILURE)
		WebUI.click(findTestObject('Object Repository/Page_TestOverview/btn_Save', ['fieldName': 'name']), FailureHandling.CONTINUE_ON_FAILURE)
	}
	
	WebUI.comment('Edit Test Duration')
	if (true) {
		WebUI.scrollToElement(findTestObject('Object Repository/Page_TestOverview/span_ShowingTextOrNumberField', ['fieldName': 'duration']), 3, FailureHandling.CONTINUE_ON_FAILURE)
		WebUI.click(findTestObject('Object Repository/Page_TestOverview/span_ShowingTextOrNumberField', ['fieldName': 'duration']), FailureHandling.CONTINUE_ON_FAILURE)
		WebUI.setText(findTestObject('Object Repository/Page_TestOverview/input_TextOrNumberField', ['fieldName': 'duration']), testDuration, FailureHandling.CONTINUE_ON_FAILURE)
		WebUI.click(findTestObject('Object Repository/Page_TestOverview/btn_Save', ['fieldName': 'duration']), FailureHandling.CONTINUE_ON_FAILURE)
	}
	
	WebUI.comment('Edit Test Type')
	if (true) {
		WebUI.scrollToElement(findTestObject('Object Repository/Page_TestOverview/span_ShowingTextOrNumberField', ['fieldName': 'type']), 3, FailureHandling.CONTINUE_ON_FAILURE)
		WebUI.click(findTestObject('Object Repository/Page_TestOverview/span_ShowingTextOrNumberField', ['fieldName': 'type']), FailureHandling.CONTINUE_ON_FAILURE)
		WebUI.selectOptionByValue(findTestObject('Object Repository/Page_TestOverview/input_TextOrNumberField', ['fieldName': 'type']), testType, false, FailureHandling.CONTINUE_ON_FAILURE)
		WebUI.click(findTestObject('Object Repository/Page_TestOverview/btn_Save', ['fieldName': 'type']), FailureHandling.CONTINUE_ON_FAILURE)
	}
	
	WebUI.comment('Edit Test Link')
	if (true) {
		WebUI.scrollToElement(findTestObject('Object Repository/Page_TestOverview/span_ShowingTextOrNumberField', ['fieldName': 'testLink']), 3, FailureHandling.CONTINUE_ON_FAILURE)
		WebUI.click(findTestObject('Object Repository/Page_TestOverview/span_ShowingTextOrNumberField', ['fieldName': 'testLink']), FailureHandling.CONTINUE_ON_FAILURE)
		WebUI.setText(findTestObject('Object Repository/Page_TestOverview/input_TextOrNumberField', ['fieldName': 'testLink']), testLink, FailureHandling.CONTINUE_ON_FAILURE)
		WebUI.click(findTestObject('Object Repository/Page_TestOverview/btn_Save', ['fieldName': 'testLink']), FailureHandling.CONTINUE_ON_FAILURE)
	}
	
	WebUI.comment('Edit Tags')
	if (true) {
		WebUI.scrollToElement(findTestObject('Object Repository/Page_TestOverview/span_ShowingTextOrNumberField', ['fieldName': 'tags']), 3, FailureHandling.CONTINUE_ON_FAILURE)
		WebUI.click(findTestObject('Object Repository/Page_TestOverview/span_ShowingTextOrNumberField', ['fieldName': 'tags']), FailureHandling.CONTINUE_ON_FAILURE)
		WebUI.setText(findTestObject('Object Repository/Page_TestOverview/input_TestTags'), testTag, FailureHandling.CONTINUE_ON_FAILURE)
		WebUI.sendKeys(findTestObject('Object Repository/Page_TestOverview/input_TestTags'), Keys.chord(Keys.ENTER), FailureHandling.CONTINUE_ON_FAILURE)
		WebUI.click(findTestObject('Object Repository/Page_TestOverview/btn_Save', ['fieldName': 'tags']), FailureHandling.CONTINUE_ON_FAILURE)
	}
	
	WebUI.comment('Edit Test Description')
	if (true) {
		WebUI.scrollToElement(findTestObject('Object Repository/Page_TestOverview/icon_EditRichText', ['fieldName': 'TEST DESCRIPTION']), 3, FailureHandling.CONTINUE_ON_FAILURE)
		WebUI.click(findTestObject('Object Repository/Page_TestOverview/icon_EditRichText', ['fieldName': 'TEST DESCRIPTION']), FailureHandling.CONTINUE_ON_FAILURE)
		WebUI.sendKeys(findTestObject('Object Repository/Page_TestOverview/input_RichText', ['fieldName': 'description']), testDescription, FailureHandling.CONTINUE_ON_FAILURE)
		WebUI.click(findTestObject('Object Repository/Page_TestOverview/btn_Save', ['fieldName': 'description']), FailureHandling.CONTINUE_ON_FAILURE)
	}
	
	WebUI.comment('Edit Test Instruction')
	if (true) {
		WebUI.scrollToElement(findTestObject('Object Repository/Page_TestOverview/icon_EditRichText', ['fieldName': 'TEST INSTRUCTIONS']), 3, FailureHandling.CONTINUE_ON_FAILURE)
		WebUI.click(findTestObject('Object Repository/Page_TestOverview/icon_EditRichText', ['fieldName': 'TEST INSTRUCTIONS']), FailureHandling.CONTINUE_ON_FAILURE)
		templateInstruction = WebUI.getText(findTestObject('Object Repository/Page_TestOverview/input_RichText', ['fieldName': 'instructions']), FailureHandling.STOP_ON_FAILURE)
		WebUI.sendKeys(findTestObject('Object Repository/Page_TestOverview/input_RichText', ['fieldName': 'instructions']), testInstructions, FailureHandling.CONTINUE_ON_FAILURE)
		WebUI.click(findTestObject('Object Repository/Page_TestOverview/btn_Save', ['fieldName': 'instructions']), FailureHandling.CONTINUE_ON_FAILURE)
	}
}

WebUI.comment('Verify the fields has been updated with the corresponding values.')
if (true) {
	WebUI.comment('Verify Test Name')
	if(true) {
		WebUI.scrollToElement(findTestObject('Object Repository/Page_TestOverview/span_ShowingTextOrNumberField', ['fieldName': 'name']), 3, FailureHandling.CONTINUE_ON_FAILURE)
		WebUI.verifyElementText(findTestObject('Object Repository/Page_TestOverview/span_ShowingTextOrNumberField', ['fieldName': 'name']), testName, FailureHandling.CONTINUE_ON_FAILURE)
	}
	
	WebUI.comment('Verify Test Duration')
	if(true) {
		WebUI.scrollToElement(findTestObject('Object Repository/Page_TestOverview/span_ShowingTextOrNumberField', ['fieldName': 'duration']), 3, FailureHandling.CONTINUE_ON_FAILURE)
		WebUI.verifyElementText(findTestObject('Object Repository/Page_TestOverview/span_ShowingTextOrNumberField', ['fieldName': 'duration']), testDuration + " min(s)", FailureHandling.CONTINUE_ON_FAILURE)
	}
	
	WebUI.comment('Verify Test Type')
	if(true) {
		WebUI.scrollToElement(findTestObject('Object Repository/Page_TestOverview/span_ShowingTextOrNumberField', ['fieldName': 'type']), 3, FailureHandling.CONTINUE_ON_FAILURE)
		WebUI.verifyElementText(findTestObject('Object Repository/Page_TestOverview/span_ShowingTextOrNumberField', ['fieldName': 'type']), testType, FailureHandling.CONTINUE_ON_FAILURE)
	}
	
	WebUI.comment('Verify Test Link')
	if(true) {
		WebUI.scrollToElement(findTestObject('Object Repository/Page_TestOverview/span_ShowingTextOrNumberField', ['fieldName': 'testLink']), 3, FailureHandling.CONTINUE_ON_FAILURE)
		WebUI.verifyElementText(findTestObject('Object Repository/Page_TestOverview/span_ShowingTextOrNumberField', ['fieldName': 'testLink']), testLink, FailureHandling.CONTINUE_ON_FAILURE)
	}
	
	WebUI.comment('Verify Tags')
	if (true) {
		WebUI.scrollToElement(findTestObject('Object Repository/Page_TestOverview/span_TheFirstTagInTagsField'), 3, FailureHandling.CONTINUE_ON_FAILURE)
		WebUI.verifyElementText(findTestObject('Object Repository/Page_TestOverview/span_TheFirstTagInTagsField'), testTag, FailureHandling.CONTINUE_ON_FAILURE)
	}
	
	WebUI.comment('Verify Test Description')
	if (true) {
		WebUI.scrollToElement(findTestObject('Object Repository/Page_TestOverview/span_ShowingTestDescription'), 3, FailureHandling.CONTINUE_ON_FAILURE)
		WebUI.verifyElementText(findTestObject('Object Repository/Page_TestOverview/span_ShowingTestDescription'), testDescription, FailureHandling.CONTINUE_ON_FAILURE)
	}
	
	WebUI.comment('Verify Test Instruction')
	def matchInstruction = templateInstruction + testInstructions
	if (true) {
		WebUI.scrollToElement(findTestObject('Object Repository/Page_TestOverview/span_ShowingTestInstructions'), 3, FailureHandling.CONTINUE_ON_FAILURE)
		WebUI.verifyElementText(findTestObject('Object Repository/Page_TestOverview/span_ShowingTestInstructions'), templateInstruction + testInstructions, FailureHandling.CONTINUE_ON_FAILURE)
	}
}