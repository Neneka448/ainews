package com.mortis.ainews.infra.persistence.repository.interfaces;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mortis.ainews.infra.persistence.po.users.UserSubScriptionRel;

@Repository
public interface UserSubScriptionRelRepository
                extends JpaRepository<UserSubScriptionRel, UserSubScriptionRel.UserSubScriptionRelPK> {

        /**
         * 根据 userId 和 deleted 标记查询订阅关系
         * 使用 Spring Data JPA 方法命名约定自动实现
         *
         * @param userId  用户 ID
         * @param deleted 删除标记（例如 0 未删除，1 已删除）
         * @return 订阅关系列表
         */
        List<UserSubScriptionRel> findByIdUserIdAndDeleted(Long userId, int deleted);
}
