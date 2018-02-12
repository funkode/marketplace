package com.marketplace.bid.repository;

import com.marketplace.base.Repository;
import com.marketplace.bid.model.Bid;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;

public interface BidRepository extends Repository<Bid, Integer> {

    @Query(name = "Bid.findByUserAndProject")
    Bid findByUserAndProject(@Param("userId") int user, @Param("projectId") int project);

    @Query(name = "Bid.findByProject")
    Collection<Bid> findByProject(@Param("projectId") int projectId);

    @Query(name = "Bid.findByUser")
    Collection<Bid> findByUser(int user);
}


