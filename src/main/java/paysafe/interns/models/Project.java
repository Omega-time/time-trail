package paysafe.interns.models;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

/**
 * Class representing a single Project
 * in the database
 */
@Entity
public class Project extends BaseEntity{
    /** Project name */
    @NotNull
    private String name;
    //TODO: tasks

    public Project() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
