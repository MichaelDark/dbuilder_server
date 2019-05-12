package com.mtemnohud.dbuilder.controller;

import com.mtemnohud.dbuilder.model.user.UserEntity;
import com.mtemnohud.dbuilder.model.request.CreateUserRequest;
import com.mtemnohud.dbuilder.model.response.UserResponse;
import com.mtemnohud.dbuilder.security.model.LoginRequest;
import com.mtemnohud.dbuilder.security.model.TokenDTO;
import com.mtemnohud.dbuilder.service.impl.secured.impl.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.Authorization;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@Api(tags = "1) User Controller")
@Slf4j
@RestController
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @CrossOrigin
    @ApiOperation(value = "Login a user.", response = TokenDTO.class, produces = "application/json")
    @RequestMapping(value = "/login", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public TokenDTO login(@RequestBody LoginRequest request) {
        log.trace("[UsersController] [login].");
        return null;
    }

    @CrossOrigin
    @ApiOperation(value = "Create user profile", response = UserResponse.class, produces = "application/json")
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public UserResponse addUser(@ApiParam(value = "User profile data JSON", required = true) @RequestBody CreateUserRequest request) {
        log.trace("[UsersController] [POST] username={}.", request.getUsername());
        return userService.createUser(request);
    }

    @CrossOrigin
    @ApiOperation(value = "Create user profile for company", response = UserResponse.class, produces = "application/json", authorizations = @Authorization("Authorization"))
    @RequestMapping(value = "/register/company", method = RequestMethod.POST)
    @ResponseBody
    public UserResponse addCompanyUser(@ApiParam(value = "User profile data JSON", required = true) @RequestBody CreateUserRequest request) {
        log.trace("[UsersController] [POST] username={}.", request.getUsername());
        return userService.createCompanyUser(request);
    }

    @CrossOrigin
    @ApiOperation(value = "Get user info", response = UserResponse.class, produces = "application/json", authorizations = @Authorization("Authorization"))
    @RequestMapping(value = "/userInfo", method = RequestMethod.GET)
    @ResponseBody
    public UserEntity getUserDetails() {
        log.trace("[UsersController] [GET]");
        return userService.getUserInfo();
    }

}
