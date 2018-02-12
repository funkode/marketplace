package com.marketplace.user.service;

import com.marketplace.base.Service;
import com.marketplace.user.model.User;
import com.marketplace.user.repository.UserRepository;

import java.util.Optional;

@org.springframework.stereotype.Service
public interface UserService extends Service<User, Integer, UserRepository> {
    Optional<User> findByEmail(String email);

    Optional<User> findByPhone(String phone);
}
