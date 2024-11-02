package com.automation.web.tests;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import java.lang.reflect.Method;

public class PerformanceTestBase extends BaseTest {
    private long testStartTime;
    protected static final long TEST_PERFORMANCE_THRESHOLD = 2000; // 2000ms threshold

    @BeforeMethod
    public void setupPerformanceTest(Method method) {
        testStartTime = System.currentTimeMillis();
    }

    @AfterMethod
    public void checkPerformance(ITestResult result) {
        long duration = System.currentTimeMillis() - testStartTime;

        // Add performance data to test result
        result.setAttribute("duration", duration);

        if (duration > TEST_PERFORMANCE_THRESHOLD) {
            String message = String.format(
                    "Performance threshold exceeded. Test took %dms (threshold: %dms)",
                    duration, TEST_PERFORMANCE_THRESHOLD
            );
            result.setStatus(ITestResult.FAILURE);
            result.setThrowable(new AssertionError(message));
        }
    }
}
