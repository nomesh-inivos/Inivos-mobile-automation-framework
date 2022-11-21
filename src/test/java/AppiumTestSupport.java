import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.touch.TapOptions;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.ElementOption;
import io.appium.java_client.touch.offset.PointOption;
import org.jetbrains.annotations.NotNull;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;

import java.time.Duration;

public class AppiumTestSupport {
    public static AndroidElement locateElement(@NotNull AndroidDriver<?> driver, @NotNull String locator, @NotNull String method) throws InterruptedException {
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
            case "ACCESSIBLEID":
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

    public static void tapOnCoordinate(AndroidDriver<?> driver, Point tap){
        new TouchAction<>(driver).tap(PointOption.point(tap)).perform();
    }

    public static void tapOnElement(AndroidDriver<?> driver, AndroidElement element){
        new TouchAction<>(driver)
                .tap(TapOptions.tapOptions().withElement(ElementOption.element(element)));
    }

    public static void pressOnElement(AndroidDriver<?> driver, @NotNull AndroidElement element){
        new TouchAction<>(driver)
                .press(PointOption.point(element.getCenter().x,element.getCenter().y)).perform();
    }

    public static void longPressOnElement(AndroidDriver<?> driver, @NotNull AndroidElement element){
        new TouchAction<>(driver)
                .longPress(PointOption.point(element.getCenter().x,element.getCenter().y)).perform();
    }

    public static void pressAndroidKey(@NotNull AndroidDriver<?> driver, AndroidKey key){
        driver.pressKey(new KeyEvent(key));
    }

    public static void sendTextInput(@NotNull AndroidElement input, String text){
        input.sendKeys(text);
    }

    public static String getElementText(@NotNull AndroidElement element){
        return element.getText();
    }

    public static String getCurrentActivity(@NotNull AndroidDriver<?> driver){
        return driver.currentActivity();
    }
}
