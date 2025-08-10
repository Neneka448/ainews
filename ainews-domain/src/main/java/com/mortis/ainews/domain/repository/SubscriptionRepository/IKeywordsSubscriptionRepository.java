package com.mortis.ainews.domain.repository.SubscriptionRepository;

import java.util.List;
import com.mortis.ainews.domain.model.KeywordDO;
import com.mortis.ainews.domain.model.PageQuery;
import com.mortis.ainews.domain.model.PageData;

/**
 * 订阅相关的领域仓库接口
 */
public interface IKeywordsSubscriptionRepository {

    /**
     * 查询用户订阅的关键字（domain DO 列表）
     *
     * @param userId 用户 ID
     * @return 该用户订阅的关键字列表（可能为空）
     */
    List<KeywordDO> findKeywordsByUserId(Long userId);

    /**
     * 分页查询用户订阅的关键字
     *
     * @param userId    用户 ID
     * @param pageQuery 分页参数
     * @return 分页结果
     */
    PageData<KeywordDO> findKeywordsByUserIdWithPaging(Long userId, PageQuery pageQuery);
}
