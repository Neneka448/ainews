package com.mortis.ainews.infra.service.exception;

public class ServiceException extends RuntimeException {
    private final ErrorCode code;

    public ServiceException(ErrorCode code) {
        super(code.getMessage());
        this.code = code;
    }

    public ErrorCode getCode() {
        return code;
    }
}
