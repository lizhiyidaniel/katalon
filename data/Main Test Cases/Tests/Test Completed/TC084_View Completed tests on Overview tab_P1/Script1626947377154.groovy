import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject

import com.kms.katalon.core.annotation.SetUp
import com.kms.katalon.core.annotation.TearDown
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
import com.database.Database as DB
import org.openqa.selenium.WebElement as WebElement
import org.openqa.selenium.By as By
import org.openqa.selenium.WebDriver as WebDriver
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import com.kms.katalon.core.util.KeywordUtil as KeyworUtil
import com.kms.katalon.core.webui.common.WebUiCommonHelper
import org.openqa.selenium.JavascriptExecutor

import groovy.transform.Field
import generator.DynamicGenerator
@Field String randomString = DynamicGenerator.getInstance().getDynamicVariableByClassName(getClass())
@SetUp()
def setUp() {
	WebDriver driver = DriverFactory.getWebDriver()
	'Login'
	WebUI.comment('Precondition: User already logged in')

	WebUI.callTestCase(findTestCase('Test Cases/Common Test Cases/Login/Login'), [('Email') : GlobalVariable.G_Email, ('Password') : GlobalVariable.G_Password],
		FailureHandling.STOP_ON_FAILURE)
	WebUI.callTestCase(findTestCase('Test Cases/Common Test Cases/Test/Insert Test To DB'), ['testData':testData,'randomString':randomString])
}
@TearDown()
def tearDown() {
	'delete the test after done'
	def testName = WebUI.getText(findTestObject('Object Repository/Page_AddTest/txt_TestName'))
	def testId = WebUI.getText(findTestObject('Object Repository/Page_AddTest/txt_testID'))
	WebUI.callTestCase(findTestCase('Test Cases/Common Test Cases/Test/Tear Down Test'), ['testName': testName,'name':testId])
}
"click on Tests sidebar"
WebUI.click(findTestObject('Object Repository/Module_Navigation/nav_item_tests'))
"Click on draft test"
WebUI.click(findTestObject('Object Repository/Page_SearchTests/nav_testStatus',["order":2]))

def testSpanElements = findTestObject('Object Repository/Page_SearchTests/txt_testName',['testName':randomString+"_"+testName])

WebUI.waitForElementPresent(testSpanElements, 10, FailureHandling.STOP_ON_FAILURE)
WebUI.scrollToElement(testSpanElements, 30, FailureHandling.STOP_ON_FAILURE)

WebDriver driver = DriverFactory.getWebDriver()
WebElement element = WebUiCommonHelper.findWebElement(testSpanElements, 5)
JavascriptExecutor executor = ((driver) as JavascriptExecutor)
executor.executeScript('arguments[0].click()', element)

def attribute = WebUI.getAttribute(findTestObject('Object Repository/Page_TestOverview/btn_Overview'), 'class')

'Verify if  attribute value is active'
WebUI.verifyMatch(attribute, 'active btn btn-outline-primary d-block w-100 text-left mt-2', false)

'Verify test accessible is of and not editable'
WebUI.waitForElementPresent(findTestObject('Object Repository/Page_TestOverview/switch_TestAccessible_Disable'), 3)