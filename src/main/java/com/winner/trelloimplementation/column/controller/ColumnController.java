package com.winner.trelloimplementation.column.controller;

import com.winner.trelloimplementation.column.dto.ColumnRequestDto;
import com.winner.trelloimplementation.column.dto.GetOneColumnResponseDto;
import com.winner.trelloimplementation.column.service.ColumnService;
import com.winner.trelloimplementation.common.dto.ApiResponseDto;
import com.winner.trelloimplementation.common.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ColumnController {

    private final ColumnService columnService;

    @PostMapping("/board/{boardNo}/column")
    public ResponseEntity<ApiResponseDto> create(@PathVariable Long boardNo,
                                                 @RequestParam(required = false) Long lastPosition,
                                                 @RequestBody ColumnRequestDto requestDto,
                                                 @AuthenticationPrincipal UserDetailsImpl userDetails) {
        columnService.create(boardNo, lastPosition, requestDto, userDetails.getUser());
        return ResponseEntity.status(201).body(new ApiResponseDto("컬럼이 생성 되었습니다", HttpStatus.CREATED.value()));
    }

    @PutMapping("/column/{columnNo}")
    public ResponseEntity<ApiResponseDto> update(@PathVariable Long columnNo,
                                                 @RequestBody ColumnRequestDto requestDto,
                                                 @AuthenticationPrincipal UserDetailsImpl userDetails) {
        columnService.update(columnNo, requestDto, userDetails.getUser());
        return ResponseEntity.status(202).body(new ApiResponseDto("컬럼이 수정 되었습니다", HttpStatus.ACCEPTED.value()));
    }

    // 삭제시 순서를 맞춰야함 -> 1,2,3,4에서 3이 삭제가 된다면 1,2,4가 아니고 1,2,3이 되어야함(컬럼 이동 떄문에)
    @DeleteMapping("/column/{columnNo}")
    public ResponseEntity<ApiResponseDto> delete(@PathVariable Long columnNo) {
        columnService.delete(columnNo);
        return ResponseEntity.status(200).body(new ApiResponseDto("컬럼이 삭제 되었습니다", HttpStatus.OK.value()));
    }

    @PatchMapping("/board/{boardNo}/column/{currentPosition}/change-column/{newPosition}")
    public ResponseEntity<ApiResponseDto> move(@PathVariable Long boardNo, @PathVariable Long currentPosition, @PathVariable Long newPosition) {
        columnService.move(boardNo, currentPosition, newPosition);
        return ResponseEntity.status(200).body(new ApiResponseDto("컬럼이 이동이 되었습니다", HttpStatus.OK.value()));

    }

    @GetMapping("/column/{columnNo}")
    public GetOneColumnResponseDto getOneColumn(@PathVariable Long columnNo){
        return columnService.getOneColumn(columnNo);
    }
}
