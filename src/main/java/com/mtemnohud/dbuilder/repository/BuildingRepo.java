package com.mtemnohud.dbuilder.repository;

import com.mtemnohud.dbuilder.model.entity.Building;
import com.mtemnohud.dbuilder.model.entity.criterion.NumberCriteria;
import org.springframework.data.repository.CrudRepository;

public interface BuildingRepo extends CrudRepository<Building, Long> {

}
