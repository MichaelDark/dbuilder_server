package com.mtemnohud.dbuilder.security.api;


import com.mtemnohud.dbuilder.exception.BadRequestException;
import com.mtemnohud.dbuilder.exception.Unauthorized;
import com.mtemnohud.dbuilder.model.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@ControllerAdvice
class SecurityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public ErrorResponse handleException(AuthenticationException ex, HttpServletRequest request) {
        log.error("URI={}, {}", request.getRequestURI(), ex.getMessage());
        return ErrorResponse.of(ex.getMessage(), HttpStatus.UNAUTHORIZED.value(), request.getRequestURI());
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse handleException(BadRequestException ex, HttpServletRequest request) {
        log.error("URI={}, {}", request.getRequestURI(), ex.getMessage());
        return ErrorResponse.of(ex.getMessage(), HttpStatus.BAD_REQUEST.value(), request.getRequestURI());
    }

    @ExceptionHandler(Unauthorized.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public ErrorResponse handleException(Unauthorized ex, HttpServletRequest request) {
        log.error("URI={}, {}", request.getRequestURI(), ex.getMessage());
        return ErrorResponse.of(ex.getMessage(), HttpStatus.UNAUTHORIZED.value(), request.getRequestURI());
    }


}
