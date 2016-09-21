package paysafe.interns.helpers;

import paysafe.interns.exceptions.InvalidProjectException;
import paysafe.interns.exceptions.InvalidTaskException;
import paysafe.interns.exceptions.UserAccessException;
import paysafe.interns.models.Project;
import paysafe.interns.models.UserInfo;
import paysafe.interns.repositories.ProjectsRepository;
import paysafe.interns.repositories.TasksRepository;

public class AccessChecker {
    private static final String NO_ACCESS_RIGHTS_MESSAGE = "No access permissions";
    private static final String NO_PROJECT_FOUND_MESSAGE = "Unable to find project with id %d";
    private static final String NO_DOC_FOUND_MESSAGE = "Unable to find doc with name %d";
    private static final String NO_TASK_FOUND_MESSAGE = "Unable to find task with id %d";


    public void checkTaskAccess(Long taskId, UserInfo user, TasksRepository tasksRepository,
            ProjectsRepository projectsRepository) {

        checkTaskExists(taskId, tasksRepository);
        Long projectId = tasksRepository.getOne(taskId).getProject().getId();
        checkUserRights(projectId, user, projectsRepository);
    }

    public void checkDocAccess(String docName, Long projectId, UserInfo user,
            ProjectsRepository projectsRepository) {
        checkProjectExists(projectId, projectsRepository);
        checkDocExists(docName, projectId, projectsRepository);
        checkUserRights(projectId, user, projectsRepository);
    }

    public void checkProjectAccess(Long projectId, UserInfo user, ProjectsRepository projectsRepository) {
        checkProjectExists(projectId, projectsRepository);
        checkUserRights(projectId, user, projectsRepository);
    }

    private void checkTaskExists(Long taskId, TasksRepository tasksRepository) {
        if (!tasksRepository.exists(taskId)) {
            throw new InvalidTaskException(String.format(NO_TASK_FOUND_MESSAGE, taskId));
        }
    }

    private void checkDocExists(String docName, Long projectId, ProjectsRepository projectsRepository) {
        Project project = projectsRepository.findOne(projectId);
        if (DocUtilities.getDocFromProjectByName(docName, project) == null) {
            throw new InvalidTaskException(String.format(NO_DOC_FOUND_MESSAGE, docName));
        }
    }

    private void checkProjectExists(Long projectId, ProjectsRepository projectsRepository) {
        if (!projectsRepository.exists(projectId)) {
            throw new InvalidProjectException(String.format(NO_PROJECT_FOUND_MESSAGE, projectId));
        }
    }

    private void checkUserRights(Long projectId, UserInfo user, ProjectsRepository projectsRepository) {
        Project project = projectsRepository.findOne(projectId);
        boolean checkOwner = project.getOwner().getId().equals(user.getId());
        boolean checkClient = false;
        for(UserInfo client : project.getClients()) {
            if(client.getId().equals(user.getId())) {
                checkClient = true;
                break;
            }
        }
        if (!checkOwner && !checkClient) {
            throw new UserAccessException(String.format(NO_ACCESS_RIGHTS_MESSAGE));
        }
    }
}
