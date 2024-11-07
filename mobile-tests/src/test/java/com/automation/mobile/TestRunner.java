package com.automation.mobile;

import org.junit.platform.suite.api.*;
import static io.cucumber.junit.platform.engine.Constants.PLUGIN_PROPERTY_NAME;

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features")
@ConfigurationParameter(key = PLUGIN_PROPERTY_NAME, value = "pretty, html:target/reports/cucumber.html, json:target/reports/cucumber.json")
public class TestRunner {
}
