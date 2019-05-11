package com.mtemnohud.dbuilder.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatusResponse {

    private StatusType status;

    private String message;

    public static StatusResponse success() {
        return new StatusResponse(StatusType.OK, "");
    }

    public static StatusResponse success(String message) {
        return new StatusResponse(StatusType.OK, message);
    }

    public static StatusResponse failure(String message) {
        return new StatusResponse(StatusType.ERROR, message);
    }
}
