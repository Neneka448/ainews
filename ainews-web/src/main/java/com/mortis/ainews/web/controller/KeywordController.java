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
import com.mortis.ainews.domain.service.keyword.IKeywordService;
import com.mortis.ainews.web.converter.DtoConverter;
import com.mortis.ainews.web.dto.ApiResponse;
import com.mortis.ainews.web.dto.PageInfo;
import com.mortis.ainews.web.dto.PageResult;
import com.mortis.ainews.web.dto.keyword.KeywordCreateRequest;
import com.mortis.ainews.web.dto.keyword.KeywordDeleteRequest;
import com.mortis.ainews.web.dto.keyword.KeywordUpdateRequest;
import com.mortis.ainews.web.dto.keyword.KeywordResponse;

import com.mortis.ainews.web.exception.ParamsValidationException;

@RestController
@RequestMapping("/keyword")
@RequiredArgsConstructor
public class KeywordController {

    private final IKeywordService keywordService;
    private final DtoConverter dtoConverter;

    // 读接口统一 GET
    @GetMapping("/getByIds")
    public ApiResponse<List<KeywordResponse>> getByIds(@RequestParam("ids") List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            throw new ParamsValidationException("ids不能为空");
        }
        if (ids.stream().anyMatch(id -> id == null || id <= 0)) {
            throw new ParamsValidationException("ids中不能包含无效ID");
        }
        List<KeywordDO> keywords = keywordService.findKeywordsByIds(ids);
        return ApiResponse.success(dtoConverter.toKeywordResponses(keywords));
    }

    // 分页查询所有关键词
    @GetMapping("/list")
    public ApiResponse<PageResult<KeywordResponse>> list(
            @RequestParam(value = "pageNum", required = false) Integer pageNum,
            @RequestParam(value = "pageSize", required = false) Integer pageSize) {

        PageInfo pageInfo = new PageInfo(pageNum, pageSize);

        // 创建领域层的分页查询对象
        PageQuery pageQuery = new PageQuery(pageInfo.getPageNum(), pageInfo.getPageSize());

        // 调用Service层的分页查询方法
        PageData<KeywordDO> pageData = keywordService.findKeywordsWithPaging(pageQuery);

        // 转换为Web层的响应对象
        List<KeywordResponse> responses = dtoConverter.toKeywordResponses(pageData.getData());
        PageResult<KeywordResponse> pageResult = new PageResult<>(responses, pageData.getTotal(), pageInfo);
        return ApiResponse.success(pageResult);
    }

    // 写接口统一 POST
    @PostMapping("/create")
    public ApiResponse<List<Long>> create(@RequestBody KeywordCreateRequest req) {
        if (req == null || req.getContents() == null || req.getContents().isEmpty()) {
            throw new ParamsValidationException("contents不能为空");
        }
        if (req.getContents().stream().anyMatch(content -> content == null || content.isBlank())) {
            throw new ParamsValidationException("关键词内容不能为空");
        }
        if (req.getContents().stream().anyMatch(content -> content.length() > 100)) {
            throw new ParamsValidationException("关键词长度不能超过100个字符");
        }
        return ApiResponse.success(keywordService.createKeywords(req.getContents()));
    }

    @PostMapping("/update")
    public ApiResponse<List<Long>> update(@RequestBody KeywordUpdateRequest req) {
        if (req == null || req.getKeywords() == null || req.getKeywords().isEmpty()) {
            throw new ParamsValidationException("keywords不能为空");
        }

        // 验证DTO数据有效性
        req.getKeywords().forEach(item -> {
            if (item.getId() == null || item.getId() <= 0) {
                throw new ParamsValidationException("关键词ID不能为空且必须大于0");
            }
            if (item.getContent() == null || item.getContent().isBlank()) {
                throw new ParamsValidationException("关键词内容不能为空");
            }
            if (item.getContent().length() > 100) {
                throw new ParamsValidationException("关键词长度不能超过100个字符");
            }
        });

        // 使用转换器将DTO转换为领域模型
        List<KeywordDO> keywordDOs = dtoConverter.toKeywordDOs(req.getKeywords());
        return ApiResponse.success(keywordService.updateKeywords(keywordDOs));
    }

    @PostMapping("/delete")
    public ApiResponse<List<Long>> delete(@RequestBody KeywordDeleteRequest req) {
        if (req == null || req.getIds() == null || req.getIds().isEmpty()) {
            throw new ParamsValidationException("ids不能为空");
        }
        if (req.getIds().stream().anyMatch(id -> id == null || id <= 0)) {
            throw new ParamsValidationException("ids中不能包含无效ID");
        }
        return ApiResponse.success(keywordService.deleteKeywords(req.getIds()));
    }
}
