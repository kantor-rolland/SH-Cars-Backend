package edu.bbte.idde.krim2244.dataaccess.jpa;

import edu.bbte.idde.krim2244.dataaccess.model.Extra;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Profile("jpa")
@Repository
public interface ExtraJPADao extends JpaRepository<Extra, Long> {

    // resznev
    Extra findByName(String name);
}

