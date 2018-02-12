package com.marketplace.common.exception;

import lombok.Getter;

import java.util.Map;

public class ResourceFormatException extends MarketplaceException {

    @Getter
    private final Map<String, Object> errors;

    public ResourceFormatException(String code, String message, Map<String, Object> errors) {
        super(code, message);
        this.errors = errors;
    }
}
