package paysafe.interns.models;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.*;
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

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "client_project",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "client_id"))
    private Set<UserInfo> clients;

    /** List of tasks for the chosen project */
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "project")
    @JsonManagedReference
    private Set<Task> tasks;

    /** Set of files(docs) for the chosen project */
    @ElementCollection
    @LazyCollection(LazyCollectionOption.FALSE)
    private Set<Doc> files;

    private String lastTaskName;

    private Timestamp dateOfCreation;

    private Timestamp dateLastChanged;

    public Project() {
        this.clients = new HashSet<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Task> getTasks() {
        return tasks;
    }

    public void setTasks(Set<Task> tasks) {
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
        return dateOfCreation;
    }

    public void setDateOfCreation(Timestamp dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
    }

    public Timestamp getDateLastChanged() {
        return dateLastChanged;
    }

    public void setDateLastChanged(Timestamp dateLastChanged) {
        this.dateLastChanged = dateLastChanged;
    }

    public Set<UserInfo> getClients() {
        return clients;
    }

    public void addClient(UserInfo client) {
        this.clients.add(client);
    }
}