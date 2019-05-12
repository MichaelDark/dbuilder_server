package com.mtemnohud.dbuilder.service.impl.secured.impl;


import com.google.common.collect.Lists;
import com.mtemnohud.dbuilder.component.Validator;
import com.mtemnohud.dbuilder.exception.BadRequestException;
import com.mtemnohud.dbuilder.model.entity.Building;
import com.mtemnohud.dbuilder.model.entity.BuildingResponse;
import com.mtemnohud.dbuilder.model.entity.BuildingTask;
import com.mtemnohud.dbuilder.model.entity.BuildingTaskResponse;
import com.mtemnohud.dbuilder.model.request.CreateBuildingTaskRequest;
import com.mtemnohud.dbuilder.model.response.StatusResponse;
import com.mtemnohud.dbuilder.model.user.UserEntity;
import com.mtemnohud.dbuilder.repository.BuildingRepo;
import com.mtemnohud.dbuilder.repository.BuildingTaskRepo;
import com.mtemnohud.dbuilder.repository.UserRepository;
import com.mtemnohud.dbuilder.service.impl.secured.BaseSecuredService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.beans.Transient;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BuildingTaskService extends BaseSecuredService {

    private final BuildingRepo buildingRepo;
    private final BuildingTaskRepo buildingTaskRepo;

    @Autowired
    public BuildingTaskService(Validator validator, UserRepository userRepository, BuildingRepo buildingRepo, BuildingTaskRepo buildingTaskRepo) {
        super(validator, userRepository);
        this.buildingRepo = buildingRepo;
        this.buildingTaskRepo = buildingTaskRepo;
    }

    public BuildingTaskResponse createBuildingTask(Long buildingId, CreateBuildingTaskRequest request) {
        UserEntity user = getUserEntity();

        if (buildingId == null) {
            throw new BadRequestException("ID can not be empty");
        }

        if (user.getCompany() == null) {
            throw new BadRequestException("User is not company member");
        }
        if (user.getIsCompanyOwner() == null || !user.getIsCompanyOwner()) {
            throw new BadRequestException("User is not company owner");
        }

        Optional<Building> buildingOptional = buildingRepo.findById(buildingId);
        if (!buildingOptional.isPresent()) {
            throw new BadRequestException("No building with such ID");
        }


        return new BuildingTaskResponse(buildingTaskRepo.save(BuildingTask.createFromRequest(buildingOptional.get(), request)));
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public StatusResponse deleteBuildingTask(Long buildingTaskId) {
        if (buildingTaskId == null) {
            throw new BadRequestException("invalid id");
        }

        Optional<BuildingTask> taskOptional = buildingTaskRepo.findById(buildingTaskId);
        if (!taskOptional.isPresent()) {
            throw new BadRequestException("no building task with such id");
        }

        BuildingTask task = taskOptional.get();
        task.setBuilding(null);
        task.setUser(null);

        buildingTaskRepo.delete(buildingTaskRepo.save(task));

        return StatusResponse.success();
    }

    public List<BuildingTaskResponse> getAll(Long buildingId) {
        if (buildingId == null) {
            throw new BadRequestException("invalid id");
        }

        Optional<Building> buildingOptional = buildingRepo.findById(buildingId);
        if (!buildingOptional.isPresent()) {
            throw new BadRequestException("No building with such ID");
        }

        return buildingOptional.get().getBuildingTasks().stream().map(BuildingTaskResponse::new).distinct().collect(Collectors.toList());
    }
}
