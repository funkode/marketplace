package com.marketplace.project.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.marketplace.base.Resource;
import com.marketplace.common.utils.MarketplaceDateSerializer;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@EqualsAndHashCode
@ToString(callSuper = true)
@NoArgsConstructor
@Data
@JsonInclude(NON_NULL)
@MappedSuperclass
public abstract class ProjectBase implements Resource {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Integer id;

    @NotNull
    protected String name;

    @NotNull
    protected String description;

    @NotNull
    @JsonSerialize(using = MarketplaceDateSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING , pattern = MarketplaceDateSerializer.DATE_PATTERN)
    protected Date bidEndDate;

    @Enumerated(EnumType.STRING)
    protected ProjectStatus status;

    @Column(name = "created_by")
    @NotNull
    protected Integer createdBy;
}
