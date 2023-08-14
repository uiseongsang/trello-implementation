package com.winner.trelloimplementation.column.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
public class ColumnRequestDto {
    private String title;

    @Builder
    public ColumnRequestDto (String title) {
        this.title = title;
    }
}
