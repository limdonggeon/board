package com.ldg.board.service;

import com.ldg.board.dto.PageRequestDTO;
import com.ldg.board.dto.PageResponseDTO;
import com.ldg.board.dto.ReplyDTO;

public interface ReplyService {

    Long register(ReplyDTO replyDTO);

    ReplyDTO read(Long rno);

    void modify(ReplyDTO replyDTO);

    void remove(Long rno);

    PageResponseDTO<ReplyDTO> getListOfBoard(Long bno, PageRequestDTO pageRequestDTO);
}
