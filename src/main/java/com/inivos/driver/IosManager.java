package com.inivos.driver;


import com.inivos.config.Constants;
import com.inivos.enums.MobilePlatform;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.remote.AutomationName;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.URL;

public class IosManager{

    public static AppiumDriver<?> driver;

    public static IOSDriver getDriver() {
        // Initializing the Appium driver
        try {
            DesiredCapabilities cap = new DesiredCapabilities();
            cap.setCapability(MobileCapabilityType.PLATFORM_NAME, MobilePlatform.IOS);
            cap.setCapability(MobileCapabilityType.PLATFORM_VERSION, Constants.IOS_PLATFORM_VERSION);
            cap.setCapability(MobileCapabilityType.DEVICE_NAME, Constants.IOS_DEVICE_NAME);
            cap.setCapability(MobileCapabilityType.UDID, Constants.IOS_UDID);
            cap.setCapability("bundleId", Constants.IOS_BUNDLE_ID);
            cap.setCapability("autoLaunch", false);
            cap.setCapability(MobileCapabilityType.AUTOMATION_NAME, AutomationName.IOS_XCUI_TEST);
        // driver is declared as an IOSDriver, which supports IOSElements
            driver = new IOSDriver<IOSElement>(new URL(Constants.APPIUM_URL), cap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //Returning the instance of the driver to the parent method
        return (IOSDriver<?>) driver;
    }
}
