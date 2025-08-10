package com.mortis.ainews.web.dto.subscription;

import java.util.List;

import lombok.Data;

@Data
public class SubscriptionRequest {
    private Long userId;
    private List<Long> keywordIds;
}

