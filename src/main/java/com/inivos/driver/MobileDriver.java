
package com.inivos.driver;

import com.inivos.config.Constants;
import com.inivos.enums.MobilePlatform;
import io.appium.java_client.*;
import io.appium.java_client.android.AndroidDriver;

public class MobileDriver {
    public static AppiumDriver<?> driver = null;

    public static AppiumDriver getDriver(String platform) {
        if(platform.equalsIgnoreCase(String.valueOf(MobilePlatform.ANDROID))){
            driver = AndroidManager.getDriver();
        }else{
            driver = IosManager.getDriver();
        }

        return null;
    }

}