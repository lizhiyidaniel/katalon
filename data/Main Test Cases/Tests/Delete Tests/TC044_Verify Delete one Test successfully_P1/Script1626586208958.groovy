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
import groovy.transform.Field
import generator.DynamicGenerator
import com.kms.katalon.core.webui.common.WebUiCommonHelper
import org.openqa.selenium.JavascriptExecutor

@Field String randomString = DynamicGenerator.getInstance().getDynamicVariableByClassName(getClass())
@SetUp()
def setUp() {
	'Login'
	WebDriver driver = DriverFactory.getWebDriver()
	WebUI.comment('Precondition: User already logged in')
	WebUI.callTestCase(findTestCase('Test Cases/Common Test Cases/Login/Login'), [('Email') : GlobalVariable.G_Email, ('Password') : GlobalVariable.G_Password],
		FailureHandling.STOP_ON_FAILURE)
	'create list of test by delete all tests and add new once'
	WebUI.callTestCase(findTestCase('Test Cases/Common Test Cases/Test/Insert Test To DB'), ['testData':testData,'randomString':randomString])
}
@TearDown()
def tearDown() {
}
"click on Tests sidebar"
WebUI.click(findTestObject('Object Repository/Module_Navigation/nav_item_tests'))
"Click on draft test"
WebUI.click(findTestObject('Object Repository/Page_SearchTests/nav_testStatus',["order":3]))

def testSpanElements = findTestObject('Object Repository/Page_SearchTests/txt_testName',['testName':randomString+"_"+testName])

WebUI.waitForElementPresent(testSpanElements, 30, FailureHandling.STOP_ON_FAILURE)
WebUI.scrollToElement(testSpanElements, 60, FailureHandling.STOP_ON_FAILURE)

WebDriver driver = DriverFactory.getWebDriver()
WebElement element = WebUiCommonHelper.findWebElement(testSpanElements, 5)
JavascriptExecutor executor = ((driver) as JavascriptExecutor)
executor.executeScript('arguments[0].click()', element)

"Click more"
WebUI.click(findTestObject('Object Repository/Page_SearchTests/cpn_more'))
"Click delete"
WebUI.click(findTestObject('Object Repository/Page_SearchTests/txt_toggle_option',['order':1]))
"Click OK"
WebUI.click(findTestObject('Page_SearchTests/btn_delete_dialog_ok'))

def message = WebUI.getText(findTestObject('Object Repository/Page_SearchTests/successToast'), FailureHandling.CONTINUE_ON_FAILURE)
WebUI.verifyMatch(message, successMessage, false)
WebUI.verifyElementVisible(findTestObject('Object Repository/Page_SearchTests/successToast'), FailureHandling.STOP_ON_FAILURE)
"Check if navigated to listing page by click on draft test"
WebUI.click(findTestObject('Object Repository/Page_SearchTests/nav_testStatus',["order":3]))
//WebUI.takeScreenshotAsCheckpoint("Delete one test successfully")

/*
Delete all Draft Test
def numOfTest = WebUI.findWebElements(findTestObject('Object Repository/Page_SearchTests/cpn_tests'), 5).size()
if (numOfTest != 0) {
		int i = 1;
		while (i <= numOfLibraries) {
		//cpn_more: //*[@id="dropdown-button"]
			WebUI.click(findTestObject('Object Repository/Page_SearchTests/cpn_more'))
		//txt_toggle_option: //*[@id="test-container"]//li[${order}]/a
			WebUI.click(findTestObject('Object Repository/Page_SearchTests/txt_toggle_option',['order':1]))
			WebUI.click(findTestObject('Page_SearchTests/btn_delete_dialog_ok'))
			i = i + 1
		}
	}
*/