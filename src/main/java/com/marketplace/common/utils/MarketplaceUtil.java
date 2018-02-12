package com.marketplace.common.utils;

import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Date;

@Component
public class MarketplaceUtil {

    public Date getCurrentDate() {
        return new Date(Instant.now().toEpochMilli());
    }
}
