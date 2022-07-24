import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.annotation.SetUp
import com.kms.katalon.core.annotation.TearDown
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import generator.DynamicGenerator
import groovy.transform.Field

@Field String testLibraryNameUnique = testLibraryName + DynamicGenerator.getInstance().getDynamicVariableByClassName(getClass())
@SetUp()
def setUp( ) {
	WebUI.comment('Given I am logged in and there is at least ONE libraries,')
	
	'Login'
	WebUI.callTestCase(findTestCase('Common Test Cases/Login/Login'), null)
	
	WebUI.comment('When I click Libraries on the menu,')
	WebUI.click(findTestObject('Object Repository/Module_Navigation/nav_item_libraries'))
	
	WebUI.waitForElementVisible(findTestObject('Object Repository/Page_Libraries/link_homeLib'),5)
	
	'create Testing Subjective Question.'
	WebUI.callTestCase(findTestCase('Common Test Cases/Library/Create New Subjective'), ['libraryName': testLibraryNameUnique])
}

@TearDown()
def tearDown() {
	'delete the test libary'
	WebUI.callTestCase(findTestCase('Common Test Cases/Library/Tear Down Test Library'), ['libraryName': testLibraryNameUnique])
}

WebUI.comment('Click an existing Library')
WebUI.click(findTestObject('Page_Libraries/btn_libraryName_relyOnName', ['libraryName': testLibraryNameUnique]))

WebUI.comment('Click Subjective on Question types')
WebUI.click(findTestObject('Page_Libraries/btn_questionType', ['type': 'subjective']))

WebUI.comment('Click Subjective need to update')
WebUI.click(findTestObject('Page_Libraries/btn_questionList', ['order': 1]))

WebUI.comment('Verify dialog Update Subjective Question is displayed')
WebUI.waitForElementPresent(findTestObject('Page_Libraries/Question_Dialog/dialog_UpdateQuestion'), 10)

WebUI.comment('Enter invalid values')
if (true) {
	statement = WebUI.getText(findTestObject('Page_Libraries/txt_QuestionStatement', ['order': 1]))
	
	difficulty = WebUI.getText(findTestObject('Page_Libraries/txt_QuestionDifficulty', ['order': 1]))
	
	WebUI.clearText(findTestObject('Page_Libraries/Question_Dialog/txt_ProblemUpdate'), FailureHandling.STOP_ON_FAILURE)
	
	WebUI.clearText(findTestObject('Page_Libraries/Question_Dialog/txt_SuggestedAnswerUpdate'), FailureHandling.STOP_ON_FAILURE)
	
	WebUI.clearText(findTestObject('Page_Libraries/Question_Dialog/tbx_Tags'), FailureHandling.STOP_ON_FAILURE)
}

WebUI.comment('Click Submit')
WebUI.click(findTestObject('Page_Libraries/Question_Dialog/btn_Submit'))

WebUI.comment('Verify error message is displayed')
if (true) {
	WebUI.verifyElementText(findTestObject('Page_Libraries/Question_Dialog/txt_InvalidStatement'), 'This field is required.')
	//inbug- block temporary
	//WebUI.verifyElementText(findTestObject('Page_Libraries/Question_Dialog/txt_InvalidSugestedAnswer'), 'This field is required.')
}

WebUI.comment('Click Cancel')
WebUI.click(findTestObject('Page_Libraries/Question_Dialog/btn_Cancel'))

WebUI.comment('Verify MCQ is updated unsuccessfully, disregard changes, redirect to Questions listing page of that question type.')
if (true) {
	WebUI.verifyElementText(findTestObject('Page_Libraries/txt_QuestionDifficulty', ['order': 1]), difficulty)
	
	WebUI.verifyElementText(findTestObject('Page_Libraries/txt_QuestionStatement', ['order': 1]), statement)
}