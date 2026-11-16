package com.ldg.board.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardDTO {

    private Long bno;

    @NotEmpty
    @Size(max =100)
    private String title;

    @NotEmpty
    private String content;

    private String writer;

    private LocalDateTime regDate;

    private LocalDateTime modDate;
}
