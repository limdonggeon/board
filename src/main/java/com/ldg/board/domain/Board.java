package com.ldg.board.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Board extends BaseEntity {

    // 반드시 Id 필요
    @Id
    // auto increment
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bno;

    // 칼럼의 길이와 null 허용 여부
    @Column(length = 500, nullable = false)
    private String title;

    @Column(length = 2000, nullable = false)
    private String content;

    @Column(length = 50, nullable = false)
    private String writer;


    public void change(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
