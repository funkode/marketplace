package com.marketplace.common.exception;

public class InvalidOperationException extends MarketplaceException {

    public InvalidOperationException(String code, String message) {
        super(code, message);
    }
}
