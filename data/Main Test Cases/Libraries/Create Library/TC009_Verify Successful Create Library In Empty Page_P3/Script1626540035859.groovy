import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver

import com.kms.katalon.core.annotation.SetUp
import com.kms.katalon.core.annotation.TearDown
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import generator.DynamicGenerator
import groovy.transform.Field

WebUI.comment('Story: [ON] As a Recruiter, I want to create question library.')

@Field String validNameUnique = validName + DynamicGenerator.getInstance().getDynamicVariableByClassName(getClass())

@SetUp()
def deteleAllLibraries() {
	def libraryNavItem = findTestObject('Object Repository/Module_Navigation/nav_item_libraries')
	def linkHomeLib = findTestObject('Object Repository/Page_Libraries/link_homeLib')
	
	WebUI.comment('Given I am logged in and there are no libraries yet,')
		
	'Login'
	WebUI.callTestCase(findTestCase('Common Test Cases/Login/Login'), null)
		
	WebUI.comment('When I click Libraries on the menu,')
	WebUI.click(libraryNavItem)
		
	WebUI.waitForElementVisible(linkHomeLib,5)
		
	'Find the number of libraries'
	WebDriver driver = DriverFactory.getWebDriver()
	def numOfLibraries = driver.findElements(By.xpath(libraryElementsXpath)).size()
		
	'Make sure page is empty'
	if (numOfLibraries != 0) {
		WebUI.callTestCase(findTestCase('Common Test Cases/Library/Delete All Libraries'), null)
	}
}

def emptyLibraryMessage = findTestObject('Page_Libraries/Empty_Page/txt_empyLibraryMessage')
def createLibraryButton = findTestObject('Page_Libraries/Empty_Page/btn_createLibrary')
def addLibraryDialog = findTestObject('Page_Libraries/dialog_addLibrary')
def fieldName = findTestObject('Page_Libraries/Library_Dialog/txt_nameField')
def submitButton = findTestObject('Page_Libraries/Library_Dialog/btn_Submit')
def txtSuccessfulMessage = findTestObject('Page_Libraries/txt_SuccessfullMessage')
def addQuestionsButton = findTestObject('Page_Libraries/btn_addQuestion')

WebUI.comment('Main Body')
WebUI.comment('Then I see a notice “You have no libraries yet.” and button “Create Library” on the screen.')
if (true) {
	WebUI.verifyElementText(emptyLibraryMessage, emptyMessage, FailureHandling.CONTINUE_ON_FAILURE)
	WebUI.verifyElementVisible(createLibraryButton)
}

WebUI.comment('When I click Create Library button on the screen,')
WebUI.click(createLibraryButton)

WebUI.comment('Then dialog Create Library is displayed')
WebUI.verifyElementVisible(addLibraryDialog)

WebUI.comment('When Enter valid Library Name and Click Submit')
WebUI.setText(fieldName, validNameUnique)
WebUI.click(submitButton)

WebUI.comment('Then Close dialog, show a successful message and create a library. Redirect to Listing of MCQ screen of the newly created library')
if (true) {
	WebUI.waitForElementPresent(txtSuccessfulMessage, 20, FailureHandling.CONTINUE_ON_FAILURE)
	def message = WebUI.getText(txtSuccessfulMessage, FailureHandling.CONTINUE_ON_FAILURE)
	WebUI.verifyMatch(message, successMessage, false)
	
	WebUI.verifyElementNotPresent(addLibraryDialog, 1, FailureHandling.CONTINUE_ON_FAILURE)
	
	WebUI.verifyElementVisible(addQuestionsButton, FailureHandling.CONTINUE_ON_FAILURE)
}

@TearDown()
def teardown() {
	'delete the test libary'
	WebUI.callTestCase(findTestCase('Common Test Cases/Library/Tear Down Test Library'), ['libraryName': validNameUnique])
}