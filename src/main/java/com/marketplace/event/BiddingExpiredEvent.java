package com.marketplace.event;

import com.marketplace.project.model.Project;

/**
 * An event to indicate the project bidding window has closed
 *
 * @author preddy
 */
public class BiddingExpiredEvent  extends MarketplaceEvent {

    public BiddingExpiredEvent(Object source, Project project) {
        super(source, project);
    }

}
