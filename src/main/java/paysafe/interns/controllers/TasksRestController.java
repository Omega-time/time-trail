package paysafe.interns.controllers;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import paysafe.interns.exceptions.InvalidTaskException;
import paysafe.interns.models.Project;
import paysafe.interns.models.Task;
import paysafe.interns.repositories.ProjectsRepository;
import paysafe.interns.repositories.TasksRepository;

import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import java.io.Serializable;

/**
 * A RestController class where we define the endpoint URLs for the Task
 * service. It uses Autowired annotation in order to use component scan for our
 * {@link TasksRepository}
 */
@RequestMapping("/api")
@RestController
public class TasksRestController extends BaseRestController {

    @Autowired
    private ProjectsRepository projectsRepository;

    @Autowired
    private TasksRepository tasksRepository;

    /**
     * POST Serves the {@link TasksRepository#save(Object)}} method. Saves a task in
     * the database related to the provided project id.
     *
     * @param task          the new Task which will be saved to the database.
     * @param projectId     the project for which the new task is created.
     * @param bindingResult checks the entity validations
     * @return Id of the newly created Task or Bad Request with error data
     * object
     */
    @RequestMapping(value = "/tasks/{projectId}", method = RequestMethod.POST)
    public String addTask(@Valid @RequestBody Task task, @PathVariable Long projectId, BindingResult bindingResult) {
        try {
            Project project = projectsRepository.getOne(projectId);
            task.setProject(project);
            tasksRepository.saveAndFlush(task);
        } catch (ConstraintViolationException cve) {
            throw new InvalidTaskException(cve);
        }
        JSONObject response = new JSONObject();
        try {
            response.put("id", task.getId());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return response.toString();
    }

    /**
     * GET Serving {@link TasksRepository#findAllByProjectId(Long)} method
     *
     * @param projectId a path parameter by which the method finds a specific tasks
     * @return a json string array of all tasks with current projectId or empty
     * array
     */
    @RequestMapping(value = "/tasks/{projectId}", method = RequestMethod.GET)
    public Iterable<Task> getAllTasksByProjectId(@PathVariable Long projectId) {

        return tasksRepository.findAllByProjectId(projectId);
    }

    /**
     * GET Serving {@link TasksRepository#getOne(Serializable)} method
     *
     * @param taskId a path parameter by which the method finds a specific task
     * @return task with current taskId or Unable to find task with id current
     * taskId
     */
    @RequestMapping(value = "/task/{taskId}", method = RequestMethod.GET)
    public String getTaskByTaskId(@PathVariable Long taskId) {
        try {
            Task task = null;
            try {
                task = tasksRepository.getOne(taskId);
            } catch (ConstraintViolationException cve) {
                throw new InvalidTaskException(cve);
            }
            JSONObject response = new JSONObject();
            try {
                response.put("id", task.getId());
                response.put("name", task.getName());
                response.put("duration", task.getDuration());
                response.put("comment", task.getComment());
                response.put("projectId", task.getProject().getId());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return response.toString();
        } catch (EntityNotFoundException enfe) {
            throw new InvalidTaskException("Task not found");
        }
    }

    /**
     * DELETE Serving {@link TasksRepository#delete(Serializable)} method
     *
     * @param taskId a path parameter by which the method finds a specific task
     * @return Task with current taskId deleted or Unable to find task with
     * current taskId.
     */
    @RequestMapping(value = "/task/{taskId}", method = RequestMethod.DELETE)
    public String deleteTaskByTaskId(@PathVariable Long taskId) {
        JSONObject response = new JSONObject();
        try {
            Task taskForDeleted = tasksRepository.getOne(taskId);
            tasksRepository.delete(taskForDeleted);
            response.put("id", taskForDeleted.getId());
            return response.toString();
        } catch (JpaObjectRetrievalFailureException jorfe) {
            throw new InvalidTaskException("No task found with id " + taskId);
        } catch (JSONException e) {
            throw new InvalidTaskException("Error deleting task");
        }
    }
}
