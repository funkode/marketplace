package com.marketplace;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marketplace.bid.model.BidStatus;
import com.marketplace.common.exception.ProjectNotFoundException;
import com.marketplace.common.utils.MarketplaceUtil;
import com.marketplace.event.BiddingExpiredEvent;
import com.marketplace.project.model.Project;
import com.marketplace.project.model.ProjectStatus;
import com.marketplace.project.service.ProjectService;
import com.marketplace.user.model.User;
import com.marketplace.user.model.UserStatus;
import com.marketplace.user.model.UserType;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = MarketplaceApplication.class
)
@AutoConfigureMockMvc
@Slf4j
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MarketplaceApplicationTest {

    @Autowired
    MockMvc mockMvc;;

    @Autowired
    MarketplaceUtil marketplaceUtil;

    @Autowired
    ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    ProjectService projectService;

    ObjectMapper mapper = new ObjectMapper();

    long eventSleepTime;

    User user1;
    User user2;
    User user3;
    User user4;

    @Before
    public void setUp() {
        mapper.enableDefaultTyping(ObjectMapper.DefaultTyping.OBJECT_AND_NON_CONCRETE);
        eventSleepTime = 3000;

        user1 = new User(null, "Greg", "Chappel", "greg@nowhere.com", "6876876868", UserType.USER, UserStatus.ACTIVE);
        user2 = new User(null, "Ian", "Gloud", "ian@nowhere.com", "9098978907", UserType.USER, UserStatus.ACTIVE);
        user3 = new User(null, "Mark", "Anthony", "mark@nowhere.com", "8088978907", UserType.USER, UserStatus.ACTIVE);
        user4 = new User(null, "David", "Bake", "david@nowhere.com", "8089978907", UserType.USER, UserStatus.ACTIVE);

    }


    @Test
    public void testA1PostUser() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.put("/api/v1/user")
                        .contentType("application/json")
                        .content(mapper.writeValueAsString(user1))).andExpect(status().isOk()).andReturn();

        mockMvc.perform(
                MockMvcRequestBuilders.put("/api/v1/user")
                    .contentType("application/json")
                    .content(mapper.writeValueAsString(user2))).andExpect(status().isOk()).andReturn();
        mockMvc.perform(
                MockMvcRequestBuilders.put("/api/v1/user")
                        .contentType("application/json")
                        .content(mapper.writeValueAsString(user3))).andExpect(status().isOk()).andReturn();
        mockMvc.perform(
                MockMvcRequestBuilders.put("/api/v1/user")
                        .contentType("application/json")
                        .content(mapper.writeValueAsString(user4))).andExpect(status().isOk()).andReturn();
    }

    @Test
    public void testA2GetUser() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/v1/user/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.email").value(user1.getEmail()))
                .andExpect(jsonPath("$.phone").value(user1.getPhone()))
                .andExpect(jsonPath("$.type").value(user1.getType().value()))
                .andReturn();
    }

    @Test
    public void testA3PutProject() throws Exception {

        mockMvc.perform(
                MockMvcRequestBuilders.put("/api/v1/project")
                        .contentType("application/json")
                        .content("{\n" +
                                "  \"name\": \"Test Project 1\",\n" +
                                "  \"description\":\"Test Project Description 1\",\n" +
                                "  \"createdBy\": \"1\",\n" +
                                "  \"bidEndDate\":\"12-25-2020 21:55:59\"\n" +
                                "}")).andExpect(status().isOk()).andReturn();
        mockMvc.perform(
                MockMvcRequestBuilders.put("/api/v1/project")
                        .contentType("application/json")
                        .content("{\n" +
                                "  \"name\": \"Test Project 2\",\n" +
                                "  \"description\":\"Test Project Description 2\",\n" +
                                "  \"createdBy\": \"1\",\n" +
                                "  \"bidEndDate\":\"12-25-2020 21:55:59\"\n" +
                                "}")).andExpect(status().isOk()).andReturn();

    }

    @Test
    public void testA4GetProject() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/v1/project/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("Test Project 1"))
                .andExpect(jsonPath("$.description").value("Test Project Description 1" ))
                .andReturn();

        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/v1/project/2").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("Test Project 2"))
                .andExpect(jsonPath("$.description").value("Test Project Description 2" ))
                .andReturn();

    }

    @Test
    public void testA5GetAllOpenProject() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/v1/project/open").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name").value("Test Project 1"))
                .andExpect(jsonPath("$[1].name").value("Test Project 2"))
                .andReturn();
    }

    @Test
    public void testA6SellerPutBid() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.put("/api/v1/bid")
                        .contentType("application/json")
                        .content("{\n" +
                                "  \"projectId\" : \"1\",\n" +
                                "  \"userId\": \"1\",\n" +
                                "  \"quote\":\"999\",\n" +
                                "  \"estimatedCompletionDate\":\"12-25-2019 21:55:59\"\n" +
                                "}")).andExpect(status().is4xxClientError())
                .andExpect(content().string("project.seller.not.allowed.to.bid")).andReturn();

    }

    @Test
    public void testA6PutBid() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.put("/api/v1/bid")
                        .contentType("application/json")
                        .content("{\n" +
                                "  \"projectId\" : \"1\",\n" +
                                "  \"userId\": \"3\",\n" +
                                "  \"quote\":\"999\",\n" +
                                "  \"estimatedCompletionDate\":\"12-25-2019 21:55:59\"\n" +
                                "}")).andExpect(status().isOk()).andReturn();

        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/v1/project/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.lowestBid.quote").value("999.0"))
                .andReturn();
    }

    @Test
    public void testA6PutNewLowestBid() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.put("/api/v1/bid")
                        .contentType("application/json")
                        .content("{\n" +
                                "  \"projectId\" : \"1\",\n" +
                                "  \"userId\": \"2\",\n" +
                                "  \"quote\":\"99\",\n" +
                                "  \"estimatedCompletionDate\":\"12-25-2018 21:55:59\"\n" +
                                "}")).andExpect(status().isOk()).andReturn();
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/v1/project/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.lowestBid.quote").value("99.0"))
                .andReturn();
    }

    @Test
    public void testA7LowestBidOnProject() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/v1/project/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.lowestBid.quote").value("99.0"))
                .andReturn();
    }

    public void test8UserBidOnProject() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/v1/bid/user/1/project/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(BidStatus.SUBMITTED.value()))
                .andReturn();
    }

    @Test
    public void testA9BidForExpiredProject() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.put("/api/v1/project")
                        .contentType("application/json")
                        .content("{\n" +
                                "  \"name\": \"Test Project 3\",\n" +
                                "  \"description\":\"Test Project Description 3\",\n" +
                                "  \"createdBy\": \"1\",\n" +
                                "  \"bidEndDate\":\"12-25-2010 21:55:59\"\n" +
                                "}")).andExpect(status().isOk()).andReturn();
        mockMvc.perform(
                MockMvcRequestBuilders.put("/api/v1/bid")
                        .contentType("application/json")
                        .content("{\n" +
                                "  \"projectId\" : \"3\",\n" +
                                "  \"userId\": \"2\",\n" +
                                "  \"quote\":\"99\",\n" +
                                "  \"estimatedCompletionDate\":\"12-25-2019 21:55:59\"\n" +
                                "}")).andExpect(status().is4xxClientError()).andReturn();
    }

    @Test
    public void testB1PutDuplicateBid() throws Exception {

        mockMvc.perform(
                MockMvcRequestBuilders.put("/api/v1/bid")
                        .contentType("application/json")
                        .content("{\n" +
                                "  \"projectId\" : \"1\",\n" +
                                "  \"userId\": \"3\",\n" +
                                "  \"quote\":\"899\",\n" +
                                "  \"estimatedCompletionDate\":\"12-25-2018 21:55:59\"\n" +
                                "}")).andExpect(status().is4xxClientError())
                .andExpect(content().string("project.user.bid.already.exists")).andReturn();

    }

    @Test
    public void testB1PutExcessiveBid() throws Exception {

        mockMvc.perform(
                MockMvcRequestBuilders.put("/api/v1/bid")
                        .contentType("application/json")
                        .content("{\n" +
                                "  \"projectId\" : \"1\",\n" +
                                "  \"userId\": \"4\",\n" +
                                "  \"quote\":\"899\",\n" +
                                "  \"estimatedCompletionDate\":\"12-25-2018 21:55:59\"\n" +
                                "}")).andExpect(status().isOk()).andReturn();

        //Check that the new bid with more than the least quote would not affect the existing least quote
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/v1/project/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(ProjectStatus.OPEN.value()))
                .andExpect(jsonPath("$.lowestBid.quote").value("99.0"))
                .andExpect(jsonPath("$.lowestBid.status").value(BidStatus.SUBMITTED.value()))
                .andReturn();
    }

    @Test
    public void testB2VerifyBidStatusUpdate() throws Exception {
        Project project = projectService.findOne(1).orElseThrow(ProjectNotFoundException::new);
        BiddingExpiredEvent event = new BiddingExpiredEvent(this, project);
        applicationEventPublisher.publishEvent(event);

        //Sleep for 3 seconds so that event gets handled
        log.debug("Sleeping for {} milliseconds to allow event propogation", eventSleepTime);
        Thread.sleep(eventSleepTime);

        //Check that the new bid with more than the least quote would not affect the existing least quote
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/v1/project/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(ProjectStatus.CLOSED.value()))
                .andExpect(jsonPath("$.lowestBid.quote").value("99.0"))
                .andExpect(jsonPath("$.lowestBid.status").value(BidStatus.WON.value()))
                .andReturn();

        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/v1/bid/user/2/project/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.quote").value("99.0"))
                .andExpect(jsonPath("$.status").value(BidStatus.WON.value()))
                .andReturn();

        //Make sure only person has won the bid
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/v1/bid/user/3/project/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.quote").value("999.0"))
                .andExpect(jsonPath("$.status").value(BidStatus.SUBMITTED.value()))
                .andReturn();
    }

    @Test
    public void testB3DuplicateProject() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.put("/api/v1/project")
                        .contentType("application/json")
                        .content("{\n" +
                                "  \"name\": \"Test Project 2\",\n" +
                                "  \"description\":\"Test Project Description 2\",\n" +
                                "  \"createdBy\": \"1\",\n" +
                                "  \"bidEndDate\":\"12-25-2020 21:55:59\"\n" +
                                "}")).andExpect(status().is4xxClientError()).andExpect(content().string("project.already.exists")).andReturn();
    }

}