package com.marketplace.common.utils;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class IdGenerator {
    public String newUUID() {
        return UUID.randomUUID().toString();
    }
}
