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
import internal.GlobalVariable as GlobalVariable

@Field String validNameUnique = "Ontest283 TC2 " + DynamicGenerator.getInstance().getDynamicVariableByClassName(getClass())
@Field String validEmail = DynamicGenerator.getInstance().getDynamicVariableByClassName(getClass()) + "@gmail.com"
@Field String validName = "Hoa Pham"

@SetUp
def setup() {
	WebUI.comment('There is at least one feedback candidate test')
	def createATestWithFeedback = findTestCase('Test Cases/Common Test Cases/Test/Create A Test With Feedback')
	WebUI.callTestCase(createATestWithFeedback, ["validNameUnique": validNameUnique, "validEmail": validEmail, "validName": validName])
}

@TearDown
def teardown() {
	WebUI.comment('Remove the tested test')
	def removeTheTestedTestTestCase = findTestCase('Test Cases/Common Test Cases/Test/Tear Down Challenge Test')
	WebUI.callTestCase(removeTheTestedTestTestCase, ['testName': validNameUnique, 'email':validEmail])
}

def candidiateFeedbackTab = findTestObject('Object Repository/Page_TestFeedback/nav_CandiateFeedback')
def pageTitle = findTestObject('Object Repository/Page_TestFeedback/title_PageTitle')

WebUI.comment('Step 1: Click Tests')
if (true) {
	def testsNavItem = findTestObject('Object Repository/Module_Navigation/nav_item_tests')
	def testNameSpan = findTestObject('Object Repository/Page_SearchTests/span_Ongoing_TestName', ['testName': validNameUnique])
	def testItemByName = findTestObject('Object Repository/Page_SearchTests/item_Ongoing_Test_By_Name', ['testName': validNameUnique])
	
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
}

WebUI.comment('Step 2: On Analytics: Click Candidate Feedback')
WebUI.scrollToElement(candidiateFeedbackTab, 3)
WebUI.click(candidiateFeedbackTab)

WebUI.comment('Step 3: Verify System navigates to the Candidate Feedback page')
WebUI.verifyElementVisible(pageTitle)

WebUI.comment('Step 4: Verify Average rating for 3 categories')
if (true) {
	def averageClarity = findTestObject('Object Repository/Page_TestFeedback/text_AverageClarity')
	def averageFairness = findTestObject('Object Repository/Page_TestFeedback/text_AverageFairness')
	def averageUsability = findTestObject('Object Repository/Page_TestFeedback/text_AverageUsability')
	
	WebUI.comment('Clarity of questions')
	def givenAverateClarity = WebUI.getText(averageClarity)
	WebUI.verifyMatch(givenAverateClarity, "1", false)
	
	WebUI.comment('Usability of test interface')
	def givenAverageFairness = WebUI.getText(averageFairness)
	WebUI.verifyMatch(givenAverageFairness, "1", false)
	
	WebUI.comment('Fairness of skill assessment')
	def givenAverateUsability = WebUI.getText(averageUsability)
	WebUI.verifyMatch(givenAverateUsability, "1", false)
}

