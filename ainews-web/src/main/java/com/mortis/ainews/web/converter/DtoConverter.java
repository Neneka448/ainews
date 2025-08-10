package com.mortis.ainews.web.converter;

import com.mortis.ainews.domain.model.UserDO;
import com.mortis.ainews.domain.model.KeywordDO;
import com.mortis.ainews.web.dto.user.UserResponse;
import com.mortis.ainews.web.dto.keyword.KeywordResponse;
import com.mortis.ainews.web.dto.keyword.KeywordUpdateItem;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DtoConverter {

    public UserResponse toUserResponse(UserDO userDO) {
        if (userDO == null) {
            return null;
        }
        return new UserResponse(userDO.getId(), userDO.getUserName(), userDO.getAc());
    }

    public List<UserResponse> toUserResponses(List<UserDO> userDOs) {
        return userDOs.stream()
                .map(this::toUserResponse)
                .collect(Collectors.toList());
    }

    public KeywordResponse toKeywordResponse(KeywordDO keywordDO) {
        if (keywordDO == null) {
            return null;
        }
        return new KeywordResponse(keywordDO.getId(), keywordDO.getContent());
    }

    public List<KeywordResponse> toKeywordResponses(List<KeywordDO> keywordDOs) {
        return keywordDOs.stream()
                .map(this::toKeywordResponse)
                .collect(Collectors.toList());
    }

    /**
     * 将KeywordUpdateItem转换为KeywordDO
     * 用于将Web层DTO转换为领域模型
     */
    public KeywordDO toKeywordDO(KeywordUpdateItem item) {
        if (item == null) {
            return null;
        }
        KeywordDO keywordDO = new KeywordDO();
        keywordDO.setId(item.getId());
        keywordDO.setContent(item.getContent());
        return keywordDO;
    }

    /**
     * 批量将KeywordUpdateItem转换为KeywordDO
     */
    public List<KeywordDO> toKeywordDOs(List<KeywordUpdateItem> items) {
        if (items == null) {
            return null;
        }
        return items.stream()
                .map(this::toKeywordDO)
                .collect(Collectors.toList());
    }
}
