package com.marketplace.project.service;

import com.marketplace.base.Service;
import com.marketplace.project.model.Project;
import com.marketplace.project.repository.ProjectRepository;

import java.util.Collection;
import java.util.Date;
import java.util.Optional;

@org.springframework.stereotype.Service
public interface ProjectService extends Service<Project, Integer, ProjectRepository> {

    Optional<Project> findByName(String name);

    Optional<Collection<Project>> findAllExpiredProjects(Date currentDate);

    Optional<Collection<Project>> findAllOpenProjects(Date currentDate);
}
