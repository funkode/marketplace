package com.marketplace.user.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum UserStatus {
    @JsonProperty("active")
    ACTIVE("ACTIVE"),

    @JsonProperty("pending")
    PENDING("pending"),

    @JsonProperty("blocked")
    COMPLETED("blocked");

    private final String type;

    UserStatus(String type) {
        this.type = type;
    }

    public String value() {
        return this.type;
    }
}
