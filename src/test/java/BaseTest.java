import com.inivos.config.Constants;
import com.inivos.driver.AppiumDriverFactory;
import com.inivos.driver.MobileDriver;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public abstract class BaseTest {

    private static WebDriver driver;
    private static ExtentReports extent;
    private static ExtentHtmlReporter htmlReporter;
    private static ExtentTest logger;

    @BeforeTest
    public void beforeTest() {
        htmlReporter = new ExtentHtmlReporter(System.getProperty ("user.dir") + File.separator+ "reports" +File.separator + "AutomationReport.html");
        htmlReporter.config().setEncoding("utf-8");
        htmlReporter.config().setDocumentTitle("Automation Report");
        htmlReporter.config(). setReportName ("Automation Test Results");
        htmlReporter.config(). setTheme (Theme. DARK);
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
        extent.setSystemInfo("Automation Tester", "Inivos - QA Department");

    }

//    @BeforeMethod
//    @Parameters(value={"platform"})
//    public void beforeMethodTest(String platform, Method testMethod) {
//        logger = extent.createTest(testMethod.getName());
//        driver = AppiumDriverFactory.getDriver(platform);
//        driver.get(Constants.PLATFORM);
//        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
//    }

    private static AppiumDriverLocalService service;

    @BeforeSuite
    public void globalSetup () throws IOException {
        service = AppiumDriverLocalService.buildDefaultService();
        service.start();
    }

    @AfterSuite
    public void globalTearDown () {
        if (service != null) {
            service.stop();
        }
    }

    public URL getServiceUrl () {
        return service.getUrl();
    }

}