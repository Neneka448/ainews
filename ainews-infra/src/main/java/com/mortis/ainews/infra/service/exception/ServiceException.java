package com.mortis.ainews.infra.service.exception;

public class ServiceException extends RuntimeException {
    private final int code;

    public ServiceException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
    }

    public int getCode() {
        return code;
    }
}
