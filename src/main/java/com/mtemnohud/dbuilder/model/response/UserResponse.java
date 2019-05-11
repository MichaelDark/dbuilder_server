package com.mtemnohud.dbuilder.model.response;

import com.mtemnohud.dbuilder.model.entity.Company;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserResponse {

    private Long id;

    private boolean enabled;

    private String username;

    private String name;

    private String surname;

    private Boolean isCompanyOwner;

    private Company company;


}
