package com.automation.web.driver;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import java.net.URL;
import java.time.Duration;

public class DriverManager {
    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    private static final String SELENIUM_GRID_URL = "http://selenium-hub:4444";
    private static final boolean IS_RUNNING_IN_CONTAINER = System.getenv().containsKey("JENKINS_HOME");

    private DriverManager() {
        // Private constructor to prevent instantiation
    }

    public static WebDriver getDriver() {
        return driver.get();
    }

    public static void setDriver(String browserType) {
        try {
            WebDriver webDriver;

            if (IS_RUNNING_IN_CONTAINER) {
                // Running in Jenkins container - use Selenium Grid
                ChromeOptions options = new ChromeOptions();
                options.addArguments("--no-sandbox");
                options.addArguments("--disable-dev-shm-usage");
                options.addArguments("--headless");

                webDriver = new RemoteWebDriver(new URL(SELENIUM_GRID_URL + "/wd/hub"), options);
            } else {
                // Running locally - use local WebDriver
                webDriver = switch (browserType.toLowerCase()) {
                    case "chrome" -> {
                        WebDriverManager.chromedriver().setup();
                        yield new ChromeDriver();
                    }
                    case "firefox" -> {
                        WebDriverManager.firefoxdriver().setup();
                        yield new FirefoxDriver();
                    }
                    case "edge" -> {
                        WebDriverManager.edgedriver().setup();
                        yield new EdgeDriver();
                    }
                    default -> throw new IllegalArgumentException("Browser type not supported: " + browserType);
                };
            }

            webDriver.manage().window().maximize();
            webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            driver.set(webDriver);

        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize WebDriver: " + e.getMessage(), e);
        }
    }

    public static void quitDriver() {
        if (driver.get() != null) {
            driver.get().quit();
            driver.remove();
        }
    }
}