package com.marketplace.bid.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotNull;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@EqualsAndHashCode
@ToString(callSuper = true)
@NoArgsConstructor
@Data
@JsonInclude(NON_NULL)
public class BidDto extends BidBase {

    @NotNull
    int userId;

    @NotNull
    int projectId;

    public BidDto(Bid bid) {
        this.userId = bid.getUser().getId();
        this.projectId = bid.getProject().getId();
        this.quote = bid.getQuote();
        this.estimatedCompletionDate = bid.getEstimatedCompletionDate();
        this.status = bid.getStatus();
    }
}
