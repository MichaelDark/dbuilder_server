package com.mtemnohud.dbuilder.controller;

import com.mtemnohud.dbuilder.model.entity.values.NumberValue;
import com.mtemnohud.dbuilder.model.request.CreateNumberValueRequest;
import com.mtemnohud.dbuilder.model.response.StatusResponse;
import com.mtemnohud.dbuilder.service.impl.secured.impl.NumberValueService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "6) Number Value Controller")
@Slf4j
@RestController
public class NumberValueController {

    private final NumberValueService numberValueService;

    @Autowired
    public NumberValueController(NumberValueService numberValueService) {
        this.numberValueService = numberValueService;
    }

    @CrossOrigin
    @ApiOperation(
            value = "Create numberValue",
            response = NumberValue.class,
            produces = "application/json",
            authorizations = @Authorization("Authorization"))
    @RequestMapping(value = "/values/number", method = RequestMethod.POST)
    @ResponseBody
    public NumberValue createNumberValue(@RequestBody CreateNumberValueRequest request) {
        return numberValueService.createNumberValue(request);
    }

    @CrossOrigin
    @ApiOperation(
            value = "Get all numberValues by task id",
            response = NumberValue.class,
            responseContainer = "List",
            produces = "application/json",
            authorizations = @Authorization("Authorization"))
    @RequestMapping(value = "/values/number/{buildingTaskId}", method = RequestMethod.GET)
    @ResponseBody
    public List<NumberValue> getNumberValuesByBuilding(@PathVariable String buildingTaskId) {
        return numberValueService.getByBuildingTask(Long.valueOf(buildingTaskId));
    }

    @CrossOrigin
    @ApiOperation(
            value = "Delete numberValue",
            response = StatusResponse.class,
            produces = "application/json",
            authorizations = @Authorization("Authorization"))
    @RequestMapping(value = "/values/number/{numberValueId}", method = RequestMethod.DELETE)
    @ResponseBody
    public StatusResponse deleteNumberValue(@PathVariable("numberValueId") String numberValueId) {
        return numberValueService.deleteNumberValue(Long.valueOf(numberValueId));
    }

    @CrossOrigin
    @ApiOperation(
            value = "Delete numberValue by task id and criteria id",
            response = StatusResponse.class,
            produces = "application/json",
            authorizations = @Authorization("Authorization"))
    @RequestMapping(value = "/values/number/{buildingTaskId}/{numberCriteriaId}", method = RequestMethod.DELETE)
    @ResponseBody
    public StatusResponse deleteNumberValueByBuilding(@PathVariable String buildingTaskId, @PathVariable String numberCriteriaId) {
        return numberValueService.deleteNumberValueByBuilding(Long.valueOf(buildingTaskId), Long.valueOf(numberCriteriaId));
    }

}
