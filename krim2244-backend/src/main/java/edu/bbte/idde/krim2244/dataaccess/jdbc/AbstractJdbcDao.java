package edu.bbte.idde.krim2244.dataaccess.jdbc;

import edu.bbte.idde.krim2244.dataaccess.model.BaseEntity;
import edu.bbte.idde.krim2244.dataaccess.repository.Dao;

import javax.sql.DataSource;

public abstract class AbstractJdbcDao<T extends BaseEntity> implements Dao<T> {
    protected final DataSource connectionPool = HikariConnection.buildDataSource();
}
