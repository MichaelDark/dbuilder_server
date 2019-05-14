package com.mtemnohud.dbuilder.service.impl.secured.impl;


import com.mtemnohud.dbuilder.component.Validator;
import com.mtemnohud.dbuilder.exception.BadRequestException;
import com.mtemnohud.dbuilder.model.entity.values.NumberValue;
import com.mtemnohud.dbuilder.model.request.CreateNumberValueRequest;
import com.mtemnohud.dbuilder.model.response.StatusResponse;
import com.mtemnohud.dbuilder.repository.*;
import com.mtemnohud.dbuilder.service.impl.secured.BaseSecuredService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NumberValueService extends BaseSecuredService {

    private final BuildingTaskRepo buildingTaskRepo;
    private final NumberValueRepo numberValueRepo;
    private final NumberCriteriaRepo numberCriteriaRepo;

    @Autowired
    public NumberValueService(Validator validator, UserRepository userRepository, BuildingTaskRepo buildingTaskRepo, NumberValueRepo numberValueRepo, NumberCriteriaRepo numberCriteriaRepo) {
        super(validator, userRepository);
        this.buildingTaskRepo = buildingTaskRepo;
        this.numberValueRepo = numberValueRepo;
        this.numberCriteriaRepo = numberCriteriaRepo;
    }

    public NumberValue createNumberValue(CreateNumberValueRequest request) {
        if (request.getBuildingTaskId() == null) {
            throw new BadRequestException("device id can not be empty");
        }

        NumberValue numberValue = new NumberValue();

        return numberValueRepo.save(numberValue);
    }

    public StatusResponse deleteNumberValueByBuilding(Long buildingTaskId, Long numberCriteriaId) {
        if (!numberCriteriaRepo.findById(numberCriteriaId).isPresent()) {
            throw new BadRequestException("no number criteria with such id");
        }
        if (!buildingTaskRepo.findById(buildingTaskId).isPresent()) {
            throw new BadRequestException("no building with such id");
        }


        return StatusResponse.success();
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

    public List<NumberValue> getByBuildingTask(Long buildingTaskId) {
        if (buildingTaskId == null) {
            throw new BadRequestException("invalid id");
        }
        if (!buildingTaskRepo.findById(buildingTaskId).isPresent()) {
            throw new BadRequestException("no building task with such id");
        }

        return buildingTaskRepo.findById(buildingTaskId).get().getNumberValues();
    }

}
