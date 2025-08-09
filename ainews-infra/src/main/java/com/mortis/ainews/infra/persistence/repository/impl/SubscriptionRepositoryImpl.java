package com.mortis.ainews.infra.persistence.repository.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import com.mortis.ainews.domain.model.KeywordDO;
import com.mortis.ainews.domain.repository.SubscriptionRepository.ISubscriptionRepository;
import com.mortis.ainews.infra.persistence.converter.KeywordConverter;
import com.mortis.ainews.infra.persistence.po.keywords.Keyword;
import com.mortis.ainews.infra.persistence.po.users.UserSubScriptionRel;
import com.mortis.ainews.infra.persistence.repository.interfaces.KeywordRepository;
import com.mortis.ainews.infra.persistence.repository.interfaces.UserSubScriptionRelRepository;

@Repository
@RequiredArgsConstructor
public class SubscriptionRepositoryImpl implements ISubscriptionRepository {

    private final UserSubScriptionRelRepository relRepo;
    private final KeywordRepository keywordRepo;
    private final KeywordConverter keywordConverter;

    /**
     * 根据 userId 查询该用户订阅的 Keyword 列表
     */
    @Override
    @Transactional(readOnly = true)
    public List<KeywordDO> findKeywordsByUserId(Long userId) {
        List<UserSubScriptionRel> rels = relRepo.findByIdUserIdAndDeleted(userId, 0);
        if (rels == null || rels.isEmpty()) {
            return List.of();
        }

        // 先提取 PK 列表，再从 PK 中取出 keywordId，避免链式映射导致的类型推断问题
        List<UserSubScriptionRel.UserSubScriptionRelPK> pks = rels.stream()
                .map(UserSubScriptionRel::getId)
                .collect(Collectors.toList());

        List<Long> ids = pks.stream()
                .map(pk -> pk.getKeywordId())
                .filter(java.util.Objects::nonNull)
                .distinct()
                .collect(Collectors.toList());

        List<Keyword> pos = keywordRepo.findAllById(ids);
        return keywordConverter.toDOs(pos);
    }
}
