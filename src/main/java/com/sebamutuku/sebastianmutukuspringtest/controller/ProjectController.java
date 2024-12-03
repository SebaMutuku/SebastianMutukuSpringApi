package com.sebamutuku.sebastianmutukuspringtest.controller;

import com.sebamutuku.sebastianmutukuspringtest.dto.requests.BaseRequest;
import com.sebamutuku.sebastianmutukuspringtest.dto.requests.ProjectRequest;
import com.sebamutuku.sebastianmutukuspringtest.dto.requests.TaskRequest;
import com.sebamutuku.sebastianmutukuspringtest.dto.responses.BaseResponse;
import com.sebamutuku.sebastianmutukuspringtest.dto.responses.ProjectResponse;
import com.sebamutuku.sebastianmutukuspringtest.dto.responses.ProjectSummaryResponse;
import com.sebamutuku.sebastianmutukuspringtest.dto.responses.TaskResponse;
import com.sebamutuku.sebastianmutukuspringtest.services.ProjectService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.Date;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/projects", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
public class ProjectController {
    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BaseResponse<ProjectResponse>> createCustomer(@Valid @RequestBody BaseRequest<ProjectRequest> apiRequest) {
        return projectService.create(apiRequest);
    }

    @GetMapping(value = "/findAll", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BaseResponse<Page<ProjectResponse>>> fetchAllProjects(Pageable pageable) {
        return projectService.getAll(pageable);
    }


    @GetMapping(value = "/{projectId}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BaseResponse<ProjectResponse>> findById(@NotEmpty(message = "Project Id cannot be empty") @NotNull(message = "Project Id cannot be null") @PathVariable String projectId) {
        return projectService.findByProjectId(projectId);
    }

    @PostMapping(value = "/{projectId}/tasks", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BaseResponse<TaskResponse>> addTasks(@Valid @RequestBody BaseRequest<TaskRequest> apiRequest, @PathVariable String projectId) {
        return projectService.addTasks(apiRequest, projectId);
    }

    @GetMapping(value = "/{projectId}/tasks", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BaseResponse<Page<TaskResponse>>> findAllProjectTasks(Pageable pageable, @PathVariable String projectId, @RequestParam(required = false) String status, @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date dueDate) {
        return projectService.fetchProjectTasks(pageable, projectId, status, dueDate);
    }


    @GetMapping(value = "/projects/summary", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BaseResponse<Page<ProjectSummaryResponse>>> getProjectsSummary(Pageable pageable) {
        return projectService.getProjectSummary(pageable);
    }
}
