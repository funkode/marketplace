package com.marketplace.common.exception;

public class UserNotFoundException extends MarketplaceException {

    public UserNotFoundException(String code, String message) {
        super(code, message);
    }

    public UserNotFoundException() {
        super("invalid.input", "user.not.found");
    }
}
