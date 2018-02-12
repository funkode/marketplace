package com.marketplace.bid.controller;

import com.marketplace.base.BaseController;
import com.marketplace.bid.model.Bid;
import com.marketplace.bid.model.BidDto;
import com.marketplace.bid.model.BidKey;
import com.marketplace.bid.model.BidStatus;
import com.marketplace.bid.repository.BidRepository;
import com.marketplace.bid.service.BidService;
import com.marketplace.common.exception.*;
import com.marketplace.project.model.Project;
import com.marketplace.project.service.ProjectService;
import com.marketplace.user.model.User;
import com.marketplace.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Bid for open projects. User can bid for a project only once and the seller cannot bid for the own project.
 * Any user user can bid for any project as long as the user is not the seller of the project.
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/bid")
public class BidController extends BaseController<Bid, Integer, BidRepository, BidService> {

    @Autowired
    private UserService userService;

    @Autowired
    private ProjectService projectService;

    /**
     * Create new bid for the project
     * @param bidDto
     * @param bindingResult
     * @return
     */
    @PutMapping
    public BidDto create(@RequestBody @Valid BidDto bidDto, BindingResult bindingResult) {

        checkBindingResult(bindingResult);

        log.debug("Creating Bid {}", bidDto);
        service.findByUserAndProject(bidDto.getUserId(), bidDto.getProjectId()).ifPresent(bid1 -> {
            log.debug("User has already submitted bid for project {}", bidDto.getProjectId());
            throw new DuplicateResourceException("duplicate.bid", "project.user.bid.already.exists");
        });


        Project project = projectService.findOne(bidDto.getProjectId()).orElseThrow(ProjectNotFoundException::new);
        if(project.getBidEndDate().before(marketplaceUtil.getCurrentDate())) {
            throw new ExpiredResourceException("project.expired", "project.bidding.closed");
        }

        if(project.getCreatedBy() == bidDto.getUserId()) {
            throw new InvalidOperationException("seller.bid", "project.seller.not.allowed.to.bid");
        }

        User user = userService.findOne(bidDto.getUserId()).orElseThrow(UserNotFoundException::new);

        log.debug("Posting new bid {}", bidDto);

        //String bidId = idGenerator.newUUID();
        BidKey bk = new BidKey();
        bk.setProjectId(bidDto.getProjectId());
        bk.setUserId(bidDto.getUserId());

        Bid bid = new Bid();
        bid.setId(bk);
        bid.setQuote(bidDto.getQuote());
        bid.setEstimatedCompletionDate(bidDto.getEstimatedCompletionDate());

        bid.setUser(user);
        bid.setProject(project);
        bid.setStatus(BidStatus.SUBMITTED);

        service.save(bid);

        log.debug("Bid created {}", bid);

        return new BidDto(bid);
    }

    /**
     * Get all the bids for the project
     * @param projectId
     * @return
     */
    @GetMapping("/project/{projectId}")
    public List<BidDto> getAllBidsByProject(@PathVariable int projectId) {
        log.debug("Fetching bids for project {}", projectId);
        Collection<Bid> bids = service.findByProject(projectId).orElseThrow(BidNotFoundException::new);
        return convert2BidDtos(bids);
    }

    private List<BidDto> convert2BidDtos(Collection<Bid> bids) {
        List<BidDto> bidDtos = new ArrayList<>(bids.size());
        for (Bid bid : bids) {
            bidDtos.add(new BidDto(bid));
        }
        return bidDtos;
    }

    /**
     * Get all bids for the user from all projects
     * @param userId
     * @return
     */
    @GetMapping("/user/{userId}")
    public List<BidDto> getBidsByUser(@PathVariable int userId) {
        log.debug("Fetching bids for user {}", userId);
        Collection<Bid> bids = service.findByUser(userId).orElseThrow(BidNotFoundException::new);
        return convert2BidDtos(bids);
    }

    /**
     * Search for the bid for the given user and project
     * @param userId
     * @param projectId
     * @return
     */
    @GetMapping("/user/{userId}/project/{projectId}")
    public BidDto getBidsByUserAndProject(@PathVariable int userId, @PathVariable int projectId) {
        log.debug("Fetching bids for user {} and project {}", userId, projectId);
        Bid bid = service.findByUserAndProject(userId, projectId).orElseThrow(BidNotFoundException::new);
        return new BidDto(bid);
    }
}
