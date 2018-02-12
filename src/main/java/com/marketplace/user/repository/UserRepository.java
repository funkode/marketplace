package com.marketplace.user.repository;

import com.marketplace.base.Repository;
import com.marketplace.user.model.User;

public interface UserRepository extends Repository<User, Integer> {
    User findByEmailIgnoreCase(String email);

    User findByPhoneIgnoreCase(String phone);
}

