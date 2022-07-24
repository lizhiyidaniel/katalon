import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver

import com.database.Database as DB
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.util.KeywordUtil as KeyworUtil
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import generator.DynamicGenerator
import groovy.transform.Field
import internal.GlobalVariable

WebUI.comment('Click Multiple Choice on Question types')

WebUI.click(findTestObject('Object Repository/Module_Navigation/btn_Libraries'))

WebUI.waitForElementVisible(findTestObject('Object Repository/Page_Libraries/link_homeLib'),5)

WebUI.comment('Clear all search keywords')

WebUI.setText(findTestObject('Page_Libraries/tbx_SearchQuestion'), criteria)

WebUI.comment('Click search')

WebUI.click(findTestObject('Page_Libraries/btn_Search'))

'Get query result from database'

@Field String testLibraryNameUnique = testLibraryName + DynamicGenerator.getInstance().getDynamicVariableByClassName(getClass())

def libraryId = WebUI.findWebElement(findTestObject('Page_Libraries/btn_libraryName_relyOnName', ['libraryName': testLibraryNameUnique]), 10).getAttribute('id')

def questionIds = getResultFromDatabase(libraryId)

'Verify'
try {
	def questionElements = WebUI.findWebElements(findTestObject('Page_Libraries/cpn_Questions'), 5)

	for (int i = 0; i < questionIds.size(); i++) {
		try {
			if (!(questionElements.get(i).getAttribute('id').equals(questionIds.get(i).get(0).toString()))) {
				KeyworUtil.markFailed('Question\'s id is not matched')
			}
		}
		catch (Exception ex) {
			KeyworUtil.markFailed('Questions are not matched')
		}
	}
}
catch (Exception ex) {
	if (ids.size() != 0) {
		KeyworUtil.markFailed('Questions are not matched')
	}
}

@com.kms.katalon.core.annotation.SetUp
def setUp() {
	WebDriver driver = DriverFactory.getWebDriver()

	'Login'
	WebUI.comment('Precondition: User already logged in')

	WebUI.callTestCase(findTestCase('Test Cases/Common Test Cases/Login/Login'), [('Email') : GlobalVariable.G_Email, ('Password') : GlobalVariable.G_Password],
		FailureHandling.STOP_ON_FAILURE)

	'Create library'
	WebUI.comment('Click Libraries on the sidebar')

	WebUI.click(findTestObject('Object Repository/Module_Navigation/btn_Libraries'))
	
	WebUI.waitForElementVisible(findTestObject('Object Repository/Page_Libraries/link_homeLib'),5)
	
	def libraryElementsXpath = '//*[@id="library-container"]/*[@id]'
	
	def numOfLibraries = driver.findElements(By.xpath(libraryElementsXpath)).size()
	
	if (numOfLibraries == 0) {
		WebUI.callTestCase(findTestCase('Common Test Cases/Library/Create New Library In Empty Page'), [('validName') : testLibraryNameUnique])
	} else {
		WebUI.callTestCase(findTestCase('Common Test Cases/Library/Create New Library'), [('validName') : testLibraryNameUnique])
	}

	WebUI.comment('Click an existing library')

	WebUI.click(findTestObject('Page_Libraries/btn_libraryName_relyOnName', ['libraryName': testLibraryNameUnique]))

	'Create MCQs'
	WebUI.comment('Create MCQs')
	
	for (question in questions.allData) {
		WebUI.callTestCase(findTestCase('Common Test Cases/Question/Create Multiple Choice Question'), [
			'libraryName': testLibraryNameUnique,
			'statement': question[0]
			]
		)
	}

	'Connect to database'
	WebUI.comment('Connect to database')
	DB.connect(GlobalVariable.G_DB_HOST, GlobalVariable.G_DB_NAME, GlobalVariable.G_DB_PORT, GlobalVariable.G_DB_USERNAME,
		GlobalVariable.G_DB_PASSWORD)
}

@com.kms.katalon.core.annotation.TearDown
def tearDown() {
	'Delete library'
	WebUI.click(findTestObject('Page_Libraries/More_Action/span_moreToggle_relyOnName', ['libraryName': testLibraryNameUnique]))
	
	WebUI.click(findTestObject('Page_Libraries/More_Action/deleteOption_relyOnName', ['libraryName': testLibraryNameUnique]))
	
	WebUI.click(findTestObject('Page_Libraries/More_Action/btn_confirmDelete'))
	

	'Close database connection'
	DB.close()
}

def getResultFromDatabase(String libraryId) {
	def query = "SELECT q.id FROM question q WHERE q.library_id='" + libraryId + "' AND q.question_type='Multiple Choice' AND to_tsvector(q.statement || ' ' || array_to_string(q.tags, ' ')) @@ to_tsquery('" + criteria + "') ORDER BY q.created_at DESC;"
	
	def result = DB.execute(query)

	return result
}