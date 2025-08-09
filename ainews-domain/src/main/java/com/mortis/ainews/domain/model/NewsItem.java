package com.mortis.ainews.domain.model;

import lombok.Data;

@Data
public class NewsItem {
    private String id;
    private String title;
    private String content;
    private java.time.Instant publishTime;
    private String source;
}