import io.appium.java_client.PerformsTouchActions;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.touch.TapOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.By;

import org.openqa.selenium.Keys;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import io.appium.java_client.android.Activity;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.*;


import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

public class AndroidBasicInteractionsTest extends BaseTest {
    private AndroidDriver<WebElement> driver;
    private final String SEARCH_ACTIVITY = ".app.SearchInvoke";
    private final String ALERT_DIALOG_ACTIVITY = ".app.AlertDialogSamples";

    private final String KEY_EVENT_ACTIVITY = ".text.KeyEventText";
    private final String Text_Link_ACTIVITY = ".text.Link";
    private final String PACKAGE = "io.appium.android.apis";

    @BeforeClass
    public void setUp() throws IOException {
        File classpathRoot = new File(System.getProperty("user.dir"));
        File appDir = new File(classpathRoot, "../Inivos-mobile-automation-framework/app");
        File app = new File(appDir.getCanonicalPath(), "ApiDemos-debug.apk");
        DesiredCapabilities capabilities = new DesiredCapabilities();
        /*
        'deviceName' capability only affects device selection if you run the test in a cloud
        environment. It has no effect if the test is executed on a local machine.
        */
        capabilities.setCapability("deviceName", "Pixel_2");

        /*
        It makes sense to set device udid if there are multiple devices/emulators
        connected to the local machine. Run `adb devices -l` to check which devices
        are online and what are their identifiers.
        If only one device is connected then this capability might be omitted
        */
        capabilities.setCapability("udid", "emulator-5554");
        capabilities.setCapability("platformVersion", "11.0.0");

        /*
        It is recommended to set a full path to the app being tested.
        Appium for Android supports application .apk and .apks bundles.
        If this capability is not set then your test starts on Dashboard view.
        It is also possible to provide an URL where the app is located.
        */
        capabilities.setCapability("app", app.getAbsolutePath());

        /*
        By default Appium tries to autodetect the main application activity,
        but if you app's very first activity is not the main one then
        it is necessary to provide its name explicitly. Check
        https://github.com/appium/appium/blob/master/docs/en/writing-running-appium/android/activity-startup.md
        for more details on activities selection topic.
        */
        // capabilities.setCapability("appActivity", "com.myapp.SplashActivity"));
        // capabilities.setCapability("appPackage", "com.myapp"));
        // capabilities.setCapability("appWaitActivity", "com.myapp.MainActivity"));

        /*
        Appium for Android supports multiple automation backends, where
        each of them has its own pros and cons. The default one is UIAutomator2.
        */
        // capabilities.setCapability("automationName", "UIAutomator2");
        // capabilities.setCapability("automationName", "Espresso");

        /*
        There are much more capabilities and driver settings, that allow
        you to customize and tune your test to achieve the best automation
        experience. Read http://appium.io/docs/en/writing-running-appium/caps/
        and http://appium.io/docs/en/advanced-concepts/settings/
        for more details.

        Feel free to visit our forum at https://discuss.appium.io/
        if you have more questions.
        */

        driver = new AndroidDriver<WebElement>(getServiceUrl(), capabilities);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            //driver.quit();
        }
    }

//    @BeforeMethod
//    public void beforeMethodTest(){
//        super.beforeMethodTest("ANDROID",n);
//    }


    @Test()
    public void testSendKeys() {
        driver.startActivity(new Activity(PACKAGE, SEARCH_ACTIVITY));
        AndroidElement searchBoxEl = (AndroidElement) driver.findElementById("txt_query_prefill");
        searchBoxEl.sendKeys("Hello world!");
        AndroidElement onSearchRequestedBtn = (AndroidElement) driver.findElementById("btn_start_search");
        onSearchRequestedBtn.click();
        AndroidElement searchText = (AndroidElement) new WebDriverWait(driver, 30)
                .until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/search_src_text")));
        String searchTextValue = searchText.getText();
        Assert.assertEquals(searchTextValue, "Hello world!");
        driver.hideKeyboard();
    }

    @Test
    public void testOpensAlert() {
        // Open the "Alert Dialog" activity of the android app
        driver.startActivity(new Activity(PACKAGE, ALERT_DIALOG_ACTIVITY));

        // Click button that opens a dialog
        AndroidElement openDialogButton = (AndroidElement) driver.findElementById("io.appium.android.apis:id/two_buttons");
        openDialogButton.click();

        // Check that the dialog is there
        AndroidElement alertElement = (AndroidElement) driver.findElementById("android:id/alertTitle");
        String alertText = alertElement.getText();
        Assert.assertEquals(alertText, "Lorem ipsum dolor sit aie consectetur adipiscing\nPlloaso mako nuto siwuf cakso dodtos anr koop.");
        AndroidElement closeDialogButton = (AndroidElement) driver.findElementById("android:id/button1");

        // Close the dialog
        closeDialogButton.click();
    }

    @Test
    public void testKeyPress() throws StringIndexOutOfBoundsException{
        //Opening KeyEventText Activity
        driver.startActivity(new Activity(PACKAGE,KEY_EVENT_ACTIVITY));

        //Click on the TextView

        AndroidElement TextView = (AndroidElement) driver.findElementById("io.appium.android.apis:id/text");
        driver.pressKey(new KeyEvent(AndroidKey.A));
        String text = TextView.getText();
        String actual = text.substring(0,12);
        System.out.println(actual);
        String expected = "[keycode=29]";
        Assert.assertEquals(actual,expected);
    }

    @Test
    public void testTextLinksBrowser(){
        //Opening Text Linkfy Activity
        driver.startActivity(new Activity(PACKAGE,Text_Link_ACTIVITY));
//        AndroidElement link = (AndroidElement) driver.findElementById("io.appium.android.apis:id/text1");
//        link.findElementByXPath("//*[contains(.,'http://google.com')]").click();
        //Tap http:www.google.com link
        driver.performTouchAction(new TouchAction<>(driver).tap(PointOption.point(246,400)).perform());

        String expected = "org.chromium.chrome.browser.firstrun.FirstRunActivity";
        String actual = driver.currentActivity();
        Assert.assertEquals(actual,expected);
    }

    @Test
    public void testTextLinksPhone(){
        //Opening Text Linkfy Activity
        driver.startActivity(new Activity(PACKAGE,Text_Link_ACTIVITY));
//        AndroidElement link = (AndroidElement) driver.findElementById("io.appium.android.apis:id/text1");
//        link.findElementByXPath("//*[contains(.,'http://google.com')]").click();
        //tap on a phone number
        driver.performTouchAction(new TouchAction<>(driver).tap(PointOption.point(333,518)).perform());

        String expected = ".main.impl.MainActivity";
        String actual = driver.currentActivity();
        Assert.assertEquals(actual,expected);
    }
}