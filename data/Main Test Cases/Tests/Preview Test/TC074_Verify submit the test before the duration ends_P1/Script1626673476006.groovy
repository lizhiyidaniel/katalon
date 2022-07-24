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
	
	'Add one mcq'
	WebUI.callTestCase(findTestCase('Test Cases/Common Test Cases/Test/Create MCQ'), null)
	
	'Add one more mcq'
	WebUI.callTestCase(findTestCase('Test Cases/Common Test Cases/Test/Create MCQ'), null)
}

@TearDown()
def tearDown() {
	'Delete the test Test'
	def id = WebUI.getAttribute(findTestObject('Object Repository/Page_PreviewTest/txt_TestName'), 'data-id')
	WebUI.callTestCase(findTestCase('Test Cases/Common Test Cases/Test/Delete Test By ID'), ['id': id])
}

'Click preview test'
WebUI.scrollToElement(findTestObject('Object Repository/Page_TestOverview/btn_PreviewTest'), 10)
WebUI.click(findTestObject('Object Repository/Page_TestOverview/btn_PreviewTest'))

'Click Enter Test'
WebUI.scrollToElement(findTestObject('Object Repository/Page_PreviewTest/btn_EnterTest'), 10)
WebUI.click(findTestObject('Object Repository/Page_PreviewTest/btn_EnterTest'))

'Wait for 1mins'
WebUI.delay(60)

'Click Start Test'
WebUI.scrollToElement(findTestObject('Object Repository/Page_PreviewTest/btn_StartTest'), 10)
WebUI.click(findTestObject('Object Repository/Page_PreviewTest/btn_StartTest'))

'There are 2 questions in total'
'On the first question'
'Verify there is no back button'
WebUI.verifyElementNotClickable(findTestObject('Object Repository/Page_PreviewTest/btn_Back'))

'Click next'
WebUI.click(findTestObject('Object Repository/Page_PreviewTest/btn_Next'))

'On the last question'
'Verify there is no next button'
WebUI.verifyElementNotClickable(findTestObject('Object Repository/Page_PreviewTest/btn_Next'))

'Click submit'
WebUI.click(findTestObject('Object Repository/Page_PreviewTest/btn_Submit'))

'Verify if confirm dialog is displayed'
WebUI.verifyElementPresent(findTestObject('Object Repository/Page_PreviewTest/dialog_Dialog'), 10)

'Verify the confirm message'
if (true) {
	text = WebUI.getText(findTestObject('Object Repository/Page_PreviewTest/txt_Confirm'))
	WebUI.verifyMatch(text, "Are you sure you want to submit your answers?", false)
}

'Click ok'
WebUI.click(findTestObject('Object Repository/Page_PreviewTest/btn_OK'))

'Verify if the system redirects user back to enter the test'
WebUI.verifyElementPresent(findTestObject('Object Repository/Page_PreviewTest/btn_EnterTest'), 10)