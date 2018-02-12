package com.marketplace.event;

import com.marketplace.base.Resource;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Component;

@Component
public abstract class MarketplaceEvent extends ApplicationEvent {

    @Getter
    Resource resource;

    public MarketplaceEvent(Object source, Resource resource) {
        super(source);
        this.resource = resource;
    }
}
