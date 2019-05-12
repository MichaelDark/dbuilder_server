package com.mtemnohud.dbuilder.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mtemnohud.dbuilder.model.user.UserEntity;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Date;

@Data
public class BuildingTaskResponse {

    private Long id;
    private String name;
    private Date dueDate;
    private Date createdAt;
    private Date updatedAt;

    public BuildingTaskResponse(BuildingTask buildingTask) {
        id = buildingTask.getId();
        name = buildingTask.getName();
        dueDate = buildingTask.getDueDate();
        createdAt = buildingTask.getCreatedAt();
        updatedAt = buildingTask.getUpdatedAt();
    }

}
