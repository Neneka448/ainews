package com.mortis.ainews.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageQuery {
    private int pageNum = 1; // 页码，从1开始
    private int pageSize = 10; // 每页大小，默认10

    public PageQuery(Integer pageNum, Integer pageSize) {
        this.pageNum = pageNum != null && pageNum > 0 ? pageNum : 1;
        this.pageSize = pageSize != null && pageSize > 0 ? Math.min(pageSize, 100) : 10; // 限制最大100
    }

    // 计算偏移量
    public int getOffset() {
        return (pageNum - 1) * pageSize;
    }
}
