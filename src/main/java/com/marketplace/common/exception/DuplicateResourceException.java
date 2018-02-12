package com.marketplace.common.exception;

public class DuplicateResourceException extends MarketplaceException {

    public DuplicateResourceException(String code, String message) {
        super(code, message);
    }
}
