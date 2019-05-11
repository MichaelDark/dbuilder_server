package com.mtemnohud.dbuilder.service.impl.secured.impl;

import com.google.common.collect.Lists;
import com.mtemnohud.dbuilder.component.Validator;
import com.mtemnohud.dbuilder.exception.BadRequestException;
import com.mtemnohud.dbuilder.model.entity.Building;
import com.mtemnohud.dbuilder.model.entity.BuildingTask;
import com.mtemnohud.dbuilder.model.entity.Company;
import com.mtemnohud.dbuilder.model.entity.criterion.NumberCriteria;
import com.mtemnohud.dbuilder.model.request.CreateBuildingRequest;
import com.mtemnohud.dbuilder.model.request.CreateBuildingTaskRequest;
import com.mtemnohud.dbuilder.model.request.CreateNumberCriteriaRequest;
import com.mtemnohud.dbuilder.model.response.StatusResponse;
import com.mtemnohud.dbuilder.model.user.UserEntity;
import com.mtemnohud.dbuilder.repository.BuildingRepo;
import com.mtemnohud.dbuilder.repository.BuildingTaskRepo;
import com.mtemnohud.dbuilder.repository.NumberCriteriaRepo;
import com.mtemnohud.dbuilder.repository.UserRepository;
import com.mtemnohud.dbuilder.service.impl.secured.BaseSecuredService;
import jdk.nashorn.internal.runtime.options.Option;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BuildingService extends BaseSecuredService {

    private final BuildingRepo buildingRepo;

    @Autowired
    public BuildingService(Validator validator, UserRepository userRepository, BuildingRepo buildingRepo) {
        super(validator, userRepository);
        this.buildingRepo = buildingRepo;
    }

    public Building createBuilding(CreateBuildingRequest request) {
        UserEntity user = getUserEntity();

        if (user.getCompany() == null) {
            throw new BadRequestException("User is not company member");
        }
        if (user.getIsCompanyOwner() == null || !user.getIsCompanyOwner()) {
            throw new BadRequestException("User is not company owner");
        }

        return buildingRepo.save(Building.createFromRequest(user.getCompany(), request));
    }

    public StatusResponse deleteBuilding(Long buildingId) {
        UserEntity user = getUserEntity();
        Company company = user.getCompany();

        if (company == null) {
            throw new BadRequestException("User has no company");
        }
        if (user.getIsCompanyOwner() == null || !user.getIsCompanyOwner()) {
            throw new BadRequestException("User is not company owner");
        }

        Optional<Building> building = buildingRepo.findById(buildingId);
        if (!building.isPresent()) {
            throw new BadRequestException("No building with such ID");
        }
        if (!building.get().getCompany().getId().equals(company.getId())) {
            throw new BadRequestException("No building in current company");
        }

        buildingRepo.deleteById(buildingId);

        return StatusResponse.success();
    }

    public List<Building> getAll() {
        return Lists.newArrayList(buildingRepo.findAll());
    }
}
