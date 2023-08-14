package com.winner.trelloimplementation.web;

import com.winner.trelloimplementation.board.entity.Board;
import com.winner.trelloimplementation.board.service.BoardService;
import com.winner.trelloimplementation.board.service.BoardServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class WebController {

    @GetMapping("/web")
    public String home() {
        return "index";
    }


    @GetMapping("/web/boardMember/{boardId}")
    public String showBoardMemberPage(@PathVariable Long boardId, Model model) {

        model.addAttribute("boardId", boardId);

        return "boardMember";
    }
}
