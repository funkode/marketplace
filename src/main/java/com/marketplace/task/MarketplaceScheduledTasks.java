package com.marketplace.task;

import com.marketplace.common.utils.MarketplaceUtil;
import com.marketplace.event.BiddingExpiredEvent;
import com.marketplace.project.model.Project;
import com.marketplace.project.service.ProjectService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;

@Slf4j
@Component
public class MarketplaceScheduledTasks {

    @Autowired
    ProjectService projectService;

    @Autowired
    MarketplaceUtil marketplaceUtil;

    @Autowired
    ApplicationEventPublisher applicationEventPublisher;

    @Scheduled(fixedRate = 30000, initialDelay = 60000) //Runs every 30 seconds after 60 seconds of app startup
    public void checkProjectExpiry() {

        Collection<Project> projects = projectService.findAllExpiredProjects(marketplaceUtil.getCurrentDate()).orElse(new ArrayList<>(0));
        log.debug("Found {} expired projects", projects.size());

        for(Project project : projects) {
                //Update the project status to reject all future bids and send a notification for seller to finalize the grant
                BiddingExpiredEvent event = new BiddingExpiredEvent(this, project);

                applicationEventPublisher.publishEvent(event);
        }
    }
}
