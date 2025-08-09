package com.mortis.ainews.infra.service.impl.subscription;

import java.util.List;
import java.util.stream.Collectors;

import com.mortis.ainews.domain.model.KeywordDO;
import com.mortis.ainews.infra.persistence.po.users.UserSubScriptionRel;
import com.mortis.ainews.infra.persistence.repository.impl.KeywordsSubscriptionRepositoryImpl;
import com.mortis.ainews.infra.persistence.repository.interfaces.UserSubScriptionRelRepository;
import com.mortis.ainews.infra.service.exception.ErrorCode;
import com.mortis.ainews.infra.service.exception.ServiceException;
import com.mortis.ainews.infra.service.impl.keyword.KeywordService;
import com.mortis.ainews.infra.service.impl.user.UserService;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import com.mortis.ainews.domain.service.subscription.IKeywordSubscriptionService;

@Service
@RequiredArgsConstructor
public class KeywordSubscriptionService implements IKeywordSubscriptionService {

    private final UserSubScriptionRelRepository relRepo;
    private final KeywordsSubscriptionRepositoryImpl subscriptionRepository;
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

        var relPOs = keywords.stream()
                .map(keyword -> new UserSubScriptionRel(
                        new UserSubScriptionRel.UserSubScriptionRelPK(userId, keyword.getId()), 0))
                .collect(Collectors.toList());

        return relRepo.saveAll(relPOs).stream().map(rel -> rel.getId().getKeywordId())
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
        relPOs.forEach(rel -> rel.setDeleted(1));

        return relRepo.saveAll(relPOs).stream().map(rel -> rel.getId().getKeywordId())
                .collect(Collectors.toList());
    }

    @Override
    public List<KeywordDO> findKeywordsByUserId(Long userId) {
        return subscriptionRepository.findKeywordsByUserId(userId);
    }
}
