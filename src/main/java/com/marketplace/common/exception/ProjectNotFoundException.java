package com.marketplace.common.exception;

public class ProjectNotFoundException extends MarketplaceException {

    public ProjectNotFoundException(String code, String message) {
        super(code, message);
    }

    public ProjectNotFoundException() {
        super("invalid.id", "project.not.found");
    }
}
