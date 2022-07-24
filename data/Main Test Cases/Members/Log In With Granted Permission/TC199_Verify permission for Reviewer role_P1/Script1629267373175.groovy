import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.github.kklisura.cdt.protocol.commands.Log
import com.google.common.base.Verify
import com.kms.katalon.core.annotation.SetUp
import com.kms.katalon.core.annotation.TearDown
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import generator.DynamicGenerator
import groovy.transform.Field
import internal.GlobalVariable as GlobalVariable

@Field String memberEmail = DynamicGenerator.getInstance().getDynamicVariableByClassName(getClass()).toString().toLowerCase() + "@gmail.com"
@Field String memberName = "Hoa Pham"
@Field String memberPassword = DynamicGenerator.getInstance().getDynamicVariableByClassName(getClass()) + "@a1"
@Field String role = "Reviewer"
@Field String testName = DynamicGenerator.getInstance().getDynamicVariableByClassName(getClass())

@SetUp
def setup() {
	WebUI.comment('Create a reviewer member')
	if (true) {
		def createMemberTestCase = findTestCase('Test Cases/Common Test Cases/Members/Create A Member')
		WebUI.callTestCase(createMemberTestCase, ["role": role, "email": memberEmail, "name": memberName, "password": memberPassword])
	}
}

@TearDown
def teardown() {
	WebUI.comment('Remove the tested member')
	if (true) {
		def removeMemberTestCase = findTestCase('Test Cases/Common Test Cases/Members/Tear Down Member')
		WebUI.callTestCase(removeMemberTestCase, ['email': memberEmail ])
	}
	
	WebUI.comment('Remove the tested test')
	if (true) {
		def removeTheTestedTestTestCase = findTestCase('Test Cases/Common Test Cases/Test/Tear Down Test')
		WebUI.callTestCase(removeTheTestedTestTestCase, ['testName': testName])
	}
}

WebUI.comment('Step 1: Log in to the system with the Super Admin role')
if (true) {
	def loginTestCase = findTestCase('Test Cases/Common Test Cases/Login/Login With Password And Username')
	WebUI.callTestCase(loginTestCase, ['username': memberEmail,'password': memberPassword])
}

WebUI.comment('Step 2: Verify can ONLY see and take actions on the module: Candidate Tests')
if (true) {
	WebUI.comment('Can not see Libraries')
	if (true) {
		def libraryNav = findTestObject('Object Repository/Module_Navigation/btn_Libraries')
		WebUI.verifyElementNotPresent(libraryNav, 3)
	}
	
	WebUI.comment('Tests')
	if (true) {
		def testNav = findTestObject('Object Repository/Module_Navigation/nav_item_tests')
		WebUI.verifyElementVisible(testNav)
		WebUI.verifyElementClickable(testNav)
	}
	
	WebUI.comment('Candidates Tests')
	if (true) {
		// Because the assign reviewer feature has not developed yet, so this assert will be update later.
	}
	
	WebUI.comment('Can not see Setting > Members')
	if (true) {
		def memberNav = findTestObject('Object Repository/Module_Navigation/nav_Members')
		WebUI.verifyElementNotPresent(memberNav, 3)
	}
	
	WebUI.comment('Can not see Setting > Intergration')
	if (true) {
		def integrationsNav = findTestObject('Object Repository/Module_Navigation/nav_Integrations')
		WebUI.verifyElementNotPresent(integrationsNav, 3)
	}
}
