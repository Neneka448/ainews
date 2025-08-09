package com.mortis.ainews.infra.persistence.repository.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mortis.ainews.infra.persistence.po.keywords.Keyword;

@Repository
public interface KeywordRepository extends JpaRepository<Keyword, Long> {

}
