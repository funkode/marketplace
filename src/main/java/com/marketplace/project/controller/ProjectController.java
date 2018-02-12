package com.marketplace.project.controller;

import com.marketplace.base.BaseController;
import com.marketplace.bid.model.Bid;
import com.marketplace.bid.service.BidService;
import com.marketplace.common.exception.DuplicateResourceException;
import com.marketplace.common.exception.ProjectNotFoundException;
import com.marketplace.project.model.Project;
import com.marketplace.project.model.ProjectDto;
import com.marketplace.project.model.ProjectStatus;
import com.marketplace.project.repository.ProjectRepository;
import com.marketplace.project.service.ProjectService;
import com.marketplace.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Controller for project related RESTful services
 * @author preddy
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/project")
public class ProjectController extends BaseController<Project, Integer, ProjectRepository, ProjectService> {

    @Autowired
    BidService bidService;

    @Autowired
    UserService userService;

    /**
     * Create new project
     * @param project
     * @param bindingResult
     * @return
     */
    @PutMapping
    public ProjectDto create(@RequestBody @Valid Project project, BindingResult bindingResult) {

        checkBindingResult(bindingResult);

        log.debug("Creating Project {}", project);
        service.findByName(project.getName()).ifPresent(project1 -> {
            log.debug("Project already registered with the given name {}", project.getName());
            throw new DuplicateResourceException("duplicate.project", "project.already.exists");
        });

        project.setCreatedBy(userService.findOne(project.getCreatedBy()).get().getId());

        log.debug("Registering new project {}", project);

        project.setStatus(ProjectStatus.OPEN);

        //String projectId = idGenerator.newUUID();
        //project.setId(projectId);

        service.save(project);

        log.debug("Project created {}", project);
        return new ProjectDto(project);
    }

    /**
     * Return project details along with lowest bid details, intended for sellers
     * @param projectId
     * @return
     */
    @GetMapping("/{projectId}")
    public ProjectDto getProject(@PathVariable Integer projectId) {
        log.debug("Fetching project for id {}", projectId);
        Project project = service.findOne(projectId).orElseThrow(ProjectNotFoundException::new);

        Bid bid = null;
        if(project.getLowestBidUserId() != null) {
            bid = bidService.findByUserAndProject(project.getLowestBidUserId(), project.getId()).orElse(null);
        }

        return new ProjectDto(project, bid);
    }

    /**
     * List all open projects, resonse will not include the lowest bid details as this would be used by buyers
     * @return
     */
    @GetMapping("/open")
    public Collection<ProjectDto> getOpenProjects() {
        log.debug("Fetching all open projects");
        Collection<Project> projects = service.findAllOpenProjects(marketplaceUtil.getCurrentDate()).orElse(new ArrayList<Project>(0));
        ArrayList<ProjectDto> dtos = new ArrayList<>(projects.size());
        for(Project project : projects) {
            dtos.add(new ProjectDto(project));
        }
        return dtos;
    }

    /**
     * Get all project details by using project name as the filter
     * @param projectName
     * @return
     */
    @GetMapping("/name/{projectName}")
    public ProjectDto getProjectByName(@PathVariable String projectName) {
        log.debug("Fetching project for id {}", projectName);
        Project project = service.findByName(projectName).orElseThrow(ProjectNotFoundException::new);
        return new ProjectDto(project);
    }
}
