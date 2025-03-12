package edu.bbte.idde.krim2244.dataaccess;

import edu.bbte.idde.krim2244.config.ConfigurationFactory;
import edu.bbte.idde.krim2244.config.MainConfiguration;
import edu.bbte.idde.krim2244.dataaccess.memory.InMemoDaoFactory;
import edu.bbte.idde.krim2244.dataaccess.repository.CarDao;
import edu.bbte.idde.krim2244.dataaccess.jdbc.JdbcDaoFactory;

import java.util.logging.Logger;

public abstract class AbstractDaoFactory {
    private static AbstractDaoFactory instance;
    private static final Logger LOG = Logger.getLogger(AbstractDaoFactory.class.getName());

    private static final MainConfiguration config = ConfigurationFactory.getMainConfiguration();

    public abstract CarDao getCarDao();

    // singleton lazy minta
    public static synchronized AbstractDaoFactory getInstance() {
        if (instance == null) {
            String dbType = config.getDbType();

            switch (dbType) {
                case "jdbc":
                    instance = new JdbcDaoFactory();
                    break;
                case "mem":
                    instance = new InMemoDaoFactory();
                    break;
                default: {
                    LOG.info("Invalid dao type");
                }
            }
        }
        return instance;
    }
}