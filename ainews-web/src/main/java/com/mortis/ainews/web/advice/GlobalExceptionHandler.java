package com.mortis.ainews.web.advice;

import com.mortis.ainews.infra.service.exception.ServiceException;
import com.mortis.ainews.web.dto.ApiResponse;
import com.mortis.ainews.web.exception.ParamsValidationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import lombok.extern.slf4j.Slf4j;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<ApiResponse<Void>> handleServiceException(ServiceException e) {
        // 记录业务异常日志，包含错误码和错误信息
        log.error("Service exception occurred - ErrorCode: {}, Message: {}",
                e.getCode(), e.getMessage(), e);

        // 返回脱敏的错误信息，不暴露内部实现细节
        return new ResponseEntity<>(ApiResponse.error(e.getCode()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({ IllegalArgumentException.class, ParamsValidationException.class })
    public ResponseEntity<ApiResponse<Void>> handleBadRequest(Exception e) {
        // 获取请求信息用于日志记录
        String requestInfo = getRequestInfo();

        // 记录参数验证异常
        log.warn("Parameter validation failed - Exception: {}, Message: {}, Request: {}",
                e.getClass().getSimpleName(), e.getMessage(), requestInfo);

        // 返回客户端错误信息
        return new ResponseEntity<>(ApiResponse.error(e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleUnknown(Exception e) {
        // 获取请求信息
        String requestInfo = getRequestInfo();

        // 记录未知异常的详细信息
        log.error("Unexpected exception occurred - Exception: {}, Message: {}, Request: {}",
                e.getClass().getName(), e.getMessage(), requestInfo, e);

        // 返回通用错误信息，不暴露具体异常内容
        return new ResponseEntity<>(ApiResponse.error("系统内部错误，请稍后重试"), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * 获取请求信息用于日志记录
     */
    private String getRequestInfo() {
        try {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder
                    .getRequestAttributes();
            if (attributes != null) {
                HttpServletRequest request = attributes.getRequest();
                return String.format("URI: %s, Method: %s, IP: %s",
                        request.getRequestURI(),
                        request.getMethod(),
                        getClientIpAddress(request));
            }
        } catch (Exception e) {
            log.debug("Failed to get request info", e);
        }
        return "Unknown request";
    }

    /**
     * 获取客户端真实IP地址
     */
    private String getClientIpAddress(HttpServletRequest request) {
        String xForwardedFor = request.getHeader("X-Forwarded-For");
        if (xForwardedFor != null && !xForwardedFor.isEmpty() && !"unknown".equalsIgnoreCase(xForwardedFor)) {
            return xForwardedFor.split(",")[0].trim();
        }

        String xRealIp = request.getHeader("X-Real-IP");
        if (xRealIp != null && !xRealIp.isEmpty() && !"unknown".equalsIgnoreCase(xRealIp)) {
            return xRealIp;
        }

        return request.getRemoteAddr();
    }
}
