package com.sebamutuku.sebastianmutukuspringtest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sebamutuku.sebastianmutukuspringtest.dto.requests.BaseRequest;
import com.sebamutuku.sebastianmutukuspringtest.dto.requests.TaskRequest;
import com.sebamutuku.sebastianmutukuspringtest.services.TaskService;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {TaskController.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class TaskControllerTest {
    @Autowired
    private TaskController taskController;

    @MockitoBean
    private TaskService taskService;

    @Test
    void testCreateCustomer() throws Exception {
        when(taskService.addOrUpdate(Mockito.any(), Mockito.any())).thenReturn(new ResponseEntity<>(HttpStatusCode.valueOf(200)));
        TaskRequest taskRequest = new TaskRequest();
        taskRequest.setDescription("The characteristics of someone or something");
        taskRequest.setDueDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        taskRequest.setProjectId(UUID.randomUUID().toString());
        taskRequest.setStatus(TaskRequest.Status.TO_DO);
        taskRequest.setTitle("Dr");
        BaseRequest.BaseRequestBuilder<TaskRequest> builderResult = BaseRequest.builder();
        BaseRequest.BaseRequestBuilder<TaskRequest> dataResult = builderResult.data(taskRequest);
        BaseRequest<TaskRequest> buildResult = dataResult.extraData(new ArrayList<>()).requestId("42").build();
        String content = (new ObjectMapper()).writeValueAsString(buildResult);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/v1/tasks/{taskId}", "42").contentType(MediaType.APPLICATION_JSON).content(content);
        MockMvcBuilders.standaloneSetup(taskController).build().perform(requestBuilder).andExpect(MockMvcResultMatchers.status().isOk());
    }


    @Test
    void testFetchAllProjects() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/v1/tasks/{taskId}", "42");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(taskController).build().perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(415));
    }
}
