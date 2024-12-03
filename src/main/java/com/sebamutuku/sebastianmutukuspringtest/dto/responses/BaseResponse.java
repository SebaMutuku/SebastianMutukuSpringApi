package com.sebamutuku.sebastianmutukuspringtest.dto.responses;

import com.sebamutuku.sebastianmutukuspringtest.dto.requests.ExtraData;
import java.util.List;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class BaseResponse<T> {
    private String responseId;
    private int statusCode;
    private String statusDescription;
    private String requestId;
    private T data;
    private List<Error> error;
    private List<ExtraData> extraData;
}

