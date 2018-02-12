package com.marketplace.bid.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.marketplace.project.model.Project;
import com.marketplace.user.model.User;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;


@Entity
@EqualsAndHashCode
@ToString(callSuper = true)
@NoArgsConstructor
@Data
@JsonInclude(NON_NULL)
@NamedQueries({
    @NamedQuery(name = "Bid.findByUserAndProject",
        query = "SELECT b FROM Bid b WHERE b.id.userId = ?1 and b.id.projectId = ?2"),
    @NamedQuery(name = "Bid.findByProject", query = "SELECT b FROM Bid b WHERE b.id.projectId = ?1"),
    @NamedQuery(name = "Bid.findByUser", query = "SELECT b FROM Bid b WHERE b.id.userId = ?1")
})
@Table(name = "bid")
public class Bid extends BidBase {

    @EmbeddedId
    private BidKey id;


    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", insertable = false, updatable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "project_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Project project;

}
