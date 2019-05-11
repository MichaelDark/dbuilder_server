package com.mtemnohud.dbuilder.repository;

import com.mtemnohud.dbuilder.model.entity.Building;
import com.mtemnohud.dbuilder.model.entity.Company;
import org.springframework.data.repository.CrudRepository;

public interface CompanyRepo extends CrudRepository<Company, Long> {

}

