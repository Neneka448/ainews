package com.mortis.ainews.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageData<T> {
    private List<T> data; // 数据列表
    private long total; // 总记录数
    private int pageNum; // 当前页码
    private int pageSize; // 每页大小
    private int totalPages; // 总页数

    public PageData(List<T> data, long total, PageQuery pageQuery) {
        this.data = data;
        this.total = total;
        this.pageNum = pageQuery.getPageNum();
        this.pageSize = pageQuery.getPageSize();
        this.totalPages = (int) Math.ceil((double) total / pageSize);
    }
}
