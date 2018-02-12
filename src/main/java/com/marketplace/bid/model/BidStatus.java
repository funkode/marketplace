package com.marketplace.bid.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum BidStatus {

    @JsonProperty("won")
    WON("won"),

    @JsonProperty("submitted")
    SUBMITTED("submitted");

    private final String type;

    BidStatus(String type) {
        this.type = type;
    }

    public String value() {
        return this.type;
    }
}