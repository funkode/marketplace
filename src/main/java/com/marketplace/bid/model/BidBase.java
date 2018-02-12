package com.marketplace.bid.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.marketplace.base.Resource;
import com.marketplace.common.utils.MarketplaceDateSerializer;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@EqualsAndHashCode
@ToString(callSuper = true)
@NoArgsConstructor
@Data
@JsonInclude(NON_NULL)
@MappedSuperclass
public abstract class BidBase implements Resource {

    @NotNull
    @JsonSerialize(using = MarketplaceDateSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING , pattern = MarketplaceDateSerializer.DATE_PATTERN)
    protected Date estimatedCompletionDate;

    @Enumerated(EnumType.STRING)
    protected BidStatus status;

    @NotNull
    protected BigDecimal quote;
}
