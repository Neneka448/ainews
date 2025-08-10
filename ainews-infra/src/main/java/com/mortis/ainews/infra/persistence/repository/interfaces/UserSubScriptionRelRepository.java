package com.mortis.ainews.infra.persistence.repository.interfaces;

import com.mortis.ainews.infra.persistence.po.keywords.Keyword;
import com.mortis.ainews.infra.persistence.po.users.UserSubScriptionRel;
import com.mortis.ainews.infra.persistence.po.users.UserSubScriptionRel.UserSubScriptionRelPK;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserSubScriptionRelRepository extends JpaRepository<UserSubScriptionRel, UserSubScriptionRelPK> {

        List<UserSubScriptionRel> findByIdUserIdAndDeleted(Long userId, int deleted);

        List<UserSubScriptionRel> findByIdKeywordIdAndDeleted(Long keywordId, int deleted);

        Optional<UserSubScriptionRel> findByIdUserIdAndIdKeywordIdAndDeleted(Long userId, Long keywordId, int deleted);

        // 分页查询用户订阅的关键词关系
        Page<UserSubScriptionRel> findByIdUserIdAndDeleted(Long userId, int deleted, Pageable pageable);

        /**
         * 使用JOIN查询直接获取用户订阅的关键词（分页）
         * 这个方法解决了N+1查询问题，通过单个JOIN查询获取所有需要的数据
         */
        @Query("SELECT k FROM Keyword k " +
                        "JOIN UserSubScriptionRel r ON k.id = r.id.keywordId " +
                        "WHERE r.id.userId = :userId " +
                        "AND r.deleted = 0 " +
                        "AND k.deleted = 0 " +
                        "ORDER BY r.id.keywordId DESC")
        Page<Keyword> findKeywordsByUserIdWithJoin(@Param("userId") Long userId, Pageable pageable);
}
