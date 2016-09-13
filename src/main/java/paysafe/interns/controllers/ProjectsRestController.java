package paysafe.interns.controllers;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import paysafe.interns.exceptions.InvalidProjectException;
import paysafe.interns.models.Doc;
import paysafe.interns.models.Project;
import paysafe.interns.models.Task;
import paysafe.interns.repositories.ProjectsRepository;
import paysafe.interns.repositories.TasksRepository;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import java.io.IOException;
import java.io.Serializable;
import java.util.Set;

/**
 * A RestController class where we define the endpoint URLs for the Project
 * service. It uses Autowired annotation in order to use component scan for our
 * {@link ProjectsRepository}
 */
@RestController
public class ProjectsRestController {
	@Autowired
	private ProjectsRepository projectsRepository;

	@Autowired
	private TasksRepository tasksRepository;

	/**
	 * GET Serving {@link ProjectsRepository#findAll()} method
	 * 
	 * @return a json string array of all projects or empty array
	 */
	// TODO: Should return all projects created by a user
	@RequestMapping("/projects")
	Iterable<Project> getAllProjects() {
		return this.projectsRepository.findAll();
	}

	/**
	 * GET Serving the {@link ProjectsRepository#findOne(Serializable)} method
	 * 
	 * @param id
	 *            a path parameter by which the method finds a specific project
	 * @return a json representation of a project or empty string
	 */
	@RequestMapping("/project/{id}")
	Project getProjectById(@PathVariable Long id) {
		Project project = this.projectsRepository.findOne(id);
		if (project == null) {
			throw new InvalidProjectException(String.format("Unable to find task with id %d", id));
		}
		return this.projectsRepository.findOne(id);
	}

	/**
	 * POST Serves the {@link ProjectsRepository#save(Object)} method. Saves a
	 * project in the database
	 * 
	 * @param project
	 *            the project to be saved which is passed by a json body request
	 *            parameter
	 * @return json string of an anonymous object holding the id of the created
	 *         project
	 * @throws InvalidProjectException
	 *             thrown when the JPA throws a
	 *             {@link javax.validation.ConstraintViolationException} when
	 *             validating the input json request body.
	 */
	@RequestMapping(value = "/projects", method = RequestMethod.POST)
	String addProject(@RequestBody Project project) throws InvalidProjectException {
		try {
			this.projectsRepository.save(project);
		} catch (ConstraintViolationException cve) {
			throw new InvalidProjectException(cve);
		}

		JSONObject response = new JSONObject();
		try {
			response.put("id", project.getId());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return response.toString();
	}

	/**
	 * DELETE Serving {@link ProjectsRepository}
	 * method. First get all tasks related to the provided project id and
	 * deleted tasks.Then delete current project.
	 * 
	 * @param projectId
	 *            a path parameter by which the method finds a specific progect
	 * @return Project with current projectId deleted or Unable to find project
	 *         with current projectId.
	 */
	@RequestMapping(value = "/project/{projectId}", method = RequestMethod.DELETE)
	public String deleteProjectByProjectId(@PathVariable Long projectId) {
		try {
			Project projectForDeleted = projectsRepository.getOne(projectId);
			Iterable<Task> tasksByCurrentProject = tasksRepository.findAllByProjectId(projectId);
			for (Task task : tasksByCurrentProject) {
				tasksRepository.delete(task);
			}

			projectsRepository.delete(projectForDeleted);

			return "Project " + projectId + " deleted.";

		} catch (JpaObjectRetrievalFailureException jorfe) {
			return "Unable to find project with id " + projectId;
		}
	}

    @RequestMapping(value = "/project/{projectId}", method = RequestMethod.PATCH)
    public String uploadFileToProject(@Valid @PathVariable Long projectId, @RequestParam("file")
                                        MultipartFile multipartFile){
        Project project = projectsRepository.findOne(projectId);
        try{
            Doc file = new Doc();
			file.setName("Test Doc One!");
            file.setFile(multipartFile.getBytes());
			project.getFiles().add(file);
            projectsRepository.save(project);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Doc saved!";
    }

	@RequestMapping(value = "/download/{projectId}", method = RequestMethod.GET)
	public void getFileFromProject(@PathVariable Long projectId, HttpServletResponse response){
		Project project = projectsRepository.findOne(projectId);
		try{
			Set<Doc> files = project.getFiles();
			//TODO: get a particular file instead of the first one
			Doc file = files.iterator().next();
			response.setContentLength(file.getFile().length);
			//TODO: set ContentType
			FileCopyUtils.copy(file.getFile(), response.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
