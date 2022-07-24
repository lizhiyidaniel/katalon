import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement

import com.database.Database as DB
import com.kms.katalon.core.annotation.SetUp
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.webui.common.WebUiCommonHelper
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import generator.DynamicGenerator
import groovy.transform.Field
import internal.GlobalVariable

@Field String validNameUnique = "Ontest273 TC1 " + DynamicGenerator.getInstance().getDynamicVariableByClassName(getClass())
@Field String validEmail = "katalontestingontest@gmail.com"
@Field String validName = "Hoa Pham"
@Field String validEmailAndFullName = validEmail + " " + validName

@SetUp
def setup() {
	def testsNavItem = findTestObject('Object Repository/Module_Navigation/nav_item_tests')
	def testNameSpan = findTestObject('Object Repository/Page_SearchTests/span_Ongoing_TestName', ['testName': validNameUnique])
	def testItemByName = findTestObject('Object Repository/Page_SearchTests/item_Ongoing_Test_By_Name', ['testName': validNameUnique])
	def inviteCandidatesButton = findTestObject('Object Repository/Page_TestOverview/btn_Invite_Candidates')
	def addCandidatesButton = findTestObject('Object Repository/Page_InviteCandidates/btn_AddCandidates')
	def addCandidatesModal = findTestObject('Object Repository/Page_InviteCandidates/modal_AddCandidates')
	def inputAddCandidates = findTestObject('Object Repository/Page_InviteCandidates/Add Candidates Modal/input_AddCandidates')
	def addButton = findTestObject('Object Repository/Page_InviteCandidates/Add Candidates Modal/btn_Add')
	def inviteButton = findTestObject('Object Repository/Page_InviteCandidates/btn_Invite')
	def confirmInviteModal = findTestObject('Object Repository/Page_InviteCandidates/modal_ConfirmInvite')
	def confirmInviteButton = findTestObject('Object Repository/Page_InviteCandidates/Confirm Invite Modal/btn_OK')
	def successToast = findTestObject('Object Repository/Module_Navigation/toast_Success')
	
	WebUI.comment('Delete all email')
	CustomKeywords.'com.testwithhari.katalon.plugins.Gmail.deleteAllEMails'(validEmail, '0865800354', 'Inbox')
	
	WebUI.comment('There is at least one candidate added to the invite list')
	if(true) {
		// Create an ongoing test
		WebUI.callTestCase(findTestCase('Test Cases/Common Test Cases/Test/Create An Ongoing Test'), ['testName': validNameUnique])
		
		// Add an candidate to invite list
		WebUI.click(testsNavItem, FailureHandling.STOP_ON_FAILURE)
		if (true) {
			WebUI.scrollToElement(testNameSpan, 3, FailureHandling.CONTINUE_ON_FAILURE)
			try {
				WebUI.click(testItemByName, FailureHandling.STOP_ON_FAILURE)
			}
			catch (Exception e) {
				WebDriver driver = DriverFactory.getWebDriver()
				WebElement element = WebUiCommonHelper.findWebElement(testItemByName, 5)
				JavascriptExecutor executor = ((driver) as JavascriptExecutor)
				executor.executeScript('arguments[0].click()', element)
			}
		}
		
		WebUI.scrollToElement(inviteCandidatesButton, 3)
		WebUI.click(inviteCandidatesButton)
		WebUI.delay(2)
		
		WebUI.click(addCandidatesButton)
		WebUI.verifyElementVisible(addCandidatesModal)
		WebUI.setText(inputAddCandidates, validEmailAndFullName)
		WebUI.click(addButton)
		WebUI.delay(5)
		
		WebUI.scrollToElement(inviteButton, 3)
		WebUI.click(inviteButton)
		
		WebUI.waitForElementVisible(confirmInviteModal, 3)
		WebUI.click(confirmInviteButton)
		
		WebUI.waitForElementVisible(successToast, 10)
	}
	
	'Connect DB'
	DB.connect(GlobalVariable.G_DB_HOST, GlobalVariable.G_DB_NAME, GlobalVariable.G_DB_PORT, GlobalVariable.G_DB_USERNAME,
		GlobalVariable.G_DB_PASSWORD)
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
	println(url)
	return url.toString()
}

def url = getTestUrl()
printf url

WebUI.openBrowser(url)

WebUI.waitForPageLoad(15)
WebUI.waitForElementClickable(findTestObject('Page_TakeTest/btn_Enter Test'), 0)







