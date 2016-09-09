package paysafe.interns.models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

/**
 * Class representing a single Project in the database and its connection to the
 * tasks it should contain.
 */
@Entity
public class Project extends BaseEntity {
	/** Project name */
	@NotNull
	private String name;

	/** List of tasks for the chosen project. */
	@OneToMany(mappedBy = "project")
	private List<Task> tasks;

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
	
	@Override
	public String toString() {
		return "Project [name=" + name + ", tasks=" + tasks + "]";
	}

}