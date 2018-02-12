package com.marketplace.project.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.marketplace.bid.model.Bid;
import com.marketplace.bid.model.BidDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@EqualsAndHashCode
@ToString(callSuper = true)
@NoArgsConstructor
@Data
@JsonInclude(NON_NULL)
public class ProjectDto extends ProjectBase {

    private BidDto lowestBid;

    public ProjectDto(Project project) {
        this(project, null);
    }

    public ProjectDto(Project project, Bid bid) {
        if(bid != null) {
            this.lowestBid = new BidDto(bid);
        }
        this.setBidEndDate(project.getBidEndDate());
        this.setDescription(project.getDescription());
        this.setName(project.getName());
        this.setId(project.getId());
        this.setStatus(project.getStatus());
        this.setCreatedBy(project.getCreatedBy());
    }
}
