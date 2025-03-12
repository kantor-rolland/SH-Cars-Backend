package edu.bbte.idde.krim2244.dataaccess.repository;

import edu.bbte.idde.krim2244.dataaccess.model.BaseEntity;
import edu.bbte.idde.krim2244.dataaccess.exception.RepoNotFoundException;
import edu.bbte.idde.krim2244.dataaccess.exception.RepoOperationFailedException;

import java.util.List;

public interface Dao<T extends BaseEntity> {

    T create(T entity) throws RepoOperationFailedException;

    T update(T entity) throws RepoNotFoundException;

    void delete(T entity) throws RepoNotFoundException;

    List<T> findAll();

    T findById(Long id) throws RepoNotFoundException;
}
