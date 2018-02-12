package com.marketplace.common.exception;

public class BidNotFoundException extends MarketplaceException {

    public BidNotFoundException(String code, String message) {
        super(code, message);
    }

    public BidNotFoundException() {
        super("invalid.input", "bid.not.found");
    }
}
