package com.mortis.ainews.infra.service.exception;

public enum ErrorCode {
    // 正常
    SUCCESS(0, "成功"),

    // 通用错误
    COMMON_ERROR(10001, "未知错误"),
    PARAMS_VALIDATION_ERROR(10002, "参数校验错误"),

    // UserService错误
    USER_NOT_FOUND(20001, "用户不存在"),
    USER_ALREADY_EXISTS(20002, "用户已存在"),

    // KeywordService错误
    KEYWORD_NOT_FOUND(30001, "关键字不存在"),
    KEYWORDS_LIST_EMPTY(30002, "关键字列表为空或关键词均不存在");

    private final int code;
    private final String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
