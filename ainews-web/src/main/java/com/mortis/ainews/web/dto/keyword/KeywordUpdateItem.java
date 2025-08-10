package com.mortis.ainews.web.dto.keyword;

import lombok.Data;

/**
 * 关键词更新项DTO
 * 用于Web层接收客户端的关键词更新请求，避免直接使用领域模型
 */
@Data
public class KeywordUpdateItem {

    /**
     * 关键词ID
     */
    private Long id;

    /**
     * 关键词内容
     */
    private String content;

    public KeywordUpdateItem() {
    }

    public KeywordUpdateItem(Long id, String content) {
        this.id = id;
        this.content = content;
    }
}
