package com.mtemnohud.dbuilder.service.impl.secured.impl;


import com.google.common.collect.Lists;
import com.mtemnohud.dbuilder.component.Validator;
import com.mtemnohud.dbuilder.exception.BadRequestException;
import com.mtemnohud.dbuilder.model.entity.criterion.NumberCriteria;
import com.mtemnohud.dbuilder.model.request.CreateNumberCriteriaRequest;
import com.mtemnohud.dbuilder.model.response.StatusResponse;
import com.mtemnohud.dbuilder.repository.NumberCriteriaRepo;
import com.mtemnohud.dbuilder.repository.UserRepository;
import com.mtemnohud.dbuilder.service.impl.secured.BaseSecuredService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NumberCriteriaService extends BaseSecuredService {

    private final NumberCriteriaRepo numberCriteriaRepo;

    @Autowired
    public NumberCriteriaService(Validator validator, UserRepository userRepository, NumberCriteriaRepo numberCriteriaRepo) {
        super(validator, userRepository);
        this.numberCriteriaRepo = numberCriteriaRepo;
    }

    public NumberCriteria createNumberCriteria(CreateNumberCriteriaRequest request) {
        NumberCriteria criteria = NumberCriteria.createFromRequest(request);

        validateNumberCriteria(criteria);

        return numberCriteriaRepo.save(criteria);
    }

    private void validateNumberCriteria(NumberCriteria criteria) {
        if (criteria.getName() == null) {
            throw new BadRequestException("Name is null");
        }
        if (criteria.getUnit() == null) {
            throw new BadRequestException("Unit is null");
        }
        if (criteria.getMinValue() == null) {
            throw new BadRequestException("Min value is null");
        }
        if (criteria.getMaxValue() == null) {
            throw new BadRequestException("Max value is null");
        }
    }

    public StatusResponse deleteNumberCriteria(Long numberCriteriaId) {
        if (numberCriteriaId == null) {
            throw new BadRequestException("invalid id");
        }

        if (!numberCriteriaRepo.findById(numberCriteriaId).isPresent()) {
            throw new BadRequestException("no number criteria with such id");
        }

        numberCriteriaRepo.deleteById(numberCriteriaId);

        return StatusResponse.success();
    }

    public List<NumberCriteria> getAll() {
        return Lists.newArrayList(numberCriteriaRepo.findAll());
    }

}
