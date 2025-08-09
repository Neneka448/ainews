package com.mortis.ainews.domain.repository.SubscriptionRepository;

import java.util.List;
import com.mortis.ainews.domain.model.KeywordDO;

/**
 * Domain 层聚合契约：订阅相关的领域仓库接口
 */
public interface ISubscriptionRepository {

    /**
     * 查询用户订阅的关键字（domain DO 列表）
     * 
     * @param userId 用户 ID
     * @return 该用户订阅的关键字列表（可能为空）
     */
    List<KeywordDO> findKeywordsByUserId(Long userId);
}
