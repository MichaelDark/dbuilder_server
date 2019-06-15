package com.mtemnohud.dbuilder.service.impl.secured.impl;

import com.google.common.collect.Lists;
import com.mtemnohud.dbuilder.exception.BadRequestException;
import com.mtemnohud.dbuilder.model.entity.Building;
import com.mtemnohud.dbuilder.model.entity.Company;
import com.mtemnohud.dbuilder.model.request.CreateBuildingRequest;
import com.mtemnohud.dbuilder.model.user.UserEntity;
import com.mtemnohud.dbuilder.repository.BuildingRepo;
import com.mtemnohud.dbuilder.repository.CompanyRepo;
import com.mtemnohud.dbuilder.repository.UserRepository;
import com.mtemnohud.dbuilder.service.impl.secured.BaseSecuredService;
import com.mtemnohud.dbuilder.component.Validator;
import com.mtemnohud.dbuilder.model.request.CreateCompanyRequest;
import com.mtemnohud.dbuilder.model.response.StatusResponse;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class CompanyService extends BaseSecuredService {

    private final CompanyRepo companyRepo;

    @Autowired
    public CompanyService(Validator validator, UserRepository userRepository, CompanyRepo companyRepo) {
        super(validator, userRepository);
        this.companyRepo = companyRepo;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Company createCompany(CreateCompanyRequest request) {
        UserEntity user = getUserEntity();

        if (StringUtils.isEmpty(request.getName())) {
            throw new BadRequestException("Company name should not be empty");
        }
        if (user.getIsCompanyOwner() == null || !user.getIsCompanyOwner()) {
            throw new BadRequestException("User is not company owner");
        }
        if (user.getCompany() != null) {
            throw new BadRequestException("User already has company");
        }

        Company savedCompany = companyRepo.save(Company.createFromRequest(request));

        user.setCompany(savedCompany);
        repository.save(user);

        return savedCompany;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public StatusResponse deleteCompany() {
        UserEntity user = getUserEntity();

        if (user.getCompany() == null) {
            throw new BadRequestException("User has no owned company");
        }

        Company company = user.getCompany();
        for (UserEntity userEntity : company.getUsers()) {
            userEntity.setCompany(null);
            repository.save(userEntity);
        }
        company.setUsers(new ArrayList<>());

        companyRepo.save(company);
        companyRepo.deleteById(company.getId());

        return StatusResponse.success();
    }

    public Company getCompany() {
        if (getUserEntity() == null) {
            throw new BadRequestException("Unauthorized");
        }
        if (getUserEntity().getCompany() == null) {
            throw new BadRequestException("User has no owned company");
        }

        return getUserEntity().getCompany();
    }

    public List<UserEntity> getUsers() {
        UserEntity user = getUserEntity();

        if (user.getCompany() == null) {
            throw new BadRequestException("User has no owned company");
        }

        return user.getCompany().getUsers();
    }
}

