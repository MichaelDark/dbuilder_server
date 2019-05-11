package com.mtemnohud.dbuilder.repository;

import com.mtemnohud.dbuilder.model.entity.criterion.NumberCriteria;
import com.mtemnohud.dbuilder.model.entity.values.NumberValue;
import org.springframework.data.repository.CrudRepository;

public interface NumberValueRepo extends CrudRepository<NumberValue, Long> {

}
