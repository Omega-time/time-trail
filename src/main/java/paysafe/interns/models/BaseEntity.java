package paysafe.interns.models;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * A base class for an entity.
 */
@MappedSuperclass
public class BaseEntity {
    @Id
    @GeneratedValue
    private Long id;

    public BaseEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
