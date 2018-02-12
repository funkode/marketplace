package com.marketplace.project.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;


@Entity(name = "project")
@EqualsAndHashCode
@ToString(callSuper = true)
@NoArgsConstructor
@Data
@JsonInclude(NON_NULL)
@NamedQueries({
  @NamedQuery(name = "Project.findAllExpiredProjects", query = "SELECT p FROM project p WHERE p.bidEndDate < ?1 and p.status = com.marketplace.project.model.ProjectStatus.OPEN"),
  @NamedQuery(name = "Project.findAllOpenProjects",
          query = "SELECT p FROM project p WHERE p.bidEndDate > ?1 AND p.status = com.marketplace.project.model.ProjectStatus.OPEN")
})
@AllArgsConstructor
public class Project extends ProjectBase {

    @Column(name = "lowest_bid_user_id", nullable = true)
    private Integer lowestBidUserId;

}
