package com.ldg.board.repository;

import com.ldg.board.domain.Board;
import com.ldg.board.repository.search.BoardSearch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long>, BoardSearch {
}
