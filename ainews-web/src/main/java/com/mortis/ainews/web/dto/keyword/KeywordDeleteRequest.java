package com.mortis.ainews.web.dto.keyword;

import java.util.List;

import lombok.Data;

@Data
public class KeywordDeleteRequest {
    private List<Long> ids;
}

