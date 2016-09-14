package paysafe.interns.controllers;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import paysafe.interns.exceptions.InvalidDocException;
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
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

	/**
	 * PATCH Serving {@link ProjectsRepository} method.
	 * Saves a given file in the project.
	 * @param projectId
	 * 				the project in which the file will be saved.
	 * @param multipartFile
	 * 				the file that will be saved in the project.
	 * 				The file should have a valid content type {@link #fileContentTypeIsAllowed(String)}
     * @return File saved! or throws exception
	 * 		File with type ___ not supported
	 * 		or There is already a file with the same name!
     */
    @RequestMapping(value = "/project/{projectId}", method = RequestMethod.PATCH)
    public String uploadFileToProject(@Valid @PathVariable Long projectId, @RequestParam("file")
                                        MultipartFile multipartFile){
		if (this.fileContentTypeIsAllowed(multipartFile.getContentType())){
			Project project = projectsRepository.findOne(projectId);
			//TODO: refactor checking the filename for uniqueness
			for(Doc file : project.getFiles()){
				if (file.getName().equals(multipartFile.getOriginalFilename())){
					throw new InvalidDocException("There is already a file with the same name!");
				}
			}
			try{
				Doc file = new Doc();
				file.setName(multipartFile.getOriginalFilename());
				file.setFile(multipartFile.getBytes());
				file.setType(multipartFile.getContentType());
				project.getFiles().add(file);
				projectsRepository.save(project);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return "File saved!";
		} else{
			throw new InvalidDocException(
					String.format("File with type %s not supported", multipartFile.getContentType()));
		}
    }

	/**
	 * GET Serving {@link ProjectsRepository} files method.
	 * Lists the names of all files currently associated with the project.
	 * @param projectId the id of the project
	 * @return a List of all the names of the files currently saved in the project.
     */
	@RequestMapping(value = "/project/{projectId}/files", method = RequestMethod.GET)
	public List<String> getAllFileNamesByProjectId(@Valid @PathVariable Long projectId){
		List<String> fileNames = new LinkedList<>();
		Project project = projectsRepository.findOne(projectId);
		fileNames.addAll(project.getFiles().stream().map(Doc::getName).collect(Collectors.toList()));
		return fileNames;
	}

	@RequestMapping(value = "/project/{projectId}/{fileName:.+}", method = RequestMethod.GET)
	public void getFileFromProjectByName(@PathVariable Long projectId, @PathVariable String fileName, HttpServletResponse response){
		Doc tempFile;
		Doc file = null;
		try{
			Project project = projectsRepository.findOne(projectId);
			Set<Doc> files = project.getFiles();
			Iterator<Doc> it = files.iterator();
			while (it.hasNext()){
				tempFile = it.next();
				if (fileName.equals(tempFile.getName())){
					file = tempFile;
					break;
				}

			}
			if (file == null){
				throw new InvalidDocException("No file found with name: " + fileName);
			} else{
				response.setHeader("Content-Disposition", "inline; filename=\""+ file.getName() +"\"");
				response.setContentType(file.getType());
				response.setContentLength(file.getFile().length);
				FileCopyUtils.copy(file.getFile(), response.getOutputStream());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Temporary method to be used while testing Doc Upload and for reference when
	 * creating Download Doc.
	 * @param projectId the id of a project with a saved file
	 * @param response the first file saved in the project with projectId
     */
	@RequestMapping(value = "/download/{projectId}", method = RequestMethod.GET)
	public void getFileFromProject(@PathVariable Long projectId, HttpServletResponse response){
		Project project = projectsRepository.findOne(projectId);
		try{
			Set<Doc> files = project.getFiles();
			Doc file = files.iterator().next();
			response.setHeader("Content-Disposition", "inline; filename=\""+ file.getName() +"\"");
			response.setContentType(file.getType());
			response.setContentLength(file.getFile().length);
			FileCopyUtils.copy(file.getFile(), response.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private boolean fileContentTypeIsAllowed(String contentType){
		//TODO: add all desirable MimeTypes
		return contentType.equalsIgnoreCase(MimeTypeUtils.IMAGE_JPEG_VALUE)
				|| contentType.equalsIgnoreCase(MimeTypeUtils.IMAGE_PNG_VALUE)
				|| contentType.equalsIgnoreCase(MimeTypeUtils.TEXT_PLAIN_VALUE)
				|| contentType.equalsIgnoreCase("application/vnd.openxmlformats-officedocument.wordprocessingml.document")
				|| contentType.equalsIgnoreCase("application/msword")
				|| contentType.equalsIgnoreCase("application/pdf");
	}
}
