package com.marketplace.bid.service;

import com.marketplace.base.Service;
import com.marketplace.bid.model.Bid;
import com.marketplace.bid.repository.BidRepository;

import java.util.Collection;
import java.util.Optional;

public interface BidService extends Service<Bid, Integer, BidRepository> {

    Optional<Collection<Bid>> findByProject(int project);

    Optional<Collection<Bid>> findByUser(int user);

    Optional<Bid> findByUserAndProject(int user, int project);
}
