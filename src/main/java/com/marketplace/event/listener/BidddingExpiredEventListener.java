package com.marketplace.event.listener;

import com.marketplace.bid.model.Bid;
import com.marketplace.bid.model.BidStatus;
import com.marketplace.bid.service.BidService;
import com.marketplace.event.BiddingExpiredEvent;
import com.marketplace.project.model.Project;
import com.marketplace.project.model.ProjectStatus;
import com.marketplace.project.service.ProjectService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * Once the bidding has expired for the project, an event is triggered by the scheduler.
 * This listeners will mark the project as closed and awards the lowest bidding user with the project.
 * @author preddy
 */
@Slf4j
@Component
public class BidddingExpiredEventListener {

    @Autowired
    BidService bidService;

    @Autowired
    ProjectService projectService;

    @EventListener
    public void handleEvent(BiddingExpiredEvent event) {
        log.debug("Received ProjectBidExpiredEvent for project {}", (Project)event.getResource());
        Project project = (Project) event.getResource();
        if(project.getLowestBidUserId() != null) {
            Bid bid = bidService.findByUserAndProject(project.getLowestBidUserId(), project.getId()).get();
            bid.setStatus(BidStatus.WON);
            bidService.save(bid);
            log.debug("Updated bid as won: {}", bid);
            //We can integrate to send out an email notification if required
        }
        project.setStatus(ProjectStatus.CLOSED);
        projectService.save(project);
        log.debug("Closed project {}", project);

    }

}
