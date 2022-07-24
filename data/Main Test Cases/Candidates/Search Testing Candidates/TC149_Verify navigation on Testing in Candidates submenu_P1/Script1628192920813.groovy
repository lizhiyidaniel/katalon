import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.annotation.SetUp
import com.kms.katalon.core.annotation.TearDown
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

WebUI.comment('Click Testing on the submenu')
WebUI.click(findTestObject('Object Repository/Page_TestCandidates/btn_TestingCandidates'), FailureHandling.STOP_ON_FAILURE)

WebUI.comment('Verify System navigates to Testing Candidate Tests listing page')
WebUI.verifyElementPresent(findTestObject('Object Repository/Page_TestCandidates/txt_CandidateStatus'), 5)

WebUI.comment('Verify title name, Menu, List of fields')
WebUI.verifyElementPresent(findTestObject('Object Repository/Page_TestCandidates/table_Candidates'), 5)

@SetUp
def setup() {
    WebUI.comment('User must be logged in to the system successfully')
    WebUI.callTestCase(findTestCase('Common Test Cases/Login/Login'), [:], FailureHandling.STOP_ON_FAILURE)

    WebUI.comment('There is at least one ongoing test')
	
	'Create test'
	WebUI.waitForElementPresent(findTestObject('Object Repository/Module_Navigation/nav_item_tests'), 5)
	WebUI.click(findTestObject('Object Repository/Module_Navigation/nav_item_tests'))
    WebUI.callTestCase(findTestCase('Common Test Cases/Test/Add Test'), [:], FailureHandling.STOP_ON_FAILURE)
	
	'Publish test'
    WebUI.callTestCase(findTestCase('Common Test Cases/Test/Publish Test'), [:], FailureHandling.STOP_ON_FAILURE)
	
	test_name = WebUI.getText(findTestObject('Object Repository/Page_TestOverview/txt_TestName'), FailureHandling.STOP_ON_FAILURE)
}

@TearDown
def teardown() {
    WebUI.comment('Remove the ongoing tested test')
    WebUI.callTestCase(findTestCase('Test Cases/Common Test Cases/Test/Tear Down Test'), [('testName') : test_name])
}

