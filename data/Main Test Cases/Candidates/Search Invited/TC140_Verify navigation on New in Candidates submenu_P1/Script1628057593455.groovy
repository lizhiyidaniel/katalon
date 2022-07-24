import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import org.openqa.selenium.By
import org.openqa.selenium.Keys
import org.openqa.selenium.WebDriver
import internal.GlobalVariable as GlobalVariable
import com.kms.katalon.core.annotation.SetUp
import com.kms.katalon.core.annotation.TearDown
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

@SetUp
def setUp() {
	'Login'
	WebUI.callTestCase(findTestCase('Common Test Cases/Login/Login'), null)
	
	'Click test in navigation sidebar'
	WebUI.click(findTestObject('Object Repository/Module_Navigation/nav_item_tests'))
	
	'Create Test for test'
	WebDriver driver = DriverFactory.getWebDriver()
	def empty = driver.findElements(By.xpath("(//*[contains(text(),'You have no tests yet.')])")).size()
	
	if(empty != 0){
		WebUI.click(findTestObject('Page_AddTest/EmptyPage/btn_CreateTest-Empty'))
	} else {
		WebUI.click(findTestObject('Object Repository/Page_AddTest/btn_CreateTest'))
	}
	
	'Publish test'
	WebUI.callTestCase(findTestCase('Test Cases/Common Test Cases/Test/Publish Test'), null)
}

@TearDown
def tearDown() {
	'Back to test overview'
	WebUI.click(findTestObject('Object Repository/Page_Candidates/nav_TestName'))
	
	'Delete test'
	def id = WebUI.getAttribute(findTestObject('Object Repository/Page_TestOverview/txt_TestName'), 'data-id')
	WebUI.callTestCase(findTestCase('Test Cases/Common Test Cases/Test/Delete Test By ID'), [id: id])
}

'Click "New"'
WebUI.click(findTestObject('Object Repository/Page_Candidates/btn-NewCandidate'))

'Verify title'
if (true) {
	def title = WebUI.getText(findTestObject('Object Repository/Page_InviteCandidates/title_HeadingTitle'))
	WebUI.verifyMatch(title, 'Invite Candidates', false)
}