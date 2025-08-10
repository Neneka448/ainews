package com.mortis.ainews.infra.service.impl.keyword;

import java.util.List;
import java.util.stream.Collectors;

import com.mortis.ainews.domain.model.KeywordDO;
import com.mortis.ainews.domain.model.PageQuery;
import com.mortis.ainews.domain.model.PageData;
import com.mortis.ainews.infra.persistence.converter.facade.ConverterFacade;
import com.mortis.ainews.infra.persistence.po.keywords.Keyword;
import com.mortis.ainews.infra.persistence.repository.interfaces.KeywordRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.mortis.ainews.domain.service.keyword.IKeywordService;

@Service
@RequiredArgsConstructor
public class KeywordService implements IKeywordService {

    private final KeywordRepository keywordRepo;

    private final ConverterFacade converter;

    @Override
    public List<Long> createKeywords(List<String> contents) {
        var keywords = keywordRepo.findByContentInAndDeleted(contents, 0);
        var contentsToInsert = contents.stream()
                .filter(content -> !keywords.stream().anyMatch(keyword -> keyword.getContent().equals(content)))
                .collect(Collectors.toList());

        if (contentsToInsert.isEmpty()) {
            return List.of();
        }

        List<Keyword> newKeywords = contentsToInsert.stream().map(content -> new Keyword(content, 0))
                .collect(Collectors.toList());

        var result = keywordRepo.saveAll(newKeywords);

        return result.stream().map(Keyword::getId).collect(Collectors.toList());
    }

    @Override
    public List<Long> deleteKeywords(List<Long> keywordIds) {
        var existKeywords = keywordRepo.findByIdInAndDeleted(keywordIds, 0);
        if (existKeywords == null || existKeywords.isEmpty()) {
            return List.of();
        }

        var result = keywordRepo.saveAll(existKeywords.stream().map(keyword -> {
            keyword.setDeleted(1);
            return keyword;
        }).collect(Collectors.toList()));

        return result.stream().map(Keyword::getId).collect(Collectors.toList());
    }

    /**
     * 更新关键字内容
     * 如果关键词id不存在则不处理
     * 
     * @param keywordDOs 关键字 DO 列表
     * @return 更新后的关键字 ID 列表
     */
    @Override
    public List<Long> updateKeywords(List<KeywordDO> keywordDOs) {
        var result = keywordRepo
                .saveAll(keywordDOs.stream().filter(keywordDo -> keywordDo.getId() != null)
                        .map(converter.keywordConverter::toPO).collect(Collectors.toList()));

        return result.stream().map(Keyword::getId).collect(Collectors.toList());
    }

    @Override
    public List<KeywordDO> findKeywordsByIds(List<Long> keywordIds) {
        return converter.keywordConverter
                .toDOs(keywordRepo.findAllById(keywordIds).stream().filter(keyword -> keyword.getDeleted() == 0)
                        .collect(Collectors.toList()));
    }

    @Override
    public PageData<KeywordDO> findKeywordsWithPaging(PageQuery pageQuery) {
        Pageable pageable = PageRequest.of(
                pageQuery.getPageNum() - 1, // Spring Data的页码从0开始
                pageQuery.getPageSize(),
                Sort.by(Sort.Direction.DESC, "id")); // 按ID倒序

        Page<Keyword> page = keywordRepo.findByDeleted(0, pageable);
        List<KeywordDO> keywordDOs = converter.keywordConverter.toDOs(page.getContent());

        return new PageData<>(keywordDOs, page.getTotalElements(), pageQuery);
    }

    @Override
    public PageData<KeywordDO> searchKeywordsByContent(String content, PageQuery pageQuery) {
        if (content == null || content.isBlank()) {
            return findKeywordsWithPaging(pageQuery);
        }

        Pageable pageable = PageRequest.of(
                pageQuery.getPageNum() - 1, // Spring Data的页码从0开始
                pageQuery.getPageSize(),
                Sort.by(Sort.Direction.DESC, "id")); // 按ID倒序

        Page<Keyword> page = keywordRepo.findByContentContainingAndDeleted(content.trim(), 0, pageable);
        List<KeywordDO> keywordDOs = converter.keywordConverter.toDOs(page.getContent());

        return new PageData<>(keywordDOs, page.getTotalElements(), pageQuery);
    }

}
