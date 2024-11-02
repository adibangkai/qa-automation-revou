package com.automation.web.config;

import com.automation.web.enums.UserType;
import lombok.Getter;

@Getter
public class TestConfig {
    private static final int STANDARD_TIMEOUT = 10;
    private static final int PERFORMANCE_TIMEOUT = 15;

    public static int getTimeoutForUser(UserType userType) {
        return userType == UserType.PERFORMANCE_GLITCH_USER ?
                PERFORMANCE_TIMEOUT : STANDARD_TIMEOUT;
    }

    public static boolean shouldCheckImages(UserType userType) {
        return userType != UserType.PROBLEM_USER;
    }

    public static boolean shouldCheckPrices(UserType userType) {
        return userType != UserType.VISUAL_USER;
    }

    public static boolean shouldCheckFunctionality(UserType userType) {
        return userType != UserType.ERROR_USER;
    }

    public static String getExpectedInventoryState(UserType userType) {
        return switch (userType) {
            case PROBLEM_USER -> "Problem user will see incorrect product images";
            case VISUAL_USER -> "Visual user will see layout and styling issues";
            case ERROR_USER -> "Error user will encounter various functional issues";
            default -> "Standard inventory page display";
        };
    }
}
