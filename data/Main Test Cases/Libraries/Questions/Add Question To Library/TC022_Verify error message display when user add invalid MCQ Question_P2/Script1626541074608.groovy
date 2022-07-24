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

WebUI.comment('Click Add Multiple Choice Question')
WebUI.click(findTestObject('Page_Libraries/btn_questionType', ['type': 'mcq']))
WebUI.click(findTestObject('Page_Libraries/btn_addQuestion'))

WebUI.comment('Verify dialog Add Multiple Choice Question is displayed')
WebUI.verifyElementVisible(findTestObject('Page_Libraries/dialog_addQuestion'))

WebUI.comment('Enter invalid values')
if (true) {
	WebUI.click(findTestObject('Page_Libraries/Question_Dialog/txt_ProblemAdd'))
	
	WebUI.sendKeys(findTestObject('Page_Libraries/Question_Dialog/txt_ckeditor_problem'), Problem)
	
	WebUI.click(findTestObject('Page_Libraries/Question_Dialog/lbl_Multiple_Choose',[('CHOOSE_TYPE'):ChooseType]))
	
	def answers = Answers
	
	List pending = WebUI.findWebElements(findTestObject('Page_Libraries/Question_Dialog/tbx_ChoiceValue'),30)
	
	int closetabsSize = pending.size()
	
	if (closetabsSize > 0) {
		for (int i = 0; i < closetabsSize; i++) {
			pending.get(i).sendKeys(answers[i])
		}
	}
	
	WebUI.setText(findTestObject('Page_Libraries/Question_Dialog/tbx_MaxTime'), '13')
	
	WebUI.selectOptionByValue(findTestObject('Page_Libraries/Question_Dialog/slt_DifficultyLevel'), DifficultLevel, false)
	
	for(tag in Tags) {
		
		WebUI.sendKeys(findTestObject('Page_Libraries/Question_Dialog/tbx_Tags'), tag)
		
		WebUI.sendKeys(findTestObject('Page_Libraries/Question_Dialog/tbx_Tags'), Keys.chord(Keys.ENTER))
		
	}
}

WebUI.comment('Click Add')
WebUI.click(findTestObject('Page_Libraries/Question_Dialog/btn_Submit'))

WebUI.comment('Verify error message is displayed')
if (true) {
	def message;
	"order is the order of invalid feedback rely on the order of form's field"
	WebUI.comment('Verify invalid message in problem statement')

	WebUI.waitForElementPresent(findTestObject('Page_Libraries/Question_Dialog/txt_MCQ_ErrorMessage', ['order': 1]), 20, FailureHandling.CONTINUE_ON_FAILURE)
	message = WebUI.getText(findTestObject('Page_Libraries/Question_Dialog/txt_MCQ_ErrorMessage', ['order': 1]), FailureHandling.CONTINUE_ON_FAILURE)
	WebUI.verifyMatch(message, invalidFeedback[0], false)
	
	WebUI.comment('Verify invalid message in option fields')
	WebUI.waitForElementPresent(findTestObject('Page_Libraries/Question_Dialog/txt_MCQ_ErrorMessage', ['order': 2]), 20, FailureHandling.CONTINUE_ON_FAILURE)
	message = WebUI.getText(findTestObject('Page_Libraries/Question_Dialog/txt_MCQ_ErrorMessage', ['order': 2]), FailureHandling.CONTINUE_ON_FAILURE)
	WebUI.verifyMatch(message, invalidFeedback[1], false)
	
	WebUI.comment('Verify invalid message in answer fields')
	WebUI.waitForElementPresent(findTestObject('Page_Libraries/Question_Dialog/txt_MCQ_ErrorMessage', ['order': 3]), 20, FailureHandling.CONTINUE_ON_FAILURE)
	message = WebUI.getText(findTestObject('Page_Libraries/Question_Dialog/txt_MCQ_ErrorMessage', ['order': 3]), FailureHandling.CONTINUE_ON_FAILURE)
	WebUI.verifyMatch(message, invalidFeedback[2], false)

}

WebUI.comment('Click Cancel')
WebUI.click(findTestObject('Page_Libraries/Question_Dialog/btn_Cancel'))
