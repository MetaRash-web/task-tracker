package com.metarash.backend.model.dto;

import com.metarash.dto.TaskDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;

import java.util.List;

public record TaskResponse(
        List<TaskDto> content,
        Pageable pageable,
        int size,
        int number,
        Sort sort,
        boolean first,
        boolean last,
        int numberOfElements,
        boolean empty,
        boolean hasNext
) {
    public static TaskResponse fromSlice(Slice<TaskDto> slice) {
        return new TaskResponse(
                slice.getContent(),
                slice.getPageable(),
                slice.getSize(),
                slice.getNumber(),
                slice.getSort(),
                slice.isFirst(),
                slice.isLast(),
                slice.getNumberOfElements(),
                slice.isEmpty(),
                slice.hasNext()
        );
    }
}
