package edu.bbte.idde.krim2244.dataaccess.repository;

import edu.bbte.idde.krim2244.dataaccess.model.BaseEntity;
import edu.bbte.idde.krim2244.dataaccess.model.Car;
import edu.bbte.idde.krim2244.dataaccess.exception.RepoNotFoundException;
import edu.bbte.idde.krim2244.dataaccess.exception.RepoOperationFailedException;

import java.util.List;

public interface Dao<T extends BaseEntity> {

    void create(T entity) throws RepoOperationFailedException;

    void update(T entity) throws RepoNotFoundException;

    void delete(T entity) throws RepoNotFoundException;

    List<Car> findAll();

    Car findById(Long id) throws RepoNotFoundException;
}
