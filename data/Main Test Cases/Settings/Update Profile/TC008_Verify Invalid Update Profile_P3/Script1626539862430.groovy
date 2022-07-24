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
import org.openqa.selenium.By as By
import org.openqa.selenium.Keys as Keys
import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject

WebUI.comment('Story: [ON] As a Recruiter, I want update profile')

WebUI.comment('Log in system')

WebUI.callTestCase(findTestCase('Common Test Cases/Login/Login'), [('Email') : GlobalVariable.G_Email, ('Password') : GlobalVariable.G_Password],
	FailureHandling.STOP_ON_FAILURE)

WebUI.comment('In navbar, I click on avatar')

WebUI.click(findTestObject('Object Repository/Page_UpdateProfile/img_Avatar'))

WebUI.comment('After that, i choose My profile, and profile page will appear')

WebUI.click(findTestObject('Object Repository/Page_UpdateProfile/txt_MyProfile'))

WebUI.waitForElementPresent(findTestObject('Object Repository/Page_UpdateProfile/txt_email'), 20)

WebUI.comment('Update Profile')

WebUI.setText(findTestObject('Object Repository/Page_UpdateProfile/txt_FullName'), newFullName)

WebUI.setText(findTestObject('Object Repository/Page_UpdateProfile/txt_Phone'), newPhone)

WebUI.click(findTestObject('Object Repository/Page_UpdateProfile/btn_Submit'))

WebUI.comment('Verify update profile unsuccessfully')

WebUI.waitForPageLoad(10)

WebUI.takeScreenshotAsCheckpoint("Update Profile")

def messageName = WebUI.getText(findTestObject('Page_UpdateProfile/txt_FullNameMessage'))

def messagePhone = WebUI.getText(findTestObject('Page_UpdateProfile/txt_PhoneMessage'))

WebUI.verifyMatch(messageName, NameMessage, false)

WebUI.verifyMatch(messagePhone, PhoneMessage, false)
