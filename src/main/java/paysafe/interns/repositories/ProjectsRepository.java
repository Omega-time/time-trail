package paysafe.interns.repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import paysafe.interns.models.Project;
import paysafe.interns.models.UserInfo;

/**
 * Standard repository interface which is
 * injected via component scan in order to have access
 * to the CRUD methods.
 */
@Transactional
public interface ProjectsRepository extends JpaRepository<Project, Long> {
    public Collection<Project> findAllByOwner(UserInfo owner);
    public Collection<Project> findAllByClients(UserInfo client);
}
