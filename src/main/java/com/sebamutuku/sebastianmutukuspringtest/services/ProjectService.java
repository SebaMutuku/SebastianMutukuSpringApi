package com.sebamutuku.sebastianmutukuspringtest.services;

import com.sebamutuku.sebastianmutukuspringtest.dto.requests.BaseRequest;
import com.sebamutuku.sebastianmutukuspringtest.dto.requests.ProjectRequest;
import com.sebamutuku.sebastianmutukuspringtest.dto.requests.TaskRequest;
import com.sebamutuku.sebastianmutukuspringtest.dto.responses.BaseResponse;
import com.sebamutuku.sebastianmutukuspringtest.dto.responses.ProjectResponse;
import com.sebamutuku.sebastianmutukuspringtest.dto.responses.ProjectSummaryResponse;
import com.sebamutuku.sebastianmutukuspringtest.dto.responses.TaskResponse;
import java.util.Date;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public abstract class ProjectService {
    public abstract ResponseEntity<BaseResponse<ProjectResponse>> create(BaseRequest<ProjectRequest> request);

    public abstract ResponseEntity<BaseResponse<ProjectResponse>> findByProjectId(String projectId);

    public abstract ResponseEntity<BaseResponse<TaskResponse>> addTasks(BaseRequest<TaskRequest> request, String projectId);

    public abstract ResponseEntity<BaseResponse<Page<ProjectResponse>>> getAll(Pageable pageable);

    public abstract ResponseEntity<BaseResponse<Page<TaskResponse>>> fetchProjectTasks(Pageable pageable, String projectId, String status, Date dueDate);

    public abstract ResponseEntity<BaseResponse<Page<ProjectSummaryResponse>>> getProjectSummary(Pageable pageable);
}
