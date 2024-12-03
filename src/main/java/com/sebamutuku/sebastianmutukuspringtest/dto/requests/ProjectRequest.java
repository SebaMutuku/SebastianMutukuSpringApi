package com.sebamutuku.sebastianmutukuspringtest.dto.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ProjectRequest {
    @NotNull(message = "Name cannot be blank")
    @NotEmpty(message = "Name must be between 2 and 100 characters")
    private String name;

    @NotBlank(message = "Description cannot be blank")
    @NotEmpty(message = "Description must be between 10 and 500 characters")
    private String description;
}
