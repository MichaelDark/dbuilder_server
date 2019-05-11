package com.mtemnohud.dbuilder.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserRequest {

    private String password;

    private String username;

    private String name;

    private String surname;

    private Boolean isCompanyOwner;

}