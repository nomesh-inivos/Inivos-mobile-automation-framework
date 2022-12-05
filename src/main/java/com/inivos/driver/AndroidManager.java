package com.inivos.driver;

import com.inivos.config.Constants;
import com.inivos.enums.MobilePlatform;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.AutomationName;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.URL;

public class AndroidManager {
    public static AppiumDriver<?> driver;

    public static AndroidDriver<?> getDriver() {
        // Initializing the Appium driver
        try {
            DesiredCapabilities cap = new DesiredCapabilities();
            cap.setCapability(MobileCapabilityType.PLATFORM_NAME, MobilePlatform.ANDROID);
            cap.setCapability(MobileCapabilityType.PLATFORM_VERSION, Constants.ANDROID_PLATFORM_VERSION);
            cap.setCapability(MobileCapabilityType.DEVICE_NAME, Constants.ANDROID_DEVICE_NAME);
            cap.setCapability(AndroidMobileCapabilityType.AVD, Constants.AVD);
            cap.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, Constants.APP_ACTIVITY);
            cap.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, Constants.APP_PACKAGE);
            cap.setCapability(AndroidMobileCapabilityType.AUTO_LAUNCH, Constants.AUTO_LAUNCH);
            cap.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, Constants.NEW_COMMAND_TIMEOUT);
            cap.setCapability(MobileCapabilityType.AUTOMATION_NAME, AutomationName.ANDROID_UIAUTOMATOR2);

            // driver is declared as an AndroidDriver, which supports AndroidElements
            driver = new AndroidDriver<AndroidElement>(new URL(Constants.APPIUM_URL), cap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //Returning the instance of the driver to the parent method
        return (AndroidDriver<?>) driver;
    }

}
