package com.mtemnohud.dbuilder.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateBuildingTaskRequest {

    private Long buildingId;

    private String name;

}
