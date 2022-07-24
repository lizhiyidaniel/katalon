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

WebUI.comment('Story: [ON] As a Recruiter, I want to log in to the system with INVALID credentials')

WebUI.comment('Given I am on the Login page with login URL')

WebUI.comment('When I enter INVALID email and password')

WebUI.setText(findTestObject('Page_Login/tbx_Email'), Email)

WebUI.setText(findTestObject('Page_Login/tbx_Password'), Password)

WebUI.click(findTestObject('Page_Login/btn_Login'))

WebUI.comment('Then system shows an error message')

WebUI.takeScreenshotAsCheckpoint("Failed Login")

WebUI.waitForElementPresent(findTestObject('Page_Login/txt_EmailErrorMessage'), 3, FailureHandling.STOP_ON_FAILURE)

def actualEmailErrorMessage = WebUI.getText(findTestObject('Page_Login/txt_EmailErrorMessage'))

WebUI.verifyMatch(actualEmailErrorMessage, expectedEmailErrorMessage, false)

WebUI.waitForElementPresent(findTestObject('Page_Login/txt_PasswordErrorMessage'), 3, FailureHandling.STOP_ON_FAILURE)

def actualPasswordErrorMessage = WebUI.getText(findTestObject('Page_Login/txt_PasswordErrorMessage'))

WebUI.verifyMatch(actualPasswordErrorMessage, expectedPasswordErrorMessage, false)