package com.marketplace.event.listener;

import com.marketplace.bid.model.Bid;
import com.marketplace.bid.service.BidService;
import com.marketplace.event.BidUpdateEvent;
import com.marketplace.project.model.Project;
import com.marketplace.project.service.ProjectService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * On every new bid, check is this is the lowest bid and update the project with the lowest bid information.
 *
 * @author preddy
 */
@Slf4j
@Component
public class BidUpdateEventListener {

    @Autowired
    ProjectService projectService;

    @Autowired
    BidService bidService;

    @EventListener
    public void handleEvent(BidUpdateEvent event) {
        log.debug("Received BidUpdateEvent for project {}", event.getResource());

        Project project = ((Bid)event.getResource()).getProject();

        if(isLowestBid((Bid) event.getResource())) {
            log.debug("Found new lowest quote, updating project with new lowest quote for project {}", project);
            project.setLowestBidUserId(((Bid) event.getResource()).getUser().getId());
            projectService.save(project);
            log.debug("Updated project {} with lowest bid", project);
        }
    }

    private boolean isLowestBid(Bid bid) {
        boolean isLowest = false;
        Project project = bid.getProject();
        if(project.getLowestBidUserId() == null) {
            isLowest = true;
        } else {
            Bid lowestBid = bidService.findByUserAndProject(project.getLowestBidUserId(), bid.getProject().getId()).get();
            log.debug("Comparing current lowest quote {} with the current quote {}", lowestBid.getQuote(), bid.getQuote());
            isLowest = lowestBid.getQuote().compareTo(bid.getQuote()) == 1 ? true : false;
        }
        return isLowest;
    }

}
