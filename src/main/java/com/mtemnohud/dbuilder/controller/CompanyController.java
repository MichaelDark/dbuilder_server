package com.mtemnohud.dbuilder.controller;

import com.mtemnohud.dbuilder.model.entity.Building;
import com.mtemnohud.dbuilder.model.entity.Company;
import com.mtemnohud.dbuilder.model.request.CreateBuildingRequest;
import com.mtemnohud.dbuilder.model.request.CreateCompanyRequest;
import com.mtemnohud.dbuilder.model.response.StatusResponse;
import com.mtemnohud.dbuilder.service.impl.secured.impl.BuildingService;
import com.mtemnohud.dbuilder.service.impl.secured.impl.CompanyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "2) Company Controller")
@Slf4j
@RestController
public class CompanyController {

    private final CompanyService companyService;

    @Autowired
    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @ApiOperation(
            value = "Create company",
            response = Company.class,
            produces = "application/json",
            authorizations = @Authorization("Authorization"))
    @RequestMapping(value = "/company/create", method = RequestMethod.POST)
    @ResponseBody
    public Company createCompany(@RequestBody CreateCompanyRequest request) {
        return companyService.createCompany(request);
    }

    @ApiOperation(
            value = "Delete company",
            response = StatusResponse.class,
            produces = "application/json",
            authorizations = @Authorization("Authorization"))
    @RequestMapping(value = "/company/delete", method = RequestMethod.DELETE)
    @ResponseBody
    public StatusResponse deleteCompany() {
        return companyService.deleteCompany();
    }

    @ApiOperation(
            value = "Get owned company",
            response = Company.class,
            produces = "application/json",
            authorizations = @Authorization("Authorization"))
    @RequestMapping(value = "/company", method = RequestMethod.GET)
    @ResponseBody
    public Company getCompany() {
        return companyService.getCompany();
    }

}

