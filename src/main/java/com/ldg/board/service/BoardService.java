package com.ldg.board.service;

import com.ldg.board.dto.BoardDTO;
import com.ldg.board.dto.PageRequestDTO;
import com.ldg.board.dto.PageResponseDTO;

public interface BoardService {

    Long register(BoardDTO boardDTO);

    BoardDTO readOne(Long bno);

    void modify(BoardDTO boardDTO);

    void remove(Long bno);

    PageResponseDTO<BoardDTO> list(PageRequestDTO pageRequestDTO);
}
