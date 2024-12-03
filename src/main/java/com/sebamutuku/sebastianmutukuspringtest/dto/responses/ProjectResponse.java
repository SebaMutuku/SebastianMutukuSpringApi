package com.sebamutuku.sebastianmutukuspringtest.dto.responses;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProjectResponse {
    private String name;
    private String description;
}
