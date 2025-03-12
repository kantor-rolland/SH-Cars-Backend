package edu.bbte.idde.krim2244.dataaccess.jdbc;

import edu.bbte.idde.krim2244.dataaccess.AbstractDaoFactory;
import edu.bbte.idde.krim2244.dataaccess.repository.CarDao;

public class JdbcDaoFactory extends AbstractDaoFactory {
    private CarDao carDao;

    @Override
    public CarDao getCarDao() {
        if (carDao == null) {
            carDao = new CarDaoJdbcImpl();
        }
        return carDao;
    }
}
