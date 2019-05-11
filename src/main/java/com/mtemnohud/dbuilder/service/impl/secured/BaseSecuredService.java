package com.mtemnohud.dbuilder.service.impl.secured;

import com.mtemnohud.dbuilder.repository.UserRepository;
import com.mtemnohud.dbuilder.service.BaseService;
import com.mtemnohud.dbuilder.component.Validator;
import com.mtemnohud.dbuilder.model.user.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BaseSecuredService extends BaseService<UserRepository> {

    @Autowired
    public BaseSecuredService(Validator validator, UserRepository userRepository) {
        super(validator,userRepository);
    }

    public boolean isAdmin() {
        return getUserName().contains("admin");
    }

    public String getUserName() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    protected Long getUserId() {
        UserEntity user = repository.findFirstByUsername(getUserName());
        return user == null ? null : user.getId();
    }

    protected UserEntity getUserEntity() {
        return repository.findFirstByUsername(getUserName());
    }


    protected UserEntity getUserEntity(String username) {
        return repository.findFirstByUsername(username);
    }

    protected UserEntity getUserEntity(Long userId) {
        return repository.findFirstById(userId);
    }

    protected List<UserEntity> getUserList(){
        return (List<UserEntity>) repository.findAll();
    }

}
