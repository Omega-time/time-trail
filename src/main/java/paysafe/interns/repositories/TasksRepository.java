package paysafe.interns.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import paysafe.interns.models.Task;

public interface TasksRepository extends JpaRepository<Task, Long> {
}
