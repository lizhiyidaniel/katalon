import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.annotation.SetUp
import com.kms.katalon.core.annotation.TearDown
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

WebUI.comment('Click Testing on the submenu')
WebUI.click(findTestObject('Page_TestCandidates/btn_TestingCandidates'), FailureHandling.STOP_ON_FAILURE)

WebUI.comment('On Testing Candidate Tests listing page: Search tests by candidate’s full name or email (search by KEYWORD)')
WebUI.setText(findTestObject('Page_TestCandidates/input_SearchCandidate'), query)

WebUI.comment('Click search')
WebUI.click(findTestObject('Page_TestCandidates/btn_SearchCandidate'))

WebUI.comment('Verify Display the number of results found. Ex: 1 result(s) found.')
//WebUI.waitForElementPresent(findTestObject('Object Repository/Module_Navigation/nav_item_tests'), 5)
WebUI.verifyElementPresent(findTestObject('Page_TestCandidates/txt_SearchResult'), 5)

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

