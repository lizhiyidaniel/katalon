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
	WebUI.callTestCase(findTestCase('Common Test Cases/Test/Invite Candidate With MCQ Question'), ['name':validNameUnique, 'email':validEmail,'inviteName':validName, 'duration':false , 'multipe': false ])
	'Connect DB'
	DB.connect(GlobalVariable.G_DB_HOST, GlobalVariable.G_DB_NAME, GlobalVariable.G_DB_PORT, GlobalVariable.G_DB_USERNAME,
		GlobalVariable.G_DB_PASSWORD)
}
@TearDown()
def tearDown() {
	'Delete the candidate and test'
	WebUI.callTestCase(findTestCase('Test Cases/Common Test Cases/Test/Tear Down Challenge Test'), ['testName': validNameUnique, 'email':validEmail])
	
}
WebUI.comment('Delete all email')
CustomKeywords.'com.testwithhari.katalon.plugins.Gmail.deleteAllEMails'(validEmail, '0865800354', 'Inbox')

WebUI.waitForElementVisible(findTestObject('Object Repository/Page_TakeTest/txt_Candidate Item',['status':'Invited']), 10)
//Click to Candidate status
WebUI.click(findTestObject('Object Repository/Page_TakeTest/txt_Candidate Item',['status':'Invited']))

WebUI.verifyElementPresent(findTestObject('Object Repository/Page_TakeTest/txt_Email Cell',['email':validEmail]), 10, FailureHandling.STOP_ON_FAILURE)

WebUI.click(findTestObject('Object Repository/Page_TestInvited/btn_Cancel Candidate',['email':validEmail]))

def message = WebUI.getText(findTestObject('Object Repository/Page_TestInvited/txt_Cancel_Message'))
WebUI.verifyMatch(message, 'Are you sure you want to cancel this candidate to take the test?', false, FailureHandling.STOP_ON_FAILURE)

WebUI.click(findTestObject('Object Repository/Page_TestInvited/btn_Cancel_Cancel'))

WebUI.verifyElementNotPresent(findTestObject('Object Repository/Page_TestInvited/cpn_Cancel_Modal'), 10)

def numberOfEmail = CustomKeywords.'com.testwithhari.katalon.plugins.Gmail.getEmailsCount'(validEmail, '0865800354', 'Inbox')
WebUI.verifyMatch(numberOfEmail.toString(), '0', false, FailureHandling.STOP_ON_FAILURE)