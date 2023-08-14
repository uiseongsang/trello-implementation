package com.winner.trelloimplementation.column.dto;

import com.winner.trelloimplementation.card.dto.CardResponseDto;
import com.winner.trelloimplementation.card.entity.Card;
import com.winner.trelloimplementation.column.entity.ColumnEntity;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class GetOneColumnResponseDto {
    private Long id;
    private String title;
    private List<CardResponseDto> cardList = new ArrayList<CardResponseDto>();

    public GetOneColumnResponseDto(ColumnEntity columnEntity) {
        this.id = columnEntity.getId();
        this.title = columnEntity.getTitle();
        for(Card card : columnEntity.getCardList()) {
            cardList.add(new CardResponseDto(card));
        }
    }
}
