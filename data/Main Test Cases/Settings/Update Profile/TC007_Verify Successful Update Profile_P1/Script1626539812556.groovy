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

WebUI.comment('Story: [ON] As a Recruiter, I want update profile')

@SetUp()
def setUp( ) {
	'Login'
	WebUI.callTestCase(findTestCase('Common Test Cases/Login/Login'), [('Email') : GlobalVariable.G_Email, ('Password') : GlobalVariable.G_Password],
		FailureHandling.STOP_ON_FAILURE)
}

WebUI.comment('In navbar, I click on avatar')

WebUI.click(findTestObject('Object Repository/Page_UpdateProfile/img_Avatar'))

WebUI.comment('After that, I choose My profile, and profile page will appear')

WebUI.click(findTestObject('Object Repository/Page_UpdateProfile/txt_MyProfile'))

WebUI.waitForElementPresent(findTestObject('Object Repository/Page_UpdateProfile/txt_email'), 20)

WebUI.comment('Update Profile')

WebUI.setText(findTestObject('Object Repository/Page_UpdateProfile/txt_FullName'), newFullName)

WebUI.setText(findTestObject('Object Repository/Page_UpdateProfile/txt_Phone'), newPhone)

WebUI.click(findTestObject('Object Repository/Page_UpdateProfile/btn_Submit'))

WebUI.comment('Verify update profile successfully')

WebUI.waitForPageLoad(10)

WebUI.takeScreenshotAsCheckpoint("Update Profile")

WebUI.verifyTextPresent("Update profile successfully.", false, FailureHandling.STOP_ON_FAILURE)
