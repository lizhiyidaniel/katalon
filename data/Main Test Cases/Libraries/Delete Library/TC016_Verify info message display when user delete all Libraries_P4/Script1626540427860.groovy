import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver

import com.kms.katalon.core.annotation.SetUp
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import org.openqa.selenium.Keys as Keys

WebUI.comment('Story: [ON] As a Recruiter, I want to delete question libraries')

def name = 'Test Lib ' + System.currentTimeMillis()

'Set up'
if (true) {
	'Login'
	WebUI.callTestCase(findTestCase('Common Test Cases/Login/Login'), null)
	
	'When I click Libraries on the menu'
	WebUI.click(findTestObject('Object Repository/Module_Navigation/nav_item_libraries'))
	
	'Find the number of libraries'
	def numOfLibraries = WebUI.findWebElements(findTestObject('Object Repository/Page_Libraries/cpn_Library'), 5).size()
	
	'Make sure there is at least one library'
	if (numOfLibraries == 0) {
		WebUI.callTestCase(findTestCase('Common Test Cases/Library/Create New Library In Empty Page'), ["validName": name])
	} else {		
		WebUI.callTestCase(findTestCase('Common Test Cases/Library/Create New Library'), ["validName": name])
	}
}

WebUI.waitForPageLoad(10)
'Find the number of libraries'
def numOfLibraries = WebUI.findWebElements(findTestObject('Object Repository/Page_Libraries/cpn_Library'), 5).size()

'Delete all library'
if (numOfLibraries != 0) {
		int i = 1;
		while (i <= numOfLibraries) {
			'Click More action on the library'
			WebUI.click(findTestObject('Object Repository/Page_Libraries/More_Action/span_moreToggle_relyOnOrder', ['order': 0]))
			'Click on Delete option'
			WebUI.click(findTestObject('Object Repository/Page_Libraries/More_Action/deleteOption_relyOnOrder', ['order': 1]))
			'Click OK to confirm'
			WebUI.click(findTestObject('Object Repository/Page_Libraries/More_Action/btn_confirmDelete'))
			'Verify Library Name is deleted successfully'
			WebUI.verifyTextPresent("Delete library successfully.", false, FailureHandling.STOP_ON_FAILURE)
			WebUI.waitForPageLoad(10)
			WebUI.takeScreenshotAsCheckpoint("Delete All Library Name")
			i = i + 1
		}
	}

'Verify there are no libraries yet'
WebUI.verifyTextPresent("You have no libraries yet.", false, FailureHandling.STOP_ON_FAILURE)