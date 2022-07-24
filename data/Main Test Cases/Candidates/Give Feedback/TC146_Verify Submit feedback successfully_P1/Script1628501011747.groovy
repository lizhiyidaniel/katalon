import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement

import com.kms.katalon.core.annotation.SetUp
import com.kms.katalon.core.annotation.TearDown
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.webui.common.WebUiCommonHelper
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import generator.DynamicGenerator
import groovy.transform.Field
import internal.GlobalVariable

@Field String validNameUnique = "Ontest283 TC2 " + DynamicGenerator.getInstance().getDynamicVariableByClassName(getClass())
@Field String validEmail = DynamicGenerator.getInstance().getDynamicVariableByClassName(getClass()) + "@gmail.com"
@Field String validName = "Hoa Pham"

@SetUp
def setup() {
	WebUI.comment('Step 1: Check the test link in the invite email and submit test')
	WebUI.callTestCase(findTestCase('Test Cases/Common Test Cases/Test/Submit Test On Time'), ["validNameUnique": validNameUnique, "validEmail": validEmail, "validName": validName])
}

@TearDown()
def tearDown() {
	WebUI.comment('Delete the tested test')
	WebUI.callTestCase(findTestCase('Test Cases/Common Test Cases/Test/Tear Down Challenge Test'), ['testName': validNameUnique, 'email':validEmail])
}

def clarityVoting = findTestObject('Object Repository/Page_SendFeedback/star_ClarityVoting')
def usabilityVoting = findTestObject('Object Repository/Page_SendFeedback/star_UsabilityVoting')
def fairnessVoting = findTestObject('Object Repository/Page_SendFeedback/star_FairnessVoting')
def textareaFeedback = findTestObject('Object Repository/Page_SendFeedback/textarea_Feedback')
def submitButton = findTestObject('Object Repository/Page_SendFeedback/btn_SubmitFeedback')

WebUI.comment('Step 2: Verify Navigation to the Feedback page')
WebUI.verifyElementVisible(clarityVoting)

WebUI.comment('Step 3: Enter feedback')
if (true) {
	WebUI.click(clarityVoting)
	WebUI.click(usabilityVoting)
	WebUI.click(fairnessVoting)
	WebUI.setText(textareaFeedback, "This is a good test!")
}

WebUI.comment('Step 4: Click Submit')
if (true) {
	WebUI.scrollToElement(submitButton, 3)
	WebUI.click(submitButton)
	WebUI.delay(2)
}

def testsNavItem = findTestObject('Object Repository/Module_Navigation/nav_item_tests')
def testNameSpan = findTestObject('Object Repository/Page_SearchTests/span_Ongoing_TestName', ['testName': validNameUnique])
def testItemByName = findTestObject('Object Repository/Page_SearchTests/item_Ongoing_Test_By_Name', ['testName': validNameUnique])
def feedbackTab = findTestObject('Object Repository/Page_TestFeedback/nav_CandiateFeedback')
def detailsTab = findTestObject('Object Repository/Page_TestFeedback/nav_DetailsTab')
def emptyMessage = findTestObject('Object Repository/Page_TestFeedback/text_EmptyMessage')

WebUI.comment('Step 6: Verify feedback is saved')
if (true) {
	WebUI.openBrowser(GlobalVariable.G_SiteURL)
	
	WebUI.callTestCase(findTestCase('Test Cases/Common Test Cases/Login/Login'), null)
	
	WebUI.click(testsNavItem, FailureHandling.STOP_ON_FAILURE)
	if (true) {
		WebUI.scrollToElement(testNameSpan, 3, FailureHandling.CONTINUE_ON_FAILURE)
		try {
			WebUI.click(testItemByName, FailureHandling.STOP_ON_FAILURE)
		}
		catch (Exception e) {
			WebDriver driver = DriverFactory.getWebDriver()
			WebElement element = WebUiCommonHelper.findWebElement(testItemByName, 5)
			JavascriptExecutor executor = ((driver) as JavascriptExecutor)
			executor.executeScript('arguments[0].click()', element)
		}
	}
	
	WebUI.scrollToElement(feedbackTab, 3)
	WebUI.click(feedbackTab)
	
	WebUI.scrollToElement(detailsTab, 3)
	WebUI.click(detailsTab)
	
	WebUI.verifyElementNotPresent(emptyMessage, 3)
}