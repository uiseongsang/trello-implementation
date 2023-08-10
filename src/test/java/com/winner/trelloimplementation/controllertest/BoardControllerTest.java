package com.winner.trelloimplementation.controllertest;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.winner.trelloimplementation.WithMockCustomUser;
import com.winner.trelloimplementation.board.dto.CreateBoardRequestDto;
import com.winner.trelloimplementation.board.dto.EmailRequestDto;
import com.winner.trelloimplementation.board.dto.ModifyBoardRequestDto;
import com.winner.trelloimplementation.board.entity.Board;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BoardControllerTest {

    @Autowired
    ObjectMapper mapper;

    @Autowired
    MockMvc mvc;

    @Autowired
    private WebApplicationContext ctx;

    @BeforeAll
    public void setup() {
        this.mvc = MockMvcBuilders.webAppContextSetup(ctx)
                .addFilters(new CharacterEncodingFilter("UTF-8", true))  // 필터 추가
                .apply(springSecurity())
                .alwaysDo(print())
                .build();
    }

    @Test
    @DisplayName("보드 생성 테스트")
    @WithMockCustomUser
    void creatBoard () throws Exception {
        // given
        String title = "testBoard";
        String description = "testBoardDescription";
        String color = "testBoardColor";
        CreateBoardRequestDto createBoardRequestDto = CreateBoardRequestDto.builder()
                .title(title)
                .description(description)
                .color(color)
                .build();
        // when
        String body = mapper.writeValueAsString(
                createBoardRequestDto
        );
        //then
        mvc.perform(post("/api/board")
                        .content(body) //HTTP Body에 데이터를 담는다
                        .contentType(MediaType.APPLICATION_JSON) //보내는 데이터의 타입을 명시
                )
                .andExpect(status().isCreated())
                .andExpect(content().string("{\"msg\":\"Board 생성 성공\",\"statusCode\":201}"));
    }

    @Test
    @DisplayName("보드 수정 테스트")
    @WithMockCustomUser
    void modifyBoard () throws Exception {
        // given
        String title = "modifyBoard";
        String description = "modifyBoardDescription";
        String color = "modifyBoardColor";
        ModifyBoardRequestDto modifyBoardRequestDto = ModifyBoardRequestDto.builder()
                .title(title)
                .description(description)
                .color(color)
                .build();
        // when
        Long boardNo = 3L;
        String body = mapper.writeValueAsString(
                modifyBoardRequestDto
        );
        //then
        mvc.perform(put("/api/board/" + boardNo)
                        .content(body) //HTTP Body에 데이터를 담는다
                        .contentType(MediaType.APPLICATION_JSON) //보내는 데이터의 타입을 명시
                )
                .andExpect(status().isAccepted())
                .andExpect(content().string("{\"msg\":\"Board 수정 성공\",\"statusCode\":202}"));
    }

    @Test
    @DisplayName("보드 삭제 테스트")
    @WithMockCustomUser
    void deleteBoard () throws Exception {
        // given
        // when
        Long boardNo = 3L;
        //then
        mvc.perform(delete("/api/board/" + boardNo)
                        .contentType(MediaType.APPLICATION_JSON) //보내는 데이터의 타입을 명시
                )
                .andExpect(status().isOk())
                .andExpect(content().string("{\"msg\":\"Board 삭제 성공\",\"statusCode\":200}"));
    }

    @Test
    @DisplayName("보드 초대 테스트")
    @WithMockCustomUser
    void inviteBoard () throws Exception {
        // given
        String email = "wjsdudals56@gmail.com";
        EmailRequestDto emailRequestDto = EmailRequestDto.builder()
                .email(email)
                .build();
        // when
        Long boardNo = 3L;
        String body = mapper.writeValueAsString(
                emailRequestDto
        );
        //then
        mvc.perform(post("/api/board/" + boardNo + "/invitation")
                        .content(body) //HTTP Body에 데이터를 담는다
                        .contentType(MediaType.APPLICATION_JSON) //보내는 데이터의 타입을 명시
                )
                .andExpect(status().isOk())
                .andExpect(content().string("{\"msg\":\"" + email + " 초대 완료\",\"statusCode\":200}"));
    }

    @Test
    @DisplayName("보드 리스트 조회 테스트")
    @WithMockCustomUser
    void getBoards () throws Exception {
        // given
        // when
        //then
        mvc.perform(get("/api/boards")
                        .contentType(MediaType.APPLICATION_JSON) //보내는 데이터의 타입을 명시
                )
                .andExpect(status().isOk())
                .andExpect(content().string("[{\"id\":3,\"title\":\"testBoard\"},{\"id\":4,\"title\":\"testBoard\"}]"));
    }

    @Test
    @DisplayName("보드 단건 조회 테스트")
    @WithMockCustomUser
    void getOneBoards () throws Exception {
        // given
        Long boardNo = 3L;
        // when
        //then
        mvc.perform(get("/api/boards/" + boardNo)
                        .contentType(MediaType.APPLICATION_JSON) //보내는 데이터의 타입을 명시
                )
                .andExpect(status().isOk())
                .andExpect(content().string("{\"id\":3,\"title\":\"testBoard\",\"color\":\"testBoardColor\",\"columns\":[]}"));
    }

}
