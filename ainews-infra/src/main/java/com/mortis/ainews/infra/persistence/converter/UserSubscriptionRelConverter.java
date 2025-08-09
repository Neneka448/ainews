package com.mortis.ainews.infra.persistence.converter;

import org.mapstruct.Mapper;
import java.util.List;
import com.mortis.ainews.infra.persistence.po.users.UserSubScriptionRel;
import com.mortis.ainews.domain.model.UserSubscriptionRelDO;

@Mapper(componentModel = "spring")
public interface UserSubscriptionRelConverter {
    UserSubscriptionRelDO toDO(UserSubScriptionRel rel);

    List<UserSubscriptionRelDO> toDOs(List<UserSubScriptionRel> rels);
}