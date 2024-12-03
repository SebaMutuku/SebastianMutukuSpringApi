package com.sebamutuku.sebastianmutukuspringtest.services;

import com.sebamutuku.sebastianmutukuspringtest.dto.requests.BaseRequest;
import com.sebamutuku.sebastianmutukuspringtest.dto.requests.TaskRequest;
import com.sebamutuku.sebastianmutukuspringtest.dto.responses.BaseResponse;
import com.sebamutuku.sebastianmutukuspringtest.dto.responses.TaskResponse;
import org.springframework.http.ResponseEntity;

public abstract class TaskService {
    public abstract ResponseEntity<BaseResponse<TaskResponse>> addOrUpdate(BaseRequest<TaskRequest> request, String taskId);

    public abstract ResponseEntity<BaseResponse<Void>> deletedById(String taskId);

}
