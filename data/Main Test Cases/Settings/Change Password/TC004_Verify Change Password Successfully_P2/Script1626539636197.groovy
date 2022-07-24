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

def newPassword = 'Admin@123' + System.currentTimeMillis()

@SetUp()
def setUp( ) {
	'Login'
	WebUI.callTestCase(findTestCase('Common Test Cases/Login/Login'), [('Email') : GlobalVariable.G_Email, ('Password') : GlobalVariable.G_Password],
		FailureHandling.STOP_ON_FAILURE)
}

WebUI.comment('Click Change Password')

WebUI.click(findTestObject('Module_Navigation/lbl_Avatar'))

WebUI.click(findTestObject('Module_Navigation/btn_ChangePassword'))

WebUI.comment('Verify page name (ex: Change Password)')

WebUI.waitForElementPresent(findTestObject('Page_ChangePassword/txt_Title'), 10)

WebUI.comment('Enter valid values:')

WebUI.setText(findTestObject('Page_ChangePassword/tbx_CurrentPassword'), GlobalVariable.G_Password)

WebUI.setText(findTestObject('Page_ChangePassword/tbx_NewPassword'), newPassword)

WebUI.setText(findTestObject('Page_ChangePassword/tbx_ConfirmPassword'), newPassword)

WebUI.comment('Click Submit')

WebUI.click(findTestObject('Page_ChangePassword/btn_Submit'))

WebUI.comment('Verify save changes, show a successful message, and stay on the page')

WebUI.verifyElementPresent(findTestObject('Page_ChangePassword/txt_SuccessMessage'), 10)

WebUI.takeScreenshotAsCheckpoint("Successful Change Password")

WebUI.comment('Logout system')

WebUI.click(findTestObject('Page_ChangePassword/tbx_CurrentPassword'))

WebUI.waitForElementNotPresent(findTestObject('Page_ChangePassword/txt_SuccessMessage'), 20)

WebUI.callTestCase(findTestCase('Common Test Cases/Login/Logout'), null)

WebUI.comment('Login system with the new password')

WebUI.callTestCase(findTestCase('Test Cases/Common Test Cases/Login/Login With Password And Username'), [('username') : GlobalVariable.G_Email, ('password') : newPassword])

WebUI.comment('Verify login with new password successfully')

WebUI.verifyElementPresent(findTestObject('Module_Navigation/txt_title'), 10)

WebUI.takeScreenshotAsCheckpoint("Successful Login")

'Tear down'
if (true) {
	WebUI.setText(findTestObject('Page_ChangePassword/tbx_CurrentPassword'), newPassword)
	
	WebUI.setText(findTestObject('Page_ChangePassword/tbx_NewPassword'), GlobalVariable.G_Password)
	
	WebUI.setText(findTestObject('Page_ChangePassword/tbx_ConfirmPassword'), GlobalVariable.G_Password)
	
	WebUI.click(findTestObject('Page_ChangePassword/btn_Submit'))
	
	WebUI.verifyElementPresent(findTestObject('Page_ChangePassword/txt_SuccessMessage'), 10)
}

