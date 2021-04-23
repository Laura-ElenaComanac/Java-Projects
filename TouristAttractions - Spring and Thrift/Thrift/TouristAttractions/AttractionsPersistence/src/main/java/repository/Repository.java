package repository;

import model.Entity;

public interface Repository<T extends Entity<Tid>, Tid> {
    void add(T elem);
    void delete(T elem);
    void update (T elem, Tid id);
    T findById (Tid id);
    Iterable<T> findAll();
    int size();
}
