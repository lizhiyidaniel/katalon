import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import org.openqa.selenium.By
import org.openqa.selenium.Keys
import org.openqa.selenium.WebDriver

import com.kms.katalon.core.annotation.SetUp
import com.kms.katalon.core.annotation.TearDown
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

'As a Recruiter, I want to add questions from libraries to a draft test'

def libname = 'random lib' + System.currentTimeMillis()

'Set up'
if (true) {
	'Login'
	WebUI.callTestCase(findTestCase('Common Test Cases/Login/Login'), null)
	
	'Click library on the navigation menu'
	WebUI.click(findTestObject('Object Repository/Module_Navigation/nav_item_libraries'))
	
	WebUI.waitForElementVisible(findTestObject('Object Repository/Page_Libraries/link_homeLib'),5)
	
	'Create a MCQ question'
	WebUI.callTestCase(findTestCase('Test Cases/Common Test Cases/Library/Create New MCQ'), ['libraryName': libname])
	
	'Click test on the navigation menu'
	WebUI.click(findTestObject('Object Repository/Module_Navigation/nav_item_tests'))
	
	'Create Test for test'
	WebDriver driver = DriverFactory.getWebDriver()
	def empty = driver.findElements(By.xpath(emptyXpath)).size()
	
	if(empty != 0){
		WebUI.click(findTestObject('Page_AddTest/EmptyPage/btn_CreateTest-Empty'))
	} else {
		WebUI.click(findTestObject('Object Repository/Page_AddTest/btn_CreateTest'))
	}
	
	'Click questions in the left sidebar'
	WebUI.click(findTestObject('Page_TestQuestions/btn_TestQuestion'))
}

'Click Choose from Library button'
WebUI.click(findTestObject('Object Repository/Page_TestQuestions/btn_ChooseFromLibrary'))

'Verify if the dialog displays'
WebUI.verifyElementPresent(findTestObject('Page_TestQuestions/dialog_Dialog'), 10)

'Select one question'
WebUI.click(findTestObject('Object Repository/Page_TestQuestions/cbx_LibraryQuestion'))

'Click cancel'
WebUI.click(findTestObject('Object Repository/Page_TestQuestions/btn_CancelButton'))

'Verify if the dialog disappears'
WebUI.verifyElementNotPresent(findTestObject('Page_TestQuestions/dialog_Dialog'), 10)

'Tear down'
if (true) {
	'Delete the test Test'
	def id = WebUI.getAttribute(findTestObject('Object Repository/Page_AddTest/txt_TestName'), 'data-id')
	WebUI.callTestCase(findTestCase('Test Cases/Common Test Cases/Test/Tear Down Test'), ['id': id])
	
	'Click library on the navigation menu'
	WebUI.click(findTestObject('Object Repository/Module_Navigation/nav_item_libraries'))
	
	'Delete the test library'
	WebUI.callTestCase(findTestCase('Test Cases/Common Test Cases/Library/Tear Down Test Library'), ['libraryName': libname])
}