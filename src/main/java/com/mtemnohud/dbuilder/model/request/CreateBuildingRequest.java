package com.mtemnohud.dbuilder.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateBuildingRequest {

    private String name;

    private String description;

}

