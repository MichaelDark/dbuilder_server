package com.mtemnohud.dbuilder.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateNumberCriteriaRequest {

    private String name;

    private String unit;

    private Double minValue;

    private Double maxValue;

}
