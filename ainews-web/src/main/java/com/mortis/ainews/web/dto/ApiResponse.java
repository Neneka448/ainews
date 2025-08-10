package com.mortis.ainews.web.dto;

import com.mortis.ainews.infra.service.exception.ErrorCode;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse<T> {
    private int code;
    private String message;
    private T data;

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(ErrorCode.SUCCESS.getCode(), ErrorCode.SUCCESS.getMessage(), data);
    }

    public static <T> ApiResponse<PageResult<T>> success(PageResult<T> pageResult) {
        return new ApiResponse<>(ErrorCode.SUCCESS.getCode(), ErrorCode.SUCCESS.getMessage(), pageResult);
    }

    public static <T> ApiResponse<T> error(ErrorCode errorCode) {
        return new ApiResponse<>(errorCode.getCode(), errorCode.getMessage(), null);
    }

    /*
     * 客户端校验错误用
     */
    public static <T> ApiResponse<T> error(String message) {
        return new ApiResponse<>(ErrorCode.PARAMS_VALIDATION_ERROR.getCode(), message, null);
    }

    public static <T> ApiResponse<T> error(Exception e) {
        return new ApiResponse<>(ErrorCode.COMMON_ERROR.getCode(), e.getMessage(), null);
    }
}
