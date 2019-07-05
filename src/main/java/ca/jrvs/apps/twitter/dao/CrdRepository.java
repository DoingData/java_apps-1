package ca.jrvs.apps.twitter.dao;

public interface CrdRepository<T, ID> {
    T create(T entity);

    T findById(ID id);

    T deleteById(ID id);
}
