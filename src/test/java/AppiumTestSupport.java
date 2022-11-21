import android.widget.Switch;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.touch.LongPressOptions;
import io.appium.java_client.touch.TapOptions;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.ElementOption;
import io.appium.java_client.touch.offset.PointOption;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class AppiumTestSupport {


    public static @Nullable AndroidElement locateElement(@NotNull AndroidDriver<?> driver, @NotNull String locator, @NotNull String method) throws InterruptedException {
        AndroidElement element;
        switch (method.toUpperCase()){
            case "ID":
                element = (AndroidElement) driver.findElementById(locator);
                if(element.isDisplayed()){
                    return element;
                }
                else{
                    Thread.sleep(10000);
                    return (AndroidElement) driver.findElementById(locator);
                }
            case "ACCESSIBILITYID":
                element = (AndroidElement) driver.findElementByAccessibilityId(locator);
                if(element.isDisplayed()){
                    return element;
                }
                else{
                    Thread.sleep(10000);
                    return (AndroidElement) driver.findElementByAccessibilityId(locator);
                }
            case "XPATH":
                element = (AndroidElement) driver.findElementByXPath(locator);
                if(element.isDisplayed()){
                    return element;
                }
                else{
                    Thread.sleep(10000);
                    return (AndroidElement) driver.findElementByXPath(locator);
                }
            case "CLASSNAME":
                element = (AndroidElement) driver.findElementByClassName(locator);
                if(element.isDisplayed()){
                    return element;
                }
                else{
                    Thread.sleep(10000);
                    return (AndroidElement) driver.findElementByClassName(locator);
                }
        }
        return null;
    }

    public static @Nullable List<AndroidElement> locateElements(@NotNull AndroidDriver<?> driver, @NotNull String locator, @NotNull String method) throws InterruptedException {
        List<AndroidElement> elements;
        switch (method.toUpperCase()){
            case "ID":
                elements = (List<AndroidElement>) driver.findElementsById(locator);
                if(elements.get(0).isDisplayed()){
                    return elements;
                }
                else{
                    Thread.sleep(10000);
                    return (List<AndroidElement>) driver.findElementsById(locator);
                }
            case "ACCESSIBILITYID":
                elements = (List<AndroidElement>) driver.findElementsByAccessibilityId(locator);
                if(elements.get(0).isDisplayed()){
                    return elements;
                }
                else{
                    Thread.sleep(10000);
                    return (List<AndroidElement>) driver.findElementsByAccessibilityId(locator);
                }
            case "XPATH":
                elements = (List<AndroidElement>) driver.findElementsByXPath(locator);
                if(elements.get(0).isDisplayed()){
                    return elements;
                }
                else{
                    Thread.sleep(10000);
                    return (List<AndroidElement>) driver.findElementsByXPath(locator);
                }
            case "CLASSNAME":
                elements = (List<AndroidElement>) driver.findElementsByClassName(locator);
                if(elements.get(0).isDisplayed()){
                    return elements;
                }
                else{
                    Thread.sleep(10000);
                    return (List<AndroidElement>) driver.findElementsByClassName(locator);
                }
        }
        return null;
    }

    public static void buttonClick(@NotNull AndroidElement button){
        button.click();
    }

    public static void buttonMultipleClick(@NotNull AndroidElement button, int times){
        for(int i = 0; i<times;i++){
            button.click();
        }
    }

    public static void swipeByCoordinate(AndroidDriver<?> driver, Point start, Point end){
        new TouchAction<>(driver).press(PointOption.point(start))
                .moveTo(PointOption.point(end)).release().perform();
    }

    public static void swipeByCoordinate(AndroidDriver<?> driver, Point startPoint, Point endPoint,long waitOfMillis){
        new TouchAction<>(driver).press(PointOption.point(startPoint)).waitAction(WaitOptions.waitOptions(Duration.ofMillis(waitOfMillis)))
                .moveTo(PointOption.point(endPoint)).release().perform();
    }

    public static void swipeByPercentage (@NotNull AndroidDriver<?> driver, double startPercentage, double endPercentage, double anchorPercentage, @NotNull String direction) {
        Dimension size = driver.manage().window().getSize();
        if(direction.equalsIgnoreCase("Horizontal")){
            int anchor = (int) (size.height * anchorPercentage);
            int startPoint = (int) (size.width * startPercentage);
            int endPoint = (int) (size.width * endPercentage);
            new TouchAction(driver)
                    .press(PointOption.point(startPoint, anchor))
                    .waitAction(WaitOptions.waitOptions(Duration.ofMillis(1000)))
                    .moveTo(PointOption.point(endPoint, anchor))
                    .release().perform();
        }
        else if(direction.equalsIgnoreCase("Vertical")){
            int anchor = (int) (size.width * anchorPercentage);
            int startPoint = (int) (size.height * startPercentage);
            int endPoint = (int) (size.height * endPercentage);
            new TouchAction(driver)
                    .press(PointOption.point(startPoint, anchor))
                    .waitAction(WaitOptions.waitOptions(Duration.ofMillis(1000)))
                    .moveTo(PointOption.point(endPoint, anchor))
                    .release().perform();
        }
    }

    public static void swipeByElements (AndroidDriver<?> driver, @NotNull AndroidElement startElement, @NotNull AndroidElement endElement) {
        int startX = startElement.getLocation().getX() + (startElement.getSize().getWidth() / 2);
        int startY = startElement.getLocation().getY() + (startElement.getSize().getHeight() / 2);
        int endX = endElement.getLocation().getX() + (endElement.getSize().getWidth() / 2);
        int endY = endElement.getLocation().getY() + (endElement.getSize().getHeight() / 2);
        new TouchAction(driver)
                .press(PointOption.point(startX,startY))
                .waitAction(WaitOptions.waitOptions(Duration.ofMillis(1000)))
                .moveTo(PointOption.point(endX, endY))
                .release().perform();
    }

    public static void dragNDrop(AndroidDriver<?> driver, AndroidElement startElement, AndroidElement endElement){
        new TouchAction<>(driver)
                .longPress(LongPressOptions.longPressOptions().withElement(ElementOption.element(startElement)))
                .moveTo(PointOption.point(endElement.getCenter().getX(),endElement.getCenter().getY()))
                .release().perform();

    }

    public static TouchAction tapOnCoordinate(AndroidDriver<?> driver, Point tap){
        return new TouchAction<>(driver).tap(PointOption.point(tap)).perform();
    }

    public static TouchAction tapOnCoordinate(AndroidDriver<?> driver, int x, int y){
        return new TouchAction<>(driver).tap(PointOption.point(x,y)).perform();
    }

    public static TouchAction tapOnElement(AndroidDriver<?> driver, AndroidElement element){
        return new TouchAction<>(driver)
                .tap(TapOptions.tapOptions().withElement(ElementOption.element(element))).perform();
    }

    public static TouchAction tapOnElement(AndroidDriver<?> driver, AndroidElement element,long waitMillis){
        return new TouchAction<>(driver)
                .tap(TapOptions.tapOptions().withElement(ElementOption.element(element)))
                .waitAction(WaitOptions.waitOptions(Duration.ofMillis(waitMillis))).perform();
    }

    public static TouchAction pressOnCoordinate(AndroidDriver<?> driver, @NotNull Point press){
        return new TouchAction<>(driver)
                .press(PointOption.point(press)).release().perform();
    }

    public static TouchAction pressOnElement(AndroidDriver<?> driver, @NotNull AndroidElement element){
        return new TouchAction<>(driver)
                .press(PointOption.point(element.getCenter().x,element.getCenter().y)).release().perform();
    }

    public static TouchAction longPressOnElement(AndroidDriver<?> driver, @NotNull AndroidElement element){
        return new TouchAction<>(driver)
                .longPress(PointOption.point(element.getCenter().x,element.getCenter().y)).perform();
    }

    public static void pressAndroidKey(@NotNull AndroidDriver<?> driver, AndroidKey key){
        driver.pressKey(new KeyEvent(key));
    }

    public static void sendTextInput(@NotNull AndroidElement input, String text){
        input.sendKeys(text);
    }

    public static String getElementText(@NotNull AndroidElement element) throws InterruptedException {
       try{
           return element.getText();
       }
       catch (StaleElementReferenceException e){
           Thread.sleep(5000);
           return element.getText();
       }

    }

    public static String getCurrentActivity(@NotNull AndroidDriver<?> driver){
        return driver.currentActivity();
    }

    public static void hideKeyboard(@NotNull AndroidDriver<?> driver){ driver.hideKeyboard(); }

    public static String getCurrentOrientation(@NotNull AndroidDriver<?> driver){ return driver.getOrientation().name(); }

    public static void setOrientation(AndroidDriver<?> driver, @NotNull String orientation){
        switch(orientation.toUpperCase()){
            case "LANDSCAPE":
                driver.rotate(ScreenOrientation.LANDSCAPE);
                break;
            default:
                driver.rotate(ScreenOrientation.PORTRAIT);
                break;
        }
    }

}