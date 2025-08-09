package com.mortis.ainews.domain.service.keyword;

import java.util.List;
import com.mortis.ainews.domain.model.KeywordDO;

/**
 * 关键词相关的领域服务接口
 */
public interface IKeywordService {

    List<Long> createKeywords(List<String> contents);

    List<Long> deleteKeywords(List<Long> keywordIds);

    List<Long> updateKeywords(List<KeywordDO> keywordDOs);

    List<KeywordDO> findKeywordsByIds(List<Long> keywordIds);
}
