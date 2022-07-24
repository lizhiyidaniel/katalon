import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.By
import org.openqa.selenium.Keys
import org.openqa.selenium.WebDriver

import com.kms.katalon.core.annotation.SetUp
import com.kms.katalon.core.annotation.TearDown
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

'Setup'
if (true) {
	WebUI.comment('Log in system')
	WebUI.callTestCase(findTestCase('Common Test Cases/Login/Login'), [('Email') : GlobalVariable.G_Email, ('Password') : GlobalVariable.G_Password],
		FailureHandling.STOP_ON_FAILURE)
}


WebUI.comment('Click Change Password')

WebUI.click(findTestObject('Module_Navigation/lbl_Avatar'))

WebUI.click(findTestObject('Module_Navigation/btn_ChangePassword'))

WebUI.comment('Verify page name (ex: Change Password)')

WebUI.waitForElementPresent(findTestObject('Page_ChangePassword/txt_Title'), 10)

WebUI.comment('Enter valid values:')

WebUI.setText(findTestObject('Page_ChangePassword/tbx_CurrentPassword'), "")

WebUI.setText(findTestObject('Page_ChangePassword/tbx_NewPassword'), "")

WebUI.setText(findTestObject('Page_ChangePassword/tbx_ConfirmPassword'), "")

WebUI.comment('Click Submit')

WebUI.click(findTestObject('Page_ChangePassword/btn_Submit'))

WebUI.comment('Then system shows an error message')

WebUI.takeScreenshotAsCheckpoint("Change Invalid Password")

WebUI.waitForElementPresent(findTestObject('Page_ChangePassword/txt_CurrentPWErrorMessage'), 3, FailureHandling.STOP_ON_FAILURE)

def actualCurrentPWErrorMessage = WebUI.getText(findTestObject('Page_ChangePassword/txt_CurrentPWErrorMessage'))

WebUI.verifyMatch(actualCurrentPWErrorMessage, expectedCurrentPWErrorMessage, false)

WebUI.waitForElementPresent(findTestObject('Page_ChangePassword/txt_NewPWErrorMessage'), 3, FailureHandling.STOP_ON_FAILURE)

def actualNewPWErrorMessage = WebUI.getText(findTestObject('Page_ChangePassword/txt_NewPWErrorMessage'))

WebUI.verifyMatch(actualNewPWErrorMessage, expectedNewPWErrorMessage, false)

WebUI.waitForElementPresent(findTestObject('Page_ChangePassword/txt_ConfirmPWErrorMessage'), 3, FailureHandling.STOP_ON_FAILURE)

def actualConfirmPWErrorMessage = WebUI.getText(findTestObject('Page_ChangePassword/txt_ConfirmPWErrorMessage'))

WebUI.verifyMatch(actualConfirmPWErrorMessage, expectedConfirmPWErrorMessage, false)
