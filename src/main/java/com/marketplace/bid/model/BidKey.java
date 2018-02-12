package com.marketplace.bid.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.marketplace.base.Resource;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@EqualsAndHashCode
@ToString(callSuper = true)
@NoArgsConstructor
@Data
@JsonInclude(NON_NULL)
@Embeddable
public class BidKey implements Resource {

    @NotNull
    @Column(name = "project_id")
    private int projectId;

    @NotNull
    @Column(name = "user_id")
    private int userId;
}
