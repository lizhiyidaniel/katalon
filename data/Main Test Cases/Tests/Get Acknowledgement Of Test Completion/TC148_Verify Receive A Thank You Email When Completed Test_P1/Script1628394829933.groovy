import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase

import com.kms.katalon.core.annotation.SetUp
import com.kms.katalon.core.annotation.TearDown
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import generator.DynamicGenerator
import groovy.transform.Field

@Field String validNameUnique = "Ontest283 TC2 " + DynamicGenerator.getInstance().getDynamicVariableByClassName(getClass())
@Field String validEmail = email
@Field String validName = "Hoa Pham"

@SetUp
def setup() {
	WebUI.comment('Delete all emails')
	CustomKeywords.'com.testwithhari.katalon.plugins.Gmail.deleteAllEMails'(email, password, "Inbox")
}

@TearDown()
def tearDown() {
	'Delete the candidate and test'
	WebUI.callTestCase(findTestCase('Test Cases/Common Test Cases/Test/Tear Down Challenge Test'), ['testName': validNameUnique, 'email':validEmail])
}

WebUI.comment('Step 1: Check the test link in the invite email and submit test')
WebUI.callTestCase(findTestCase('Test Cases/Common Test Cases/Test/Submit Test On Time'), ["validNameUnique": validNameUnique, "validEmail": validEmail, "validName": validName])

WebUI.comment('Verify receive a thank you email')
def numberOfEmail = CustomKeywords.'com.testwithhari.katalon.plugins.Gmail.getEmailsCount'(email, password, "Inbox")
WebUI.verifyMatch(numberOfEmail.toString(), "2", false)
