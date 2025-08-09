package com.mortis.ainews.infra.persistence.converter;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;
import com.mortis.ainews.infra.persistence.po.users.UserSubScriptionRel;
import com.mortis.ainews.domain.model.UserSubscriptionRelDO;

@Mapper(componentModel = "spring")
public interface UserSubscriptionRelConverter {
    @Mappings({
            @Mapping(source = "id.userId", target = "userId"),
            @Mapping(source = "id.keywordId", target = "keywordId")
    })
    UserSubscriptionRelDO toDO(UserSubScriptionRel rel);

    List<UserSubscriptionRelDO> toDOs(List<UserSubScriptionRel> rels);

    @Mappings({
            @Mapping(target = "id.userId", source = "userId"),
            @Mapping(target = "id.keywordId", source = "keywordId")
    })
    UserSubScriptionRel toPO(UserSubscriptionRelDO relDO);

    List<UserSubScriptionRel> toPOs(List<UserSubscriptionRelDO> relDOs);

    default UserSubScriptionRel.UserSubScriptionRelPK toPK(UserSubscriptionRelDO relDO) {
        return new UserSubScriptionRel.UserSubScriptionRelPK(relDO.getUserId(), relDO.getKeywordId());
    }
}