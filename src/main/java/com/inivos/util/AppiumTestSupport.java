package com.inivos.util;

import com.beust.jcommander.ParameterException;
import com.thoughtworks.qdox.model.expression.And;
import io.appium.java_client.MobileDriver;
import io.appium.java_client.MultiTouchAction;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.MobileElement;
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

import java.time.Duration;
import java.util.List;
import java.util.Set;

public class AppiumTestSupport {

    private MobileDriver<?> driver;
    public AppiumTestSupport(MobileDriver<?> driver){
        this.driver = driver;
    }

    /**
     * <p>This method can locate and return specific android element defined by the method parameters</p>
     *
     * @param driver  - MobileDriver&lt;?&gt; - Mobile Driver Instance
     * @param locator - String - Element locator string
     * @param method  - String - location method (Id,AccessibilityId,Xpath,Classname) Case insensitive
     * @return MobileElement - Located Element
     * @throws InterruptedException
     * @throws ParameterException
     */
    public static @Nullable MobileElement locateElement(@NotNull MobileDriver<?> driver, @NotNull String locator, @NotNull String method) throws InterruptedException {
        MobileElement element;
        switch (method.toUpperCase()) {
            case "ID":
                try {
                    element = (MobileElement) driver.findElementById(locator);
                    return element;
                } catch (StaleElementReferenceException e) {
                    Thread.sleep(5000);
                    return (MobileElement) driver.findElementById(locator);
                }
            case "ACCESSIBILITYID":
                try {
                    element = (MobileElement) driver.findElementByAccessibilityId(locator);
                    return element;
                } catch (StaleElementReferenceException e) {
                    Thread.sleep(5000);
                    return (MobileElement) driver.findElementByAccessibilityId(locator);
                }
            case "XPATH":
                try {
                    element = (MobileElement) driver.findElementByXPath(locator);
                    return element;
                } catch (StaleElementReferenceException e) {
                    Thread.sleep(5000);
                    return (MobileElement) driver.findElementByXPath(locator);
                }
            case "CLASSNAME":
                try {
                    element = (MobileElement) driver.findElementByClassName(locator);
                    return element;
                } catch (StaleElementReferenceException e) {
                    Thread.sleep(5000);
                    return (MobileElement) driver.findElementByClassName(locator);
                }
            default:
                throw new ParameterException("ID,ACCESSIBILITYID,CLASSNAME and XPATH are only valid locating methods.");
        }
    }

    /**
     * <p>This method can locate and return specific set of android element defined by the method parameters as a list of android elements</p>
     *
     * @param driver  - MobileDriver&lt;?&gt; - Mobile Driver Instance
     * @param locator - String - Elements locator string
     * @param method  - String - location method (Id,AccessibilityId,Xpath,Classname) Case insensitive
     * @return - List&lt;MobileElement&gt; - List of Android Elements
     * @throws InterruptedException
     */
    public static @Nullable List<MobileElement> locateElements(@NotNull MobileDriver<?> driver, @NotNull String locator, @NotNull String method) throws InterruptedException {
        List<MobileElement> elements;
        switch (method.toUpperCase()) {
            case "ID":
                try {
                    elements = (List<MobileElement>) driver.findElementsById(locator);
                    return elements;
                } catch (Exception e) {
                    Thread.sleep(5000);
                    return (List<MobileElement>) driver.findElementsById(locator);
                }
            case "ACCESSIBILITYID":
                try {
                    elements = (List<MobileElement>) driver.findElementsByAccessibilityId(locator);
                    return elements;
                } catch (Exception e) {
                    Thread.sleep(5000);
                    return (List<MobileElement>) driver.findElementsByAccessibilityId(locator);
                }

            case "XPATH":
                try {
                    elements = (List<MobileElement>) driver.findElementsByXPath(locator);
                    return elements;
                } catch (Exception e) {
                    Thread.sleep(5000);
                    return (List<MobileElement>) driver.findElementsByXPath(locator);
                }

            case "CLASSNAME":
                try {
                    elements = (List<MobileElement>) driver.findElementsByClassName(locator);
                    return elements;
                } catch (Exception e) {
                    Thread.sleep(5000);
                    return (List<MobileElement>) driver.findElementsByClassName(locator);
                }
            default:
                throw new ParameterException("ID,ACCESSIBILITYID,CLASSNAME and XPATH are only valid locating methods.");
        }
    }

    /**
     * This method can be used to click a button
     *
     * @param driver  - MobileDriver&lt;?&gt; - Mobile Driver Instance
     * @param locator - String - Elements locator string
     * @param method  - String - location method (Id,AccessibilityId,Xpath,Classname) Case insensitive
     * @throws InterruptedException
     */
    public static void buttonClick(MobileDriver<?> driver, String locator, String method) throws InterruptedException {
        locateElement(driver, locator, method).click();
    }

    /**
     * This method can be used to click a button
     *
     * @param element - MobileElement - Button Element
     */
    public static void buttonClick(MobileElement element) {
        element.click();
    }

    /**
     * This method is for clicking multiple times
     *
     * @param button - MobileElement - Button element
     * @param times  - int - Number of Clicks
     */
    public static void buttonMultipleClick(@NotNull MobileElement button, int times) {
        for (int i = 0; i < times; i++) {
            button.click();
        }
    }

    /**
     * This method performs swipe touch action the screen using coordinates of screen, start and end
     *
     * @param driver - MobileDriver&lt;?&gt; - Mobile Driver Instance
     * @param start  - Point() - Start Coordinate as Point(x,y)
     * @param end    - Point() - End Coordinate as Point(x,y)
     */
    public static void swipeByCoordinatePerform(MobileDriver<?> driver, Point start, Point end) {
        new TouchAction<>(driver).press(PointOption.point(start))
                .moveTo(PointOption.point(end)).release().perform();
    }

    /**
     * Performs custom wait action of given time in seconds
     *
     * @param driver        - MobileDriver&lt;?&gt; - Mobile Driver Instance
     * @param waitInSeconds - int - Wait time in seconds
     */
    public static void customWaitAction(MobileDriver<?> driver, int waitInSeconds) {
        new TouchAction<>(driver).waitAction(WaitOptions.waitOptions(Duration.ofSeconds(waitInSeconds))).perform();
    }

    /**
     * This method returns a swipe TouchAction object defined by Start and End Coordinates
     *
     * @param driver - MobileDriver&lt;?&gt; - Mobile Driver Instance
     * @param start  - Point() - Start Coordinate as Point Object
     * @param end    - Point() - End Coordinate as Point Object
     * @return - TouchAction() - Swipe TouchAction object ready to perform
     */
    public static TouchAction swipeByCoordinate(MobileDriver<?> driver, Point start, Point end) {
        return new TouchAction<>(driver).press(PointOption.point(start))
                .moveTo(PointOption.point(end)).release();
    }

    /**
     * Swipe touch action with wait action of specific time defined by parameter
     *
     * @param driver       - MobileDriver&lt;?&gt; - Mobile Driver Instance
     * @param startPoint   - Point() - Start Coordinate Point()
     * @param endPoint     - Point() - End Coordinate Point()
     * @param waitOfMillis - long - Swiping time in milliseconds (long)
     */
    public static void swipeByCoordinatePerform(MobileDriver<?> driver, Point startPoint, Point endPoint, long waitOfMillis) {
        new TouchAction<>(driver).press(PointOption.point(startPoint)).waitAction(WaitOptions.waitOptions(Duration.ofMillis(waitOfMillis)))
                .moveTo(PointOption.point(endPoint)).release().perform();
    }

    /**
     * This method return Swipe TouchAction object that is ready perform a swiping action specified by Start Percentage,End Percentage, Anchor Percentage and Swiping Direction.
     * Percentages should be defined by considering the complete screen.
     *
     * @param driver           - MobileDriver&lt;?&gt; - Mobile Driver Instance
     * @param startPercentage  - double - Start of the action in terms of display percentage.
     * @param endPercentage    - double - End of the action in terms of display percentage.
     * @param anchorPercentage - double - Anchor point of the touch action in terms of display percentage.
     * @param direction        - String - Direction of the swiping action(Horizontal , Vertical)
     * @return TouchAction - Swipe Touch action ready to perform
     */
    public static @Nullable TouchAction swipeByPercentage(@NotNull MobileDriver<?> driver, double startPercentage, double endPercentage, double anchorPercentage, @NotNull String direction) {
        Dimension size = driver.manage().window().getSize();
        if (direction.equalsIgnoreCase("Horizontal")) {
            int anchor = (int) (size.height * anchorPercentage);
            int startPoint = (int) (size.width * startPercentage);
            int endPoint = (int) (size.width * endPercentage);
            return new TouchAction(driver)
                    .press(PointOption.point(startPoint, anchor))
                    .moveTo(PointOption.point(endPoint, anchor))
                    .release();
        } else if (direction.equalsIgnoreCase("Vertical")) {
            int anchor = (int) (size.width * anchorPercentage);
            int startPoint = (int) (size.height * startPercentage);
            int endPoint = (int) (size.height * endPercentage);
            return new TouchAction(driver)
                    .press(PointOption.point(anchor, startPoint))
                    .moveTo(PointOption.point(anchor, endPoint))
                    .release();
        }
        return null;
    }

    /**
     * This method return Swipe TouchAction object that is ready perform a swiping action specified by Start Percentage,End Percentage, Anchor Percentage and Swiping Direction.
     * Percentages should be defined by considering the complete screen.
     *
     * @param driver           - MobileDriver&lt;?&gt; - Mobile Driver Instance
     * @param startPercentage  - double - Start of the action in terms of display percentage.
     * @param endPercentage    - double - End of the action in terms of display percentage.
     * @param anchorPercentage - double - Anchor point of the touch action in terms of display percentage.
     * @param direction        - String - Direction of the swiping action(Horizontal , Vertical)
     * @param waitMillis       - long - Swiping time in milliseconds (long)
     * @return TouchAction - Swipe Touch action ready to perform
     */
    public static @Nullable TouchAction swipeByPercentage(@NotNull MobileDriver<?> driver, double startPercentage, double endPercentage, double anchorPercentage, @NotNull String direction, long waitMillis) {
        Dimension size = driver.manage().window().getSize();
        if (direction.equalsIgnoreCase("Horizontal")) {
            int anchor = (int) (size.height * anchorPercentage);
            int startPoint = (int) (size.width * startPercentage);
            int endPoint = (int) (size.width * endPercentage);
            return new TouchAction(driver)
                    .press(PointOption.point(startPoint, anchor))
                    .waitAction(WaitOptions.waitOptions(Duration.ofMillis(waitMillis)))
                    .moveTo(PointOption.point(endPoint, anchor))
                    .release();
        } else if (direction.equalsIgnoreCase("Vertical")) {
            int anchor = (int) (size.width * anchorPercentage);
            int startPoint = (int) (size.height * startPercentage);
            int endPoint = (int) (size.height * endPercentage);
            return new TouchAction(driver)
                    .press(PointOption.point(anchor, startPoint))
                    .moveTo(PointOption.point(anchor, endPoint))
                    .release();
        }
        return null;
    }


    /**
     * This method performs a swiping action specified by Start Percentage,End Percentage, Anchor Percentage and Swiping Direction.
     * Percentages should be defined by considering the complete screen.
     *
     * @param driver           - MobileDriver&lt;?&gt; - Mobile Driver Instance
     * @param startPercentage  - double - Start of the action in terms of display percentage.
     * @param endPercentage    - double - End of the action in terms of display percentage.
     * @param anchorPercentage - double - Anchor point of the touch action in terms of display percentage.
     * @param direction        - String - Direction of the swiping action(Horizontal , Vertical)
     * @param waitMillis       - long - Swiping time in milliseconds (long)
     */
    public static void swipeByPercentagePerform(@NotNull MobileDriver<?> driver, double startPercentage, double endPercentage, double anchorPercentage, @NotNull String direction, long waitMillis) {
        Dimension size = driver.manage().window().getSize();
        if (direction.equalsIgnoreCase("Horizontal")) {
            int anchor = (int) (size.height * anchorPercentage);
            int startPoint = (int) (size.width * startPercentage);
            int endPoint = (int) (size.width * endPercentage);
            new TouchAction(driver)
                    .press(PointOption.point(startPoint, anchor))
                    .waitAction(WaitOptions.waitOptions(Duration.ofMillis(waitMillis)))
                    .moveTo(PointOption.point(endPoint, anchor))
                    .release().perform();
        } else if (direction.equalsIgnoreCase("Vertical")) {
            int anchor = (int) (size.width * anchorPercentage);
            int startPoint = (int) (size.height * startPercentage);
            int endPoint = (int) (size.height * endPercentage);
            new TouchAction(driver)
                    .press(PointOption.point(anchor, startPoint))
                    .waitAction(WaitOptions.waitOptions(Duration.ofMillis(waitMillis)))
                    .moveTo(PointOption.point(anchor, endPoint))
                    .release().perform();
        }
    }

    /**
     * This method performs a swiping action specified by Start Percentage,End Percentage, Anchor Percentage Swiping Direction and Swiping duration.
     * Percentages should be defined by considering the given element.
     *
     * @param driver           - MobileDriver - Mobile Driver Instance
     * @param element          - MobileElement - Android Element
     * @param startPercentage  - double - Start percentage in terms of given element size
     * @param endPercentage    - double - End percentage in terms of given element size
     * @param anchorPercentage - double - Anchor percentage in terms of given element size
     * @param direction        - String - Swiping direction (Horizontal, Vertical)
     * @param waitMillis       - long - Swiping time duration
     */
    public static void swipeByPercentageOnElementPerform(@NotNull MobileDriver<?> driver, MobileElement element, double startPercentage, double endPercentage, double anchorPercentage, @NotNull String direction, long waitMillis) {
        Dimension size = driver.manage().window().getSize();

        if (direction.equalsIgnoreCase("Horizontal")) {
            Point StartPoint = AppiumTestSupport.getCoordinateOnElementPerc(element, startPercentage, anchorPercentage);
            Point EndPoint = AppiumTestSupport.getCoordinateOnElementPerc(element, endPercentage, anchorPercentage);

            new TouchAction(driver)
                    .press(PointOption.point(StartPoint))
                    .waitAction(WaitOptions.waitOptions(Duration.ofMillis(waitMillis)))
                    .moveTo(PointOption.point(EndPoint))
                    .release().perform();
        } else if (direction.equalsIgnoreCase("Vertical")) {
            Point StartPoint = AppiumTestSupport.getCoordinateOnElementPerc(element, anchorPercentage, startPercentage);
            Point EndPoint = AppiumTestSupport.getCoordinateOnElementPerc(element, anchorPercentage, endPercentage);

            new TouchAction(driver)
                    .press(PointOption.point(StartPoint))
                    .waitAction(WaitOptions.waitOptions(Duration.ofMillis(waitMillis)))
                    .moveTo(PointOption.point(EndPoint))
                    .release().perform();
        }
    }

    /**
     * This method returns a swiping touch action that ready to perform, specified by Start Percentage,End Percentage, Anchor Percentage Swiping Direction and Swiping duration.
     * Percentages should be defined by considering the given element.
     *
     * @param driver           - MobileDriver&lt;?&gt; - Mobile Driver Instance
     * @param element          - MobileElement - Android Element
     * @param startPercentage  - double - Start percentage in terms of given element size
     * @param endPercentage    - double - End percentage in terms of given element size
     * @param anchorPercentage - double - Anchor percentage in terms of given element size
     * @param direction        - String - Swiping direction (Horizontal, Vertical)
     * @param waitMillis       - long - Swiping time duration
     * @return TouchAction() - Swiping touch Action
     */
    public static @Nullable TouchAction swipeByPercentageOnElement(@NotNull MobileDriver<?> driver, MobileElement element, double startPercentage, double endPercentage, double anchorPercentage, @NotNull String direction, long waitMillis) {
        Dimension size = driver.manage().window().getSize();

        if (direction.equalsIgnoreCase("Horizontal")) {
            Point StartPoint = AppiumTestSupport.getCoordinateOnElementPerc(element, startPercentage, anchorPercentage);
            Point EndPoint = AppiumTestSupport.getCoordinateOnElementPerc(element, endPercentage, anchorPercentage);

            return new TouchAction(driver)
                    .press(PointOption.point(StartPoint))
                    .waitAction(WaitOptions.waitOptions(Duration.ofMillis(waitMillis)))
                    .moveTo(PointOption.point(EndPoint))
                    .release();
        } else if (direction.equalsIgnoreCase("Vertical")) {
            Point StartPoint = AppiumTestSupport.getCoordinateOnElementPerc(element, anchorPercentage, startPercentage);
            Point EndPoint = AppiumTestSupport.getCoordinateOnElementPerc(element, anchorPercentage, endPercentage);

            return new TouchAction(driver)
                    .press(PointOption.point(StartPoint))
                    .waitAction(WaitOptions.waitOptions(Duration.ofMillis(waitMillis)))
                    .moveTo(PointOption.point(EndPoint))
                    .release();
        }
        return null;
    }

    /**
     * This method performs a swipe action from a start element to an end element.
     *
     * @param driver       - MobileDriver&lt;?&gt; - Mobile Driver Instance
     * @param startElement - MobileElement - Start Element
     * @param endElement   - MobileElement - End Element
     */
    public static void swipeByElementsPerform(MobileDriver<?> driver, @NotNull MobileElement startElement, @NotNull MobileElement endElement) {
        int startX = startElement.getLocation().getX() + (startElement.getSize().getWidth() / 2);
        int startY = startElement.getLocation().getY() + (startElement.getSize().getHeight() / 2);
        int endX = endElement.getLocation().getX() + (endElement.getSize().getWidth() / 2);
        int endY = endElement.getLocation().getY() + (endElement.getSize().getHeight() / 2);
        new TouchAction(driver)
                .press(PointOption.point(startX, startY))
                .waitAction(WaitOptions.waitOptions(Duration.ofMillis(1000)))
                .moveTo(PointOption.point(endX, endY))
                .release().perform();
    }

    /**
     * This method returns a touch action object that ready to perform a swipe from a start element to an end element.
     *
     * @param driver       - MobileDriver&lt;?&gt; - Mobile Driver Instance
     * @param startElement - MobileElement - Start Element
     * @param endElement   - MobileElement - End Element
     * @return TouchAction
     */
    public static TouchAction swipeByElements(MobileDriver<?> driver, @NotNull MobileElement startElement, @NotNull MobileElement endElement) {
        int startX = startElement.getLocation().getX() + (startElement.getSize().getWidth() / 2);
        int startY = startElement.getLocation().getY() + (startElement.getSize().getHeight() / 2);
        int endX = endElement.getLocation().getX() + (endElement.getSize().getWidth() / 2);
        int endY = endElement.getLocation().getY() + (endElement.getSize().getHeight() / 2);
        return new TouchAction(driver)
                .press(PointOption.point(startX, startY))
                .waitAction(WaitOptions.waitOptions(Duration.ofMillis(1000)))
                .moveTo(PointOption.point(endX, endY))
                .release();
    }

    /**
     * Draws out quick settings panel
     *
     * @param driver - MobileDriver&lt;?&gt; - Mobile Driver Instance
     */
    public static void drawQuickSettings(MobileDriver<?> driver) {
        AppiumTestSupport.swipeByPercentagePerform(driver, 0, 0.9, 0.5, "Vertical", 3000);

    }

    /**
     * This method locate drag and drop elements and performs a drag and drop action .
     *
     * @param driver             - MobileDriver&lt;?&gt; - Mobile Driver Instance
     * @param dragLocator        - String - Locator of the drag object
     * @param dragLocatingMethod - String - Locating method of the drag object
     * @param dropLocator        - String - Locator of the drop object
     * @param dropLocatingMethod - String - Locating method of the drop object
     * @throws InterruptedException
     */
    public static void dragNDrop(MobileDriver<?> driver, String dragLocator, String dragLocatingMethod, String dropLocator, String dropLocatingMethod) throws InterruptedException {
        MobileElement dot1 = AppiumTestSupport.locateElement(driver, dragLocator, dragLocatingMethod);
        MobileElement dot2 = AppiumTestSupport.locateElement(driver, dropLocator, dropLocatingMethod);

        new TouchAction<>(driver)
                .longPress(LongPressOptions.longPressOptions().withElement(ElementOption.element(dot1)))
                .moveTo(PointOption.point(dot2.getCenter().getX(), dot2.getCenter().getY()))
                .release().perform();
    }

    /**
     * This method performs a drag and drop action using given elements through parameters.
     *
     * @param driver      - MobileDriver&lt;?&gt; - Mobile Driver Instance
     * @param dragElement - MobileElement - Drag element
     * @param dropElement - MobileElement - Drop element
     */
    public static void dragNDrop(MobileDriver<?> driver, MobileElement dragElement, MobileElement dropElement) {
        new TouchAction<>(driver)
                .longPress(LongPressOptions.longPressOptions().withElement(ElementOption.element(dragElement)))
                .moveTo(PointOption.point(dropElement.getCenter().getX(), dropElement.getCenter().getY()))
                .release().perform();
    }

    /**
     * Returns Tap action on given coordinates that ready to perform.
     *
     * @param driver - MobileDriver&lt;?&gt; - Mobile Driver Instance
     * @param tap    - Point() - Coordinate to tap on
     * @return TouchAction()
     */
    public static TouchAction tapOnCoordinate(MobileDriver<?> driver, Point tap) {
        return new TouchAction<>(driver).tap(PointOption.point(tap));
    }

    /**
     * Performs Tap action on given coordinates given by Point object.
     *
     * @param driver - MobileDriver&lt;?&gt; - Mobile Driver Instance
     * @param tap    - Point() - Coordinate to tap on
     */
    public static void tapOnCoordinatePerform(MobileDriver<?> driver, Point tap) {
        new TouchAction<>(driver).tap(PointOption.point(tap)).perform();
    }

    /**
     * Performs Tap action on given coordinates given by x and y values.
     *
     * @param driver - MobileDriver&lt;?&gt; - Mobile Driver Instance
     * @param x      - int - X Coordinate of the point tap on
     * @param y      - int - Y Coordinate of the point tap on
     */
    public static void tapOnCoordinatePerform(MobileDriver<?> driver, int x, int y) {
        new TouchAction<>(driver).tap(PointOption.point(x, y)).perform();
    }

    /**
     * Returns a Tap action on given element that ready to perform.
     *
     * @param driver  - MobileDriver&lt;?&gt; - Mobile Driver Instance
     * @param element - MobileElement - Element to tap on
     * @return TouchAction - Tap action
     */
    public static TouchAction tapOnElement(MobileDriver<?> driver, MobileElement element) {
        return new TouchAction<>(driver)
                .tap(TapOptions.tapOptions().withElement(ElementOption.element(element)));
    }

    /**
     * Performs a Tap action on given element for specified time duration.
     *
     * @param driver     - MobileDriver&lt;?&gt; - Mobile Driver Instance
     * @param element    - MobileElement - Element to tap on
     * @param waitMillis - long - tapping time duration
     */
    public static void tapOnElementPerform(MobileDriver<?> driver, MobileElement element, long waitMillis) {
        new TouchAction<>(driver)
                .tap(TapOptions.tapOptions().withElement(ElementOption.element(element)))
                .waitAction(WaitOptions.waitOptions(Duration.ofMillis(waitMillis))).perform();
    }

    /**
     * Returns Press action on given coordinates that ready to perform.
     *
     * @param driver - MobileDriver&lt;?&gt; - Mobile Driver Instance
     * @param press  - Point() - Coordinate to press on
     * @return TouchAction()
     */
    public static TouchAction pressOnCoordinate(MobileDriver<?> driver, @NotNull Point press) {
        return new TouchAction<>(driver)
                .press(PointOption.point(press)).release();
    }

    /**
     * Performs Press action on given coordinates given by Point object.
     *
     * @param driver - MobileDriver&lt;?&gt; - Mobile Driver Instance
     * @param press  - Point() - Coordinate to press on
     */
    public static void pressOnCoordinatePerform(MobileDriver<?> driver, @NotNull Point press) {
        new TouchAction<>(driver)
                .press(PointOption.point(press)).release().perform();
    }

    /**
     * Returns a Press action on given element that ready to perform.
     *
     * @param driver  - MobileDriver&lt;?&gt; - Mobile Driver Instance
     * @param element - MobileElement - Element to Press on
     * @return TouchAction - Press action
     */
    public static TouchAction pressOnElement(MobileDriver<?> driver, @NotNull MobileElement element) {
        return new TouchAction<>(driver)
                .press(PointOption.point(element.getCenter().x, element.getCenter().y)).release();
    }

    /**
     * Performs a Press action on given element.
     *
     * @param driver  - MobileDriver&lt;?&gt; - Mobile Driver Instance
     * @param element - MobileElement - Element to Press on
     */
    public static void pressOnElementPerform(MobileDriver<?> driver, @NotNull MobileElement element) {
        new TouchAction<>(driver)
                .press(PointOption.point(element.getCenter().x, element.getCenter().y)).release().perform();
    }

    /**
     * Performs a Long press action on given element.
     *
     * @param driver  - MobileDriver&lt;?&gt; - Mobile Driver Instance
     * @param element - MobileElement - Element to Press on
     */
    public static void longPressOnElementPerform(MobileDriver<?> driver, @NotNull MobileElement element) {
        new TouchAction<>(driver)
                .longPress(LongPressOptions.longPressOptions().withElement(ElementOption.element(element))).perform();
    }

    /**
     * Returns a Long press action on given element that ready to perform.
     *
     * @param driver  - MobileDriver&lt;?&gt; - Mobile Driver Instance
     * @param element - MobileElement - Element to Press on
     * @return TouchAction - LongPress action
     */
    public static TouchAction longPressOnElement(MobileDriver<?> driver, @NotNull MobileElement element) {
        return new TouchAction<>(driver)
                .longPress(LongPressOptions.longPressOptions().withElement(ElementOption.element(element)));
    }

    /**
     * Performs given android key press event. Driver instance should be android.
     *
     * @param driver - AndroidDriver&lt;?&gt; - Android Driver Instance
     * @param key    - AndroidKey - Android Key object needs to be pressed
     */
    public static void pressAndroidKey(@NotNull AndroidDriver<?> driver, AndroidKey key) {
        driver.pressKey(new KeyEvent(key));
    }

    /**
     * Press the back button on Android
     * @param driver - AndroidDriver&lt;?&gt; - Android Driver Instance
     */
    public static void pressBackButtonAndroid(@NotNull AndroidDriver<?> driver) {
        AppiumTestSupport.pressAndroidKey(driver, AndroidKey.BACK);
    }

    /**
     * Performs android key press event on array of android keys. Driver instance should be android.
     *
     * @param driver - AndroidDriver&lt;?&gt; - Android Driver Instance
     * @param keys   - AndroidKey[] - Array of Android Keys
     */
    public static void pressMultipleAndroidKey(AndroidDriver<?> driver, AndroidKey[] keys) {
        for (AndroidKey key : keys) {
            pressAndroidKey(driver, key);
        }
    }

    /**
     * This method performs given array of touch actions simultaneously.
     *
     * @param driver  - MobileDriver&lt;?&gt; - Mobile Driver Instance
     * @param actions - TouchAction[] - Array of Touch actions
     * @return MultiTouchAction - Performed multitouch action object
     */
    public static MultiTouchAction multipleTouchAction(MobileDriver<?> driver, TouchAction[] actions) {
        MultiTouchAction multiAction = new MultiTouchAction(driver);
        for (TouchAction action : actions) {
            multiAction.add(action);
        }
        return multiAction.perform();
    }

    /**
     * Send given text to an element
     *
     * @param input - MobileElement - Android Input Element
     * @param text  - String - Text string
     */
    public static void sendTextInput(@NotNull MobileElement input, String text) {
        input.sendKeys(text);
    }

    /**
     * Send given text to located element using locator string
     *
     * @param driver  - MobileDriver&lt;?&gt; - Mobile Driver Instance
     * @param locator - Locator of the input element
     * @param method  - Locating method of the input element
     * @param text    - String - Text string
     * @throws InterruptedException
     */
    public static void sendTextInput(@NotNull MobileDriver<?> driver, String locator, String method, String text) throws InterruptedException {
        locateElement(driver, locator, method).sendKeys(text);
    }

    /**
     * Returns Text of an given element
     *
     * @param element - MobileElement
     * @return String - Extracted text
     * @throws InterruptedException
     */
    public static String getElementText(@NotNull MobileElement element) throws InterruptedException {
        try {
            return element.getText();
        } catch (StaleElementReferenceException e) {
            Thread.sleep(5000);
            return element.getText();
        }
    }

    /**
     * Returns text of an located element
     *
     * @param driver  - MobileDriver&lt;?&gt; - Mobile Driver Instance
     * @param locator - String - Locator
     * @param method  - String - Locating method
     * @return String - Extracted Text
     * @throws InterruptedException
     */
    public static String getElementText(@NotNull MobileDriver driver, String locator, String method) throws InterruptedException {
        MobileElement element = locateElement(driver, locator, method);
        try {
            return element.getText();
        } catch (StaleElementReferenceException e) {
            Thread.sleep(5000);
            return element.getText();
        }
    }

    /**
     * Returns the current app activity, Driver instance should be android.
     *
     * @param driver - AndroidDriver&lt;?&gt; - Android Driver Instance
     * @return String - Current App activity name
     */
    public static String getCurrentActivity(@NotNull AndroidDriver<?> driver) {
        return driver.currentActivity();
    }

    /**
     * Hide the phone keyboard
     *
     * @param driver - MobileDriver&lt;?&gt; - Mobile Driver Instance
     */
    public static void hideKeyboard(@NotNull MobileDriver<?> driver) {
        driver.hideKeyboard();
    }

    /**
     * Returns the Current orientation of the mobile
     *
     * @param driver - MobileDriver&lt;?&gt; - Mobile Driver Instance
     * @return String - Current Orientation
     */
    public static String getCurrentOrientation(@NotNull MobileDriver<?> driver) {
        return driver.getOrientation().name();
    }

    /**
     * Change the orientation to given value, Default Orientation is Portrait
     *
     * @param driver      - MobileDriver&lt;?&gt; - Mobile Driver Instance
     * @param orientation - String - New orientation
     */
    public static void changeOrientation(MobileDriver<?> driver, @NotNull String orientation) {
        switch (orientation.toUpperCase()) {
            case "LANDSCAPE":
                driver.rotate(ScreenOrientation.LANDSCAPE);
                break;
            default:
                driver.rotate(ScreenOrientation.PORTRAIT);
                break;
        }
    }

    /**
     * Returns Screen Width
     *
     * @param driver - MobileDriver&lt;?&gt; - Mobile Driver Instance
     * @return int - Width
     */
    public static int getScreenWidth(@NotNull MobileDriver<?> driver) {
        return driver.manage().window().getSize().getWidth();
    }

    /**
     * Returns Screen Height
     *
     * @param driver - MobileDriver&lt;?&gt; - Mobile Driver Instance
     * @return int - Height
     */
    public static int getScreenHeight(@NotNull MobileDriver<?> driver) {
        return driver.manage().window().getSize().getHeight();
    }

    /**
     * Returns Width of given element
     *
     * @param element - MobileElement
     * @return int - Width of the element
     */
    public static int getElementWidth(@NotNull MobileElement element) {
        return element.getSize().getWidth();
    }

    /**
     * Returns Height of given element
     *
     * @param element - MobileElement
     * @return int - Height of the element
     */
    public static int getElementHeight(@NotNull MobileElement element) {
        return element.getSize().getHeight();
    }

    /**
     * Returns the X coordinate of given element's center
     *
     * @param element - MobileElement
     * @return int - X coordinate of the center
     */
    public static int getElementCenterX(@NotNull MobileElement element) {
        return element.getCenter().getX();
    }

    /**
     * Returns the Y coordinate of given element's center
     *
     * @param element - MobileElement
     * @return int - Y coordinate of the center
     */
    public static int getElementCenterY(@NotNull MobileElement element) {
        return element.getCenter().getY();
    }

    /**
     * Convert coordinate given as percentage of element size to screen coordinate
     *
     * @param element - MobileElement
     * @param percX   - X coordinate as percentage of Width
     * @param percY   - Y coordinate as percentage of Height
     * @return Point() - Screen Coordinate as Point object
     */
    public static @NotNull Point getCoordinateOnElementPerc(@NotNull MobileElement element, double percX, double percY) {
        int x = element.getLocation().getX();
        int y = element.getLocation().getY();
        return new Point(x + (int) (getElementWidth(element) * percX), y + (int) (getElementHeight(element) * percY));
    }

    /**
     * Return set of available Context handles to automate
     *
     * @param driver - MobileDriver&lt;?&gt; - Mobile Driver Instance
     * @return Set&lt;String&gt; - Set of contexts
     */
    public static Set<String> getAllAvailableContexts(MobileDriver<?> driver) {
        return driver.getContextHandles();
    }
}
