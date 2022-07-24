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
import org.openqa.selenium.Keys
import org.junit.After
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import internal.GlobalVariable as GlobalVariable
import com.kms.katalon.core.webui.driver.DriverFactory


WebUI.comment('Story: [ON] As a Recruiter, I want to create tests.')

@SetUp()
def setUp() {
	WebUI.comment('Given I am logged in and there are no libraries yet,')
	
	'Login'
	WebUI.callTestCase(findTestCase('Common Test Cases/Login/Login'), null)
	
	WebUI.comment('When I click Tests on the menu,')
	WebUI.click(findTestObject('Object Repository/Module_Navigation/nav_item_tests'))
	
	

	'Make sure there is at least a draft test'
	WebDriver driver = DriverFactory.getWebDriver()
	def empty = driver.findElements(By.xpath(emptyXpath)).size()
	
	if(empty != 0){
	 WebUI.click(findTestObject('Object Repository/Module_Navigation/nav_item_tests'))
		WebUI.callTestCase(findTestCase('Test Cases/Common Test Cases/Test/Add Test'), null)
	}
		
}

@TearDown()
def teardown() {
	'delete the test after done'
	def testName = WebUI.getText(findTestObject('Object Repository/Page_AddTest/txt_TestName'))
	def testId = WebUI.getText(findTestObject('Object Repository/Page_AddTest/txt_testID'))
	WebUI.callTestCase(findTestCase('Test Cases/Common Test Cases/Test/Tear Down Test'), ['testName': testName,'name':testId,"testStatus":testStatus])
}
WebUI.comment('Click Tests on the menu')
WebUI.click(findTestObject('Object Repository/Module_Navigation/nav_item_tests'))

WebUI.click(findTestObject('Object Repository/Page_AddTest/btn_CreateTest'))

WebUI.comment('Then there is a successful message and tests have been created. Redirect to update test page')
if (true) {
	WebUI.waitForElementPresent(findTestObject('Object Repository/Page_AddTest/successToast'), 20, FailureHandling.CONTINUE_ON_FAILURE)
	def message = WebUI.getText(findTestObject('Object Repository/Page_AddTest/successToast'), FailureHandling.CONTINUE_ON_FAILURE)
	WebUI.verifyMatch(message, successMessage, false)
	WebUI.verifyElementVisible(findTestObject('Object Repository/Page_AddTest/txt_TestName'), FailureHandling.STOP_ON_FAILURE)
}

WebUI.takeScreenshotAsCheckpoint("Create Draft Test")