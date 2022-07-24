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

@Field String validEmail = "loremipsumdolorsitametvelsumoetiamtinciduntexdicuntlaboramuseuhas,etiamhabemustemporibususuad.meisdolorumestnovimconguetationreprehenduntne@gmail.com"
@Field String validName = "Lorem ipsum dolor sit amet, vel sumo etiam tincidunt ex, dicunt laboramus eu has, etiam habemus temporibus usu ad. Meis dolorum est no, vim congue tation reprehendunt ne. Sea cu movet sensibus. Commune antiopam duo ne, ex saepe bonorum has."

@SetUp()
def setUp( ) {
	WebDriver driver = DriverFactory.getWebDriver()
	
	'Login'
	WebUI.callTestCase(findTestCase('Test Cases/Common Test Cases/Login/Login'),null)
	'Connect DB'
	DB.connect(GlobalVariable.G_DB_HOST, GlobalVariable.G_DB_NAME, GlobalVariable.G_DB_PORT, GlobalVariable.G_DB_USERNAME,
		GlobalVariable.G_DB_PASSWORD)
}
@TearDown()
def tearDown() {
}

def checkErrors(email, fullname, expects) {
	WebUI.setText(findTestObject('Object Repository/Page_Create Member/inp_Email'), email)
	
	WebUI.setText(findTestObject('Object Repository/Page_Create Member/inp_Full Name'), fullname)
	
	WebUI.click(findTestObject('Object Repository/Page_Create Member/select_Role'))
	
	WebUI.click(findTestObject('Object Repository/Page_Create Member/txt_Role',['role':'Super Admin']))
	
	List errors = WebUI.findWebElements(findTestObject('Object Repository/Page_Create Member/txt_Invalid Message'),30)
	
	int errorsCount = errors.size()
	
	
	if (errorsCount > 0) {
		for (int i = 0; i < errorsCount; i++) {
			WebUI.verifyMatch(errors.get(i).getText(),expects[i], false)
		}
	}
}

WebUI.click(findTestObject('Object Repository/Module_Navigation/nav_Members'))

WebUI.click(findTestObject('Object Repository/Page_Create Member/btn_Create Member'))

checkErrors(validEmail, validName, ["Full Name must be at most 100 characters.","Email must be at most 100 characters."])
checkErrors('','', ["Full Name is required.","Email is required."])
checkErrors('admin@mail.com','', ["Full Name is required.","Email already exists."])