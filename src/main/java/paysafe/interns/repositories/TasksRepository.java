package paysafe.interns.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import paysafe.interns.models.Task;

/**
 * Standard repository interface which is
 * injected via component scan in order to have access
 * to the CRUD methods.
 */
public interface TasksRepository extends JpaRepository<Task, Long> {
}
