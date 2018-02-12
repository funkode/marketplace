package com.marketplace.project.repository;

import com.marketplace.base.Repository;
import com.marketplace.project.model.Project;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.Date;

public interface ProjectRepository extends Repository<Project, Integer> {
    Project findByNameIgnoreCase(String name);

    @Query(name = "Project.findAllExpiredProjects")
    Collection<Project> findAllExpiredProjects(@Param("currentDate") Date date);

    @Query(name = "Project.findAllOpenProjects")
    Collection<Project> findAllOpenProjects(@Param("currentDate") Date date);
}

