package edu.bbte.idde.krim2244.service.jpa;

import edu.bbte.idde.krim2244.dataaccess.exception.RepoOperationFailedException;
import edu.bbte.idde.krim2244.dataaccess.jpa.ExtraJPADao;
import edu.bbte.idde.krim2244.dataaccess.model.Extra;
import edu.bbte.idde.krim2244.service.ExtraService;
import edu.bbte.idde.krim2244.service.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Slf4j
@Profile("jpa")
@Service
public class ExtraServiceJpa implements ExtraService {

    private final ExtraJPADao extraJPADao;

    public ExtraServiceJpa(ExtraJPADao extraJPADao) {
        this.extraJPADao = extraJPADao;
    }

    @Override
    public Extra addExtra(Extra extra) throws ServiceException {
        log.info("Adding new extra");

        if (extra == null) {
            throw new ServiceException("Extra is null");
        }

        try {
            return extraJPADao.save(extra);
        } catch (RepoOperationFailedException e) {
            log.error("Error adding new extra", e);
            throw new ServiceException("Error adding new extra", e);
        }
    }

    @Override
    public Extra findById(Long id) throws ServiceException {
        log.info("Finding extra by id {}", id);
        Optional<Extra> extra = extraJPADao.findById(id);
        if (extra.isPresent()) {
            log.info("Found extra {}", extra.get());
            return extra.get();
        } else {
            log.info("Extra with id {} not found", id);
            throw new ServiceException("Extra not found with ID: " + id);
        }
    }

    @Override
    public Extra findByName(String name) throws ServiceException {
        log.info("Finding extra by name {}", name);
        Optional<Extra> extra = Optional.ofNullable(extraJPADao.findByName(name));
        if (extra.isPresent()) {
            log.info("Found extra {}", extra.get());
            return extra.get();
        } else {
            log.info("Extra with name {} not found", name);
            throw new ServiceException("Extra not found with name: " + name);
        }
    }

    @Override
    public List<Extra> findAllExtra() {
        log.info("Listing all extras");
        return extraJPADao.findAll();
    }

    @Override
    public Extra modifyExtra(Extra extra) throws ServiceException {
        log.info("Modifying extra {}", extra);
        if (!extraJPADao.existsById(extra.getId())) {
            log.info("Extra not found with ID {}", extra.getId());
            throw new ServiceException("Cannot modify a non-existing extra with ID: " + extra.getId());
        }

        return extraJPADao.save(extra);
    }

    @Override
    public void removeExtra(Long id) throws ServiceException {
        log.info("Removing extra with ID {}", id);
        if (!extraJPADao.existsById(id)) {
            log.error("Cannot remove extra with ID: " + id);
            throw new ServiceException("Cannot remove a non-existing extra with ID: " + id);
        }
        extraJPADao.deleteById(id);
    }
}

