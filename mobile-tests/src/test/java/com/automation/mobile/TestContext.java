package com.automation.mobile;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public class TestContext {
    private static AndroidDriver driver;
    private static WebDriverWait wait;
    private static String orderNumber;
    private static String gridUrl = System.getProperty("appium.url", "http://localhost:4723");
    public TestContext() throws MalformedURLException {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("appium:deviceName", "emulator-5554");
        capabilities.setCapability("platformName", "android");
        capabilities.setCapability("appium:platformVersion", "15");
        capabilities.setCapability("appium:automationName", "UiAutomator2");
        capabilities.setCapability("appium:appPackage", "com.saucelabs.mydemoapp.rn");
        capabilities.setCapability("appium:appActivity", "com.saucelabs.mydemoapp.rn.MainActivity");

        // Add these capabilities to help with stability
        capabilities.setCapability("appium:autoGrantPermissions", true);
        capabilities.setCapability("appium:fullReset", false);
        capabilities.setCapability("appium:noReset", true);
        capabilities.setCapability("appium:dontStopAppOnReset", true);
        capabilities.setCapability("appium:waitForIdleTimeout", 0);
        capabilities.setCapability("appium:newCommandTimeout", 300);

        driver = new AndroidDriver(new URL(gridUrl), capabilities);
        wait = new WebDriverWait(driver, Duration.ofSeconds(15)); // Increased timeout
    }
    public static AndroidDriver getDriver() {
        return driver;
    }

    public static WebDriverWait getWait() {
        return wait;
    }

    public static void setOrderNumber(String number) {
        orderNumber = number;
    }

    public static String getOrderNumber() {
        return orderNumber;
    }
    public static void quitDriver() {
        driver.quit();
    }
}