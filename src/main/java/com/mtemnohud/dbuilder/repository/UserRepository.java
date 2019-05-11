package com.mtemnohud.dbuilder.repository;

import com.mtemnohud.dbuilder.model.user.UserEntity;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserEntity, Long> {

    UserEntity findFirstByUsername(String username);

    UserEntity findFirstById(Long id);
}
