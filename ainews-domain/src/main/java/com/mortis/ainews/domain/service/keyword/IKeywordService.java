package com.mortis.ainews.domain.service.keyword;

import java.util.List;
import com.mortis.ainews.domain.model.KeywordDO;
import com.mortis.ainews.domain.model.PageQuery;
import com.mortis.ainews.domain.model.PageData;

/**
 * 关键词相关的领域服务接口
 */
public interface IKeywordService {

    List<Long> createKeywords(List<String> contents);

    List<Long> deleteKeywords(List<Long> keywordIds);

    List<Long> updateKeywords(List<KeywordDO> keywordDOs);

    List<KeywordDO> findKeywordsByIds(List<Long> keywordIds);

    /**
     * 分页查询关键词
     *
     * @param pageQuery 分页参数
     * @return 分页结果
     */
    PageData<KeywordDO> findKeywordsWithPaging(PageQuery pageQuery);

    /**
     * 根据内容模糊搜索关键词（分页）
     *
     * @param content   搜索内容
     * @param pageQuery 分页参数
     * @return 分页结果
     */
    PageData<KeywordDO> searchKeywordsByContent(String content, PageQuery pageQuery);

}
