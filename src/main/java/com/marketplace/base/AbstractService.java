package com.marketplace.base;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

/**
 * Service to handle the DB interactions
 *
 * @author preddy
 * @param <T>
 * @param <ID>
 * @param <R>
 */
@Slf4j
public abstract class AbstractService<T extends Resource, ID extends Serializable, R extends Repository<T, ID>> implements Service<T, ID, R> {
    @Autowired
    protected R repository;

    @Override
    public long count() {
        log.debug("Returning count of {}s", getResourceType());
        long count = repository.count();
        log.debug("There are {} {}s", count, getResourceType());
        return count;
    }

    @Override
    public T save(T resource) {
        log.debug("Saving {}: {}", getResourceType(), resource);
        return repository.saveAndFlush(resource);
    }

    @Override
    public List<T> findAll() {
        log.debug("Listing all {}s", getResourceType());
        return repository.findAll();
    }

    @Override
    public Optional<T> findOne(ID id) {
        log.debug("Fetching {} with id: {}", getResourceType(), id);
        return Optional.ofNullable(repository.findOne(id));
    }

    @Override
    public void delete(T resource) {
        if (resource == null) {
            log.debug("Cannot delete a null resource");
            return;
        }

        log.debug("Deleting {}: {}", getResourceType(), resource);
        repository.delete(resource);
    }

    @Override
    public void delete(ID id) {
        if (StringUtils.isEmpty(id)) {
            log.debug("Cannot delete a null resource");
            return;
        }

        log.debug("Deleting {} with id: {}", getResourceType(), id);
        repository.delete(id);
    }
}
