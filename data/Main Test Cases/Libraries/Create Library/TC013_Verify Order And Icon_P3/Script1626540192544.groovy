import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver

import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

WebUI.comment('Story: [ON] As a Recruiter, I want to create question library.')

def testLibraryName1Unique = testLibraryName1 + CustomKeywords.'generator.randomString.generateRandomString'(10)
def testLibraryName2Unique = testLibraryName2 + CustomKeywords.'generator.randomString.generateRandomString'(10)

// SET UP
def libraryNavItem = findTestObject('Object Repository/Module_Navigation/nav_item_libraries')
def linkHomeLibrary = findTestObject('Object Repository/Page_Libraries/link_homeLib')

WebUI.comment('SET UP')
WebUI.comment('Given I am logged in and there is at least TWO libraries,')
	
'Login'
WebUI.callTestCase(findTestCase('Common Test Cases/Login/Login'), null)
	
'Click Libraries on the menu.'
WebUI.click(libraryNavItem)
	
WebUI.waitForElementVisible(linkHomeLibrary, 5)
	
'make sure there is at least TWO library'
WebUI.callTestCase(findTestCase('Common Test Cases/Library/Create New MCQ'), ['libraryName': testLibraryName1Unique])
WebUI.callTestCase(findTestCase('Common Test Cases/Library/Create New MCQ'), ['libraryName': testLibraryName2Unique])

// MAIN BODY
def addQuestionButton = findTestObject('Page_Libraries/btn_addQuestion')

WebUI.comment('MAIN BODY')
'Find the number of libraries'
WebDriver driver = DriverFactory.getWebDriver()
def numOfLibraries = driver.findElements(By.xpath(libraryElementsXpath)).size()

WebUI.comment('Verify system displays the listing of MCQ questions of the 1st library in the library list by default,')
WebUI.verifyElementVisible(addQuestionButton, FailureHandling.CONTINUE_ON_FAILURE)

WebUI.comment('Verify the order of Question Types on sub-menu,')
if (true) {
	WebUI.verifyElementText(findTestObject('Page_Libraries/btn_questionType', ['type': "mcq"]), "Multiple Choice", FailureHandling.CONTINUE_ON_FAILURE)
	WebUI.verifyElementText(findTestObject('Page_Libraries/btn_questionType', ['type': "subjective"]), "Subjective", FailureHandling.CONTINUE_ON_FAILURE)
	WebUI.verifyElementText(findTestObject('Page_Libraries/btn_questionType', ['type': "programming"]), "Programming", FailureHandling.CONTINUE_ON_FAILURE)
	WebUI.verifyElementText(findTestObject('Page_Libraries/btn_questionType', ['type': "file"]), "File Upload", FailureHandling.CONTINUE_ON_FAILURE)
}

WebUI.comment('Verify More Action icon next to each library name,')
if (true) {
	for(int i = 0; i < numOfLibraries; i++) {
		WebUI.verifyElementVisible(findTestObject('Page_Libraries/More_Action/span_moreToggle_relyOnOrder', ['order': i]), FailureHandling.CONTINUE_ON_FAILURE)
	}
}

WebUI.comment('Click More Action, system shows menu,')
if (true) {
	WebUI.click(findTestObject('Page_Libraries/More_Action/span_moreToggle_relyOnOrder', ['order': 0]))
	'Update library option'
	WebUI.verifyElementText(findTestObject('Page_Libraries/More_Action/updateOption_relyOnOrder', ['order': 1]), 'Update library', FailureHandling.CONTINUE_ON_FAILURE)
	'Delete library option'
	WebUI.verifyElementText(findTestObject('Page_Libraries/More_Action/deleteOption_relyOnOrder', ['order': 1]), 'Delete library', FailureHandling.CONTINUE_ON_FAILURE)
}

WebUI.comment('Verify Order of Libraries on sub-menu: sort alphabetically by library name, ascending order,')
if (true) {
	int i = 1;
	while (i < numOfLibraries) {
		def currentLibraryName = WebUI.getText(findTestObject('Page_Libraries/btn_libraryName_relyOnOrder', ['order': i]))
		WebUI.comment(currentLibraryName)
		def afterLibraryName = WebUI.getText(findTestObject('Page_Libraries/btn_libraryName_relyOnOrder', ['order': i + 1]))
		WebUI.comment(afterLibraryName)
		assert currentLibraryName <= afterLibraryName
		i = i + 1;
	}
}

WebUI.click(libraryNavItem)

// TEAR DOWN
WebUI.comment('TEAR DOWN')
'delete the test libary'
WebUI.callTestCase(findTestCase('Common Test Cases/Library/Tear Down Test Library'), ['libraryName': testLibraryName1Unique])
WebUI.callTestCase(findTestCase('Common Test Cases/Library/Tear Down Test Library'), ['libraryName': testLibraryName2Unique])