import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.inivos.config.Constants;
import com.inivos.util.BaseTest;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.AutomationName;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.*;
import org.testng.log4testng.Logger;

import java.net.URL;
import java.util.concurrent.TimeUnit;

public class MobileTestSuite extends BaseTest {

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
            //cap.setCapability(MobileCapabilityType.DEVICE_NAME, Constants.ANDROID_DEVICE_NAME);
            cap.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, Constants.APP_ACTIVITY);
            cap.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, Constants.APP_PACKAGE);
            cap.setCapability(AndroidMobileCapabilityType.AUTO_LAUNCH, true);
            cap.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 500);
            cap.setCapability(MobileCapabilityType.AUTOMATION_NAME, AutomationName.ANDROID_UIAUTOMATOR2);
            cap.setCapability(AndroidMobileCapabilityType.AVD, "Pixel_2_API_30");
            // Declaring the driver as "Android driver" with the Host and Port number to communicate with Appium desktop
            driver = new AndroidDriver<AndroidElement>(new URL(Constants.APPIUM_URL), cap);
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            //Printing the driver details in Log file
            log.info("Driver declared successfully : " + driver);
        } catch (Exception e) {
            driver = null;
            log.fatal("Driver declaration failed : " + driver);
            log.fatal(e.getStackTrace());
        }
        //Returning the instance of the driver to the parent method
        return driver;
    }

    @BeforeMethod
    public static void openApp() {
        //startTest();
        log.info("Test case started successfully");
        log.info("Trying to launch the Application under Test");
        try {
            driver.launchApp();
            log.info("Application launched successfully");
        } catch (Exception e) {
            log.info("Unable to launch the application :" + e.getMessage());
        }
    }

    @Test
    public void test() {
        System.out.println("in Test");
        System.out.println(driver.getCapabilities().is(MobileCapabilityType.DEVICE_NAME));
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
