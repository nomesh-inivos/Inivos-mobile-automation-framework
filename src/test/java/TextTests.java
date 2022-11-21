import io.appium.java_client.TouchAction;
import io.appium.java_client.android.Activity;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

//API_DEMOS TEXT tests
public class TextTests {
    private AndroidDriver<?> driver;
    private final String KEY_EVENT_ACTIVITY = ".text.KeyEventText";
    private final String Text_Link_ACTIVITY = ".text.Link";

    private final String LOG_TEXT_ACTIVITY = ".text.LogTextBox1";
    private final String PACKAGE;

    public TextTests(AndroidDriver<?> driver,String packageName){
        this.PACKAGE = packageName;
        this.driver = driver;
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
        //clear text
        driver.findElementById("io.appium.android.apis:id/clear").click();
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
        driver.performTouchAction(new TouchAction<>(driver)
                .tap(PointOption.point(333,518)).perform());

        String expected = ".main.impl.MainActivity";
        String actual = driver.currentActivity();
        Assert.assertEquals(actual,expected);
    }

    @Test
    public void testLogTexBox(){
        driver.startActivity(new Activity(PACKAGE,LOG_TEXT_ACTIVITY));

        AndroidElement AddButton = (AndroidElement) driver.findElementById("io.appium.android.apis:id/add");
        AddButton.click();
        AddButton.click();

        AndroidElement LogView = (AndroidElement) driver.findElementById("io.appium.android.apis:id/text");
        String text = LogView.getText();
        int actual = text.split("\n").length;
        Assert.assertEquals(actual,2);

        AndroidElement DoNothingButton = (AndroidElement) driver.findElementByAccessibilityId("Do nothing");
        DoNothingButton.click();
        Assert.assertEquals(actual,2);
    }
}
