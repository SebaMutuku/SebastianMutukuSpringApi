package com.sebamutuku.sebastianmutukuspringtest.services.serviceimpl;

import com.sebamutuku.sebastianmutukuspringtest.dto.requests.BaseRequest;
import com.sebamutuku.sebastianmutukuspringtest.dto.requests.ProjectRequest;
import com.sebamutuku.sebastianmutukuspringtest.dto.requests.TaskRequest;
import com.sebamutuku.sebastianmutukuspringtest.dto.responses.BaseResponse;
import com.sebamutuku.sebastianmutukuspringtest.dto.responses.ProjectResponse;
import com.sebamutuku.sebastianmutukuspringtest.dto.responses.ProjectSummaryResponse;
import com.sebamutuku.sebastianmutukuspringtest.dto.responses.TaskResponse;
import com.sebamutuku.sebastianmutukuspringtest.entities.Project;
import com.sebamutuku.sebastianmutukuspringtest.entities.Task;
import com.sebamutuku.sebastianmutukuspringtest.repos.ProjectRepo;
import com.sebamutuku.sebastianmutukuspringtest.repos.TaskRepo;
import com.sebamutuku.sebastianmutukuspringtest.services.ProjectService;
import com.sebamutuku.sebastianmutukuspringtest.utils.Utils;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ProjectServiceImpl extends ProjectService {
    private final ProjectRepo projectRepo;
    private final TaskRepo taskRepo;

    @Autowired
    public ProjectServiceImpl(ProjectRepo projectRepo, TaskRepo taskRepo) {
        this.projectRepo = projectRepo;
        this.taskRepo = taskRepo;
    }

    @Override
    public ResponseEntity<BaseResponse<ProjectResponse>> create(BaseRequest<ProjectRequest> request) {
        BaseResponse<ProjectResponse> response = new BaseResponse<>();
        log.info("received request [{}]", request.getRequestId());
        try {
            ProjectRequest projectRequest = request.getData();
            if (projectRequest != null) {
                Optional<Project> existingProject = projectRepo.findByName(projectRequest.getName());
                if (existingProject.isPresent()) {
                    ProjectResponse projectResponse = existingProject.map(project -> ProjectResponse.builder().name(project.getName()).description(project.getDescription()).build()).get();
                    response.setData(projectResponse);
                    response.setResponseId(UUID.randomUUID().toString());
                    response.setResponseId(response.getRequestId());
                    response.setStatusCode(HttpStatus.ALREADY_REPORTED.value());
                    response.setExtraData(new LinkedList<>());
                    response.setStatusDescription("Found existing project with id [" + existingProject.get().getProjectId() + "]");
                    log.info("Response [{}]", response);
                    return new ResponseEntity<>(response, HttpStatus.ALREADY_REPORTED);
                }
                Project project = Project.builder().name(projectRequest.getName()).description(projectRequest.getDescription()).build();
                projectRepo.save(project);
                response.setData(null);
                response.setResponseId(UUID.randomUUID().toString());
                response.setRequestId(response.getRequestId());
                response.setStatusCode(HttpStatus.OK.value());
                response.setExtraData(new LinkedList<>());
                response.setStatusDescription("Successfully project  Id [" + project.getProjectId() + "]");
                log.info("Response [{}]", response);
                return new ResponseEntity<>(response, HttpStatus.OK);

            }
            response.setData(null);
            response.setResponseId(UUID.randomUUID().toString());
            response.setResponseId(response.getRequestId());
            response.setStatusCode(HttpStatus.BAD_REQUEST.value());
            response.setExtraData(new LinkedList<>());
            response.setStatusDescription("Failed");
            response.setError(List.of(new com.sebamutuku.sebastianmutukuspringtest.dto.responses.Error(HttpStatus.BAD_REQUEST.value(), "Failed ")));
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);

        } catch (Exception e) {
            e.printStackTrace();
            response.setData(null);
            response.setResponseId(UUID.randomUUID().toString());
            response.setResponseId(response.getRequestId());
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setExtraData(new LinkedList<>());
            response.setStatusDescription("Failed with error [" + e.getMessage() + "]");
            response.setError(List.of(new com.sebamutuku.sebastianmutukuspringtest.dto.responses.Error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Failed ")));
            log.error("Response [{}]", response);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Override
    public ResponseEntity<BaseResponse<ProjectResponse>> findByProjectId(String projectId) {
        BaseResponse<ProjectResponse> response = new BaseResponse<>();
        log.info("received request [{}]", projectId);
        try {
            if (projectId != null) {
                Optional<Project> exitingProject = projectRepo.findByProjectId(projectId);
                if (exitingProject.isPresent()) {
                    exitingProject.map(project -> ProjectResponse.builder().name(project.getName()).description(project.getDescription()).build()).get();
                    response.setResponseId(UUID.randomUUID().toString());
                    response.setResponseId(response.getRequestId());
                    response.setStatusCode(HttpStatus.OK.value());
                    response.setExtraData(new LinkedList<>());
                    response.setStatusDescription("Success");
                    return new ResponseEntity<>(response, HttpStatus.OK);
                }
                response.setResponseId(UUID.randomUUID().toString());
                response.setResponseId(response.getRequestId());
                response.setStatusCode(HttpStatus.NOT_FOUND.value());
                response.setData(null);
                response.setExtraData(new LinkedList<>());
                response.setStatusDescription("Project with id [" + projectId + "] does not exist");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
            response.setData(null);
            response.setResponseId(UUID.randomUUID().toString());
            response.setResponseId(response.getRequestId());
            response.setStatusCode(HttpStatus.BAD_REQUEST.value());
            response.setExtraData(new LinkedList<>());
            response.setStatusDescription("Failed");
            response.setError(List.of(new com.sebamutuku.sebastianmutukuspringtest.dto.responses.Error(HttpStatus.BAD_REQUEST.value(), "Failed ")));
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);

        } catch (Exception e) {
            response.setData(null);
            response.setResponseId(UUID.randomUUID().toString());
            response.setResponseId(response.getRequestId());
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setExtraData(new LinkedList<>());
            response.setStatusDescription("Failed with error [" + e.getMessage() + "]");
            response.setError(List.of(new com.sebamutuku.sebastianmutukuspringtest.dto.responses.Error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Failed ")));
            log.error("Response [{}]", response);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Override
    public ResponseEntity<BaseResponse<TaskResponse>> addTasks(BaseRequest<TaskRequest> request, String projectId) {
        log.info("received request [{}]", request);
        BaseResponse<TaskResponse> response = new BaseResponse<>();
        log.info("received request [{}]", request);
        try {
            if (request != null) {
                TaskRequest taskRequest = request.getData();
                if (taskRequest != null) {
                    Optional<Project> existingTask = projectRepo.findByProjectId(projectId);
                    if (existingTask.isPresent()) {
                        TaskResponse taskResponse = existingTask.map(project -> TaskResponse.builder().build()).get();
                        response.setData(taskResponse);
                        response.setResponseId(UUID.randomUUID().toString());
                        response.setResponseId(response.getRequestId());
                        response.setStatusCode(HttpStatus.ALREADY_REPORTED.value());
                        response.setExtraData(new LinkedList<>());
                        response.setStatusDescription("Found existing task with id [" + existingTask.get().getProjectId() + "]");
                        log.info("Response [{}]", response);
                        return new ResponseEntity<>(response, HttpStatus.ALREADY_REPORTED);
                    }
                    Task task = Task.builder().project(projectRepo.findByProjectId(projectId).get()).title(taskRequest.getTitle()).dueDate(taskRequest.getDueDate()).description(taskRequest.getDescription()).build();
                    taskRepo.save(task);
                    response.setData(null);
                    response.setResponseId(UUID.randomUUID().toString());
                    response.setResponseId(response.getRequestId());
                    response.setStatusCode(HttpStatus.OK.value());
                    response.setExtraData(new LinkedList<>());
                    response.setStatusDescription("Successfully project  Id [" + task.getId() + "]");
                    log.info("Response [{}]", response);
                    return new ResponseEntity<>(response, HttpStatus.OK);

                }
            }
            response.setData(null);
            response.setResponseId(UUID.randomUUID().toString());
            response.setResponseId(response.getRequestId());
            response.setStatusCode(HttpStatus.BAD_REQUEST.value());
            response.setExtraData(new LinkedList<>());
            response.setStatusDescription("Failed");
            response.setError(List.of(new com.sebamutuku.sebastianmutukuspringtest.dto.responses.Error(HttpStatus.BAD_REQUEST.value(), "Failed ")));
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);

        } catch (Exception e) {
            response.setData(null);
            response.setResponseId(UUID.randomUUID().toString());
            response.setResponseId(response.getRequestId());
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setExtraData(new LinkedList<>());
            response.setStatusDescription("Failed with error [" + e.getMessage() + "]");
            response.setError(List.of(new com.sebamutuku.sebastianmutukuspringtest.dto.responses.Error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Failed ")));
            log.error("Response [{}]", response);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<BaseResponse<Page<ProjectResponse>>> getAll(Pageable pageable) {
        Page<Project> projects = projectRepo.findAll(pageable);
        BaseResponse<Page<ProjectResponse>> response = new BaseResponse<>();
        response.setData(projects.map(project -> ProjectResponse.builder().name(project.getName()).description(project.getDescription()).build()));
        response.setResponseId(UUID.randomUUID().toString());
        response.setResponseId(response.getRequestId());
        response.setStatusCode(HttpStatus.OK.value());
        response.setExtraData(new LinkedList<>());
        response.setStatusDescription("Found " + projects.getTotalElements() + " projects");
        return new ResponseEntity<>(response, HttpStatus.OK);


    }

    @Override
    public ResponseEntity<BaseResponse<Page<TaskResponse>>> fetchProjectTasks(Pageable pageable, String projectId, String status, Date dueDate) {
        List<TaskResponse> listOfTasks = new LinkedList<>();
        BaseResponse<Page<TaskResponse>> response = new BaseResponse<>();
        Page<Task> tasks = taskRepo.findByProjectIdAndFilters(projectId, status, dueDate, pageable);
        tasks.forEach(task -> listOfTasks.add(TaskResponse.builder().title(task.getTitle()).description(task.getDescription()).dueDate(task.getDueDate()).status(task.getStatus().name()).build()));
        Page<TaskResponse> pagedTasks = Utils.pageData(listOfTasks, pageable);
        response.setData(pagedTasks);
        response.setResponseId(UUID.randomUUID().toString());
        response.setResponseId(response.getRequestId());
        response.setStatusCode(HttpStatus.OK.value());
        response.setExtraData(new LinkedList<>());
        response.setStatusDescription("Found " + pagedTasks.getTotalElements() + " projects");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<BaseResponse<Page<ProjectSummaryResponse>>> getProjectSummary(Pageable pageable) {
        List<Project> projects = projectRepo.findAll();
        List<ProjectSummaryResponse> projectSummaryResponses = projects.stream().map(project -> {
            List<Task> tasks = taskRepo.findByProject(project);
            Map<String, Long> taskCountByStatus = new HashMap<>();
            tasks.forEach(task -> {
                taskCountByStatus.merge(task.getStatus().name(), 1L, Long::sum);
            });
            return new ProjectSummaryResponse(project.getProjectId().toString(), project.getName(), taskCountByStatus);
        }).toList();
        BaseResponse<Page<ProjectSummaryResponse>> response = new BaseResponse<>();
        Page<ProjectSummaryResponse> pagedProjects = Utils.pageData(projectSummaryResponses, pageable);
        response.setData(pagedProjects);
        response.setResponseId(UUID.randomUUID().toString());
        response.setResponseId(response.getRequestId());
        response.setStatusCode(HttpStatus.OK.value());
        response.setExtraData(new LinkedList<>());
        response.setStatusDescription("Found " + pagedProjects.getTotalElements() + " projects");
        return new ResponseEntity<>(response, HttpStatus.OK);

    }
}
