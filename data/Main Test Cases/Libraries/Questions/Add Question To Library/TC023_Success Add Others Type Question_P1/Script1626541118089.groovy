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
import groovy.transform.Field
import generator.DynamicGenerator
@Field String randomString = DynamicGenerator.getInstance().getDynamicVariableByClassName(getClass())

WebUI.comment('Story: [ON] As a Recruiter, I want to add questions to a library.')

@SetUp()
def setUp( ) {
	WebUI.comment('Given I am logged in and there is at least ONE libraries,')
	
	'Login'
	WebUI.callTestCase(findTestCase('Common Test Cases/Login/Login'), null)
	
	WebUI.comment('When I click Libraries on the menu,')
	WebUI.click(findTestObject('Object Repository/Module_Navigation/nav_item_libraries'))
	
	WebUI.waitForElementVisible(findTestObject('Object Repository/Page_Libraries/link_homeLib'),5)
	
	'Create test library'
	WebDriver driver = DriverFactory.getWebDriver()
	def numOfLibraries = driver.findElements(By.xpath(libraryElementsXpath)).size()
	
	if (numOfLibraries == 0) {
		WebUI.callTestCase(findTestCase('Common Test Cases/Library/Create New Library In Empty Page'), ["validName": testLibraryName+randomString])
	} else {
		WebUI.callTestCase(findTestCase('Common Test Cases/Library/Create New Library'), ["validName": testLibraryName+randomString])
	}
}

@TearDown()
def tearDown() {
	'delete the test libary'
	WebUI.callTestCase(findTestCase('Common Test Cases/Library/Tear Down Test Library'), ['libraryName': testLibraryName+randomString])
}

WebUI.comment('Click an existing Library')
WebUI.click(findTestObject('Page_Libraries/btn_libraryName_relyOnName', ['libraryName': testLibraryName+randomString]))

def questionType;
def questionTypeId;
for (int i = 2; i <= 4; i++) {
	switch(i) {
		case 2:
			questionType = "Subjective"
			questionTypeId = "subjective"
			break;
		case 3:
			questionType = "Programming"
			questionTypeId = "programming"
			break;
		case 4:
			questionType = "File Upload"
			questionTypeId = "file"
			break;
	}
	WebUI.comment('Click Add' + questionType + 'Question')
	WebUI.click(findTestObject('Page_Libraries/btn_questionType', ['type': questionTypeId]))
	WebUI.click(findTestObject('Page_Libraries/btn_addQuestion'))
	
	WebUI.comment('Verify dialog Add' + questionType + 'Question is displayed')
	WebUI.verifyElementVisible(findTestObject('Page_Libraries/dialog_addQuestion'))
	
	WebUI.takeScreenshotAsCheckpoint('Add other type question to Library')
	
	WebUI.comment('Verify required fields are marked with a red asterisk after a label name')
	'modify verify later, because spell to much time.'
	
	WebUI.comment('Enter valid values')
	if (true) {
		WebUI.click(findTestObject('Page_Libraries/Question_Dialog/txt_ProblemAdd'))
		
		WebUI.sendKeys(findTestObject('Page_Libraries/Question_Dialog/txt_ckeditor_problem'), Problem)
		
		WebUI.sendKeys(findTestObject('Page_Libraries/Question_Dialog/txt_ckeditor_problem'), Keys.chord(Keys.TAB))
		
		WebUI.sendKeys(findTestObject('Page_Libraries/Question_Dialog/txt_ckeditor_SuggestedAnswer'), SuggestAnswer)
		
		WebUI.selectOptionByValue(findTestObject('Page_Libraries/Question_Dialog/slt_DifficultyLevel'), 'Medium', false)
		
		WebUI.sendKeys(findTestObject('Page_Libraries/Question_Dialog/tbx_Tags'), Tags)
		
		WebUI.sendKeys(findTestObject('Page_Libraries/Question_Dialog/tbx_Tags'), Keys.chord(Keys.ENTER))
	}
	WebUI.comment('Click Add')
	WebUI.click(findTestObject('Page_Libraries/Question_Dialog/btn_Submit'))
	
	WebUI.comment('Verify' + questionType + 'is added to Library successfully, show a successful message and redirect to Questions listing page of that question type.')
	if (true) {
		WebUI.waitForElementPresent(findTestObject('Page_Libraries/Question_Dialog/toast_Sucess'), 20, FailureHandling.STOP_ON_FAILURE)
		
		def message = WebUI.getText(findTestObject('Page_Libraries/Question_Dialog/toast_Sucess'))
		
		WebUI.verifyMatch(message, SuccessMessage, false)
	}
}
