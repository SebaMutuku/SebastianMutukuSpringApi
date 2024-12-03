package com.sebamutuku.sebastianmutukuspringtest.dto.requests;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;



@Data
@Builder
@ToString
public class BaseRequest<T> {
    @Valid
    @NotNull(message = "Request ID is required")
    @NotEmpty(message = "Request ID is required")
    private String requestId;
    @Valid
    @NotNull(message = "data is required")
    private T data;
    @Valid
    @NotNull(message = "extraData is required")
    private List<ExtraData> extraData;
}

