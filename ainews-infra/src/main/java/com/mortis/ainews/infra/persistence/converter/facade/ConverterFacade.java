package com.mortis.ainews.infra.persistence.converter.facade;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

import com.mortis.ainews.infra.persistence.converter.KeywordConverter;
import com.mortis.ainews.infra.persistence.converter.UserConverter;
import com.mortis.ainews.infra.persistence.converter.UserSubscriptionRelConverter;

@Component
@RequiredArgsConstructor
public class ConverterFacade {
    public final KeywordConverter keywordConverter;
    public final UserConverter userConverter;
    public final UserSubscriptionRelConverter userSubscriptionRelConverter;
}
