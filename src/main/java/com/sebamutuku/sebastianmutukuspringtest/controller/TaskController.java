package com.sebamutuku.sebastianmutukuspringtest.controller;

import com.sebamutuku.sebastianmutukuspringtest.dto.requests.BaseRequest;
import com.sebamutuku.sebastianmutukuspringtest.dto.requests.TaskRequest;
import com.sebamutuku.sebastianmutukuspringtest.dto.responses.BaseResponse;
import com.sebamutuku.sebastianmutukuspringtest.dto.responses.TaskResponse;
import com.sebamutuku.sebastianmutukuspringtest.services.TaskService;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/tasks", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)

public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PutMapping(value = "/{taskId}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BaseResponse<TaskResponse>> createCustomer(@Valid @RequestBody BaseRequest<TaskRequest> apiRequest, @PathVariable String taskId) {
        return taskService.addOrUpdate(apiRequest, taskId);
    }

    @DeleteMapping(value = "/{taskId}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BaseResponse<Void>> fetchAllProjects(@PathVariable String taskId) {
        return taskService.deletedById(taskId);
    }
}
