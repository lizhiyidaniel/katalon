import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.annotation.SetUp
import com.kms.katalon.core.annotation.TearDown
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

@SetUp()
def setUp() {
	WebUI.comment('User must be logged in to the system successfully ')
	WebUI.callTestCase(findTestCase('Test Cases/Common Test Cases/Login/Login'), null)
	
	WebUI.comment('There is at least one draft test')
	if (true) {
		WebUI.click(findTestObject('Object Repository/Module_Navigation/nav_item_tests'))
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

WebUI.comment('Click enable')
if (true) {
	WebUI.scrollToElement(findTestObject('Object Repository/Page_TestOverview/input_SwitchSetting'), 3, FailureHandling.CONTINUE_ON_FAILURE)
	WebUI.click(findTestObject('Object Repository/Page_TestOverview/input_SwitchSetting'), FailureHandling.CONTINUE_ON_FAILURE)
}


WebUI.comment('Verify confirmation message show')
if (true) {
	WebUI.verifyElementVisible(findTestObject('Object Repository/Page_TestOverview/modal_Confirm'), FailureHandling.CONTINUE_ON_FAILURE)
	WebUI.verifyElementText(findTestObject('Object Repository/Page_TestOverview/span_ConfirmMessage'), confirmMessage, FailureHandling.CONTINUE_ON_FAILURE)
}


WebUI.comment('Click Cancel')
WebUI.click(findTestObject('Object Repository/Page_TestOverview/btn_CancelConfirmModal'), FailureHandling.CONTINUE_ON_FAILURE)

WebUI.comment('Verify the setting for snapshots is disabled')
WebUI.verifyElementText(findTestObject('Object Repository/Page_TestOverview/label_StatusOfSetting'), 'Off', FailureHandling.STOP_ON_FAILURE)

WebUI.comment('Click OK')
if (true) {
	WebUI.scrollToElement(findTestObject('Object Repository/Page_TestOverview/input_SwitchSetting'), 3, FailureHandling.CONTINUE_ON_FAILURE)
	WebUI.click(findTestObject('Object Repository/Page_TestOverview/input_SwitchSetting'), FailureHandling.CONTINUE_ON_FAILURE)
	WebUI.click(findTestObject('Object Repository/Page_TestOverview/btn_Confirm'), FailureHandling.CONTINUE_ON_FAILURE)
}

WebUI.comment('Verify the setting for snapshots is enabled')
WebUI.delay(1)
WebUI.verifyElementText(findTestObject('Object Repository/Page_TestOverview/label_StatusOfSetting'), 'On', FailureHandling.STOP_ON_FAILURE)