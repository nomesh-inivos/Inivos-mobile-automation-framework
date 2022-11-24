package com.inivos.util;

import io.appium.java_client.android.Activity;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

public class ElementTestSupport {
    public static void testSeekBar(AndroidDriver<?> driver,String PACKAGE,String ACTIVITY,String locator,String method,String direction,double endPercentage) throws InterruptedException {
        driver.startActivity(new Activity(PACKAGE,ACTIVITY));

        AndroidElement seek_bar = AppiumTestSupport.locateElement(driver,locator,method);

        AppiumTestSupport.swipeByPercentageOnElementPerform(driver,seek_bar,0.001,endPercentage,0.5,direction,3000);
    }
}
