package com.marketplace.user.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum UserType {
    @JsonProperty("user")
    USER("user"),

    @JsonProperty("admin")
    ADMIN("admin");


    private final String type;

    UserType(String type) {
        this.type = type;
    }

    public String value() {
        return this.type;
    }
}
