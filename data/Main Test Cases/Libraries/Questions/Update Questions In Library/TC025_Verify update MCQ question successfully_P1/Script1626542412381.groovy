import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import org.openqa.selenium.Keys as Keys
import com.kms.katalon.core.annotation.SetUp as SetUp
import com.kms.katalon.core.annotation.TearDown as TearDown
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import generator.DynamicGenerator as DynamicGenerator
import groovy.transform.Field as Field

WebUI.comment('Story: [ON] As a Recruiter, I want to update questions in a libary.')

@Field String testLibraryNameUnique = testLibraryName + DynamicGenerator.getInstance().getDynamicVariableByClassName(getClass())

WebUI.comment('Click an existing Library')

WebUI.click(findTestObject('Page_Libraries/btn_libraryName_relyOnName', [('libraryName') : testLibraryNameUnique]))

WebUI.comment('Click Multiple Choice on Question types')

WebUI.click(findTestObject('Page_Libraries/btn_questionType', [('type') : 'mcq']))

WebUI.comment('Click MCQ need to update')

WebUI.click(findTestObject('Page_Libraries/btn_questionList', [('order') : 1]))

WebUI.comment('Verify dialog Update Multiple Choice Question is displayed')

WebUI.waitForElementPresent(findTestObject('Page_Libraries/Question_Dialog/dialog_UpdateQuestion'), 10)

WebUI.takeScreenshotAsCheckpoint("Update MCQ  Question in Library")

WebUI.comment('Enter valid values')

if (true) {
    WebUI.clearText(findTestObject('Page_Libraries/Question_Dialog/txt_ProblemUpdate'))

    WebUI.sendKeys(findTestObject('Page_Libraries/Question_Dialog/tbx_ProblemUpdate'), 'test')

    WebUI.click(findTestObject('Page_Libraries/Question_Dialog/btn_SingleChoice'))

    WebUI.setText(findTestObject('Page_Libraries/Question_Dialog/tbx_ChoiceValue'), 'Choice 1')

    WebUI.setText(findTestObject('Page_Libraries/Question_Dialog/tbx_MaxTime'), '10')

    WebUI.selectOptionByValue(findTestObject('Page_Libraries/Question_Dialog/slt_DifficultyLevel'), 'Medium', false)

    WebUI.setText(findTestObject('Page_Libraries/Question_Dialog/tbx_Tags'), 'newTag')

    WebUI.sendKeys(findTestObject('Page_Libraries/Question_Dialog/tbx_Tags'), Keys.chord(Keys.ENTER))
}

WebUI.comment('Click Submit')

WebUI.click(findTestObject('Page_Libraries/Question_Dialog/btn_Submit'))

WebUI.comment('Verify MCQ is updated successfully, save changes, show a successful message and redirect to Questions listing page of that question type.')

if (true) {
    WebUI.waitForElementPresent(findTestObject('Page_Libraries/txt_SuccessfullMessage'), 10)

    WebUI.verifyElementText(findTestObject('Page_Libraries/txt_SuccessfullMessage'), 'Update question successfully.')

    WebUI.verifyElementText(findTestObject('Page_Libraries/txt_QuestionStatement', [('order') : 1]), 'test')

    WebUI.verifyElementText(findTestObject('Page_Libraries/txt_QuestionDifficulty', [('order') : 1]), 'Medium')
}

@SetUp
def setUp() {
    WebUI.comment('Given I am logged in and there is at least ONE libraries,')

    'Login'
    WebUI.callTestCase(findTestCase('Common Test Cases/Login/Login'), null)

    WebUI.comment('When I click Libraries on the menu,')

    WebUI.click(findTestObject('Object Repository/Module_Navigation/btn_Libraries'))

    WebUI.waitForElementVisible(findTestObject('Object Repository/Page_Libraries/link_homeLib'), 5)

    'create Testing MCQ Question.'
    WebUI.callTestCase(findTestCase('Common Test Cases/Library/Create New MCQ'), [('libraryName') : testLibraryNameUnique])
}

@TearDown
def tearDown() {
    'delete the test libary'
    WebUI.callTestCase(findTestCase('Common Test Cases/Library/Tear Down Test Library'), [('libraryName') : testLibraryNameUnique])
}

