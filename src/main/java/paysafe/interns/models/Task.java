package paysafe.interns.models;

import javax.persistence.Entity;
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