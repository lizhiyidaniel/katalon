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

@SetUp()
def setUp( ) {
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
	
	'Click questions in the left sidebar'
	WebUI.click(findTestObject('Page_TestQuestions/btn_TestQuestion'))
	
	'Add one subjective questions'
	WebUI.callTestCase(findTestCase('Test Cases/Common Test Cases/Test/Create Question'), null)
	
	'Add one more subjective questions'
	WebUI.callTestCase(findTestCase('Test Cases/Common Test Cases/Test/Create Question'), null)
	
	'Add one mcq'
	WebUI.callTestCase(findTestCase('Test Cases/Common Test Cases/Test/Create MCQ'), null)
}

@TearDown()
def tearDown() {
	'Delete the test Test'
	def id = WebUI.getAttribute(findTestObject('Object Repository/Page_PreviewTest/txt_TestName'), 'data-id')
	WebUI.callTestCase(findTestCase('Test Cases/Common Test Cases/Test/Delete Test By ID'), ['id': id])
}

'Get the test name before navigate to preview page'
def testName = WebUI.getText(findTestObject('Object Repository/Page_PreviewTest/txt_TestName'))

'Click preview test'
WebUI.click(findTestObject('Object Repository/Page_TestOverview/btn_PreviewTest'))

'Verify if "Enter Test" button is displayed'
WebUI.verifyElementPresent(findTestObject('Object Repository/Page_PreviewTest/btn_EnterTest'), 10)

'Verify total question in question type'
def text
if (true) {
	'Verify if there is one mcq'
	text = WebUI.getText(findTestObject('Object Repository/Page_PreviewTest/txt_QuestionType', ['order': 1]))
	WebUI.verifyMatch(text, "1 Multiple Choice", false)
	'Verify if there is two subjectives'
	text = WebUI.getText(findTestObject('Object Repository/Page_PreviewTest/txt_QuestionType', ['order': 2]))
	WebUI.verifyMatch(text, "2 Subjective", false)
}

'Verify test name'
if (true) {
	def previewTestName = WebUI.getText(findTestObject('Object Repository/Page_PreviewTest/txt_TestName'))
	WebUI.verifyMatch(previewTestName, testName, false)
}

'Verify test duration (default duration of a newly created test is one minute)'
if (true) {
	text = WebUI.getText(findTestObject('Object Repository/Page_PreviewTest/txt_Duration'))
	WebUI.verifyMatch(text, "01:00:00", false)
}