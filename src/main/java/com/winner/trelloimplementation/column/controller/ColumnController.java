package com.winner.trelloimplementation.column.controller;

import com.winner.trelloimplementation.column.dto.ColumnRequestDto;
import com.winner.trelloimplementation.column.dto.ColumnResponseDto;
import com.winner.trelloimplementation.column.service.ColumnService;
import lombok.RequiredArgsConstructor;
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
    public ResponseEntity<ApiResponseDto> create(@RequestBody ColumnRequestDto requestDto,
                                                 @AuthenticationPrincipal UserDetailsImpl userDetails){
        columnService.create(requestDto,userDetails.getUser());
        return ResponseEntity.status(201).body( new ApiResponseDto("컬럼이 생성 되었습니다", status.CREATED.value()));
    }

//    @PutMapping("/{columnNo}")
//    public ResponseEntity<ApiResponseDto> update(){
//
//    }
//
//    @DeleteMapping("/{columnNo}")
//    public ResponseEntity<ApiResponseDto> delete() {
//
//    }
//
//    @PatchMapping("/{columnNo}")
//    public ResponseEntity<ApiResponseDto> move(){
//
//    }
//
//    @GetMapping("/{columnNo}")
//    public ResponseEntity<ApiResponseDto> getByNo(){
//
//    }
}
