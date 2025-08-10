package com.mortis.ainews.infra.persistence.repository.interfaces;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mortis.ainews.infra.persistence.po.keywords.Keyword;

@Repository
public interface KeywordRepository extends JpaRepository<Keyword, Long> {

    List<Keyword> findByContentInAndDeleted(List<String> contents, int deleted);

    List<Keyword> findByIdInAndDeleted(List<Long> ids, int deleted);

    /**
     * 分页查询未删除的关键词
     */
    Page<Keyword> findByDeleted(int deleted, Pageable pageable);

    /**
     * 根据内容模糊搜索关键词（分页）
     */
    Page<Keyword> findByContentContainingAndDeleted(String content, int deleted, Pageable pageable);

    /**
     * 统计未删除的关键词总数
     */
    long countByDeleted(int deleted);
}
