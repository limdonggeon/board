package com.ldg.board.repository.search;

import com.ldg.board.domain.Board;
import com.ldg.board.dto.BoardDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BoardSearch {
    Page<BoardDTO> searchAll(String[] types, String keyword, Pageable pageable);
}
