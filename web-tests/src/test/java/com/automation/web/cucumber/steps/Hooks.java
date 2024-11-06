package com.automation.web.cucumber.steps;



import com.automation.web.cucumber.TestContext;

import io.cucumber.java.After;

import io.cucumber.java.Before;

import io.cucumber.java.Scenario;



public class Hooks {

    private final TestContext context;



    public Hooks(TestContext context) {

        this.context = context;

    }



    @Before

    public void setUp(Scenario scenario) {

        // Any setup needed before each scenario

    }



    @After

    public void tearDown(Scenario scenario) {

        if (scenario.isFailed()) {

            // Add screenshot capture logic here

        }

        context.tearDown();

    }

}