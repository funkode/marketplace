package com.marketplace.event;

import com.marketplace.bid.model.Bid;

/**
 * An event to indicate there is a new bid for the project
 * @author preddy
 */
public class BidUpdateEvent extends MarketplaceEvent {

    public BidUpdateEvent(Object source, Bid bid) {
        super(source, bid);
    }
}
