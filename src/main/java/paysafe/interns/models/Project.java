package paysafe.interns.models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Class representing a single Project in the database and its connection to the
 * tasks it should contain.
 */
@Entity
public class Project extends BaseEntity {
	/** Project name */
	@NotNull
	@Size(min=2, max=255)
	private String name;

	/** List of tasks for the chosen project */
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "project")
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
}