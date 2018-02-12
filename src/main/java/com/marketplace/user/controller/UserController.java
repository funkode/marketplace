package com.marketplace.user.controller;

import com.marketplace.base.BaseController;
import com.marketplace.common.exception.DuplicateResourceException;
import com.marketplace.common.exception.UserNotFoundException;
import com.marketplace.user.model.User;
import com.marketplace.user.repository.UserRepository;
import com.marketplace.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * UserController to handle user related information
 * @author preddy
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/user")
public class UserController extends BaseController<User, Integer, UserRepository, UserService> {

    /**
     * Create new user, throw appropriate exceptions if validation fails
     * @param user
     * @param bindingResult
     * @return
     */
    @PutMapping
    public User create(@RequestBody @Valid User user, BindingResult bindingResult) {

        checkBindingResult(bindingResult);

        log.debug("Creating user {}", user);
        service.findByEmail(user.getEmail()).ifPresent(user1 -> {
            log.debug("Found user already registered with the given email {}", user.getEmail());
            throw new DuplicateResourceException("duplicate.user", "email.already.exists");
        });

        service.findByPhone(user.getPhone()).ifPresent(user1 -> {
            log.debug("Found user already registered with the given phone {}", user.getPhone());
            throw new DuplicateResourceException("duplicate.user", "phone.already.exists");
        });

        log.debug("Registering new user {}", user);

       // String userId = idGenerator.newUUID();
        //user.setId(userId);

        service.save(user);

        log.debug("User created {}", user);
        return user;
    }

    /**
     * Get user details by using database id as the filter
     * @param userId
     * @return
     */
    @GetMapping("/{userId}")
    public User getUser(@PathVariable Integer userId) {
        log.debug("Fetching user for id {}", userId);
        return service.findOne(userId).orElseThrow(UserNotFoundException::new);
    }

    /**
     * Search for user details by using email as the filter
     * @param email
     * @return
     */
    @GetMapping("/email")
    public User getUserByEmail(@RequestParam(name = "email") String email) {
        log.debug("Fetching user for email {}", email);
        return service.findByEmail(email).orElseThrow(UserNotFoundException::new);
    }

    /**
     * Search for user using phone as filter
     * @param phone
     * @return
     */
    @GetMapping("/phone")
    public User getUserByPhone(@RequestParam(name = "phone") String phone) {
        log.debug("Fetching user for phone {}", phone);
        return service.findByPhone(phone).orElseThrow(UserNotFoundException::new);
    }
}
