package com.mortis.ainews.web.dto.keyword;

import java.util.List;

import lombok.Data;

/**
 * 关键词批量更新请求DTO
 * 解耦Web层和领域层，避免直接使用领域模型
 */
@Data
public class KeywordUpdateRequest {

    /**
     * 待更新的关键词列表
     */
    private List<KeywordUpdateItem> keywords;
}
