package com.stockflow.inventory_service.mapper;

import com.stockflow.inventory_service.dto.PagedResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class PagedMapper {

    public <T, R> PagedResponseDto<R> toPagedResponseDto(Page<T> page, Function<T, R> mapperFunction) {
        List<R> content = page.getContent().stream()
                .map(mapperFunction)
                .collect(Collectors.toList());

        return PagedResponseDto.<R>builder()
                .content(content)
                .page(page.getNumber())
                .size(page.getSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .build();
    }
}
