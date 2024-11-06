package com.automation.mobile;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.testng.Assert;

import java.net.MalformedURLException;

public class BaseSteps {
    @Before
    public void configureMobile() throws MalformedURLException {
        new TestContext();
        Assert.assertNotNull(TestContext.getDriver(), "Browser should be configured");
    }

    @After
    public void teardown() {
        TestContext.quitDriver();
    }
}