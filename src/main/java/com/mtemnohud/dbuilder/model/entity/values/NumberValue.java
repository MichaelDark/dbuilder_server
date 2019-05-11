package com.mtemnohud.dbuilder.model.entity.values;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mtemnohud.dbuilder.model.entity.criterion.NumberCriteria;
import com.mtemnohud.dbuilder.model.entity.BuildingTask;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Date;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "number_criterion")
public class NumberValue {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private Double value;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "building_task_id")
    @JsonIgnore
    private BuildingTask buildingTask;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "number_criteria_id")
    @JsonIgnore
    private NumberCriteria numberCriteria;

    @Column(name = "created_at")
    @CreationTimestamp
    private Date createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private Date updatedAt;

}
