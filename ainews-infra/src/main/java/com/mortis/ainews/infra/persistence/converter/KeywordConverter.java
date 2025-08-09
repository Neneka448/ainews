package com.mortis.ainews.infra.persistence.converter;

import org.mapstruct.Mapper;
import org.springframework.integration.annotation.Default;

import java.util.List;
import com.mortis.ainews.infra.persistence.po.keywords.Keyword;
import com.mortis.ainews.domain.model.KeywordDO;

@Mapper(componentModel = "spring")
public interface KeywordConverter {
    KeywordDO toDO(Keyword keyword);

    List<KeywordDO> toDOs(List<Keyword> keywords);

    Keyword toPO(KeywordDO keywordDO);

    List<Keyword> toPOs(List<KeywordDO> keywordDOs);
}