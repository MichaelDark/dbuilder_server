package com.mtemnohud.dbuilder.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateNumberValueRequest {

    private Long buildingTaskId;

    private Long numberCriteriaId;

    private Double value;

}
