package com.marketplace.user.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.marketplace.base.Resource;
import lombok.*;
import org.hibernate.validator.constraints.Email;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;


@Entity(name = "user")
@EqualsAndHashCode
@ToString(callSuper = true)
@NoArgsConstructor
@Data
@JsonInclude(NON_NULL)
@AllArgsConstructor
public class User implements Resource {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @Email
    private String email;

    @NotNull
    private String phone;

    @NotNull
    private UserType type;

    private UserStatus status;

}
