package com.marketplace;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.ErrorMvcAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@SpringBootApplication(scanBasePackages = "com.marketplace")
@EnableRetry
@EnableAutoConfiguration(exclude = ErrorMvcAutoConfiguration.class)
@EnableConfigurationProperties
@EnableScheduling
public class MarketplaceApplication {

    @SuppressWarnings("unused")
    @PostConstruct
    void started() {
        TimeZone.setDefault((TimeZone.getTimeZone("UTC")));
    }

    public static void main(String[] args) {
        SpringApplication.run(MarketplaceApplication.class);
    }
}