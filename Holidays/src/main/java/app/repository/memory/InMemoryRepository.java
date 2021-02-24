package app.repository;

import app.domain.Entity;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class InMemoryRepository<ID, E extends Entity<ID>> implements Repository<ID, E> {
    Map<ID,E> entities;

    public InMemoryRepository(){
        entities = new HashMap<ID,E>();
    }

    @Override
    public Optional<E> findOne(ID id) {
        E e = entities.get(id);
        return Optional.ofNullable(e);
    }

    @Override
    public Iterable<E> findAll() {
        return entities.values();
    }

    @Override
    public Optional<E> save(E entity) {
        return Optional.ofNullable(entities.putIfAbsent(entity.getId(), entity));
    }

    @Override
    public Optional<E> delete(ID id) {
        return Optional.ofNullable(entities.remove(id));
    }

    @Override
    public Optional<E> update(E entity) {
        return Optional.ofNullable(entities.computeIfPresent(entity.getId(), (k,v)->entity));
    }

    @Override
    public int size() {
        return entities.size();
    }
}
