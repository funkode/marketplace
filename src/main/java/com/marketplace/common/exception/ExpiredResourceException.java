package com.marketplace.common.exception;

public class ExpiredResourceException extends MarketplaceException {

    public ExpiredResourceException(String code, String message) {
        super(code, message);
    }
}
