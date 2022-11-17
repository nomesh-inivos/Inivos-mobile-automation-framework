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

public class ViewTests {
    private AndroidDriver<WebElement> driver;
    private final String AUTOCOMPLETE_SCREENTOP_TEST = ".view.AutoComplete1";
    private final String AUTOCOMPLETE_SCROLL_TEST = ".view.AutoComplete3";
    private final String PACKAGE;

    public ViewTests(AndroidDriver<WebElement> driver,String packageName){
        this.PACKAGE = packageName;
        this.driver = driver;
    }

    @Test
    public void testAutoCompleteScreenTop(){
        //Opening autocomplete screentop activity
        driver.startActivity(new Activity(PACKAGE,AUTOCOMPLETE_SCREENTOP_TEST));

        AndroidElement TextInput = (AndroidElement) driver.findElementById("io.appium.android.apis:id/edit");
        driver.pressKey(new KeyEvent(AndroidKey.S));
        driver.pressKey(new KeyEvent(AndroidKey.R));
        int x = TextInput.getCenter().x;
        int y = TextInput.getCenter().y + 100;
        driver.performTouchAction(new TouchAction(driver).tap(PointOption.point(x,y)).perform());

        String actual = TextInput.getText();
        String expected = "Sri Lanka";

        driver.hideKeyboard();

        Assert.assertEquals(actual,expected);
    }

    @Test
    public void testAutoCompleteScroll(){
        //Opening autocomplete scroll activity
        driver.startActivity(new Activity(PACKAGE,AUTOCOMPLETE_SCROLL_TEST));

        //Scroll down to input
        AndroidElement ScrollView = (AndroidElement) driver.findElementByXPath("//android.widget.ScrollView");

        int stx = ScrollView.getCenter().x;
        int sty = ScrollView.getCenter().y + 600;
        int enx = ScrollView.getCenter().x;
        int eny = ScrollView.getCenter().y - 600;

        driver.performTouchAction(new TouchAction<>(driver).press(PointOption.point(stx,sty)).moveTo(PointOption.point(enx,eny)).release().perform());


        AndroidElement TextInput = (AndroidElement) driver.findElementById("io.appium.android.apis:id/edit");
        new TouchAction<>(driver).press(PointOption.point(TextInput.getCenter().x,TextInput.getCenter().y)).release().perform();
        driver.pressKey(new KeyEvent(AndroidKey.S));
        driver.pressKey(new KeyEvent(AndroidKey.R));

        driver.performTouchAction(new TouchAction(driver).tap(PointOption.point(TextInput.getCenter().x,TextInput.getCenter().y - 100)).perform());

        String actual = TextInput.getText();
        String expected = "Sri Lanka";

        driver.hideKeyboard();

        Assert.assertEquals(actual,expected);
    }
}
