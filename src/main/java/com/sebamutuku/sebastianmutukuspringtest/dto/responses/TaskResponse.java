package com.sebamutuku.sebastianmutukuspringtest.dto.responses;

import java.util.Date;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
public class TaskResponse {
    private String title;
    private String description;
    private String status;
    private Date dueDate;
}
