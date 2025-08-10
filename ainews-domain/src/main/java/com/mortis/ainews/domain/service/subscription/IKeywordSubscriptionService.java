package com.mortis.ainews.domain.service.subscription;

import java.util.List;
import com.mortis.ainews.domain.model.KeywordDO;
import com.mortis.ainews.domain.model.PageQuery;
import com.mortis.ainews.domain.model.PageData;

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

    /**
     * 分页查询用户订阅的关键字
     *
     * @param userId    用户 ID
     * @param pageQuery 分页参数
     * @return 分页结果
     */
    PageData<KeywordDO> findKeywordsByUserIdWithPaging(Long userId, PageQuery pageQuery);

}
