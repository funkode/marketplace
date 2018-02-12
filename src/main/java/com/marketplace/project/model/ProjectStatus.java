package com.marketplace.project.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum ProjectStatus {

    @JsonProperty("open")
    OPEN("open"),

    @JsonProperty("closed")
    CLOSED("closed");


    private final String type;

    ProjectStatus(String type) {
        this.type = type;
    }

    public String value() {
        return this.type;
    }
}
