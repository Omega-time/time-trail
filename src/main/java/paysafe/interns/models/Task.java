package paysafe.interns.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Class representing a single Task in the database
 */
@Entity
public class Task extends BaseEntity {
	/** Task name */
	@NotNull
	private String name;
	/** Task duration */
	@NotNull
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

}