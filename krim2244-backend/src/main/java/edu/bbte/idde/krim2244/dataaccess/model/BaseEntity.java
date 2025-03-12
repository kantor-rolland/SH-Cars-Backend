package edu.bbte.idde.krim2244.dataaccess.model;

import java.io.Serial;
import java.io.Serializable;

public abstract class BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    protected Long id;

    public BaseEntity() {
    }

    public BaseEntity(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
