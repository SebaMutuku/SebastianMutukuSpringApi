package com.sebamutuku.sebastianmutukuspringtest.utils;

import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

@Slf4j
public class Utils {
    public static <T> Page<T> pageData(List<T> data, @NotNull Pageable pageable) {
        if (data == null || data.isEmpty()) return new PageImpl<>(List.of(), pageable, 0);
        if (!pageable.isPaged()) {
            return new PageImpl<>(data, pageable, data.size());
        }
        int totalSize = data.size();
        int pageSize = pageable.getPageSize();
        int totalPages = (int) Math.ceil((double) totalSize / pageSize);
        log.info("Total pages [{}]", totalPages);
        int requestedPage = pageable.getPageNumber();
        int pageStart = requestedPage * pageSize;
        if (pageStart >= totalSize) return new PageImpl<>(List.of(), pageable, totalSize);
        int pageEnd = Math.min(pageStart + pageSize, totalSize);
        List<T> pagedData = data.subList(pageStart, pageEnd);
        return new PageImpl<>(pagedData, pageable, totalSize);
    }
}
