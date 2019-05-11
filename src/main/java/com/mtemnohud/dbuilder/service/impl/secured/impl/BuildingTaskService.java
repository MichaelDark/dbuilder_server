package com.mtemnohud.dbuilder.service.impl.secured.impl;


import com.google.common.collect.Lists;
import com.mtemnohud.dbuilder.component.Validator;
import com.mtemnohud.dbuilder.exception.BadRequestException;
import com.mtemnohud.dbuilder.model.entity.BuildingTask;
import com.mtemnohud.dbuilder.model.request.CreateBuildingTaskRequest;
import com.mtemnohud.dbuilder.model.response.StatusResponse;
import com.mtemnohud.dbuilder.repository.BuildingTaskRepo;
import com.mtemnohud.dbuilder.repository.UserRepository;
import com.mtemnohud.dbuilder.service.impl.secured.BaseSecuredService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BuildingTaskService extends BaseSecuredService {

    private final BuildingTaskRepo buildingTaskRepo;

    @Autowired
    public BuildingTaskService(Validator validator, UserRepository userRepository, BuildingTaskRepo buildingTaskRepo) {
        super(validator, userRepository);
        this.buildingTaskRepo = buildingTaskRepo;
    }

    public BuildingTask createBuildingTask(CreateBuildingTaskRequest request) {
        if (request.getBuildingId() == null) {
            throw new BadRequestException("device id can not be empty");
        }

        BuildingTask buildingTask = new BuildingTask();

        return buildingTaskRepo.save(buildingTask);
    }

    public BuildingTask updateBuildingTask(BuildingTask updatedBuildingTask) {
        if (updatedBuildingTask == null) {
            throw new BadRequestException("invalid data");
        }

        BuildingTask BuildingTask = new BuildingTask();

        return buildingTaskRepo.save(BuildingTask);
    }

    public StatusResponse deleteBuildingTask(Long buildingTaskId) {
        if (buildingTaskId == null) {
            throw new BadRequestException("invalid id");
        }

        if (!buildingTaskRepo.findById(buildingTaskId).isPresent()) {
            throw new BadRequestException("no building task with such id");
        }

        buildingTaskRepo.deleteById(buildingTaskId);

        return StatusResponse.success();
    }

    public List<BuildingTask> getAll() {
        return Lists.newArrayList(buildingTaskRepo.findAll());
    }
}
