import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver

import com.kms.katalon.core.annotation.SetUp
import com.kms.katalon.core.annotation.TearDown
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import org.openqa.selenium.Keys as Keys

WebUI.comment('Story: [ON] As a Recruiter, I want to delete question libraries')
def name = 'Test Lib ' + System.currentTimeMillis()

if (true) {
	'Login'
	WebUI.callTestCase(findTestCase('Common Test Cases/Login/Login'), null)
	
	'When I click Libraries on the menu'
	WebUI.click(findTestObject('Object Repository/Module_Navigation/nav_item_libraries'))
	
	WebUI.waitForElementVisible(findTestObject('Object Repository/Page_Libraries/link_homeLib'),5)
	
	'Find the number of libraries'
	def numOfLibraries = WebUI.findWebElements(findTestObject('Object Repository/Page_Libraries/cpn_Library'), 5).size()
	
	'Make sure there is at least one library'
	if (numOfLibraries == 0) {
		WebUI.callTestCase(findTestCase('Common Test Cases/Library/Create New Library In Empty Page'), ["validName": name])
	} else {		
		WebUI.callTestCase(findTestCase('Common Test Cases/Library/Create New Library'), ["validName": name])
	}
}

@TearDown()
def tearDown() {
	'Delete the test libary'
	WebUI.callTestCase(findTestCase('Common Test Cases/Library/Tear Down Test Library'), ['libraryName': name])
}
WebUI.waitForPageLoad(10)

'Click on more action on the existing Library'
WebUI.click(findTestObject("Page_Libraries/More_Action/span_moreToggle_relyOnName", ["libraryName":name]))

'Click Delete library'
WebUI.click(findTestObject("Page_Libraries/More_Action/deleteOption_relyOnName", ["libraryName":name]))

'Click Cancel'
WebUI.click(findTestObject('Page_Libraries/More_Action/btn_Cancel'))

'The library is still displayed in the library list'
WebUI.verifyTextPresent(name, false, FailureHandling.STOP_ON_FAILURE)
WebUI.takeScreenshotAsCheckpoint("Delete one Library Name unsuccessfully")

