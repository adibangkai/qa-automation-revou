package com.automation.web.cucumber;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"com.automation.web.cucumber.steps"},
        plugin = {
                "pretty",
                "html:target/cucumber-reports/cucumber-pretty.html",
                "json:target/cucumber-reports/CucumberTestReport.json"
        },
        monochrome = false
)
public class TestRunner extends AbstractTestNGCucumberTests {
    private static String currentUserType;


//    @Parameters({"userType"})
//    @BeforeClass
//    public void setUserType(String userType) {
//        currentUserType = userType;
//    }
//
//    public static String getUserType() {
//        return currentUserType;
//    }

    @Override
    @DataProvider(parallel = true)
    public Object[][] scenarios() {
        return super.scenarios();
    }
}
