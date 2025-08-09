package com.mortis.ainews.infra.service.impl.subscription;

import java.util.List;

import com.mortis.ainews.domain.model.KeywordDO;
import org.springframework.stereotype.Service;

import com.mortis.ainews.domain.service.subscription.IKeywordSubscriptionService;

@Service
public class KeywordSubscriptionService implements IKeywordSubscriptionService {

    @Override
    public List<Long> subscribe(Long userId, List<Long> keywordIds) {
        return null;
    }

    @Override
    public List<Long> unsubscribe(Long userId, List<Long> keywordIds) {
        return null;
    }

    @Override
    public List<KeywordDO> findKeywordsByUserId(Long userId) {
        return null;
    }
}
