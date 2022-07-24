import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.annotation.SetUp
import com.kms.katalon.core.annotation.TearDown
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import groovy.transform.Field
import generator.DynamicGenerator

@Field String testLibraryNameUnique = testLibraryName + DynamicGenerator.getInstance().getDynamicVariableByClassName(getClass())
@Field String validNameUnique = validName + DynamicGenerator.getInstance().getDynamicVariableByClassName(getClass())
WebUI.comment('Story: [ON] As a Recruiter, I want to create question library.')
@SetUp
def setUp( ) {
	def libraryNavItem = findTestObject('Object Repository/Module_Navigation/nav_item_libraries')
	def linkHomeLibrary = findTestObject('Object Repository/Page_Libraries/link_homeLib')
	
	WebUI.comment('Given I am logged in and there is at least TWO libraries,')
	
	'Login'
	WebUI.callTestCase(findTestCase('Common Test Cases/Login/Login'), null)
		
	WebUI.comment('When I click Libraries on the menu,')
	WebUI.click(libraryNavItem)
		
	WebUI.waitForElementVisible(linkHomeLibrary,5)
		
	'create Testing MCQ Question.'
	WebUI.callTestCase(findTestCase('Common Test Cases/Library/Create New MCQ'), ['libraryName': testLibraryNameUnique])
}

WebUI.waitForPageLoad(30)

def addLibraryButton = findTestObject('Page_Libraries/btn_addLibrary')
def addLibraryDialog = findTestObject('Page_Libraries/dialog_addLibrary')
def nameField = findTestObject('Page_Libraries/Library_Dialog/txt_nameField')
def submitButton = findTestObject('Page_Libraries/Library_Dialog/btn_submit')
def txtSuccessfullMessage = findTestObject('Page_Libraries/txt_SuccessfullMessage')
def addQuestionButton = findTestObject('Page_Libraries/btn_addQuestion')

WebUI.comment('MAIN BODY')
WebUI.waitForElementPresent(addLibraryButton, 20)
WebUI.comment('When I Click Add button next to Libraries on the sub-menu,')
WebUI.click(addLibraryButton)

WebUI.comment('Then dialog Create Library is displayed')
WebUI.verifyElementVisible(addLibraryDialog)

WebUI.takeScreenshotAsCheckpoint('Create Library Name')

WebUI.comment('When Enter valid Library Name and Click Submit')
WebUI.setText(nameField, validNameUnique)
WebUI.click(submitButton)

WebUI.waitForPageLoad(10)
WebUI.comment('Then Close dialog, show a successful message and create a library. Redirect to Listing of MCQ screen of the newly created library')
if (true) {
	WebUI.comment('Show a successful message')
	WebUI.waitForElementPresent(txtSuccessfullMessage, 20, FailureHandling.CONTINUE_ON_FAILURE)
	def message = WebUI.getText(txtSuccessfullMessage, FailureHandling.CONTINUE_ON_FAILURE)
	WebUI.verifyMatch(message, successMessage, false)
	
	WebUI.comment('Close dialog')
	WebUI.verifyElementNotPresent(addLibraryDialog, 1, FailureHandling.CONTINUE_ON_FAILURE)
	
	WebUI.comment('Redirect to Listing of MCQ screen of the newly created library')
	WebUI.verifyElementVisible(addQuestionButton, FailureHandling.CONTINUE_ON_FAILURE)
}

@TearDown()
def tearDown() {
	'delete the test libary'
	WebUI.callTestCase(findTestCase('Common Test Cases/Library/Tear Down Test Library'), ['libraryName': testLibraryNameUnique])
	WebUI.callTestCase(findTestCase('Common Test Cases/Library/Tear Down Test Library'), ['libraryName': validNameUnique])
}

