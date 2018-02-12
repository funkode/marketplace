package com.marketplace.base;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

/**
 * Service layer for DB interactions
 *
 * @author preddy
 * @param <T>
 * @param <ID>
 * @param <R>
 */
public interface Service<T extends Resource, ID extends Serializable, R extends Repository<T, ID>> {
    String getResourceType();

    long count();

    T save(T resource);

    List<T> findAll();

    Optional<T> findOne(ID id);

    void delete(T resource);

    void delete(ID id);
}
