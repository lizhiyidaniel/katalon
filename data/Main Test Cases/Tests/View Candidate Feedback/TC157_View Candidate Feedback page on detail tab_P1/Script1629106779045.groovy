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
def detailsTab = findTestObject('Object Repository/Page_TestFeedback/nav_DetailsTab')

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

WebUI.comment('Step 4: Click on the detail tab')
WebUI.scrollToElement(detailsTab, 3)
WebUI.click(detailsTab)

def candiateName = findTestObject('Object Repository/Page_TestFeedback/DetailPane/text_CandidateName')
def candidateComment = findTestObject('Object Repository/Page_TestFeedback/DetailPane/text_Comment')
def numberStarOfClarity = findTestObject('Object Repository/Page_TestFeedback/DetailPane/text_ClarityNumber')
def numberStarOfFairness = findTestObject('Object Repository/Page_TestFeedback/DetailPane/text_FairnessNumber')
def numberStarOfUsability = findTestObject('Object Repository/Page_TestFeedback/DetailPane/text_UsabilityNumber')

WebUI.comment('Step 5: Verify Candidate name, feedback Content, number of stars for each category')
if (true) {
	WebUI.comment('Verify Name')
	def givenName = WebUI.getText(candiateName)
	WebUI.verifyMatch(givenName, validName, false)
	
	WebUI.comment('Verify Comment')
	def givenComment = WebUI.getText(candidateComment)
	WebUI.verifyMatch(givenComment, "This is a good test!", false)
	
	WebUI.comment('Verify number of clarity star')
	def givenClarity = WebUI.getText(numberStarOfClarity)
	WebUI.verifyMatch(givenClarity, "1/5", false)
	
	WebUI.comment('Verify number of fairness star')
	def givenFairness = WebUI.getText(numberStarOfFairness)
	WebUI.verifyMatch(givenFairness, "1/5", false)
	
	WebUI.comment('Verifu number of usablity star')
	def givenUsability = WebUI.getText(numberStarOfUsability)
	WebUI.verifyMatch(givenUsability, "1/5", false)
}


