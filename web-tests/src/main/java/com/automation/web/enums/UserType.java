package com.automation.web.enums;

import lombok.Getter;
import lombok.AllArgsConstructor;

@Getter
@AllArgsConstructor
public enum UserType {
    STANDARD_USER("standard_user", "Standard user with normal access", false, false, false, false),
    LOCKED_OUT_USER("locked_out_user", "User that has been locked out", true, false, false, false),
    PROBLEM_USER("problem_user", "User with intentional glitches", false, true, false, false),
    PERFORMANCE_GLITCH_USER("performance_glitch_user", "User with slow performance", false, false, true, false),
    ERROR_USER("error_user", "User that triggers errors", false, false, false, true),
    VISUAL_USER("visual_user", "User with visual glitches", false, false, false, false);

    private final String username;
    private final String description;
    private final boolean isLocked;
    private final boolean hasProblems;
    private final boolean hasPerformanceIssues;
    private final boolean hasErrors;

    private static final String VALID_PASSWORD = "secret_sauce";

    public String getPassword() {
        return VALID_PASSWORD;
    }

    public boolean shouldLoginSucceed() {
        return !isLocked;
    }

    public String getExpectedBehavior() {
        if (isLocked) {
            return "Should be locked out with error message";
        } else if (hasProblems) {
            return "Should show product images incorrectly and have other UI issues";
        } else if (hasPerformanceIssues) {
            return "Should have delayed responses";
        } else if (hasErrors) {
            return "Should show various error states";
        } else {
            return "Should login and function normally";
        }
    }

    public String getExpectedInventoryState() {
        return switch (this) {
            case PROBLEM_USER -> "Images should be incorrect";
            case VISUAL_USER -> "Should have visual inconsistencies";
            case ERROR_USER -> "Should have functionality issues";
            default -> "Should display normally";
        };
    }
}