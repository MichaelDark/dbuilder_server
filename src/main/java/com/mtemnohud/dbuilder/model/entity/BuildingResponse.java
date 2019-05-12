package com.mtemnohud.dbuilder.model.entity;

import lombok.Data;

import java.sql.Date;

@Data
public
class BuildingResponse {

    private Long id;
    private String name;
    private String description;
    private Long buildingTasksCount;
    private Date createdAt;
    private Date updatedAt;

    public BuildingResponse(Building building) {
        id = building.getId();
        name = building.getName();
        description = building.getDescription();
        buildingTasksCount = (long) building.getBuildingTasks().size();
        createdAt = building.getCreatedAt();
        updatedAt = building.getUpdatedAt();
    }

}
