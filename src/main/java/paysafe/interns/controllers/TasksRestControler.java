package paysafe.interns.controllers;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import paysafe.interns.exceptions.InvalidTaskException;
import paysafe.interns.models.Project;
import paysafe.interns.models.Task;
import paysafe.interns.repositories.ProjectsRepository;
import paysafe.interns.repositories.TasksRepository;

/**
 * A RestController class where we define the endpoint URLs for the Task
 * service. It uses Autowired annotation in order to use component scan for our
 * {@link TasksRepository}
 */
@RestController
public class TasksRestControler {
	@Autowired
	private TasksRepository tasksRepository;

	@Autowired
	private ProjectsRepository projectsRepository;

	/**
	 * Get data from request, validate data, call addNewTask from TaskDAO with
	 * taskData parameter, and return ResponseEntity "200 Ok" for success or
	 * bad_request.
	 * 
	 * @return ResponseEntity "200 Ok" for success or bad_request.
	 */
	@RequestMapping(value = "/tasks/{projectId}", method = RequestMethod.POST)
	public String addTask(@Valid @RequestBody Task task, @PathVariable Long projectId, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			System.err.println("\nValidation data error - addTask!");
			System.err.println("Task name: " + task.getName());

			return "Invalid data!";
		}

		try {
			System.err.println(projectId);
			Project project = projectsRepository.findOne(projectId);
			System.err.println(project);
			task.setProject(project);
			System.err.println(task);
			this.tasksRepository.save(task);
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
}
