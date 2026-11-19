package com.ldg.board.repository.search;

import com.ldg.board.domain.Board;
import com.ldg.board.domain.QBoard;
import com.ldg.board.domain.QReply;
import com.ldg.board.dto.BoardDTO;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.query.JpaEntityGraph;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class BoardSearchImpl extends QuerydslRepositorySupport implements BoardSearch {

    public BoardSearchImpl() {
        super(Board.class);
    }

    @Override
    public Page<BoardDTO> searchAll(String[] types, String keyword, Pageable pageable) {

        QBoard board = QBoard.board;
        QReply reply = QReply.reply;

        JPQLQuery<Board> query = from (board);
        query.leftJoin(reply).on(reply.board.eq(board));

        query.groupBy(board);

        // 검색 조건과 키워드가 있으면
        if ((types != null && types.length > 0) && keyword != null) {

            BooleanBuilder booleanBuilder = new BooleanBuilder();

            for (String type : types) {
                switch (type) {
                    case "t":
                        booleanBuilder.or(board.title.contains(keyword));
                        break;
                    case "c":
                        booleanBuilder.or(board.content.contains(keyword));
                        break;
                    case "w":
                        booleanBuilder.or(board.writer.contains(keyword));
                        break;
                }
            }

            query.where(booleanBuilder);
        }

        // bno > 0
        query.where(board.bno.gt(0L));

        JPQLQuery<BoardDTO> dtoQuery = query.select(Projections.bean(BoardDTO.class,
                board.bno,
                board.title,
                board.content,
                board.writer,
                board.regDate,
                board.modDate,
                reply.rno.count().as("replyCount")
        ));

        // paging
        this.getQuerydsl().applyPagination(pageable, dtoQuery);

        List<BoardDTO> dtolist = dtoQuery.fetch();

        long count = query.fetchCount();

        return new PageImpl<>(dtolist, pageable, count);
    }
}
