package com.mortis.ainews.infra.persistence.repository.interfaces;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mortis.ainews.infra.persistence.po.keywords.Keyword;

@Repository
public interface KeywordRepository extends JpaRepository<Keyword, Long> {

    List<Keyword> findByContentInAndDeleted(List<String> contents, int deleted);

    List<Keyword> findByIdInAndDeleted(List<Long> ids, int deleted);
}
