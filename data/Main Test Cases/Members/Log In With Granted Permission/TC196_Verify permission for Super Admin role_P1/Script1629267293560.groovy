import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.annotation.SetUp
import com.kms.katalon.core.annotation.TearDown
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import generator.DynamicGenerator
import groovy.transform.Field
import internal.GlobalVariable

@Field String memberEmail = DynamicGenerator.getInstance().getDynamicVariableByClassName(getClass()).toString().toLowerCase() + "@gmail.com"
@Field String memberName = "Hoa Pham"
@Field String memberPassword = DynamicGenerator.getInstance().getDynamicVariableByClassName(getClass()) + "@a1"
@Field String role = "Super Admin"
@Field String testName = DynamicGenerator.getInstance().getDynamicVariableByClassName(getClass())

@SetUp
def setup() {
	WebUI.comment('Create a super admin member')
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

WebUI.comment('Step 2: Verify can see and take actions on all modules: Libraries, Tests, Candidate Tests, Settings')
if (true) {
	WebUI.comment('Libraries')
	if (true) {
		def libraryNav = findTestObject('Object Repository/Module_Navigation/btn_Libraries')
		WebUI.verifyElementVisible(libraryNav)
		WebUI.verifyElementClickable(libraryNav)
	}
	
	
	WebUI.comment('Tests')
	if (true) {
		def testNav = findTestObject('Object Repository/Module_Navigation/nav_item_tests')
		WebUI.verifyElementVisible(testNav)
		WebUI.verifyElementClickable(testNav)
	}
	
	WebUI.comment('Candidate Tests')
	if (true) {
		def createAnOngoingTest = findTestCase('Test Cases/Common Test Cases/Test/Create An Ongoing Test')
		def openAnOngoingTestByNameTestCase = findTestCase('Test Cases/Common Test Cases/Test/Open An Ongoing Test By Test Name')
		WebUI.callTestCase(createAnOngoingTest, ["testName": testName, "hasLogin": true])
		WebUI.callTestCase(openAnOngoingTestByNameTestCase, ["testName": testName])
		
		def reviewingTab = findTestObject('Object Repository/Ongoing Test Sidebar/tab_ReviewingCandidates')
		WebUI.verifyElementVisible(reviewingTab)
		WebUI.verifyElementClickable(reviewingTab)
		
		def scoredTab = findTestObject('Object Repository/Ongoing Test Sidebar/tab_ScoredCandidates')
		WebUI.verifyElementVisible(scoredTab)
		WebUI.verifyElementClickable(scoredTab)
	}
	
	WebUI.comment('Setting > Members')
	if (true) {
		def memberNav = findTestObject('Object Repository/Module_Navigation/nav_Members')
		WebUI.verifyElementVisible(memberNav)
		WebUI.verifyElementClickable(memberNav)
	}
	
	WebUI.comment('Setting > Intergration')
	if (true) {
		def integrationsNav = findTestObject('Object Repository/Module_Navigation/nav_Integrations')
		WebUI.verifyElementVisible(integrationsNav)
		WebUI.verifyElementClickable(integrationsNav)
	}
}

