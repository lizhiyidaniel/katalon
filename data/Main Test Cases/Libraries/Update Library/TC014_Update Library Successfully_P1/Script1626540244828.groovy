import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.annotation.SetUp
import com.kms.katalon.core.annotation.TearDown
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import internal.GlobalVariable as GlobalVariable

WebUI.comment('Story: [ON] As a Recruiter, I want to update library.')

def testLibraryName = 'Test Lib ' + System.currentTimeMillis()
def updateLibraryName = 'Test lib update ' + System.currentTimeMillis()

'Set up'
if(true) {
	WebUI.comment('Given I am logged in and there is at least ONE libraries,')
	
	'Login'
	WebUI.callTestCase(findTestCase('Common Test Cases/Login/Login'), null)
	
	WebUI.comment('When I click Libraries on the menu,')
	WebUI.click(findTestObject('Object Repository/Module_Navigation/nav_item_libraries'))
	
	WebUI.waitForElementVisible(findTestObject('Object Repository/Page_Libraries/link_homeLib'),5)
	
	'create Testing MCQ Question.'
	WebUI.callTestCase(findTestCase('Common Test Cases/Library/Create New MCQ'), ['libraryName': testLibraryName])
}

WebUI.comment('Click an existing Library')
WebUI.click(findTestObject('Page_Libraries/btn_libraryName_relyOnName', ['libraryName': testLibraryName]))

WebUI.comment('Click Update Library')
WebUI.click(findTestObject('Page_Libraries/More_Action/span_moreToggle_relyOnName', ['libraryName': testLibraryName]))
WebUI.click(findTestObject('Page_Libraries/More_Action/updateOption_relyOnName', ['libraryName': testLibraryName]))

WebUI.comment('Verify update library dialog appear')
WebUI.verifyElementPresent(findTestObject('Page_Libraries/dialog_UpdateLibrary'), 30)

WebUI.comment('Input valid data')
if (true) {
	WebUI.setText(findTestObject('Page_Libraries/Library_Dialog/tbx_LibraryName'), updateLibraryName)
}

WebUI.comment('Submit change')
WebUI.click(findTestObject('Page_Libraries/Question_Dialog/btn_Submit'))

WebUI.comment('Verify update library success')
if (true) {
	WebUI.verifyElementNotPresent(findTestObject('Page_Libraries/dialog_UpdateLibrary'), 30)
	
	WebUI.waitForPageLoad(10)
	
	WebUI.takeScreenshotAsCheckpoint('Update Library Name')
	
	WebUI.verifyTextPresent('Update library successfully.', false, FailureHandling.STOP_ON_FAILURE)
}

'Tear down'
if (true) {
	'delete the test libary'
	WebUI.callTestCase(findTestCase('Common Test Cases/Library/Tear Down Test Library'), ['libraryName': updateLibraryName])
}
