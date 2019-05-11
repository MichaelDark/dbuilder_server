package com.mtemnohud.dbuilder.controller;

import com.mtemnohud.dbuilder.model.entity.BuildingTask;
import com.mtemnohud.dbuilder.model.request.CreateBuildingTaskRequest;
import com.mtemnohud.dbuilder.model.response.StatusResponse;
import com.mtemnohud.dbuilder.service.impl.secured.impl.BuildingTaskService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "4) Building Task Controller")
@Slf4j
@RestController
public class BuildingTaskController {

    private final BuildingTaskService buildingTaskService;

    @Autowired
    public BuildingTaskController(BuildingTaskService buildingTaskService) {
        this.buildingTaskService = buildingTaskService;
    }

    @ApiOperation(
            value = "Create buildingTask",
            response = BuildingTask.class,
            produces = "application/json",
            authorizations = @Authorization("Authorization"))
    @RequestMapping(value = "/buildingTask/create", method = RequestMethod.POST)
    @ResponseBody
    public BuildingTask createBuildingTask(@RequestBody CreateBuildingTaskRequest request) {
        return buildingTaskService.createBuildingTask(request);
    }

    @ApiOperation(
            value = "Delete buildingTask",
            response = StatusResponse.class,
            produces = "application/json",
            authorizations = @Authorization("Authorization"))
    @RequestMapping(value = "/buildingTask/delete/{buildingTaskId}", method = RequestMethod.DELETE)
    @ResponseBody
    public StatusResponse deleteBuildingTask(@PathVariable("buildingTaskId") String buildingTaskId) {
        return buildingTaskService.deleteBuildingTask(Long.valueOf(buildingTaskId));
    }

    @ApiOperation(
            value = "Get all buildingTasks",
            response = BuildingTask.class,
            responseContainer = "List",
            produces = "application/json",
            authorizations = @Authorization("Authorization"))
    @RequestMapping(value = "/buildingTask/all", method = RequestMethod.GET)
    @ResponseBody
    public List<BuildingTask> getAllBuildingTasks() {
        return buildingTaskService.getAll();
    }

}
