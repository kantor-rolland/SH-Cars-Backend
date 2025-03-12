package edu.bbte.idde.krim2244.service;

import edu.bbte.idde.krim2244.dataaccess.model.Extra;
import edu.bbte.idde.krim2244.service.exception.ServiceException;

import java.util.List;

public interface ExtraService {

    Extra addExtra(Extra extra) throws ServiceException;

    Extra findById(Long id) throws ServiceException;

    Extra findByName(String name) throws ServiceException;

    List<Extra> findAllExtra() throws ServiceException;

    Extra modifyExtra(Extra extra) throws ServiceException;

    void removeExtra(Long id) throws ServiceException;

}
