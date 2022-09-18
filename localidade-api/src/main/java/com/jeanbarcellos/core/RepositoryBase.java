package com.jeanbarcellos.core;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.Sort;

public abstract class RepositoryBase<TEntity, TId>
        implements PanacheRepositoryBase<TEntity, TId> {

    public boolean existsBy(String fieldName, Object value) {
        return this.count(fieldName, value) > 0;
    }

    public Long countBy(Map<String, Object> params) {
        return this.count(this.createQueryFromMap(params), params);
    }

    public List<TEntity> findBy(String fieldName, Object value) {
        return this.list(fieldName, value);
    }

    public List<TEntity> findBy(String fieldName, Object value, Sort sort) {
        return this.list(fieldName, sort, value);
    }

    public List<TEntity> findBy(Map<String, Object> params) {
        return this.list(this.createQueryFromMap(params), params);
    }

    public List<TEntity> findBy(Map<String, Object> params, Sort sort) {
        return this.list(this.createQueryFromMap(params), sort, params);
    }

    public <T extends Throwable> TEntity findByIdOrThrow(TId id,
            Supplier<? extends T> exceptionSupplier) throws T {
        return this.findByIdOptional(id).orElseThrow(exceptionSupplier);
    }

    public TEntity findFirstBy(String fieldName, Object value) {
        var entities = this.findBy(fieldName, value);

        return entities.isEmpty() ? null : entities.get(0);
    }

    public TEntity findFirstBy(String fieldName, Object value, Sort sort) {
        var entities = this.findBy(fieldName, value, sort);

        return entities.isEmpty() ? null : entities.get(0);
    }

    public Optional<TEntity> findFirstByOptional(String fieldName, Object value) {
        var entity = this.findFirstBy(fieldName, value);

        return entity == null ? Optional.empty() : Optional.of(entity);
    }

    public <T extends Throwable> TEntity findFirstByOrTrhow(String fieldName, Object value,
            Supplier<? extends T> exceptionSupplier) throws T {
        return this.findFirstByOptional(fieldName, value).orElseThrow(exceptionSupplier);
    }

    public TEntity getReference(Class<TEntity> entityClass, TId id) {
        return this.getEntityManager().getReference(entityClass, id);
    }

    protected String createQueryFromMap(Map<String, Object> map) {
        return map.entrySet().stream()
                .map(entry -> entry.getKey() + "=:" + entry.getKey())
                .collect(Collectors.joining(" and "));
    }
}
