package com.mtemnohud.dbuilder.controller;

import com.mtemnohud.dbuilder.model.entity.Building;
import com.mtemnohud.dbuilder.model.entity.BuildingResponse;
import com.mtemnohud.dbuilder.model.entity.BuildingTask;
import com.mtemnohud.dbuilder.model.entity.Company;
import com.mtemnohud.dbuilder.model.entity.criterion.NumberCriteria;
import com.mtemnohud.dbuilder.model.request.CreateBuildingRequest;
import com.mtemnohud.dbuilder.model.request.CreateBuildingTaskRequest;
import com.mtemnohud.dbuilder.model.request.CreateNumberCriteriaRequest;
import com.mtemnohud.dbuilder.model.response.StatusResponse;
import com.mtemnohud.dbuilder.service.BaseService;
import com.mtemnohud.dbuilder.service.impl.secured.impl.BuildingService;
import com.mtemnohud.dbuilder.service.impl.secured.impl.BuildingTaskService;
import com.mtemnohud.dbuilder.service.impl.secured.impl.NumberCriteriaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "3) Building Controller")
@Slf4j
@RestController
public class BuildingController {

    private final BuildingService buildingService;

    @Autowired
    public BuildingController(BuildingService buildingService) {
        this.buildingService = buildingService;
    }

    @CrossOrigin
    @ApiOperation(
            value = "Create building",
            response = BuildingResponse.class,
            produces = "application/json",
            authorizations = @Authorization("Authorization"))
    @RequestMapping(value = "/building/create", method = RequestMethod.POST)
    @ResponseBody
    public BuildingResponse createBuilding(@RequestBody CreateBuildingRequest request) {
        return buildingService.createBuilding(request);
    }

    @CrossOrigin
    @ApiOperation(
            value = "Delete building",
            response = StatusResponse.class,
            produces = "application/json",
            authorizations = @Authorization("Authorization"))
    @RequestMapping(value = "/building/delete/{buildingId}", method = RequestMethod.DELETE)
    @ResponseBody
    public StatusResponse deleteBuilding(@PathVariable("buildingId") String buildingId) {
        return buildingService.deleteBuilding(Long.valueOf(buildingId));
    }

    @CrossOrigin
    @ApiOperation(
            value = "Get all buildings",
            response = BuildingResponse.class,
            responseContainer = "List",
            produces = "application/json",
            authorizations = @Authorization("Authorization"))
    @RequestMapping(value = "/buildings", method = RequestMethod.GET)
    @ResponseBody
    public List<BuildingResponse> getAllBuildings() {
        return buildingService.getAll();
    }

}


