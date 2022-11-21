import com.inivos.config.Constants;
import io.appium.java_client.android.Activity;
import io.appium.java_client.android.nativekey.AndroidKey;
import org.openqa.selenium.Point;
import org.testng.Assert;
import org.testng.annotations.*;

import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.remote.DesiredCapabilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.AutomationName;
import io.appium.java_client.remote.MobileCapabilityType;
import org.testng.log4testng.Logger;

public class MobileTestSuite extends BaseTest{

    public static Logger log;
    public static ExtentHtmlReporter htmlReporter;
    public static ExtentReports extent;
    public static ExtentTest test;

    // Initiating Appium Driver
    public static AndroidDriver<?> driver;
    /*
     * This method is used for initializing the Log4j and Config.properties
     */
    @BeforeSuite
    public static void startSuite() {
        log = Logger.getLogger(MobileTestSuite.class);
        log.info("Test Started successfully");
    }

    /*
     * This method is used for init the Appium Driver and Extent report
     */
    @BeforeTest
    public static void startTest() {

        // Getting the driver declared using our Capabilities
        try {
            driver = startAppium();
        } catch (Exception e) {
            log.fatal("Driver is not Initiated as Expected" + e.getMessage());
        }
        // LOC for initializing the Extent Report
        log.info("Initializing the Extent Report");
        try {
            log.info("Extent report is available under the directory " + System.getProperty("user.dir") + "/Reports/");
            // starting the HTML report
            htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir") + "/reports/testReport.html");
            // To Initialize the reporters
            extent = new ExtentReports();
            // attach only HtmlReporter
            extent.attachReporter(htmlReporter);
            // Providing internal name for the report
            htmlReporter.config().setReportName("Test Report");
            // To create a test case and steps, we need to use reference variable for
            // "ExtentTest" class.
            // This test reference will help us to create test.
            test = extent.createTest("testcase Name");
        } catch (Exception e) {
            log.error("Unable to Initialize the Extent Report" + e.getMessage());
        }
    }


    /*
     * This method is used for initiate the AppiumDriver with caps and connection protocol
     */
    public static AndroidDriver<?> startAppium() {
        // Initializing the Appium driver
        try {
            DesiredCapabilities cap = new DesiredCapabilities();
            // All Capability values are retrieved from Config.properties file.
            cap.setCapability(MobileCapabilityType.PLATFORM_NAME, Constants.PLATFORM);
            cap.setCapability(MobileCapabilityType.PLATFORM_VERSION, Constants.ANDROID_PLATFORM_VERSION);
            cap.setCapability(MobileCapabilityType.DEVICE_NAME, Constants.ANDROID_DEVICE_NAME);
            cap.setCapability("appActivity", Constants.APP_ACTIVITY);
            cap.setCapability("appPackage", Constants.APP_PACKAGE);
            cap.setCapability("autoLaunch", true);
            cap.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 500);
            cap.setCapability(MobileCapabilityType.AUTOMATION_NAME, AutomationName.ANDROID_UIAUTOMATOR2);

            // Declaring the driver as "Android driver" with the Host and Port number to communicate with Appium desktop
            driver = new AndroidDriver<AndroidElement>(new URL(Constants.APPIUM_URL), cap);
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            //Printing the driver details in Log file
            log.info("Driver declared successfully : " +driver);
        } catch (Exception e) {
            driver = null;
            log.fatal("Driver declaration failed : " +driver);
            log.fatal(e.getStackTrace());
        }
        //Returning the instance of the driver to the parent method
        return driver;
    }

    @BeforeMethod
    public static void openApp() {
        startTest();
        log.info("Test case started successfully");
        log.info("Trying to launch the Application under Test");
        try{
            driver.launchApp();
            log.info("Application launched successfully");
        }catch(Exception e) {
            log.info("Unable to launch the application :" +e.getMessage());
        }
    }

    @Test
    public void testAutoCompleteScreenTop() throws InterruptedException{
        //Opening autocomplete screentop activity
        driver.startActivity(new Activity("io.appium.android.apis",".view.AutoComplete1"));

        AndroidElement TextInput = AppiumTestSupport.locateElement(driver,"io.appium.android.apis:id/edit","Id");
        System.out.println(TextInput.isDisplayed());
        AppiumTestSupport.pressAndroidKey(driver,AndroidKey.S);
        AppiumTestSupport.pressAndroidKey(driver,AndroidKey.R);

        int x = TextInput.getCenter().x;
        int y = TextInput.getCenter().y + 100;

        AppiumTestSupport.tapOnCoordinate(driver,new Point(x,y));

        String actual = AppiumTestSupport.getElementText(TextInput);
        String expected = "Sri Lanka";

        driver.hideKeyboard();

        Assert.assertEquals(actual,expected);
    }

    @Test
    public void textTests() throws InterruptedException {
        TextTests test = new TextTests(driver,"io.appium.android.apis");
        //Key Event Text
        test.testKeyPress();
        //Click Browser Link in TextView
        test.testTextLinksBrowser();
        //Click Phone Number in TextView
        test.testTextLinksPhone();
        //Log Text Box
        test.testLogTexBox();
    }
    @Test
    public void viewTests(){
        ViewTests test = new ViewTests(driver,"io.appium.android.apis");
        //Views AutoComplete
        //AutoComplete ScreenTop Test
        test.testAutoCompleteScreenTop();
        //AutoComplete Scroll Test
        test.testAutoCompleteScroll();
        //AutoComplete Multiple Test
        test.testAutoCompleteMultiple();

        //Views ToggleButton
        test.testToggleButton();

        //Views Chronometer
        //Start,Stop,Reset Time Test
        test.testChronometerStart();

        //Views DateWidget
        //Date Widget Dialog date,time,spinner
        test.testDateWidget();

        //Views Drag and Drop
        test.testDragDrop();

        //Views Expandable Lists
        test.testExpandableList();

        //Views Image Switcher
        test.testImageSwitcher();

        //Views ScrollBars Style
        test.testScrollBars();

        //Views Rating Bar
        test.testRatingBar();

        //Views Sliding Picker
        test.testSlidingPicker();
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
