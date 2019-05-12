package com.mtemnohud.dbuilder.service.impl.secured.impl;

import com.mtemnohud.dbuilder.exception.BadRequestException;
import com.mtemnohud.dbuilder.repository.UserRepository;
import com.mtemnohud.dbuilder.security.web.Messages;
import com.mtemnohud.dbuilder.model.request.CreateUserRequest;
import com.mtemnohud.dbuilder.model.response.UserResponse;
import com.mtemnohud.dbuilder.repository.AuthoritiesRepository;
import com.mtemnohud.dbuilder.component.Validator;
import com.mtemnohud.dbuilder.service.impl.secured.BaseSecuredService;
import com.mtemnohud.dbuilder.model.user.AuthoritiesEntity;
import com.mtemnohud.dbuilder.model.user.UserEntity;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Service
public class UserService extends BaseSecuredService {

    private static final String SERVICE_NAME = "UserService";

    private final AuthoritiesRepository authoritiesRepository;

    @Value("${application.security.password.sault1}")
    private String sault1;

    @Value("${application.security.password.sault2}")
    private String sault2;

    @Autowired
    public UserService(UserRepository userRepository, AuthoritiesRepository authoritiesRepository, Validator validator) {
        super(validator, userRepository);
        this.authoritiesRepository = authoritiesRepository;
    }

    private static UserResponse buildUserResponse(UserEntity user) {
        if (user == null) {
            return null;
        }
        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setEnabled(user.isEnabled());
        response.setUsername(user.getUsername());
        response.setName(user.getName());
        response.setSurname(user.getSurname());
        response.setIsCompanyOwner(user.getIsCompanyOwner());
        response.setCompany(user.getCompany());

        return response;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public UserResponse createUser(@NonNull CreateUserRequest request) {
        String username = request.getUsername();
        log.trace(SERVICE_NAME + "[createUser] [].", username);


        UserEntity user = repository.findFirstByUsername(username);

        if (user != null) {
            throw new BadRequestException("User already exist, please choose another username.");
        }

        String password = sault1 + request.getPassword() + sault2;
        String hash = DigestUtils.md5Hex(password);


        user = new UserEntity.Builder()
                .addCredentials(request.getUsername(), hash)
                .addInfo(request.getName(), request.getSurname())
                .addCompanyOwnerStatus(request.getIsCompanyOwner())
                .build();
        user = repository.save(user);

        authoritiesRepository.save(new AuthoritiesEntity(user.getId(), "ROLE_USER"));

        return buildUserResponse(user);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public UserResponse createCompanyUser(@NonNull CreateUserRequest request) {
        if (getUserEntity().getIsCompanyOwner() == null || !getUserEntity().getIsCompanyOwner()) {
            throw new BadRequestException("User is not company owner.");
        }
        if (getUserEntity().getCompany() == null) {
            throw new BadRequestException("User has no company.");
        }

        UserResponse userResponse = createUser(request);
        UserEntity user = repository.findFirstById(userResponse.getId());
        user.setCompany(getUserEntity().getCompany());

        return buildUserResponse(user);
    }


    public UserResponse getUser(@NonNull String username) {
        validator.boolValidate(StringUtils.isEmpty(username), Messages.USERNAME_SHOULD_BE_PROVIDED);

        log.trace(SERVICE_NAME + "[getUser] [].", username);
        return buildUserResponse(getUserEntity(username));
    }


    public List<UserResponse> getUsers() {
        List<UserEntity> userList = getUserList();
        Comparator<UserEntity> comparator = Comparator.comparing(UserEntity::getId);
        userList.sort(comparator.reversed());
        return userList.stream().map(UserService::buildUserResponse).distinct().collect(Collectors.toList());
    }

    public UserEntity getUserInfo() {
        return repository.findFirstByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
    }

}
