package com.marketplace.project.service;


import com.marketplace.base.AbstractService;
import com.marketplace.project.model.Project;
import com.marketplace.project.repository.ProjectRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.Date;
import java.util.Optional;

@Slf4j
@Service
public class ProjectServiceImpl
        extends AbstractService<Project, Integer, ProjectRepository>
        implements ProjectService {

    @Override
    public String getResourceType() {
        return Project.class.getSimpleName();
    }

    @Override
    public Optional<Project> findByName(String name) {
        log.debug("Fetching Project by name: {}", name);
        if (StringUtils.isEmpty(name)) {
            log.debug("Name is empty. Returning null");
            return Optional.empty();
        }

        return Optional.ofNullable(repository.findByNameIgnoreCase(name));
    }

    @Override
    public Optional<Collection<Project>> findAllExpiredProjects(Date currentDate) {
        log.debug("Fetching expired projects based on date {}", currentDate);
        return Optional.ofNullable(repository.findAllExpiredProjects(currentDate));
    }

    @Override
    public Optional<Collection<Project>> findAllOpenProjects(Date currentDate) {
        log.debug("Fetching expired projects based on date {}", currentDate);
        return Optional.ofNullable(repository.findAllOpenProjects(currentDate));
    }
}
