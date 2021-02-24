package socialnetwork.repository.memory;

import socialnetwork.domain.Entity;
import socialnetwork.domain.exceptions.AlreadyExistentEntityException;
import socialnetwork.domain.exceptions.NullEntityException;
import socialnetwork.domain.exceptions.ValidationException;
import socialnetwork.domain.validators.Validator;
import socialnetwork.repository.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class InMemoryRepository<ID, E extends Entity<ID>> implements Repository<ID,E>{

    private Validator<E> validator;
    Map<ID, E> entities;

    public InMemoryRepository(Validator<E> validator){
        this.validator = validator;
        entities = new HashMap<ID,E>();
    }

    @Override
    public Optional<E> findOne(ID id) {
        E e = entities.get(id);
        return Optional.ofNullable(e);
    }

    @Override
    public Iterable<E> findAll() {
//        Set<E> allEntities = entities.entrySet().stream().map(entry->entry.getValue()).collect(Collectors.toSet());
//        return allEntities;
        return entities.values();
    }

    @Override
    public Optional<E> save(E entity) {
        if(entity == null){
            throw new NullEntityException("Id-ul nu poate fi nul!\n");
        }

        validator.validate(entity);

        if(entities.containsValue(entity))
            throw new AlreadyExistentEntityException("Entitate deja existenta!\n");

        return Optional.ofNullable(entities.putIfAbsent(entity.getId(), entity));
    }

    @Override
    public Optional<E> delete(ID id) {
        if(id == null)
            throw new NullEntityException("Id-ul nu poate fi nul!\n");

        return Optional.ofNullable(entities.remove(id));
    }

    @Override
    public Optional<E> update(E entity) {
        if(entity == null)
            throw new NullEntityException("Entitatea nu poate fi nul!\n");

        validator.validate(entity);

        return Optional.ofNullable(entities.computeIfPresent(entity.getId(), (k,v)->entity));
    }

    @Override
    public int size() {
        return entities.size();
    }
}