package com.mortis.ainews.domain.service.user;

import com.mortis.ainews.domain.model.UserDO;

/**
 * 用户相关的领域服务接口
 */
public interface IUserService {

    UserDO findUserById(Long userId);

    UserDO findUserByAc(String ac);

    boolean createUser(UserDO userDO);

    boolean updateUser(UserDO userDO);

    boolean deleteUser(Long userId);
}
