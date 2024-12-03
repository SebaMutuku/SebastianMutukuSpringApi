package com.sebamutuku.sebastianmutukuspringtest.services.serviceimpl;

import com.sebamutuku.sebastianmutukuspringtest.dto.requests.BaseRequest;
import com.sebamutuku.sebastianmutukuspringtest.dto.requests.TaskRequest;
import com.sebamutuku.sebastianmutukuspringtest.dto.responses.BaseResponse;
import com.sebamutuku.sebastianmutukuspringtest.dto.responses.TaskResponse;
import com.sebamutuku.sebastianmutukuspringtest.entities.Task;
import com.sebamutuku.sebastianmutukuspringtest.repos.ProjectRepo;
import com.sebamutuku.sebastianmutukuspringtest.repos.TaskRepo;
import com.sebamutuku.sebastianmutukuspringtest.services.TaskService;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TaskServiceImpl extends TaskService {
    private final TaskRepo taskRepo;
    private final ProjectRepo projectRepo;

    @Autowired
    public TaskServiceImpl(TaskRepo taskRepo, ProjectRepo projectRepo) {
        this.taskRepo = taskRepo;
        this.projectRepo = projectRepo;
    }

    @Override
    public ResponseEntity<BaseResponse<TaskResponse>> addOrUpdate(BaseRequest<TaskRequest> request, String taskId) {
        BaseResponse<TaskResponse> response = new BaseResponse<>();
        log.info("received request [{}]", request);
        try {
            if (request != null) {
                TaskRequest taskRequest = request.getData();
                if (taskRequest != null) {
                    Optional<Task> existingTask = taskRepo.findById(UUID.fromString(taskId));
                    if (existingTask.isPresent()) {
                        Task task = existingTask.get();
                        task.setDescription(taskRequest.getDescription());
                        task.setTitle(taskRequest.getTitle());
                        task.setProject(task.getProject());
                        taskRepo.save(task);
                        TaskResponse taskResponse = existingTask.map(taskResp -> TaskResponse.builder().title(taskResp.getTitle()).description(taskResp.getDescription()).dueDate(taskResp.getDueDate()).status(taskResp.getStatus().name()).build()).get();
                        response.setData(taskResponse);
                        response.setResponseId(UUID.randomUUID().toString());
                        response.setResponseId(response.getRequestId());
                        response.setStatusCode(HttpStatus.ALREADY_REPORTED.value());
                        response.setExtraData(new LinkedList<>());
                        response.setStatusDescription("Successfully update Task with if [" + existingTask.get().getId() + "]");
                        log.info("Response [{}]", response);
                        return new ResponseEntity<>(response, HttpStatus.ALREADY_REPORTED);
                    }
                    Task newtTask = Task.builder().description(taskRequest.getDescription()).project(projectRepo.findByProjectId(taskRequest.getProjectId()).get()).dueDate(taskRequest.getDueDate()).title(taskRequest.getTitle()).build();
                    taskRepo.save(newtTask);
                    response.setData(TaskResponse.builder().status(newtTask.getStatus().name()).dueDate(newtTask.getDueDate()).title(newtTask.getTitle()).description(newtTask.getDescription()).build());
                    response.setResponseId(UUID.randomUUID().toString());
                    response.setResponseId(response.getRequestId());
                    response.setStatusCode(HttpStatus.OK.value());
                    response.setExtraData(new LinkedList<>());
                    response.setStatusDescription("Successfully created new Task");
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
    public ResponseEntity<BaseResponse<Void>> deletedById(String taskId) {
        BaseResponse<Void> response = new BaseResponse<>();
        log.info("received request [{}]", taskId);
        Optional<Task> existingTask = taskRepo.findById(UUID.fromString(taskId));
        if (existingTask.isPresent()) {
            taskRepo.delete(existingTask.get());
            response.setResponseId(UUID.randomUUID().toString());
            response.setResponseId(response.getRequestId());
            response.setStatusCode(HttpStatus.OK.value());
            response.setExtraData(new LinkedList<>());
            response.setStatusDescription("Success");
            log.info("Response [{}]", response);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        response.setStatusCode(HttpStatus.NOT_FOUND.value());
        response.setExtraData(new LinkedList<>());
        response.setStatusDescription("Not found ");
        log.info("Response [{}]", response);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
