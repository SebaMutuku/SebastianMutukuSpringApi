package com.sebamutuku.sebastianmutukuspringtest.services.serviceimpl;

import com.sebamutuku.sebastianmutukuspringtest.dto.responses.BaseResponse;
import com.sebamutuku.sebastianmutukuspringtest.dto.responses.ProjectResponse;
import com.sebamutuku.sebastianmutukuspringtest.entities.Project;
import com.sebamutuku.sebastianmutukuspringtest.repos.ProjectRepo;
import com.sebamutuku.sebastianmutukuspringtest.repos.TaskRepo;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

class ProjectServiceImplTest {

    @Mock
    private ProjectRepo projectRepo;

    @Mock
    private TaskRepo taskRepo;

    @InjectMocks
    private ProjectServiceImpl projectService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void testFindByProjectId_Success() {
        String projectId = UUID.randomUUID().toString();
        Project project = new Project();
        project.setProjectId(UUID.fromString(projectId));
        project.setName("Test Project");
        project.setDescription("Test Description");

        when(projectRepo.findByProjectId(projectId)).thenReturn(Optional.of(project));

        ResponseEntity<BaseResponse<ProjectResponse>> response = projectService.findByProjectId(projectId);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Success", response.getBody().getStatusDescription());
    }

    @Test
    void testFindByProjectId_NotFound() {
        String projectId = UUID.randomUUID().toString();

        when(projectRepo.findByProjectId(projectId)).thenReturn(Optional.empty());

        ResponseEntity<BaseResponse<ProjectResponse>> response = projectService.findByProjectId(projectId);

        assertNotNull(response);
        assertEquals(404, response.getStatusCodeValue());
        assertEquals("Project with id [" + projectId + "] does not exist", response.getBody().getStatusDescription());
    }

    @Test
    void testGetAllProjects() {
        Project project1 = new Project();
        project1.setName("Project 1");
        project1.setDescription("Description 1");

        Project project2 = new Project();
        project2.setName("Project 2");
        project2.setDescription("Description 2");

        Page<Project> projectPage = new PageImpl<>(List.of(project1, project2));
        when(projectRepo.findAll(any(PageRequest.class))).thenReturn(projectPage);

        ResponseEntity<BaseResponse<Page<ProjectResponse>>> response = projectService.getAll(PageRequest.of(0, 10));

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(2, response.getBody().getData().getTotalElements());
    }
}
