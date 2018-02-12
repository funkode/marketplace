package com.marketplace.user.service;


import com.marketplace.base.AbstractService;
import com.marketplace.user.model.User;
import com.marketplace.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Slf4j
@Service
public class UserServiceImpl
        extends AbstractService<User, Integer, UserRepository>
        implements UserService {

    @Override
    public String getResourceType() {
        return User.class.getSimpleName();
    }

    @Override
    public Optional<User> findByEmail(String email) {
        log.debug("Fetching User by email: {}", email);
        if (StringUtils.isEmpty(email)) {
            log.debug("Email is empty. Returning null");
            return Optional.empty();
        }

        return Optional.ofNullable(repository.findByEmailIgnoreCase(email));
    }

    @Override
    public Optional<User> findByPhone(String phone) {
        log.debug("Fetching User by phone: {}", phone);
        if (StringUtils.isEmpty(phone)) {
            log.debug("Phone is empty. Returning null");
            return Optional.empty();
        }

        return Optional.ofNullable(repository.findByPhoneIgnoreCase(phone));
    }
}
