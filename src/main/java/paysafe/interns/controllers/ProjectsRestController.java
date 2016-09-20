package paysafe.interns.controllers;

import org.apache.http.HttpResponse;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import paysafe.interns.exceptions.DocNotFoundException;
import paysafe.interns.exceptions.InvalidDocException;
import paysafe.interns.exceptions.InvalidProjectException;
import paysafe.interns.helpers.AccessChecker;
import paysafe.interns.helpers.DocUtilities;
import paysafe.interns.models.Doc;
import paysafe.interns.models.Project;
import paysafe.interns.models.Task;
import paysafe.interns.models.UserInfo;
import paysafe.interns.repositories.ProjectsRepository;
import paysafe.interns.repositories.TasksRepository;
import paysafe.interns.repositories.UserRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import java.io.IOException;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;

/**
 * A RestController class where we define the endpoint URLs for the Project
 * service. It uses Autowired annotation in order to use component scan for our
 * {@link ProjectsRepository}
 */
@RestController
@RequestMapping("/api/projects")
public class ProjectsRestController extends BaseRestController {
    private static final int MAX_SIZE_OF_ALL_FILES_PER_PROJECT_IN_KB = 10240;
    @Autowired
    private ProjectsRepository projectsRepository;

    @Autowired
    private TasksRepository tasksRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     * GET Serving {@link ProjectsRepository#findAll()} method
     *
     * @return a json string array of all projects or empty array
     */
    // TODO: Should return all projects created by a user
    Iterable<Project> getAllProjects(HttpServletRequest request) {
        UserInfo cachedUser = (UserInfo) request.getSession().getAttribute("user");

        Collection<Project> projects = this.projectsRepository.findAllByOwner(cachedUser);
        projects.addAll(this.projectsRepository.findAllByClients(cachedUser));
        return projects;
    }

//    @RequestMapping("/projects/client")
//    Iterable<Project> getClientProjects(HttpServletRequest request) {
//        UserInfo user = (UserInfo) request.getSession().getAttribute("user");
//
//        return this.projectsRepository.findAllByClients(user);
//    }

    /**
     * GET Serving the {@link ProjectsRepository#findOne(Serializable)} method
     *
     * @param projectId
     *            a path parameter by which the method finds a specific project
     * @return a json representation of a project or empty string
     */
    @RequestMapping("/{projectId}")
    Project getProjectById(@PathVariable Long projectId, HttpServletRequest request) {
        new AccessChecker().checkProjectAccess(projectId, request, projectsRepository);

        return this.projectsRepository.findOne(projectId);
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
    @RequestMapping(method = RequestMethod.POST)
    String addProject(@RequestBody Project project, HttpServletRequest request) throws InvalidProjectException {
        UserInfo user = (UserInfo) request.getSession().getAttribute("user");
        project.setOwner(user);

        try {
            project.setDateOfCreation(new Timestamp(new Date().getTime()));
            this.projectsRepository.save(project);
            user.addProject(project);
            this.userRepository.save(user);
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

    @RequestMapping(value = "/{projectId}/client", method = RequestMethod.PATCH)
    public ResponseEntity<InputStreamResource> addClient(@PathVariable Long projectId, @RequestBody String email) {
        UserInfo client = this.userRepository.findOneByEmail(email);
        if(client != null) {
            Project project = this.projectsRepository.findOne(projectId);
            if(project != null) {
                project.addClient(client);
                this.projectsRepository.save(project);
                return new ResponseEntity<>(HttpStatus.CREATED);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    /**
     * DELETE Serving {@link ProjectsRepository} method. First get all tasks
     * related to the provided project id and deleted tasks.Then delete current
     * project.
     *
     * @param projectId
     *            a path parameter by which the method finds a specific progect
     * @return Project with current projectId deleted or Unable to find project
     *         with current projectId.
     */
    @RequestMapping(value = "/{projectId}", method = RequestMethod.DELETE)
    public String deleteProjectByProjectId(@PathVariable Long projectId, HttpServletRequest request) {
        new AccessChecker().checkProjectAccess(projectId, request, projectsRepository);

        Project projectForDeleted = projectsRepository.getOne(projectId);
        Iterable<Task> tasksByCurrentProject = tasksRepository.findAllByProjectId(projectId);
        for (Task task : tasksByCurrentProject) {
            tasksRepository.delete(task);
        }
        projectsRepository.delete(projectForDeleted);
        JSONObject response = new JSONObject();
        try {
            response.put("id", projectId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return response.toString();
    }

    /**
     * POST for project files Saves a given file in the project.
     *
     * @param projectId
     *            the project in which the file will be saved.
     * @param multipartFile
     *            the file that will be saved in the project. The file should
     *            have a valid content type
     *            {@link DocUtilities#docContentTypeIsAllowed(String)}
     * @return The name of the uploaded file if uploaded successfully.
     */
    @RequestMapping(value = "/{projectId}/files", method = RequestMethod.POST)
    public String uploadDocToProject(@Valid @PathVariable Long projectId,
            @RequestParam("file") MultipartFile multipartFile, HttpServletRequest request) {
        new AccessChecker().checkProjectAccess(projectId, request, projectsRepository);

        Project project = projectsRepository.findOne(projectId);
        project.setDateLastChanged(new Timestamp(new Date().getTime()));
        if (DocUtilities.multipartFileIsEligibleForUpload(multipartFile, project)) {
            JSONObject response = new JSONObject();
            try {
                Doc file = DocUtilities.createDocFromMultipartFile(multipartFile);
                project.getFiles().add(file);
                projectsRepository.save(project);
                response.put("name", file.getName());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return response.toString();
        } else {
            throw new InvalidDocException("File cannot be uploaded!");
        }
    }

    /**
     * GET for file names Lists the names of all files currently associated with
     * the project.
     *
     * @param projectId
     *            the id of the project
     * @return a List of all the names of the files currently saved in the
     *         project.
     */
    @RequestMapping(value = "/{projectId}/files", method = RequestMethod.GET)
    public String getAllDocNamesByProjectId(@Valid @PathVariable Long projectId, HttpServletRequest request) {
        new AccessChecker().checkProjectAccess(projectId, request, projectsRepository);

        JSONArray jsonArray = new JSONArray();
        Project project = projectsRepository.findOne(projectId);
        for (Doc doc : project.getFiles()) {
            try {
                jsonArray.put(DocUtilities.createJsonObjectFromDoc(doc));
            } catch (JSONException e) {
                System.err.println("Something went wrong when parsing Doc to JSONObject");
            }
        }
        return jsonArray.toString();
    }

    /**
     * GET for particular file Sends the file from the project with the desired
     * fileName to the OutputStream to be downloaded/used.
     *
     * @param projectId
     *            the id of the project
     * @param fileName
     *            the name of the desired file
     * @param response
     *            used to set headers and access OutputStream
     */
    @RequestMapping(value = "/{projectId}/{fileName:.+}", method = RequestMethod.GET)
    public void getDocFromProjectByName(@PathVariable Long projectId, @PathVariable String fileName,
            HttpServletResponse response, HttpServletRequest request) {
        new AccessChecker().checkDocAccess(fileName, projectId, request, projectsRepository);

        try {
            Project project = projectsRepository.findOne(projectId);
            Doc file = DocUtilities.getDocFromProjectByName(fileName, project);
            if (DocUtilities.docExistsInProject(file, project)) {
                response.setHeader("Content-Disposition", "inline; filename=\"" + file.getName() + "\"");
                response.setContentType(file.getType());
                response.setContentLength(file.getFile().length);
                FileCopyUtils.copy(file.getFile(), response.getOutputStream());
            } else {
                throw new DocNotFoundException("No file found with name: " + fileName);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * DELETE a particular file First checks if a file with the given name
     * exists, if it does, deletes it.
     *
     * @param projectId
     *            the id of the project containing the desired file
     * @param fileName
     *            the name of the file to be deleted
     * @return The name of the deleted file if successful
     */
    @RequestMapping(value = "/{projectId}/{fileName:.+}", method = RequestMethod.DELETE)
    public String deleteDocFromProjectByName(@PathVariable Long projectId, @PathVariable String fileName,
            HttpServletRequest request) {
        new AccessChecker().checkDocAccess(fileName, projectId, request, projectsRepository);

        Project project = projectsRepository.findOne(projectId);
        project.setDateLastChanged(new Timestamp(new Date().getTime()));
        Doc file = DocUtilities.getDocFromProjectByName(fileName, project);
        JSONObject response = new JSONObject();
        if (DocUtilities.docExistsInProject(file, project)) {
            project.getFiles().remove(file);
            projectsRepository.save(project);
            try {
                response.put("name", file.getName());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return response.toString();
        } else {
            throw new DocNotFoundException("No file found with name: " + fileName);
        }
    }
}
