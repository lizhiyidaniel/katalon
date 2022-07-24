import org.openqa.selenium.By
import org.openqa.selenium.Keys
import org.openqa.selenium.WebDriver
import internal.GlobalVariable as GlobalVariable
import com.kms.katalon.core.annotation.SetUp
import com.kms.katalon.core.annotation.TearDown
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import java.sql.Date
import java.text.SimpleDateFormat

import com.database.Database as DB
import generator.DynamicGenerator
import groovy.transform.Field

@Field String validNameUnique = "Ontest283 TC2 " + DynamicGenerator.getInstance().getDynamicVariableByClassName(getClass())
@Field String validEmail = "katalontestingontest@gmail.com"
@Field String validName = "Hoa Pham"

@SetUp()
def setUp( ) {
	WebDriver driver = DriverFactory.getWebDriver()
	
	'Invite'
	WebUI.callTestCase(findTestCase('Test Cases/Common Test Cases/Test/Invite Candidate With Question'), ['name':validNameUnique, 'email':validEmail,'inviteName':validName, 'duration':false , 'multipe': false, 'snapshot': true ])
	'Connect DB'
	DB.connect(GlobalVariable.G_DB_HOST, GlobalVariable.G_DB_NAME, GlobalVariable.G_DB_PORT, GlobalVariable.G_DB_USERNAME,
		GlobalVariable.G_DB_PASSWORD)
}
@TearDown()
def tearDown() {
	'Delete the candidate and test'
	WebUI.callTestCase(findTestCase('Test Cases/Common Test Cases/Test/Tear Down Challenge Test'), ['testName': validNameUnique, 'email':validEmail])
	
}
def getTestUrl() {
	def url = StringBuilder.newInstance()
	url<<GlobalVariable.G_SiteURL
	url<<"/challenge"
	def query = "SELECT assignment.id from assignment JOIN candidate on assignment.\"candidateId\" = candidate.id where email = '${validEmail}';"
	def assignmentId = DB.execute(query).get(0).get(0)
	query = "SELECT token from candidate where email = '${validEmail}';"
	def candidateToken = DB.execute(query).get(0).get(0)
	url<<"/${assignmentId}?token=${candidateToken}"
	println(url.toString().getClass())
	return url.toString()
}

def getSubmittedTime() {
	def pattern = "dd/MM/YYYY HH:mm:ss"
	def query = "SELECT assignment.submitted_at from assignment JOIN candidate on assignment.\"candidateId\" = candidate.id where email = '${validEmail}';"
	def time = DB.execute(query).get(0).get(0)
	def date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(time.toString())
	return date.format(pattern)
}

def url = getTestUrl()
WebUI.openBrowser(url)
WebUI.waitForPageLoad(15, FailureHandling.CONTINUE_ON_FAILURE)
WebUI.waitForElementClickable(findTestObject('Page_TakeTest/btn_Enter Test'), 10, FailureHandling.STOP_ON_FAILURE)
WebUI.click(findTestObject('Object Repository/Page_TakeTest/btn_Enter Test'))

WebUI.waitForElementClickable(findTestObject('Page_TakeTest/btn_Start Test'), 65, FailureHandling.STOP_ON_FAILURE)
WebUI.click(findTestObject('Object Repository/Page_TakeTest/btn_Start Test'))

WebUI.waitForElementClickable(findTestObject('Object Repository/Page_TakeTest/btn_Load Question'), 30)
WebUI.click(findTestObject('Object Repository/Page_TakeTest/btn_Load Question'))

WebUI.click(findTestObject('Object Repository/Page_TakeTest/btn_Submit Test'))

WebUI.click(findTestObject('Object Repository/Page_TakeTest/btn_Submit Test Ok'))

WebUI.delay(10)
WebUI.openBrowser(GlobalVariable.G_SiteURL)

WebUI.maximizeWindow()

WebUI.callTestCase(findTestCase('Test Cases/Common Test Cases/Test/Navigate To Candidate'),['status':'Scored','testName':validNameUnique])

WebUI.verifyElementPresent(findTestObject('Object Repository/Page_TakeTest/txt_Email Cell',['email':validEmail]), 10, FailureHandling.STOP_ON_FAILURE)

WebUI.click(findTestObject('Object Repository/Page_SearchTests/btn_View Snapshot'))

WebUI.verifyElementVisible(findTestObject('Page_Test Submitted/txt_Candidate Fullname'))
def fulname = WebUI.getText(findTestObject('Page_Test Submitted/txt_Candidate Fullname'))
WebUI.verifyMatch(fulname, "Candidate Full Name: "+validName, false)

WebUI.verifyElementVisible(findTestObject('Object Repository/Page_Test Submitted/txt_SubmittedAt'))
def submittedAt = WebUI.getText(findTestObject('Object Repository/Page_Test Submitted/txt_SubmittedAt'))
WebUI.verifyMatch(submittedAt, "Submitted At: "+getSubmittedTime(), false)
