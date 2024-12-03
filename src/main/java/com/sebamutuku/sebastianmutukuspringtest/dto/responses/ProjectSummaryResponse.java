package com.sebamutuku.sebastianmutukuspringtest.dto.responses;

import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProjectSummaryResponse {
    private String projectId;
    private String projectName;
    private Map<String, Long> taskCountByStatus;
}