package com.automation.web.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public abstract class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected static final long PERFORMANCE_THRESHOLD = 2000; // 2000ms threshold
    private long operationStartTime;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    protected void startTimingOperation() {
        operationStartTime = System.currentTimeMillis();
    }

    protected long getOperationDuration() {
        return System.currentTimeMillis() - operationStartTime;
    }

    protected void waitForVisibility(WebElement element) {
        startTimingOperation();
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    protected void waitForClickability(WebElement element) {
        startTimingOperation();
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    protected void click(WebElement element) {
        startTimingOperation();
        waitForClickability(element);
        element.click();
        long duration = getOperationDuration();
        if (duration > PERFORMANCE_THRESHOLD) {
            System.out.println("Warning: Click operation took " + duration + "ms");
        }
    }

    protected void sendKeys(WebElement element, String text) {
        startTimingOperation();
        waitForVisibility(element);
        element.clear();
        element.sendKeys(text);
        long duration = getOperationDuration();
        if (duration > PERFORMANCE_THRESHOLD) {
            System.out.println("Warning: SendKeys operation took " + duration + "ms");
        }
    }

    protected String getText(WebElement element) {
        startTimingOperation();
        waitForVisibility(element);
        String text = element.getText();
        long duration = getOperationDuration();
        if (duration > PERFORMANCE_THRESHOLD) {
            System.out.println("Warning: GetText operation took " + duration + "ms");
        }
        return text;
    }

    protected boolean isDisplayed(WebElement element) {
        try {
            startTimingOperation();
            boolean result = element.isDisplayed();
            long duration = getOperationDuration();
            if (duration > PERFORMANCE_THRESHOLD) {
                System.out.println("Warning: IsDisplayed operation took " + duration + "ms");
            }
            return result;
        } catch (Exception e) {
            return false;
        }
    }
}