package com.mortis.ainews.domain.service.subscription;

import java.util.List;
import com.mortis.ainews.domain.model.KeywordDO;

/**
 * 订阅关键词相关的领域服务接口
 */
public interface IKeywordSubscriptionService {

    /*
     * 订阅关键字
     * 
     * @param userId 用户 ID
     * 
     * @param keywordIds 关键字 ID 列表
     * 
     * @return 成功订阅的关键字 ID 列表
     */
    List<Long> subscribe(Long userId, List<Long> keywordIds);

    /*
     * 取消订阅关键字
     * 
     * @param userId 用户 ID
     * 
     * @param keywordIds 关键字 ID 列表
     * 
     * @return 成功取消订阅的关键字 ID 列表
     */
    List<Long> unsubscribe(Long userId, List<Long> keywordIds);

    /*
     * 查询用户订阅的关键字
     * 
     * @param userId 用户 ID
     * 
     * @return 该用户订阅的关键字列表（可能为空）
     */
    List<KeywordDO> findKeywordsByUserId(Long userId);

}
