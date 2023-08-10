package com.winner.trelloimplementation.column.dto;

import com.winner.trelloimplementation.card.dto.CardResponseDto;
import com.winner.trelloimplementation.card.entity.Card;
import com.winner.trelloimplementation.column.entity.ColumnEntity;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ColumnResponseDto {
    private Long id;
    private String title;
    private Integer cardListSize;
    private List<CardResponseDto> cardList = new ArrayList<CardResponseDto>();

    public ColumnResponseDto(ColumnEntity columnEntity) {
        this.id = columnEntity.getId();
        this.title = columnEntity.getTitle();
        this.cardListSize = columnEntity.getCardList().size();
        for(Card card : columnEntity.getCardList()) {
            cardList.add(new CardResponseDto(card));
        }
    }
}
