package com.automation.api.utils;

import io.restassured.specification.RequestSpecification;
import io.restassured.http.ContentType;
import com.automation.api.constants.EndPoints;

public class RequestSpecBuilder {
    public static RequestSpecification getRequestSpec() {
        return new io.restassured.builder.RequestSpecBuilder()
                .setBaseUri(EndPoints.BASE_URL)
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON)
                .build();
    }
}
