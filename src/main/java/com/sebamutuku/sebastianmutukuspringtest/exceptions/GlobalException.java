package com.sebamutuku.sebastianmutukuspringtest.exceptions;


import com.sebamutuku.sebastianmutukuspringtest.dto.responses.BaseResponse;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@Slf4j
public class GlobalException {


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<BaseResponse<?>> handleValidationErrors(Exception ex) {
        BaseResponse<?> response = new BaseResponse<>();
        log.error(ex.getMessage(), ex);
        response.setData(null);
        response.setResponseId(UUID.randomUUID().toString());
        response.setResponseId(response.getRequestId());
        response.setStatusCode(HttpStatus.BAD_REQUEST.value());
        response.setExtraData(new LinkedList<>());
        response.setStatusDescription("Failed with validation errors");
        response.setError(List.of(new com.sebamutuku.sebastianmutukuspringtest.dto.responses.Error(HttpStatus.BAD_REQUEST.value(), "Failed with error [" + ex + "]")));
        return ResponseEntity.badRequest().body(response);

    }
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<BaseResponse<?>> handleOtherExceptions(Exception ex) {
        ex.printStackTrace();
        log.error(ex.getMessage(), ex);
        BaseResponse<?> response = new BaseResponse<>();
        response.setData(null);
        response.setResponseId(UUID.randomUUID().toString());
        response.setResponseId(response.getRequestId());
        response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.setExtraData(new LinkedList<>());
        response.setStatusDescription("Failed with errors [" + ex.getMessage() + "]");
        response.setError(List.of(new com.sebamutuku.sebastianmutukuspringtest.dto.responses.Error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Failed with error  [" + ex + "]")));
        return ResponseEntity.internalServerError().body(response);
    }
}
