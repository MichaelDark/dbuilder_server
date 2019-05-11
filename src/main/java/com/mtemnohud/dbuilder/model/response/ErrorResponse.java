package com.mtemnohud.dbuilder.model.response;

import lombok.Data;

@Data
public class ErrorResponse {

    private Integer status;

    private String message;

    private Long timestamp;

    private String path;

    public ErrorResponse() {
        this.timestamp = System.currentTimeMillis();
    }

    protected ErrorResponse(final String message, final Integer status, final String path) {
        this.message = message;
        this.status = status;
        this.path = path;
        this.timestamp = System.currentTimeMillis();
    }

    public static ErrorResponse of(final String message, final Integer status, final String path) {
        return new ErrorResponse(message, status, path);
    }

}
