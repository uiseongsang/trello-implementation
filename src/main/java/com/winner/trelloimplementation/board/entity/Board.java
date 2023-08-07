package com.winner.trelloimplementation.board.entity;

import com.winner.trelloimplementation.board.dto.ModifyBoardRequestDto;
import com.winner.trelloimplementation.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "boards")
@Getter
@NoArgsConstructor
public class Board {

    /**
     * 컬럼 - 연관관계 컬럼을 제외한 컬럼을 정의합니다.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "color", nullable = false)
    private String color;
    /**
     * 생성자 - 약속된 형태로만 생성가능하도록 합니다.
     */

    public Board (String title, String description, String color) {
        this.title = title;
        this.description = description;
        this.color = color;
    }

    /**
     * 연관관계 - Foreign Key 값을 따로 컬럼으로 정의하지 않고 연관 관계로 정의합니다.
     */

//    @OneToMany(mappedBy = "boards", cascade = CascadeType.REMOVE, orphanRemoval = true)
//    private List<> columns = new ArrayList<>();
//
    @OneToMany(mappedBy = "boards", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<BoardMember> members = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    User user;

    public void update (ModifyBoardRequestDto modifyBoardRequestDto) {
        this.title = modifyBoardRequestDto.getTitle();
        this.description = modifyBoardRequestDto.getDescription();
        this.color = modifyBoardRequestDto.getColor();
    }

    public void addMember (BoardMember boardMember) {
        this.members.add(boardMember);
    }

    public void addUser (User user) {
        this.user = user;
    }
}
