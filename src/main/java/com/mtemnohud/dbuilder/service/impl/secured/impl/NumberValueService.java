package com.mtemnohud.dbuilder.service.impl.secured.impl;


import com.google.common.collect.Lists;
import com.mtemnohud.dbuilder.component.Validator;
import com.mtemnohud.dbuilder.exception.BadRequestException;
import com.mtemnohud.dbuilder.model.entity.values.NumberValue;
import com.mtemnohud.dbuilder.model.request.CreateNumberValueRequest;
import com.mtemnohud.dbuilder.model.response.StatusResponse;
import com.mtemnohud.dbuilder.repository.NumberValueRepo;
import com.mtemnohud.dbuilder.repository.UserRepository;
import com.mtemnohud.dbuilder.service.impl.secured.BaseSecuredService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NumberValueService extends BaseSecuredService {

    private final NumberValueRepo numberValueRepo;

    @Autowired
    public NumberValueService(Validator validator, UserRepository userRepository, NumberValueRepo numberValueRepo) {
        super(validator, userRepository);
        this.numberValueRepo = numberValueRepo;
    }

    public NumberValue createNumberValue(CreateNumberValueRequest request) {
        if (request.getBuildingTaskId() == null) {
            throw new BadRequestException("device id can not be empty");
        }

        NumberValue numberValue = new NumberValue();

        return numberValueRepo.save(numberValue);
    }

    public NumberValue updateNumberValue(NumberValue updatedNumberValue) {
        if (updatedNumberValue == null) {
            throw new BadRequestException("invalid data");
        }

        NumberValue NumberValue = new NumberValue();

        return numberValueRepo.save(NumberValue);
    }

    public StatusResponse deleteNumberValue(Long numberValueId) {
        if (numberValueId == null) {
            throw new BadRequestException("invalid id");
        }

        if (!numberValueRepo.findById(numberValueId).isPresent()) {
            throw new BadRequestException("no number value with such id");
        }

        numberValueRepo.deleteById(numberValueId);

        return StatusResponse.success();
    }

    public List<NumberValue> getAll() {
        return Lists.newArrayList(numberValueRepo.findAll());
    }
}
