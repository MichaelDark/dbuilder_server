package com.mtemnohud.dbuilder.security.jwt;

import lombok.Data;

@Data
public class JwtSettings {

    private Integer tokenExpirationMinutes;

    private String tokenIssuer;

    private String tokenSigningKey;

}
