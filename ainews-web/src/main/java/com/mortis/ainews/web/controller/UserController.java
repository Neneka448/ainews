package com.mortis.ainews.web.controller;

import java.util.Optional;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import com.mortis.ainews.domain.model.UserDO;
import com.mortis.ainews.domain.service.user.IUserService;
import com.mortis.ainews.web.converter.DtoConverter;
import com.mortis.ainews.web.dto.ApiResponse;
import com.mortis.ainews.web.dto.user.UserCreateRequest;
import com.mortis.ainews.web.dto.user.UserDeleteRequest;
import com.mortis.ainews.web.dto.user.UserUpdateRequest;
import com.mortis.ainews.web.dto.user.UserResponse;
import com.mortis.ainews.web.exception.ParamsValidationException;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final IUserService userService;
    private final DtoConverter dtoConverter;

    // 读接口 GET
    @GetMapping("/getById")
    public ApiResponse<UserResponse> getById(@RequestParam("id") Long id) {
        if (id == null || id <= 0) {
            throw new ParamsValidationException("id必须大于0");
        }
        Optional<UserDO> user = userService.findUserById(id);
        return ApiResponse.success(dtoConverter.toUserResponse(user.orElse(null)));
    }

    @GetMapping("/getByAc")
    public ApiResponse<UserResponse> getByAc(@RequestParam("ac") String ac) {
        if (ac == null || ac.isBlank()) {
            throw new ParamsValidationException("账号不能为空");
        }
        Optional<UserDO> user = userService.findUserByAc(ac);
        return ApiResponse.success(dtoConverter.toUserResponse(user.orElse(null)));
    }

    // 写接口 POST
    @PostMapping("/create")
    public ApiResponse<UserResponse> create(@RequestBody UserCreateRequest req) {
        if (req == null || req.getAc() == null || req.getAc().isBlank()) {
            throw new ParamsValidationException("账号不能为空");
        }
        if (req.getUserName() == null || req.getUserName().isBlank()) {
            throw new ParamsValidationException("用户名不能为空");
        }
        UserDO created = userService.createUser(new UserDO(null, req.getUserName(), req.getAc(), 0));
        return ApiResponse.success(dtoConverter.toUserResponse(created));
    }

    @PostMapping("/update")
    public ApiResponse<Boolean> update(@RequestBody UserUpdateRequest req) {
        if (req == null || req.getId() == null || req.getId() <= 0) {
            throw new ParamsValidationException("id必须大于0");
        }
        boolean ok = userService.updateUser(new UserDO(req.getId(), req.getUserName(), null, 0));
        return ApiResponse.success(ok);
    }

    @PostMapping("/delete")
    public ApiResponse<Boolean> delete(@RequestBody UserDeleteRequest req) {
        if (req == null || req.getId() == null || req.getId() <= 0) {
            throw new ParamsValidationException("id必须大于0");
        }
        boolean ok = userService.deleteUser(req.getId());
        return ApiResponse.success(ok);
    }
}
