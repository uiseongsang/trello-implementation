package com.winner.trelloimplementation.column.controller;

import com.winner.trelloimplementation.column.dto.ColumnRequestDto;
import com.winner.trelloimplementation.column.dto.ColumnResponseDto;
import com.winner.trelloimplementation.column.service.ColumnService;
import com.winner.trelloimplementation.common.dto.ApiResponseDto;
import com.winner.trelloimplementation.common.security.UserDetailsImpl;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/column")
public class ColumnController {

    private final ColumnService columnService;

    @PostMapping("")
    public ResponseEntity<ApiResponseDto> create(@RequestParam(required = false) Long lastPosition,
                                                 @RequestBody ColumnRequestDto requestDto,
                                                 @AuthenticationPrincipal UserDetailsImpl userDetails) {
        columnService.create(lastPosition, requestDto, userDetails.getUser());
        return ResponseEntity.status(201).body(new ApiResponseDto("컬럼이 생성 되었습니다", HttpStatus.CREATED.value()));
    }

//    @PutMapping("/{columnNo}")
//    public ResponseEntity<ApiResponseDto> update(){
//
//    }
//
    // 삭제시 순서를 맞춰야함 -> 1,2,3,4에서 3이 삭제가 된다면 1,2,4가 아니고 1,2,3이 되어야함(컬럼 이동 떄문에)
//    @DeleteMapping("/{columnNo}")
//    public ResponseEntity<ApiResponseDto> delete() {
//
//    }
//
//    @PatchMapping("/{columnNo}/{newPosition}")
//    public ResponseEntity<ApiResponseDto> move(@PathVariable Long columnNo, @PathVariable Long newPosition){
//        columnService.move(columnNo, newPosition);
//
//    }
//
//    @GetMapping("/{columnNo}")
//    public ResponseEntity<ApiResponseDto> getByNo(){
//
//    }
}