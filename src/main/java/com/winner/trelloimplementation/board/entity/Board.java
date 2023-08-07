package com.winner.trelloimplementation.board.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Columns;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "boards")
@Getter
@NoArgsConstructor
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "color", nullable = false)
    private String color;

//    @OneToMany(mappedBy = "boards", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
//    private List<> columns = new ArrayList<>();
//
//    @OneToMany(mappedBy = "boards", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
//    private List<Member> members = new ArrayList<>();
}
