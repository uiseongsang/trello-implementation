package com.winner.trelloimplementation.column.entity;

import com.winner.trelloimplementation.board.entity.Board;
import com.winner.trelloimplementation.user.entity.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Entity
@Table(name = "columns")
@Getter
@NoArgsConstructor
public class ColumnEntity {
    /**
     * 컬럼 - 연관관계 컬럼을 제외한 컬럼을 정의합니다.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "position", nullable = false, unique = true)
    private Long position;

    /**
     * 생성자 - 약속된 형태로만 생성가능하도록 합니다.
     */
    public ColumnEntity(String title) {
        this.title = title;
    }

    /**
     * 연관관계 - Foreign Key 값을 따로 컬럼으로 정의하지 않고 연관 관계로 정의합니다.
     */
    @ManyToOne
    @JoinColumn(name = "board_no", nullable = false)
    private Board boards;


//    @OneToMany(mappedBy = "cards", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
//    private List<Card> cardList = new ArrayList<>();

    /**
     * 연관관계 편의 메소드 - 반대쪽에는 연관관계 편의 메소드가 없도록 주의합니다.
     */


    /**
     * 서비스 메소드 - 외부에서 엔티티를 수정할 메소드를 정의합니다. (단일 책임을 가지도록 주의합니다.)
     */
    public void setPosition(Long position) {
        this.position = position;
    }

    public void addBoard (Board boards) {
        this.boards = boards;
    }
}
