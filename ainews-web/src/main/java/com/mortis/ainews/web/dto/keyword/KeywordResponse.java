package com.mortis.ainews.web.dto.keyword;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KeywordResponse {
    private Long id;
    private String content;
}
