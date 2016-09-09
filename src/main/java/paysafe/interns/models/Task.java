package paysafe.interns.models;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Class representing a single Task in the database
 */
@Entity
public class Task extends BaseEntity {
	/** Task name */
	@NotNull
	@Size(min=2,max=255)
	private String name;
	
	/** Task duration */
	@NotNull
	@Min(0)
	private long duration;
	
	/** Task comment (not required) */
	private String comment;

	/** The project to which the task belongs to. */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "project_id", nullable = false, insertable = false, updatable = false)
	private Project project;

	public Task() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getDuration() {
		return duration;
	}

	public void setDuration(long duration) {
		this.duration = duration;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	@Override
	public String toString() {
		return "Task [name=" + name + ", duration=" + duration + ", comment=" + comment + ", project=" + project + "]";
	}

	
}