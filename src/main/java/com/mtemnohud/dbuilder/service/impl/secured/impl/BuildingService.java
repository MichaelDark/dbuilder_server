package com.mtemnohud.dbuilder.service.impl.secured.impl;

import com.mtemnohud.dbuilder.component.Validator;
import com.mtemnohud.dbuilder.exception.BadRequestException;
import com.mtemnohud.dbuilder.model.entity.Building;
import com.mtemnohud.dbuilder.model.entity.BuildingResponse;
import com.mtemnohud.dbuilder.model.entity.Company;
import com.mtemnohud.dbuilder.model.request.CreateBuildingRequest;
import com.mtemnohud.dbuilder.model.response.StatusResponse;
import com.mtemnohud.dbuilder.model.user.UserEntity;
import com.mtemnohud.dbuilder.repository.*;
import com.mtemnohud.dbuilder.service.impl.secured.BaseSecuredService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BuildingService extends BaseSecuredService {

    private final CompanyRepo companyRepo;
    private final BuildingRepo buildingRepo;

    @Autowired
    public BuildingService(Validator validator, UserRepository userRepository, CompanyRepo companyRepo, BuildingRepo buildingRepo) {
        super(validator, userRepository);
        this.companyRepo = companyRepo;
        this.buildingRepo = buildingRepo;
    }

    public BuildingResponse createBuilding(CreateBuildingRequest request) {
        UserEntity user = getUserEntity();

        if (user.getCompany() == null) {
            throw new BadRequestException("User is not company member");
        }
        if (user.getIsCompanyOwner() == null || !user.getIsCompanyOwner()) {
            throw new BadRequestException("User is not company owner");
        }

        return new BuildingResponse(buildingRepo.save(Building.createFromRequest(user.getCompany(), request)));
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public StatusResponse deleteBuilding(Long buildingId) {
        UserEntity user = getUserEntity();
        Company company = user.getCompany();

        if (company == null) {
            throw new BadRequestException("User has no company");
        }
        if (user.getIsCompanyOwner() == null || !user.getIsCompanyOwner()) {
            throw new BadRequestException("User is not company owner");
        }

        Optional<Building> buildingOptional = buildingRepo.findById(buildingId);
        if (!buildingOptional.isPresent()) {
            throw new BadRequestException("No building with such ID");
        }
        if (buildingOptional.get().getCompany() == null || !buildingOptional.get().getCompany().getId().equals(company.getId())) {
            throw new BadRequestException("No building in current company");
        }

        Building building = buildingOptional.get();
        building.setCompany(null);
        buildingRepo.deleteById(buildingRepo.save(building).getId());

        return StatusResponse.success();
    }

    public List<BuildingResponse> getAll() {
        UserEntity user = getUserEntity();
        Optional<Company> company = companyRepo.findById(user.getCompany().getId());

        if (user.getCompany() == null || !company.isPresent()) {
            throw new BadRequestException("User has no company");
        }


        return company.get().getBuildings().stream().map(BuildingResponse::new).distinct().collect(Collectors.toList());
    }
}
