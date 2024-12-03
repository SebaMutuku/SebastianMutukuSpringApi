package com.sebamutuku.sebastianmutukuspringtest.dto.requests;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.Date;
import java.util.UUID;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class TaskRequest {
    @NotNull(message = "Project ID is required")
    private UUID projectId;

    @NotNull(message = "Title cannot be null")
    @NotEmpty(message = "Title must be between 1 and 255 characters")
    private String title;

    @NotNull(message = "Description cannot be null")
    @NotEmpty(message = "Description must be between 1 and 500 characters")
    private String description;

    @NotNull(message = "Status cannot be null")
    private Status status;

    @NotNull(message = "Due date cannot be null")
    @FutureOrPresent(message = "Due date must be in the present or future")
    private Date dueDate;

    public enum Status {
        TO_DO, IN_PROGRESS, DONE
    }
}
