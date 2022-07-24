import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testng.keyword.TestNGBuiltinKeywords as TestNGKW
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys

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

@Field String validNameUnique = "Ontest" + DynamicGenerator.getInstance().getDynamicVariableByClassName(getClass())

@SetUp
def setup() {
	def testsNavItem = findTestObject('Object Repository/Module_Navigation/nav_item_tests')
	def testNameSpan = findTestObject('Object Repository/Page_SearchTests/span_Ongoing_TestName', ['testName': validNameUnique])
	def testItemByName = findTestObject('Object Repository/Page_SearchTests/item_Ongoing_Test_By_Name', ['testName': validNameUnique])
	
	WebUI.comment('There is at least one ongoing test')
	WebUI.callTestCase(findTestCase('Test Cases/Common Test Cases/Test/Create An Ongoing Test'), ['testName': validNameUnique])
	
	WebUI.comment('Back to the onogoing test list')
	WebUI.click(testsNavItem, FailureHandling.STOP_ON_FAILURE)
	
	WebUI.comment('Step 1: Click an ongoing test on the Tests listing page.')
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
	WebUI.delay(5)
	WebUI.click(findTestObject('Object Repository/Page_TestOverview/switch_TestAccessible'))
	WebUI.click(findTestObject('Object Repository/Page_TestOverview/btn_Ok_Toggle_Accessible'))
}
@TearDown
def teardown() {
	WebUI.comment('Remove the ongoing tested test')
	WebUI.callTestCase(findTestCase('Test Cases/Common Test Cases/Test/Tear Down Test'), ['testName': validNameUnique])
}

WebUI.comment('Step 1: Click the remind Email Template button')
//WebUI.scrollToElement(findTestObject('Page_EmailTemplates/btn_email_Template'), 3)
WebUI.click(findTestObject('Page_EmailTemplates/btn_email_Template'), FailureHandling.STOP_ON_FAILURE)

WebUI.comment('Step 2: Click Email Type')
WebUI.click(findTestObject('Object Repository/Page_EmailTemplates/tab_Thanks_Email'))

WebUI.comment('Step 3: Enter valid new values')
WebUI.waitForElementVisible(findTestObject('Page_EmailTemplates/ipn_Subjective',['type':'thank']),1)
WebUI.scrollToElement(findTestObject('Page_EmailTemplates/ipn_Subjective',['type':'thank']), 1)
WebUI.click(findTestObject('Page_EmailTemplates/ipn_Subjective',['type':'thank']))
WebUI.setText(findTestObject('Page_EmailTemplates/ipn_Subjective',['type':'thank']), subjectTest, FailureHandling.STOP_ON_FAILURE)


WebUI.sendKeys(findTestObject('Page_EmailTemplates/ipn_Subjective',['type':'thank']), Keys.chord(Keys.TAB), FailureHandling.STOP_ON_FAILURE)
WebUI.sendKeys(findTestObject('Page_EmailTemplates/ta_body',['type':'thank']), Keys.chord(Keys.CONTROL, 'a'))
WebUI.sendKeys(findTestObject('Page_EmailTemplates/ta_body',['type':'thank']), emailBodyTest, FailureHandling.CONTINUE_ON_FAILURE)
WebUI.sendKeys(findTestObject('Page_EmailTemplates/ta_body',['type':'thank']), Keys.chord(Keys.TAB), FailureHandling.STOP_ON_FAILURE)

WebUI.comment('Step 4: Save changes')
def submit = findTestObject('Page_EmailTemplates/btn_Submit')
if (true) {
	WebUI.scrollToElement(submit, 3, FailureHandling.CONTINUE_ON_FAILURE)
	try {
		WebUI.click(submit, FailureHandling.STOP_ON_FAILURE)
	}
	catch (Exception e) {
		WebDriver driver = DriverFactory.getWebDriver()
		WebElement element = WebUiCommonHelper.findWebElement(submit, 5)
		JavascriptExecutor executor = ((driver) as JavascriptExecutor)
		executor.executeScript('arguments[0].click()', element)
	}
}

WebUI.comment('Step 5: Show success message')
def givenMessage = WebUI.getText(findTestObject('Object Repository/Module_Navigation/toast_Success'))
WebUI.verifyMatch(givenMessage, 'Update Thank you email template successfully.', false)

def afterReset = WebUI.getText(findTestObject('Page_EmailTemplates/ta_body',['type':'thank']))
WebUI.verifyMatch(emailBodyTest, afterReset, false)