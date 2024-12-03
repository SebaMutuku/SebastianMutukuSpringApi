package com.sebamutuku.sebastianmutukuspringtest.dto.responses;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Error {
    private String errorMessage;
    private Integer errorCode;

    public Error(Integer errorCode, String errorMessage) {
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
    }
}