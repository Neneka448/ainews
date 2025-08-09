package com.mortis.ainews.domain.service.user;

import com.mortis.ainews.domain.model.UserDO;

import java.util.Optional;

/**
 * 用户相关的领域服务接口
 */
public interface IUserService {

    Optional<UserDO> findUserById(Long userId);

    Optional<UserDO> findUserByAc(String ac);

    UserDO createUser(UserDO userDO);

    boolean updateUser(UserDO userDO);

    boolean deleteUser(Long userId);
}
