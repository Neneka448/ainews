package com.mortis.ainews.infra.service.impl.subscription;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.mortis.ainews.domain.model.KeywordDO;
import com.mortis.ainews.domain.model.PageQuery;
import com.mortis.ainews.domain.model.PageData;
import com.mortis.ainews.domain.repository.SubscriptionRepository.IKeywordsSubscriptionRepository;
import com.mortis.ainews.infra.persistence.po.users.UserSubScriptionRel;
import com.mortis.ainews.infra.persistence.repository.interfaces.UserSubScriptionRelRepository;
import com.mortis.ainews.infra.service.exception.ErrorCode;
import com.mortis.ainews.infra.service.exception.ServiceException;
import com.mortis.ainews.infra.service.impl.keyword.KeywordService;
import com.mortis.ainews.infra.service.impl.user.UserService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import com.mortis.ainews.domain.service.subscription.IKeywordSubscriptionService;

@Service
@RequiredArgsConstructor
@Transactional
public class KeywordSubscriptionService implements IKeywordSubscriptionService {

        private final UserSubScriptionRelRepository relRepo;
        private final IKeywordsSubscriptionRepository subscriptionRepository;
        private final UserService userService;
        private final KeywordService keywordService;

        @Override
        public List<Long> subscribe(Long userId, List<Long> keywordIds) {
                var user = userService.findUserById(userId);
                if (user.isEmpty()) {
                        throw new ServiceException(ErrorCode.USER_NOT_FOUND);
                }

                var keywords = keywordService.findKeywordsByIds(keywordIds);
                if (keywords.isEmpty()) {
                        throw new ServiceException(ErrorCode.KEYWORDS_LIST_EMPTY);
                }

                // 检查已存在的订阅关系
                var existingRelPKs = keywords.stream()
                                .map(keyword -> new UserSubScriptionRel.UserSubScriptionRelPK(userId, keyword.getId()))
                                .collect(Collectors.toList());

                var existingRels = relRepo.findAllById(existingRelPKs);

                // 分离需要新建和需要恢复的订阅
                var existingKeywordIds = existingRels.stream()
                                .map(rel -> rel.getId().getKeywordId())
                                .collect(Collectors.toList());

                // 恢复已删除的订阅
                var relsToRestore = existingRels.stream()
                                .filter(rel -> rel.getDeleted() == 1)
                                .collect(Collectors.toList());
                relsToRestore.forEach(rel -> rel.setDeleted(0));

                // 创建新的订阅关系
                var newRelPOs = keywords.stream()
                                .filter(keyword -> !existingKeywordIds.contains(keyword.getId()))
                                .map(keyword -> new UserSubScriptionRel(
                                                new UserSubScriptionRel.UserSubScriptionRelPK(userId, keyword.getId()),
                                                0))
                                .collect(Collectors.toList());

                // 保存所有变更
                var allRelsToSave = new ArrayList<UserSubScriptionRel>();
                allRelsToSave.addAll(relsToRestore);
                allRelsToSave.addAll(newRelPOs);

                if (allRelsToSave.isEmpty()) {
                        // 所有关键词都已经是有效订阅状态
                        return keywords.stream().map(KeywordDO::getId).collect(Collectors.toList());
                }

                return relRepo.saveAll(allRelsToSave).stream()
                                .map(rel -> rel.getId().getKeywordId())
                                .collect(Collectors.toList());
        }

        @Override
        public List<Long> unsubscribe(Long userId, List<Long> keywordIds) {
                var user = userService.findUserById(userId);
                if (user.isEmpty()) {
                        throw new ServiceException(ErrorCode.USER_NOT_FOUND);
                }

                var keywords = keywordService.findKeywordsByIds(keywordIds);
                if (keywords.isEmpty()) {
                        throw new ServiceException(ErrorCode.KEYWORDS_LIST_EMPTY);
                }

                var relPKs = keywords.stream()
                                .map(keyword -> new UserSubScriptionRel.UserSubScriptionRelPK(userId, keyword.getId()))
                                .collect(Collectors.toList());

                var relPOs = relRepo.findAllById(relPKs);

                // 只处理存在且未删除的订阅关系
                var validRels = relPOs.stream()
                                .filter(rel -> rel.getDeleted() == 0)
                                .collect(Collectors.toList());

                if (validRels.isEmpty()) {
                        // 没有有效的订阅关系需要取消
                        return List.of();
                }

                validRels.forEach(rel -> rel.setDeleted(1));

                return relRepo.saveAll(validRels).stream()
                                .map(rel -> rel.getId().getKeywordId())
                                .collect(Collectors.toList());
        }

        @Override
        public List<KeywordDO> findKeywordsByUserId(Long userId) {
                return subscriptionRepository.findKeywordsByUserId(userId);
        }

        @Override
        public PageData<KeywordDO> findKeywordsByUserIdWithPaging(Long userId, PageQuery pageQuery) {
                // 委托给repository层处理，避免代码重复，确保单一职责原则
                return subscriptionRepository.findKeywordsByUserIdWithPaging(userId, pageQuery);
        }
}
