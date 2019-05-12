package com.mtemnohud.dbuilder.repository;

import com.mtemnohud.dbuilder.model.entity.Building;
import com.mtemnohud.dbuilder.model.entity.criterion.NumberCriteria;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

public interface BuildingRepo extends CrudRepository<Building, Long> {

    @Transactional
    void deleteById(Long id);

}
