package com.inivos.driver;

import com.inivos.enums.MobilePlatform;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.appium.java_client.AppiumDriver;


public class AppiumDriverFactory {
    public AppiumDriverFactory() {}
    public static AppiumDriver<?> mobileDriver;
    private static Logger LOGGER = LoggerFactory.getLogger(AppiumDriverFactory.class);

    public static AppiumDriver<?> getDriver(String platform){
        System.out.println(MobilePlatform.ANDROID.name().equals(platform));
        LOGGER.debug("loading the AppiumDriverFactory getDriver.. platform is: >>>>>>>", platform);
        if(platform.equals(MobilePlatform.ANDROID.name())){
            mobileDriver = AndroidManager.getDriver();
        }else{
            mobileDriver = IosManager.getDriver();
        }
        LOGGER.debug("loading the AppiumDriverFactory getDriver>>>>>>>", mobileDriver.toString());
        return mobileDriver;
    }
}
