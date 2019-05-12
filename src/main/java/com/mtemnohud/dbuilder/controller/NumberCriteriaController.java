package com.mtemnohud.dbuilder.controller;

import com.mtemnohud.dbuilder.model.entity.criterion.NumberCriteria;
import com.mtemnohud.dbuilder.model.entity.values.NumberValue;
import com.mtemnohud.dbuilder.model.request.CreateNumberCriteriaRequest;
import com.mtemnohud.dbuilder.model.request.CreateNumberValueRequest;
import com.mtemnohud.dbuilder.model.response.StatusResponse;
import com.mtemnohud.dbuilder.service.impl.secured.impl.NumberCriteriaService;
import com.mtemnohud.dbuilder.service.impl.secured.impl.NumberValueService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "5) Number Criteria Controller")
@Slf4j
@RestController
public class NumberCriteriaController {

    private final NumberCriteriaService numberCriteriaService;

    @Autowired
    public NumberCriteriaController(NumberCriteriaService numberCriteriaService) {
        this.numberCriteriaService = numberCriteriaService;
    }

    @CrossOrigin
    @ApiOperation(
            value = "Create numberCriteria",
            response = NumberCriteria.class,
            produces = "application/json",
            authorizations = @Authorization("Authorization"))
    @RequestMapping(value = "/numberCriteria/create", method = RequestMethod.POST)
    @ResponseBody
    public NumberCriteria createNumberCriteria(@RequestBody CreateNumberCriteriaRequest request) {
        return numberCriteriaService.createNumberCriteria(request);
    }

    @CrossOrigin
    @ApiOperation(
            value = "Delete numberCriteria",
            response = StatusResponse.class,
            produces = "application/json",
            authorizations = @Authorization("Authorization"))
    @RequestMapping(value = "/numberCriteria/delete/{numberCriteriaId}", method = RequestMethod.DELETE)
    @ResponseBody
    public StatusResponse deleteNumberCriteria(@PathVariable("numberCriteriaId") String numberCriteriaId) {
        return numberCriteriaService.deleteNumberCriteria(Long.valueOf(numberCriteriaId));
    }

    @CrossOrigin
    @ApiOperation(
            value = "Get all numberCriterias",
            response = NumberCriteria.class,
            responseContainer = "List",
            produces = "application/json",
            authorizations = @Authorization("Authorization"))
    @RequestMapping(value = "/numberCriteria/all", method = RequestMethod.GET)
    @ResponseBody
    public List<NumberCriteria> getAllNumberCriterion() {
        return numberCriteriaService.getAll();
    }

}

