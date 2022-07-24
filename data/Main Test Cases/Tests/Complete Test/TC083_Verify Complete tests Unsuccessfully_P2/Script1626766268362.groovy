import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import org.openqa.selenium.Keys as Keys
import com.kms.katalon.core.annotation.SetUp as SetUp
import com.kms.katalon.core.annotation.TearDown as TearDown
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

@SetUp
def setUp() {
	WebUI.comment('User must be logged in to the system successfully ')
	WebUI.callTestCase(findTestCase('Test Cases/Common Test Cases/Login/Login'), null)
	
	WebUI.comment('There is at least one ongoing test')
	WebUI.waitForElementPresent(findTestObject('Object Repository/Module_Navigation/nav_item_tests'), 30)
	WebUI.click(findTestObject('Object Repository/Module_Navigation/nav_item_tests'))
	
	WebUI.callTestCase(findTestCase('Test Cases/Common Test Cases/Test/Add Test'), null)
	WebUI.waitForElementPresent(findTestObject('Page_TestOverview/toast_Successfully'), 10)
	WebUI.click(findTestObject('Page_TestOverview/toast_Successfully'))
	
	WebUI.waitForElementPresent(findTestObject('Object Repository/Page_TestOverview/btn_PublishTest'), 30)
	WebUI.click(findTestObject('Object Repository/Page_TestOverview/btn_PublishTest'))
	
	WebUI.waitForElementPresent(findTestObject('Object Repository/Page_TestOverview/btn_Confirm'), 30)
	WebUI.click(findTestObject('Object Repository/Page_TestOverview/btn_Confirm'))
}

@TearDown
def tearDown() {
	WebUI.comment('Delete the completed test.')	
	WebUI.waitForElementPresent(findTestObject('Object Repository/Page_TestOverview/txt_TestName'), 30)
	testName = WebUI.getText(findTestObject('Object Repository/Page_TestOverview/txt_TestName'))
	
	WebUI.callTestCase(findTestCase('Test Cases/Common Test Cases/Test/Tear Down Test'), ['testName': testName])
}

WebUI.comment('Click complete test')
WebUI.waitForElementPresent(findTestObject('Object Repository/Page_TestOverview/btn_OptionDropdown'), 10)
WebUI.click(findTestObject('Object Repository/Page_TestOverview/btn_OptionDropdown'))
WebUI.click(findTestObject('Object Repository/Page_TestOverview/btn_Option', [('optionName') : 'Complete test']))

WebUI.comment('Verify System shows a confirmation message')
WebUI.waitForElementPresent(findTestObject('Object Repository/Page_TestOverview/modal_Confirm'), 10)

WebUI.comment('Click Cancel')
WebUI.click(findTestObject('Object Repository/Page_TestOverview/btn_CancelConfirmModal'))


WebUI.comment('Verify')

WebUI.comment('Popup is closed and do nothing')
WebUI.waitForElementNotPresent(findTestObject('Object Repository/Page_TestOverview/modal_Confirm'), 10)

