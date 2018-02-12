package com.marketplace.common.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@SuppressWarnings("unused")
@ControllerAdvice
public class RestResponseExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ResourceFormatException.class)
    public ResponseEntity<Object> handleMarketplaceException(Exception exception, WebRequest request) {
        ResourceFormatException resourceFormatException = (ResourceFormatException)exception;
        return new ResponseEntity<Object>(resourceFormatException.getErrors(), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({InvalidOperationException.class, DuplicateResourceException.class, UserNotFoundException.class, ProjectNotFoundException.class, BidNotFoundException.class, ExpiredResourceException.class})
    public ResponseEntity<Object> handleDuplicateResourceException(Exception exception, WebRequest request) {
        MarketplaceException marketplaceException = (MarketplaceException)exception;
        return new ResponseEntity<Object>(marketplaceException.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }
}
