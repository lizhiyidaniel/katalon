import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.annotation.SetUp
import com.kms.katalon.core.annotation.TearDown
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import generator.DynamicGenerator
import groovy.transform.Field

WebUI.comment('Story: [ON] As a Recruiter, I want to create question library.')

@Field String validNameUnique = testLibraryName + DynamicGenerator.getInstance().getDynamicVariableByClassName(getClass())

@SetUp()
def setUp( ) {
	def libraryNavItem = findTestObject('Object Repository/Module_Navigation/nav_item_libraries')
	def linkHomeLibrary = findTestObject('Object Repository/Page_Libraries/link_homeLib')
	
	WebUI.comment('Given I am logged in and there is at least ONE libraries,')
	
	'Login'
	WebUI.callTestCase(findTestCase('Common Test Cases/Login/Login'), null)
	
	WebUI.comment('When I click Libraries on the menu,')
	WebUI.click(libraryNavItem)
	
	WebUI.waitForElementVisible(linkHomeLibrary,5)
	
	'create Testing MCQ Question.'
	WebUI.callTestCase(findTestCase('Common Test Cases/Library/Create New MCQ'), ['libraryName': validNameUnique])
}

@TearDown()
def tearDown() {
	'delete the test libary'
	WebUI.callTestCase(findTestCase('Common Test Cases/Library/Tear Down Test Library'), ['libraryName': validNameUnique])
} 

def addLibraryButton = findTestObject('Page_Libraries/btn_addLibrary')
def addLibraryDialog = findTestObject('Page_Libraries/dialog_addLibrary')
def nameField = findTestObject('Page_Libraries/Library_Dialog/txt_nameField')
def submitButton = findTestObject('Page_Libraries/Library_Dialog/btn_submit')
def txtInvalidMessage = findTestObject('Page_Libraries/Library_Dialog/txt_invalidMessage')
def cancelButton = findTestObject('Page_Libraries/More_Action/btn_Cancel')

WebUI.comment('When I Click Add button next to Libraries on the sub-menu,')
WebUI.click(addLibraryButton)

WebUI.comment('Then dialog Create Library is displayed')
WebUI.verifyElementVisible(addLibraryDialog)

WebUI.comment('When Enter INVALID Library Name and Click Submit')
WebUI.setText(nameField, invalidName)
WebUI.click(submitButton)

WebUI.comment('Check the corresponding validated message.')
WebUI.waitForElementPresent(txtInvalidMessage, 20, FailureHandling.CONTINUE_ON_FAILURE)
def message = WebUI.getText(txtInvalidMessage, FailureHandling.CONTINUE_ON_FAILURE)
WebUI.verifyMatch(message, invalidFeedback, false, FailureHandling.CONTINUE_ON_FAILURE)

WebUI.click(cancelButton)