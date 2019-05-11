package com.mtemnohud.dbuilder.service;

import com.mtemnohud.dbuilder.component.Validator;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

public class BaseService<T extends CrudRepository> {

    protected final Validator validator;

    protected final T repository;

    public BaseService(Validator validator, T repository) {
        this.validator = validator;
        this.repository = repository;
    }

}
