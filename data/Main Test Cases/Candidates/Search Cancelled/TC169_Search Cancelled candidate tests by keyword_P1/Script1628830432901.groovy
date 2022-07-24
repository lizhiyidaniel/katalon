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

import com.database.Database as DB
import groovy.transform.Field

@Field def candidateName1 = 'Example1'
@Field def candidateEmail1 = 'example1' + System.currentTimeMillis() + '@mail.com'

@Field def candidateName2 = 'Example2'
@Field def candidateEmail2 = 'example2' + System.currentTimeMillis() + '@mail.com'

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
	
	'Invite 2 candidates'
	WebUI.callTestCase(findTestCase('Test Cases/Common Test Cases/Test/Invite Candidate'), [name: candidateName1, mail: candidateEmail1 ])
	
	WebUI.scrollToElement(findTestObject('Object Repository/Page_Candidates/nav_TestName'), 10)
	WebUI.click(findTestObject('Object Repository/Page_Candidates/nav_TestName'))
	
	WebUI.callTestCase(findTestCase('Test Cases/Common Test Cases/Test/Invite Candidate'), [name: candidateName2, mail: candidateEmail2 ])
	
	'Back to test overview'
	WebUI.scrollToElement(findTestObject('Object Repository/Page_Candidates/nav_TestName'), 10)
	WebUI.click(findTestObject('Object Repository/Page_Candidates/nav_TestName'))
	
	'Make 2 candidates cancelled candidates'
	WebUI.callTestCase(findTestCase('Test Cases/Common Test Cases/Candidates/Make Candidate Cancelled'), [email: candidateEmail1])
	WebUI.callTestCase(findTestCase('Test Cases/Common Test Cases/Candidates/Make Candidate Cancelled'), [email: candidateEmail2])
	
	'Click Cancelled'
	WebUI.scrollToElement(findTestObject('Object Repository/Page_Candidates/btn_CancelledCandidate'), 10)
	WebUI.click(findTestObject('Object Repository/Page_Candidates/btn_CancelledCandidate'))
}

@TearDown
def tearDown() {
	def id = WebUI.getAttribute(findTestObject('Object Repository/Page_TestOverview/txt_TestName'), 'data-id')
	WebUI.callTestCase(findTestCase('Test Cases/Common Test Cases/Test/Delete Test By ID'), [id: id])
}

'Enter candidate fullname as keyword'
WebUI.setText(findTestObject('Object Repository/Page_Candidates/tbx_SearchCandidate'), candidateName1)

'Click search'
WebUI.click(findTestObject('Object Repository/Page_Candidates/btn_SearchButton'))

'Verify if there is only one result'
if (true) {
	'Verify the number of row, header included'
	def numOfRow = WebUI.findWebElements(findTestObject('Object Repository/Page_Candidates/tr_TableRow'), 5).size()
	WebUI.verifyMatch(numOfRow.toString(), '2', false)
	
	'Verify the informative text is displayed correctly'
	def information = WebUI.getText(findTestObject('Object Repository/Page_Candidates/txt_InformativeText'))
	WebUI.verifyMatch(information, '1 result(s) found', false)
}
