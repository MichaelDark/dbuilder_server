package com.mtemnohud.dbuilder.service.impl.secured.impl;


import com.mtemnohud.dbuilder.component.Validator;
import com.mtemnohud.dbuilder.exception.BadRequestException;
import com.mtemnohud.dbuilder.model.entity.BuildingTask;
import com.mtemnohud.dbuilder.model.entity.criterion.NumberCriteria;
import com.mtemnohud.dbuilder.model.entity.values.NumberValue;
import com.mtemnohud.dbuilder.model.request.CreateNumberValueRequest;
import com.mtemnohud.dbuilder.model.response.StatusResponse;
import com.mtemnohud.dbuilder.repository.*;
import com.mtemnohud.dbuilder.service.impl.secured.BaseSecuredService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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

        NumberValue numberValue = NumberValue.createFromRequest(request);

        BuildingTask task = buildingTaskRepo.findById(request.getBuildingTaskId()).get();
        NumberCriteria criteria = numberCriteriaRepo.findById(request.getNumberCriteriaId()).get();

        numberValue.setBuildingTask(task);
        numberValue.setNumberCriteria(criteria);
        numberValue = numberValueRepo.save(numberValue);

        task.getNumberValues().add(numberValue);
        criteria.getBuildingTasks().add(buildingTaskRepo.save(task));
        numberCriteriaRepo.save(criteria);

        return numberValueRepo.findFirstByBuildingTaskIdAndNumberCriteriaId(task.getId(), criteria.getId());
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public StatusResponse deleteNumberValueByBuilding(Long buildingTaskId, Long numberCriteriaId) {
        if (!numberCriteriaRepo.findById(numberCriteriaId).isPresent()) {
            throw new BadRequestException("no number criteria with such id");
        }
        if (!buildingTaskRepo.findById(buildingTaskId).isPresent()) {
            throw new BadRequestException("no building with such id");
        }


        return StatusResponse.success();
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public StatusResponse deleteNumberValue(Long numberValueId) {
        if (numberValueId == null) {
            throw new BadRequestException("invalid id");
        }

        if (!numberValueRepo.findById(numberValueId).isPresent()) {
            throw new BadRequestException("no number value with such id");
        }

        NumberValue value = numberValueRepo.findById(numberValueId).get();
        value.setNumberCriteria(null);
        value.setBuildingTask(null);
        numberValueRepo.delete(numberValueRepo.save(value));

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
