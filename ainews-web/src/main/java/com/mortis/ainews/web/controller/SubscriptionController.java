package com.mortis.ainews.web.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import com.mortis.ainews.domain.model.KeywordDO;
import com.mortis.ainews.domain.model.PageQuery;
import com.mortis.ainews.domain.model.PageData;
import com.mortis.ainews.domain.service.subscription.IKeywordSubscriptionService;
import com.mortis.ainews.web.converter.DtoConverter;
import com.mortis.ainews.web.dto.ApiResponse;
import com.mortis.ainews.web.dto.PageInfo;
import com.mortis.ainews.web.dto.PageResult;
import com.mortis.ainews.web.dto.subscription.SubscriptionRequest;
import com.mortis.ainews.web.dto.keyword.KeywordResponse;
import com.mortis.ainews.web.exception.ParamsValidationException;

@RestController
@RequestMapping("/subscription")
@RequiredArgsConstructor
public class SubscriptionController {

    private final IKeywordSubscriptionService subscriptionService;
    private final DtoConverter dtoConverter;

    // 读接口 GET - 添加分页支持
    @GetMapping("/getKeywordsByUserId")
    public ApiResponse<PageResult<KeywordResponse>> getKeywordsByUserId(
            @RequestParam("userId") Long userId,
            @RequestParam(value = "pageNum", required = false) Integer pageNum,
            @RequestParam(value = "pageSize", required = false) Integer pageSize) {

        if (userId == null || userId <= 0) {
            throw new ParamsValidationException("userId必须大于0");
        }

        PageInfo pageInfo = new PageInfo(pageNum, pageSize);

        // 使用服务层的分页方法替代手动分页
        PageData<KeywordDO> pageData = subscriptionService.findKeywordsByUserIdWithPaging(
                userId, new PageQuery(pageInfo.getPageNum(), pageInfo.getPageSize()));

        List<KeywordResponse> responses = dtoConverter.toKeywordResponses(pageData.getData());
        PageResult<KeywordResponse> pageResult = new PageResult<>(responses, pageData.getTotal(), pageInfo);

        return ApiResponse.success(pageResult);
    }

    // 写接口 POST
    @PostMapping("/subscribe")
    public ApiResponse<List<Long>> subscribe(@RequestBody SubscriptionRequest req) {
        if (req == null || req.getUserId() == null || req.getKeywordIds() == null || req.getKeywordIds().isEmpty()) {
            throw new ParamsValidationException("userId/keywordIds不能为空");
        }
        if (req.getUserId() <= 0) {
            throw new ParamsValidationException("userId必须大于0");
        }
        if (req.getKeywordIds().stream().anyMatch(id -> id == null || id <= 0)) {
            throw new ParamsValidationException("keywordIds中不能包含无效ID");
        }
        return ApiResponse.success(subscriptionService.subscribe(req.getUserId(), req.getKeywordIds()));
    }

    @PostMapping("/unsubscribe")
    public ApiResponse<List<Long>> unsubscribe(@RequestBody SubscriptionRequest req) {
        if (req == null || req.getUserId() == null || req.getKeywordIds() == null || req.getKeywordIds().isEmpty()) {
            throw new ParamsValidationException("userId/keywordIds不能为空");
        }
        if (req.getUserId() <= 0) {
            throw new ParamsValidationException("userId必须大于0");
        }
        if (req.getKeywordIds().stream().anyMatch(id -> id == null || id <= 0)) {
            throw new ParamsValidationException("keywordIds中不能包含无效ID");
        }
        return ApiResponse.success(subscriptionService.unsubscribe(req.getUserId(), req.getKeywordIds()));
    }
}
