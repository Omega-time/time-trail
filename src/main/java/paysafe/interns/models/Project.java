package paysafe.interns.models;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Set;

/**
 * Class representing a single Project in the database and its connection to the
 * tasks and docs it should contain.
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

	/** Set of files(docs) for the chosen project */
	@ElementCollection
	@LazyCollection(LazyCollectionOption.FALSE)
	private Set<Doc> files;


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
}