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

WebUI.comment('Story: [ON] As a Recruiter, I want to log out of the system')

@SetUp()
def setUp() {
	WebUI.comment('Log in system')
	WebUI.callTestCase(findTestCase('Common Test Cases/Login/Login'), [('Email') : GlobalVariable.G_Email, ('Password') : GlobalVariable.G_Password],
		FailureHandling.STOP_ON_FAILURE)
}

WebUI.comment('When I click Sign out')

WebUI.click(findTestObject('Module_Navigation/lbl_Avatar'))

WebUI.click(findTestObject('Page_Login/btn_Logout'))

WebUI.comment('Then I am logged out of the system and redirected to Login page')

WebUI.waitForElementPresent(findTestObject('Page_Login/btn_Login'), 10)



