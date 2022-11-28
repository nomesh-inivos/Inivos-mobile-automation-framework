package com.inivos.util;

import io.appium.java_client.MobileDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.MobileDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.nativekey.AndroidKey;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ElementTestSupport {
    /**
     * <p>Swipe along a Progress bar till the given end point specified by end percentage</p>
     *
     * @param driver        - MobileDriver&lt;?&gt; - Android Driver Instance
     * @param locator       - String - Seek Bar Locator
     * @param method        - String - Seek Bar Locating Method
     * @param direction     - String - Seek Bar Direction (Horizontal/Vertical)
     * @param endPercentage - double - Where to end as a percentage of the bar element
     * @throws InterruptedException
     */
    public static void SeekBar(MobileDriver<?> driver, String locator, String method, String direction, double endPercentage) throws InterruptedException {
        MobileElement seek_bar = AppiumTestSupport.locateElement(driver, locator, method);

        AppiumTestSupport.swipeByPercentageOnElementPerform(driver, seek_bar, 0.001, endPercentage, 0.5, direction, 3000);
    }

    /**
     * <p>Swipe along a Rating Bar till the given end point specified by end percentage</p>
     *
     * @param driver        - MobileDriver&lt;?&gt; - Android Driver Instance
     * @param locator       - String - Seek Bar Locator
     * @param method        - String - Seek Bar Locating Method
     * @param direction     - String - Seek Bar Direction (Horizontal/Vertical)
     * @param endPercentage - double - Where to end as a percentage of the bar element
     * @throws InterruptedException
     */
    public static void RatingBar(MobileDriver<?> driver, String locator, String method, String direction, double endPercentage) throws InterruptedException {
        MobileElement rating_bar = AppiumTestSupport.locateElement(driver, locator, method);

        AppiumTestSupport.swipeByPercentageOnElementPerform(driver, rating_bar, 0.001, endPercentage, 0.5, direction, 3000);
    }


    /**
     * This method performs a search action on filter search bar
     * and returns the search result set as list of android elements
     *
     * @param driver               - MobileDriver&lt;?&gt; - Android Driver Instance
     * @param searchLocator        - String - Search bar locator string
     * @param searchLocatingMethod - String - Search bar locating method
     * @param resultLocator        - String - Result list locator string
     * @param resultLocatingMethod - String - Result list locating method
     * @param keys                 - AndroidKey[] - Array of android keys required to enter into search bar
     * @return List&lt;MobileElement&gt; - Result list as list object of Android Elements
     * @throws InterruptedException
     */
    public static List<MobileElement> SearchFilter(@NotNull MobileDriver<?> driver, String searchLocator, String searchLocatingMethod, String resultLocator, String resultLocatingMethod, AndroidKey[] keys) throws InterruptedException {
        MobileElement search = AppiumTestSupport.locateElement(driver, searchLocator, searchLocatingMethod);
        AppiumTestSupport.buttonClick(search);
        AppiumTestSupport.pressMultipleAndroidKey((AndroidDriver<?>) driver, keys);
        AppiumTestSupport.hideKeyboard(driver);

        List<MobileElement> results = AppiumTestSupport.locateElements(driver, resultLocator, resultLocatingMethod);
        return results;
    }

    /**
     * Perform switch operation on switch element and returns the checked status
     *
     * @param driver  - MobileDriver&lt;?&gt; - Android Driver Instance
     * @param locator - String - Locator String
     * @param method  - String - Locating method of the element
     * @return String - Checked status of the element
     * @throws InterruptedException
     */
    public static String Switch(MobileDriver<?> driver, String locator, String method) throws InterruptedException {
        MobileElement switchElement = AppiumTestSupport.locateElement(driver, locator, method);
        AppiumTestSupport.buttonClick(switchElement);
        return switchElement.getAttribute("checked");
    }

    /**
     * Perform check operation on checkbox element and returns the checked status
     *
     * @param driver  - MobileDriver&lt;?&gt; - Android Driver Instance
     * @param locator - String - Locator String
     * @param method  - String - Locating method of the element
     * @return String - Checked status of the element
     * @throws InterruptedException
     */
    public static String CheckBox(MobileDriver<?> driver, String locator, String method) throws InterruptedException {
        MobileElement checkBox = AppiumTestSupport.locateElement(driver, locator, method);
        AppiumTestSupport.buttonClick(checkBox);
        return checkBox.getAttribute("checked");
    }

    /**
     * Select element and returns the selected attribute of the element
     *
     * @param driver
     * @param locator
     * @param method
     * @return Boolean - Selected status of the element
     * @throws InterruptedException
     */
    public static boolean SelectElement(MobileDriver<?> driver, String locator, String method) throws InterruptedException {
        MobileElement selectElement = AppiumTestSupport.locateElement(driver, locator, method);
        AppiumTestSupport.longPressOnElementPerform(driver, selectElement);
        return selectElement.isSelected();
    }

    public static String Toast(MobileDriver<?> driver,String locator,String method) throws InterruptedException {
        return AppiumTestSupport.getElementText(driver, locator, method);
    }
}