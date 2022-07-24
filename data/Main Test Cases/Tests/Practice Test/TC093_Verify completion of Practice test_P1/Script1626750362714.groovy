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
import java.awt.Robot;
import java.awt.event.KeyEvent;
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
}

@TearDown()
def tearDown() {
	'Delete the test Test'
	def id = WebUI.getAttribute(findTestObject('Object Repository/Page_PreviewTest/txt_TestName'), 'data-id')
	WebUI.callTestCase(findTestCase('Test Cases/Common Test Cases/Test/Delete Test By ID'), ['id': id])
}

'Back to Overview'
WebUI.click(findTestObject('Object Repository/Page_TestOverview/btn_Overview'))

'Get the id of test'
def id = WebUI.getAttribute(findTestObject('Object Repository/Page_AddTest/txt_TestName'), 'data-id')

'publish test'
WebUI.callTestCase(findTestCase('Common Test Cases/Test/Publish Test'), null)

'Click preview test'
WebUI.scrollToElement(findTestObject('Object Repository/Page_TestOverview/btn_PreviewTest'), 10)
WebUI.delay(2)
WebUI.click(findTestObject('Object Repository/Page_TestOverview/btn_PreviewTest'))

'Click practice test link'
WebUI.scrollToElement(findTestObject('Object Repository/Page_PreviewTest/link_PracticeTest'), 10)
WebUI.click(findTestObject('Object Repository/Page_PreviewTest/link_PracticeTest'))

'Click Enter Test'
WebUI.scrollToElement(findTestObject('Object Repository/Page_PreviewTest/btn_EnterTest'), 10)
WebUI.click(findTestObject('Object Repository/Page_PreviewTest/btn_EnterTest'))

'Wait for 1mins'
WebUI.delay(60)

'Click Start Test'
WebUI.scrollToElement(findTestObject('Object Repository/Page_PreviewTest/btn_StartTest'), 10)
WebUI.click(findTestObject('Object Repository/Page_PreviewTest/btn_StartTest'))

'Click submit'
WebUI.click(findTestObject('Object Repository/Page_PreviewTest/btn_Submit'))

'Click ok'
WebUI.click(findTestObject('Object Repository/Page_PreviewTest/btn_OK'))

'Verify system navigates to the Candidate feedback page'
WebUI.verifyElementPresent(findTestObject('Object Repository/Page_PreviewTest/img_Success'), 10)

'Enter feedback'
WebUI.setText(findTestObject('Object Repository/Page_PreviewTest/textarea_Feedback'), 'This is feedback')

'Click submit'
WebUI.scrollToElement(findTestObject('Object Repository/Page_PreviewTest/btn_SubmitFeedback'), 10)
WebUI.click(findTestObject('Object Repository/Page_PreviewTest/btn_SubmitFeedback'))

'Verify if the system redirects user back to preview the test'
WebUI.verifyElementPresent(findTestObject('Object Repository/Page_PreviewTest/Span_PreviewStatus'), 10)

'Verify if "Enter Test" button is displayed'
WebUI.verifyElementPresent(findTestObject('Object Repository/Page_PreviewTest/btn_EnterTest'), 10)