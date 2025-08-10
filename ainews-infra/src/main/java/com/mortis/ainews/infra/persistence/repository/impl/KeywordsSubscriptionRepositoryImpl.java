package com.mortis.ainews.infra.persistence.repository.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import com.mortis.ainews.domain.model.KeywordDO;
import com.mortis.ainews.domain.model.PageQuery;
import com.mortis.ainews.domain.model.PageData;
import com.mortis.ainews.domain.repository.SubscriptionRepository.IKeywordsSubscriptionRepository;
import com.mortis.ainews.infra.persistence.converter.KeywordConverter;
import com.mortis.ainews.infra.persistence.po.keywords.Keyword;
import com.mortis.ainews.infra.persistence.po.users.UserSubScriptionRel;
import com.mortis.ainews.infra.persistence.repository.interfaces.KeywordRepository;
import com.mortis.ainews.infra.persistence.repository.interfaces.UserSubScriptionRelRepository;

@Repository
@RequiredArgsConstructor
public class KeywordsSubscriptionRepositoryImpl implements IKeywordsSubscriptionRepository {

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
                if (rels.isEmpty()) {
                        return List.of();
                }

                List<UserSubScriptionRel.UserSubScriptionRelPK> pks = rels.stream()
                                .map(UserSubScriptionRel::getId)
                                .collect(Collectors.toList());

                List<Long> ids = pks.stream()
                                .map(pk -> pk.getKeywordId())
                                .distinct()
                                .collect(Collectors.toList());

                List<Keyword> pos = keywordRepo.findByIdInAndDeleted(ids, 0);
                return keywordConverter.toDOs(pos);
        }

        @Override
        @Transactional(readOnly = true)
        public PageData<KeywordDO> findKeywordsByUserIdWithPaging(Long userId, PageQuery pageQuery) {
                Pageable pageable = PageRequest.of(
                                pageQuery.getPageNum() - 1, // Spring Data从0开始
                                pageQuery.getPageSize());

                // 使用优化的JOIN查询，一次性获取所有需要的数据，解决N+1查询问题
                Page<Keyword> keywordPage = relRepo.findKeywordsByUserIdWithJoin(userId, pageable);

                // 直接转换为领域对象
                List<KeywordDO> keywordDOs = keywordConverter.toDOs(keywordPage.getContent());

                return new PageData<>(
                                keywordDOs,
                                keywordPage.getTotalElements(),
                                pageQuery);
        }
}
