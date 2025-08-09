package com.mortis.ainews.infra.persistence.converter;

import org.mapstruct.Mapper;
import java.util.List;
import com.mortis.ainews.infra.persistence.po.users.User;
import com.mortis.ainews.domain.model.UserDO;

@Mapper(componentModel = "spring")
public interface UserConverter {
    UserDO toDO(User user);

    List<UserDO> toDOs(List<User> users);

    User toPO(UserDO userDO);

    List<User> toPOs(List<UserDO> userDOs);
}