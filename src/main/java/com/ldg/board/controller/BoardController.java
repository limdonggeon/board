package com.ldg.board.controller;

import com.ldg.board.dto.BoardDTO;
import com.ldg.board.dto.PageRequestDTO;
import com.ldg.board.dto.PageResponseDTO;
import com.ldg.board.service.BoardService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@Log4j2
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @Operation(summary = "Board List", description = "GET 방식으로 특정 게시글 목록 조회")
    @GetMapping("/boards")
    public ResponseEntity<PageResponseDTO<BoardDTO>> list(PageRequestDTO pageRequestDTO) {

        PageResponseDTO<BoardDTO> responseDTO = boardService.list(pageRequestDTO);

        log.info("responseDTO: {}", responseDTO);

        return ResponseEntity.ok(responseDTO);
    }

    @Operation(summary = "Board Register", description = "POST 방식으로 게시글 등록")
    @PostMapping("/boards")
    public ResponseEntity<?> register(@Valid @RequestBody BoardDTO boardDTO, BindingResult bindingResult) {

        log.info("board POST register : {}", boardDTO);

        // 유효성 검사 에러가 발생한 경우
        if (bindingResult.hasErrors()) {
            log.info("has validation errors......");

            // 필드별 에러 메시지를 정리해서 Map에 담기
            Map<String, String> errorMap = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error -> {
                errorMap.put(error.getField(), error.getDefaultMessage());
            });

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMap);
        }

        Long bno = boardService.register(boardDTO);

        log.info("bno: {}", bno);

        Map<String, Long> response = new HashMap<>();
        response.put("bno", bno);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Board Read", description = "GET 방식으로 특정 게시글 조회")
    @GetMapping("/boards/{bno}")
    public ResponseEntity<BoardDTO> read(@PathVariable("bno") Long bno) {

        log.info("board read bno: {}", bno);

        // 서비스 계층을 통해 해당 번호의 게시글 데이터를 가져옴
        BoardDTO boardDTO = boardService.readOne(bno);

        // HTTP 200 OK 상태 코드와 함께 게시글 DTO를 JSON으로 응답
        return ResponseEntity.ok(boardDTO);
    }

    @Operation(summary = "Board Modify", description = "PUT 방식으로 특정 게시글 수정")
    @PutMapping("/boards/{bno}")
    public ResponseEntity<?> modify(@PathVariable("bno") Long bno,
                                    @Valid @RequestBody BoardDTO boardDTO,
                                    BindingResult bindingResult) {

        log.info("board modify dto: {}", boardDTO);

        // 유효성 검사 에러가 발생한 경우
        if (bindingResult.hasErrors()) {
            log.info("has validation errors......");

            Map<String, String> errorMap = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error -> {
                errorMap.put(error.getField(), error.getDefaultMessage());
            });

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMap);
        }

        boardService.modify(boardDTO);

        Map<String, Object> response = new HashMap<>();
        response.put("result", "modified");
        response.put("bno", bno);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Board Remove", description = "DELETE 방식으로 특정 게시글 삭제")
    @DeleteMapping("/boards/{bno}")
    public ResponseEntity<Map<String, String>> remove(@PathVariable("bno") Long bno) {

        log.info("board remove bno: {}", bno);

        boardService.remove(bno);

        Map<String, String> response = Map.of("result", "removed");

        return ResponseEntity.ok(response);
    }

}
