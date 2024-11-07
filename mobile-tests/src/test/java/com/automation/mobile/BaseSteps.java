package com.automation.mobile;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public class BaseSteps {
  private static AndroidDriver driver;
  private static WebDriverWait wait;

  public static AndroidDriver getDriver() {
    return driver;
  }

  public static WebDriverWait getWait() {
    return wait;
  }

  public static void setDriver() throws MalformedURLException {
    if (driver != null) {
      driver.quit();
    }

    UiAutomator2Options options = new UiAutomator2Options()
        .setDeviceName("emulator-5554")
        .setAutomationName("UiAutomator2")
        .setPlatformName("Android")
        .setAppPackage("com.saucelabs.mydemoapp.rn")
        .setAppActivity("com.saucelabs.mydemoapp.rn.MainActivity")
        .setNewCommandTimeout(Duration.ofSeconds(60))
        .setAutoGrantPermissions(true)
        .setNoReset(true);

    driver = new AndroidDriver(
        new URL("http://localhost:4723"),
        options
    );

    wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
  }

  public static void quitDriver() {
    if (driver != null) {
      driver.quit();
      driver = null;
    }
  }

  @And("the app state is reset")
  public void resetState() {
    WebElement resetButton = wait.until(ExpectedConditions.visibilityOfElementLocated(
        By.xpath("//android.view.ViewGroup[@content-desc=\"longpress reset app\"]")));
    new Actions(driver).clickAndHold(resetButton);
  }

  @Before
  public void setupDriver() throws Exception {
    setDriver();
  }

  @After
  public void tearDown() {
    quitDriver();
  }
}
