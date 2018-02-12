package com.marketplace.bid.service;


import com.marketplace.base.AbstractService;
import com.marketplace.bid.model.Bid;
import com.marketplace.bid.repository.BidRepository;
import com.marketplace.event.BidUpdateEvent;
import com.marketplace.project.model.Project;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.Optional;

@Slf4j
@Service
public class BidServiceImpl
        extends AbstractService<Bid, Integer, BidRepository>
        implements BidService {

    @Autowired
    ApplicationEventPublisher applicationEventPublisher;

    @Override
    public String getResourceType() {
        return Project.class.getSimpleName();
    }

    @Override
    public Optional<Bid> findByUserAndProject(int user, int project) {
        log.debug("Fetching Bid for user {} and project {}", user, project);
        if (StringUtils.isEmpty(user) || StringUtils.isEmpty(project)) {
            log.debug("User/project is empty. Returning null");
            return Optional.empty();
        }

        return Optional.ofNullable(repository.findByUserAndProject(user, project));
    }

    @Override
    public Optional<Collection<Bid>> findByProject(int project) {
        log.debug("Fetching all the bids for project {}", project);
        if (StringUtils.isEmpty(project)) {
            log.debug("Project is empty. Returning null");
            return Optional.empty();
        }

        return Optional.ofNullable(repository.findByProject(project));
    }

    @Override
    public Optional<Collection<Bid>> findByUser(int user) {
        log.debug("Fetching all the bids for user {}", user);
        if (StringUtils.isEmpty(user)) {
            log.debug("User is empty. Returning null");
            return Optional.empty();
        }

        return Optional.ofNullable(repository.findByUser(user));
    }

    @Override
    public Bid save (Bid bid) {
        super.save(bid);
        BidUpdateEvent event = new BidUpdateEvent(this, bid);
        applicationEventPublisher.publishEvent(event);
        return bid;
    }
}
