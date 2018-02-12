package com.marketplace.base;

import com.marketplace.common.exception.ResourceFormatException;
import com.marketplace.common.utils.IdGenerator;
import com.marketplace.common.utils.MarketplaceUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Base controller with default functionality
 *
 * @author preddy
 * @param <T>
 * @param <ID>
 * @param <R>
 * @param <S>
 */
@Slf4j
public abstract class BaseController<
        T extends Resource,
        ID extends Serializable,
        R extends Repository<T, ID>,
        S extends Service<T, ID, R>> {

    @Autowired
    protected S service;

    @Autowired
    protected IdGenerator idGenerator;

    @Autowired
    protected MarketplaceUtil marketplaceUtil;

    /*
    protected Optional<String> getLoggedInUser() {
        return Optional.ofNullable((String) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    }*/

    protected void checkBindingResult(BindingResult bindingResult) {
        log.debug("Checking binding result for field errors");
        if (bindingResult.hasFieldErrors()) {
            log.debug("Found field errors in binding result");
            Map<String, Object> errors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(
                    fieldError -> {
                        log.debug("Field: {}, Rejected Value: {}", fieldError.getField(), fieldError.getRejectedValue());
                        errors.put(
                                fieldError.getField(),
                                fieldError.getRejectedValue() == null ? "null/empty" : fieldError.getRejectedValue()
                        );
                    }
            );
            throw new ResourceFormatException("invalid.format", "invalid.format", errors);
        }

        log.debug("No errors found.");
    }
}
