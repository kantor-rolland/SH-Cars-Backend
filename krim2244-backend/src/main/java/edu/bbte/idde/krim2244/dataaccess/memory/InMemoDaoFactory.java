package edu.bbte.idde.krim2244.dataaccess.memory;

import edu.bbte.idde.krim2244.dataaccess.AbstractDaoFactory;
import edu.bbte.idde.krim2244.dataaccess.repository.CarDao;

public class InMemoDaoFactory extends AbstractDaoFactory {

    private CarDao carDao;

    @Override
    public synchronized CarDao getCarDao() {
        if (carDao == null) {
            carDao = new CarDaoImpl();
        }
        return carDao;
    }
}
