package com.inivos.util;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.WebElement;

public class AndroidTestSupport extends AppiumTestSupport{
    public AndroidTestSupport(AndroidDriver<?> driver){
        super(driver);
    }
}
