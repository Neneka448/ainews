package com.mortis.ainews.infra.service.impl.user;

import com.mortis.ainews.domain.model.UserDO;
import org.springframework.stereotype.Service;

import com.mortis.ainews.domain.service.user.IUserService;

@Service
public class UserService implements IUserService {
    @Override
    public UserDO findUserById(Long userId) {
        return null;
    }

    @Override
    public UserDO findUserByAc(String ac) {
        return null;
    }

    @Override
    public boolean createUser(UserDO userDO) {
        return false;
    }

    @Override
    public boolean updateUser(UserDO userDO) {
        return false;
    }

    @Override
    public boolean deleteUser(Long userId) {
        return false;
    }
}