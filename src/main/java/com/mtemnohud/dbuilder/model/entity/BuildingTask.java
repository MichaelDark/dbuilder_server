package com.mtemnohud.dbuilder.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mtemnohud.dbuilder.model.request.CreateBuildingTaskRequest;
import com.mtemnohud.dbuilder.model.user.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.sql.Date;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "building_tasks")
public class BuildingTask {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "building_id")
    @JsonIgnore
    private Building building;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_entity_id")
    @JsonIgnore
    private UserEntity user;

    @Column(name = "name")
    private String name;

    @Column(name = "due_date")
    private Date dueDate;

    @Column(name = "created_at")
    @CreationTimestamp
    private Date createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private Date updatedAt;

    public static BuildingTask createFromRequest(@NonNull Building building, CreateBuildingTaskRequest request) {
        BuildingTask object = new BuildingTask();
        object.setName(request.getName());
        object.setDueDate(request.getDueDate());
        object.setBuilding(building);
        return object;
    }

}
