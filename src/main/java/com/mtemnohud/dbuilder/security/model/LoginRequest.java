package com.mtemnohud.dbuilder.security.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginRequest {

    private String username;

    private String password;

}
