import io.appium.java_client.TouchAction;
import io.appium.java_client.android.Activity;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.touch.TapOptions;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.ElementOption;
import io.appium.java_client.touch.offset.PointOption;
import org.aspectj.weaver.ast.And;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.chrono.ChronoLocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class ViewTests {
    private AndroidDriver<WebElement> driver;
    private final String AUTOCOMPLETE_SCREENTOP_TEST = ".view.AutoComplete1";
    private final String AUTOCOMPLETE_SCROLL_TEST = ".view.AutoComplete3";
    private final String AUTOCOMPLETE_Multiple_TEST = ".view.AutoComplete6";
    private final String TOGGLE_BUTTON_TEST = ".view.Buttons1";
    private final String CHRONOMETER_TEST = ".view.ChronometerDemo";
    private final String DATEWIDGET_TEST = ".view.DateWidgets1";
    private final String PACKAGE;

    public ViewTests(AndroidDriver<WebElement> driver,String packageName){
        this.PACKAGE = packageName;
        this.driver = driver;
    }

//    @BeforeMethod
//    public void TestMethodStart(){
//        System.out.println("A test Started");
//    }

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

    @Test
    public void testAutoCompleteMultiple(){
        //Opening autocomplete Multiple Items activity
        driver.startActivity(new Activity(PACKAGE,AUTOCOMPLETE_Multiple_TEST));

        AndroidElement TextInput = (AndroidElement) driver.findElementById("io.appium.android.apis:id/edit");
        driver.pressKey(new KeyEvent(AndroidKey.S));
        driver.pressKey(new KeyEvent(AndroidKey.R));
        int x = TextInput.getCenter().x;
        int y = TextInput.getCenter().y + 100;
        driver.performTouchAction(new TouchAction(driver).tap(PointOption.point(x,y)).perform());

        driver.pressKey(new KeyEvent(AndroidKey.U));
        driver.pressKey(new KeyEvent(AndroidKey.N));
        driver.performTouchAction(new TouchAction(driver).tap(PointOption.point(x,y)).perform());

        driver.hideKeyboard();

        int actual = TextInput.getText().split(",").length - 1;
        int expected = 2;

        Assert.assertEquals(actual,expected);
    }

    @Test
    public void testToggleButton(){
        //Opening Views Button Activity
        driver.startActivity(new Activity(PACKAGE,TOGGLE_BUTTON_TEST));

        AndroidElement ToggleButton = (AndroidElement) driver.findElementById("io.appium.android.apis:id/button_toggle");
        String text = ToggleButton.getText();
        ToggleButton.click();
        String text1 = ToggleButton.getText();

        if(text.equals("ON")) {
            Assert.assertTrue(text1.equals("OFF"));
        }
        else{
            Assert.assertTrue(text1.equals("ON"));
        }
    }

    @Test
    public void testChronometerStart(){
        //Open Chronometer Activity
        driver.startActivity(new Activity(PACKAGE,CHRONOMETER_TEST));

        AndroidElement ChronometerText = (AndroidElement) driver.findElementById("io.appium.android.apis:id/chronometer");

        AndroidElement ChronometerStart = (AndroidElement) driver.findElementById("io.appium.android.apis:id/start");
        AndroidElement ChronometerStop = (AndroidElement) driver.findElementById("io.appium.android.apis:id/stop");

        new TouchAction<>(driver).tap(TapOptions.tapOptions().withElement(ElementOption.element(ChronometerStart))).waitAction(WaitOptions.waitOptions(Duration.ofMillis(5000))).perform().tap(TapOptions.tapOptions().withElement(ElementOption.element(ChronometerStop))).perform();

        //Filter The Text
        String text = ChronometerText.getText().replaceAll("[^\\d]","");

        Assert.assertEquals(text,"0006");

        AndroidElement ChronometerResetButton = (AndroidElement) driver.findElementById("io.appium.android.apis:id/reset");
        ChronometerResetButton.click();

        text = ChronometerText.getText().replaceAll("[^\\d]","");

        Assert.assertEquals(text,"0000");
    }

    @Test
    public void testDateWidget(){
        //Open Views Date Widget Activity
        driver.startActivity(new Activity(PACKAGE, DATEWIDGET_TEST));

        //Elements
        AndroidElement Date = (AndroidElement) driver.findElementById("io.appium.android.apis:id/dateDisplay");
        AndroidElement DatePicker = (AndroidElement) driver.findElementById("io.appium.android.apis:id/pickDate");
        AndroidElement TimePicker = (AndroidElement) driver.findElementById("io.appium.android.apis:id/pickTime");
        AndroidElement TimeSpinner = (AndroidElement) driver.findElementById("io.appium.android.apis:id/pickTimeSpinner");

        //Date Text Test
        String date = Date.getText().split(" ")[0].replaceAll("-","/");
        DateTimeFormatter f = DateTimeFormatter.ofPattern( "MM/dd/yyyy" );
        LocalDate today = LocalDate.now();
        try{
            LocalDate ld = LocalDate.parse( date , f );
            Assert.assertEquals(ld,today);
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }

        //Change Date Test

        DatePicker.click();
        String month = today.getMonth().name();
        int newDay = today.getDayOfMonth() + 7;
        String ContentDesc = newDay + " " + month.substring(0,1) + month.substring(1).toLowerCase() + " " + today.getYear();
        String ContentDescFormatted = today.getMonthValue() + "/" + newDay + "/" + today.getYear();
        System.out.println(ContentDesc);
        //Select Date
        driver.findElementByAccessibilityId(ContentDesc).click();
        //Click Ok
        driver.findElementById("android:id/button1").click();

        WebDriverWait wait = new WebDriverWait(driver,20);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("io.appium.android.apis:id/dateDisplay")));
        date = Date.getText().split(" ")[0].replaceAll("-","/");

        try{
            LocalDate ld = LocalDate.parse( date , f );
            LocalDate nd = LocalDate.parse( ContentDescFormatted,f);
            Assert.assertEquals(ld,nd);
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }

        //Change Time Test

        TimePicker.click();
        driver.findElementById("android:id/hours").click();
        driver.findElementByAccessibilityId("9").click();
        driver.findElementById("android:id/minutes").click();
        driver.findElementByAccessibilityId("45").click();
        driver.findElementById("android:id/button1").click();

        //Time Text
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("io.appium.android.apis:id/dateDisplay")));
        String time = Date.getText().split(" ")[1];
        Assert.assertEquals(time,"09:45");
    }

}
