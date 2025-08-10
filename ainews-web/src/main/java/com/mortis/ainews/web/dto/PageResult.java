package com.mortis.ainews.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageResult<T> {
    private List<T> data; // 数据列表
    private long total; // 总记录数
    private int pageNum; // 当前页码
    private int pageSize; // 每页大小
    private int totalPages; // 总页数

    public PageResult(List<T> data, long total, PageInfo pageInfo) {
        this.data = data;
        this.total = total;
        this.pageNum = pageInfo.getPageNum();
        this.pageSize = pageInfo.getPageSize();
        this.totalPages = (int) Math.ceil((double) total / pageSize);
    }
}
