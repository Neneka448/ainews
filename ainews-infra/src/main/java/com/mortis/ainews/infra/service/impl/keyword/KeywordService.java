package com.mortis.ainews.infra.service.impl.keyword;

import java.util.List;

import com.mortis.ainews.domain.model.KeywordDO;
import org.springframework.stereotype.Service;

import com.mortis.ainews.domain.service.keyword.IKeywordService;

@Service
public class KeywordService implements IKeywordService {
    @Override
    public List<Long> createKeywords(List<String> contents) {
        return null;
    }

    @Override
    public List<Long> deleteKeywords(List<Long> keywordIds) {
        return null;
    }

    @Override
    public List<Long> updateKeywords(List<KeywordDO> keywordDOs) {
        return null;
    }

    @Override
    public List<KeywordDO> findKeywordsByIds(List<Long> keywordIds) {
        return null;
    }
}
