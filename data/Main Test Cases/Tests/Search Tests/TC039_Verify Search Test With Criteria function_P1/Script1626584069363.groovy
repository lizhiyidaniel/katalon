import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject

import com.kms.katalon.core.annotation.SetUp
import com.kms.katalon.core.annotation.TearDown
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testng.keyword.TestNGBuiltinKeywords as TestNGKW
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys
import com.database.Database as DB
import org.openqa.selenium.WebElement as WebElement
import org.openqa.selenium.By as By
import org.openqa.selenium.WebDriver as WebDriver
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import com.kms.katalon.core.util.KeywordUtil as KeyworUtil
import groovy.transform.Field
import generator.DynamicGenerator
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import internal.GlobalVariable as GlobalVariable
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.common.WebUiCommonHelper
import org.openqa.selenium.JavascriptExecutor

@Field String randomString = DynamicGenerator.getInstance().getDynamicVariableByClassName(getClass())

@SetUp()
setup(){
	WebDriver driver = DriverFactory.getWebDriver()
	'Login'
	WebUI.comment('Precondition: User already logged in')

	WebUI.callTestCase(findTestCase('Test Cases/Common Test Cases/Login/Login'), [('Email') : GlobalVariable.G_Email, ('Password') : GlobalVariable.G_Password],
		FailureHandling.STOP_ON_FAILURE)
	'create list of test'
	WebUI.callTestCase(findTestCase('Test Cases/Common Test Cases/Test/Insert Test To DB'), ['testData':testData,'randomString':randomString])
	'Connect to database'
	WebUI.comment('Connect to database')
	DB.connect(GlobalVariable.G_DB_HOST, GlobalVariable.G_DB_NAME, GlobalVariable.G_DB_PORT, GlobalVariable.G_DB_USERNAME,
	GlobalVariable.G_DB_PASSWORD)
	
	"click on Tests sidebar"
	WebUI.click(findTestObject('Object Repository/Module_Navigation/nav_item_tests'))
}
@TearDown()
def tearDown() {
	'Delete all test'
	WebUI.callTestCase(findTestCase('Test Cases/Common Test Cases/Test/Delete Tests By Key'), ['key':randomString])
	'Close database connection'
	DB.close()
}
"Input search key and press enter to search"
WebUI.click(findTestObject('Object Repository/Page_SearchTests/txt_search'))
WebUI.setText(findTestObject('Object Repository/Page_SearchTests/txt_search'),randomString+'_'+searchKey)
WebUI.sendKeys(findTestObject('Object Repository/Page_SearchTests/txt_search'), Keys.chord(Keys.ENTER))
WebUI.delay(30)
"View and compare result"
def status = ["Ongoing","Completed","Draft"]
def countStatus = countAllByStatus()
for (int j = 0; j<status.size;j++) {
	"Verify count of test by status"
	def statusNavMessage = WebUI.getText(findTestObject('Object Repository/Page_SearchTests/nav_testStatus',["order":j+1]))
	WebUI.click(findTestObject('Object Repository/Page_SearchTests/nav_testStatus',["order":j+1]))
	def index = countStatus.findIndexOf {it-> it.get(0) == status[j] }
	def numberOfTests = index!=-1 ? countStatus[index].get(1) : '0'
	WebUI.verifyEqual(statusNavMessage, "${status[j]} (${numberOfTests})")
	def testIds = findTestByStatus(status[j])
	
	'Verify every test which query from db with UI'
	def testElements = WebUI.findWebElements(findTestObject('Object Repository/Page_SearchTests/cpn_tests'), 5)
	try {
		for (int i = 0; i < testIds.size(); i++) {
			try {
				if (!(testElements.get(i).getAttribute('id').trim().equals(testIds.get(i).get(0).toString()))) {
					KeyworUtil.markFailed('Test\'s id is not matched')
				}
				'Verify option of test'
				//WebUI.click(findTestObject('Object Repository/Page_SearchTests/toggle_more',["id":testElements.get(i).getAttribute('id')]))
				def toggleElements = findTestObject('Object Repository/Page_SearchTests/toggle_more',["id":testElements.get(i).getAttribute('id')])
				
				WebUI.waitForElementPresent(toggleElements, 10, FailureHandling.STOP_ON_FAILURE)
				WebUI.scrollToElement(toggleElements, 30, FailureHandling.STOP_ON_FAILURE)
				WebDriver driver = DriverFactory.getWebDriver()
				WebElement element = WebUiCommonHelper.findWebElement(toggleElements, 5)
				JavascriptExecutor executor = ((driver) as JavascriptExecutor)
				executor.executeScript('arguments[0].click()', element)
				switch (status[j]) {
					case "Ongoing": 
						def firstElem = WebUI.getText(findTestObject('Object Repository/Page_SearchTests/txt_dropdown_menu',
							["id":testElements.get(i).getAttribute('id'), 'order':1]))
						WebUI.verifyEqual(firstElem, "Complete test")
						def secondElem = WebUI.getText(findTestObject('Object Repository/Page_SearchTests/txt_dropdown_menu',
							["id":testElements.get(i).getAttribute('id'), 'order':2]))
						WebUI.verifyEqual(secondElem, "Clone test")
							break
					case "Completed":
						def firstElem = WebUI.getText(findTestObject('Object Repository/Page_SearchTests/txt_dropdown_menu',
							["id":testElements.get(i).getAttribute('id'), 'order':1]))
						WebUI.verifyEqual(firstElem, "Clone test")
							break
					case "Draft":
						def firstElem = WebUI.getText(findTestObject('Object Repository/Page_SearchTests/txt_dropdown_menu',
							["id":testElements.get(i).getAttribute('id'), 'order':1]))
						WebUI.verifyEqual(firstElem, "Delete test")
						def secondElem = WebUI.getText(findTestObject('Object Repository/Page_SearchTests/txt_dropdown_menu',
							["id":testElements.get(i).getAttribute('id'), 'order':2]))
						WebUI.verifyEqual(secondElem, "Clone test")
							break
				}
				
				
			}
			catch (Exception ex) {
				ex.printStackTrace()
				KeyworUtil.markFailed('Tests are not matched')
			}
		}
	}
	catch (Exception ex) {
	    if (testIds.size() != 0) {
	        KeyworUtil.markFailed('Tests are not matched')
	    }
} 
		
}

def findTestByStatus(status) {
	def query = "SELECT t.id FROM test t WHERE t.status='" + status +"'"+ 
	"and (to_tsvector( t.name || ' ' || t.description || ' ' || array_to_string(t.tags, ' ')) @@ to_tsquery('"+randomString+'_'+searchKey+"')"+
	"or LOWER(t.name) like LOWER('%"+randomString+'_'+searchKey+"%' ));"
	def result = DB.execute(query)
	return result
}
def countAllByStatus() {
	def query = "SELECT t.status, count(t.id)::int FROM test t "+
	" where to_tsvector( t.name || ' ' || t.description || ' ' || array_to_string(t.tags, ' ')) @@ to_tsquery('"+randomString+'_'+searchKey+"')"+
	" or LOWER(t.name) like LOWER('%"+randomString+'_'+searchKey+"%' ) group by(t.status);"
	def result = DB.execute(query)
	return result
}