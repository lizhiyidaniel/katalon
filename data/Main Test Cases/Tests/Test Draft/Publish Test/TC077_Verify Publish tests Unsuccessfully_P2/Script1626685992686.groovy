import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.annotation.SetUp
import com.kms.katalon.core.annotation.TearDown
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import generator.DynamicGenerator
import groovy.transform.Field

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
}

@TearDown()
def tearDown() {
	WebUI.comment('Delete the draft test which has just been used to test.')
	if (true) {
		WebUI.scrollToElement(findTestObject('Object Repository/Page_TestOverview/btn_OptionDropdown'), 3)
		WebUI.waitForElementVisible(findTestObject('Object Repository/Page_TestOverview/btn_OptionDropdown'), 3)
		WebUI.click(findTestObject('Object Repository/Page_TestOverview/btn_OptionDropdown'))
		WebUI.waitForElementVisible(findTestObject('Object Repository/Page_TestOverview/btn_Option', ['optionName': 'Delete test']), 3)
		WebUI.click(findTestObject('Object Repository/Page_TestOverview/btn_Option', ['optionName': 'Delete test']))
		WebUI.waitForElementVisible(findTestObject('Object Repository/Page_TestOverview/btn_Confirm'), 3)
		WebUI.click(findTestObject('Object Repository/Page_TestOverview/btn_Confirm'))
	}
}

WebUI.comment('Step 1: Click Publish test')
WebUI.scrollToElement(findTestObject('Object Repository/Page_TestOverview/btn_Publish'), 3, FailureHandling.CONTINUE_ON_FAILURE)
WebUI.click(findTestObject('Object Repository/Page_TestOverview/btn_Publish'), FailureHandling.STOP_ON_FAILURE)


WebUI.comment('Step 2: Verify System shows a confirmation message')
WebUI.waitForElementVisible(findTestObject('Object Repository/Page_TestOverview/modal_Confirm_Publish'), 3, FailureHandling.CONTINUE_ON_FAILURE)

WebUI.comment('Step 3: Click Cancel')
WebUI.click(findTestObject('Object Repository/Page_TestOverview/btn_Cancel_Publish'), FailureHandling.STOP_ON_FAILURE)

WebUI.comment('Step 4: Verify: Popup is closed and do nothing')
WebUI.waitForElementNotPresent(findTestObject('Object Repository/Page_TestOverview/modal_Confirm_Publish'), 5, FailureHandling.CONTINUE_ON_FAILURE)