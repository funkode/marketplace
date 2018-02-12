package com.marketplace.common.exception;

import lombok.Getter;

import java.io.Serializable;

public class MarketplaceException extends RuntimeException implements Serializable {

    @Getter
    private String code;

    public MarketplaceException(String code, String message) {
        this(code, message, null);
    }

    public MarketplaceException(String code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }
}
