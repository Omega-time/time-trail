package paysafe.interns.models;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

/**
 * Class representing a single Project in the database and its connection to the
 * tasks and docs it should contain.
 */
@Entity
public class Project extends BaseEntity {
    /** Project name */
    @NotNull
    @Size(min = 2, max = 255)
    private String name;

    @ManyToOne
    @JoinColumn(name = "project_owner")
    @JsonBackReference
    private UserInfo owner;

    /** List of tasks for the chosen project */
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "project")
    @JsonManagedReference
    private List<Task> tasks;

    /** Set of files(docs) for the chosen project */
    @ElementCollection
    @LazyCollection(LazyCollectionOption.FALSE)
    private Set<Doc> files;

    private String lastTaskName;

    private Timestamp DateOfCreation;

    private Timestamp DateLastChanged;

    public Project() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public Set<Doc> getFiles() {
        return files;
    }

    public void setFiles(Set<Doc> files) {
        this.files = files;
    }

    public UserInfo getOwner() {
        return owner;
    }

    public void setOwner(UserInfo owner) {
        this.owner = owner;
    }

    public String getLastTaskName() {
        return lastTaskName;
    }

    public void setLastTaskName(String lastTaskName) {
        this.lastTaskName = lastTaskName;
    }

    public Timestamp getDateOfCreation() {
        return DateOfCreation;
    }

    public void setDateOfCreation(Timestamp dateOfCreation) {
        DateOfCreation = dateOfCreation;
    }

    public Timestamp getDateLastChanged() {
        return DateLastChanged;
    }

    public void setDateLastChanged(Timestamp dateLastChanged) {
        DateLastChanged = dateLastChanged;
    }

}