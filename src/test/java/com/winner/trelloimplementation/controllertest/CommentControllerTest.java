package com.winner.trelloimplementation.controllertest;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.winner.trelloimplementation.WithMockCustomUser;
import com.winner.trelloimplementation.cardMember.dto.CardMemberRequestDto;
import com.winner.trelloimplementation.comment.dto.CommentRequestDto;
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

@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CommentControllerTest {

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
    @DisplayName("카드 댓글 추가 테스트")
    @WithMockCustomUser
    void createComment () throws Exception {
        // given
        String content = "테스트";
        CommentRequestDto commentRequestDto = CommentRequestDto.builder()
                .content(content)
                .build();
        // when
        Long cardNo = 2L;
        String body = mapper.writeValueAsString(
                commentRequestDto
        );
        //then
        mvc.perform(post("/api/card/" + cardNo +"/comment")
                        .content(body) //HTTP Body에 데이터를 담는다
                        .contentType(MediaType.APPLICATION_JSON) //보내는 데이터의 타입을 명시
                )
                .andExpect(status().isCreated())
                .andExpect(content().string("{\"id\":3,\"content\":\"테스트\",\"username\":\"testuser\"}"));
    }

    @Test
    @DisplayName("카드 댓글 수정 테스트")
    @WithMockCustomUser
    void updateComment () throws Exception {
        // given
        String content = "NEW 테스트";
        CommentRequestDto commentRequestDto = CommentRequestDto.builder()
                .content(content)
                .build();
        // when
        Long commentNo = 2L;
        String body = mapper.writeValueAsString(
                commentRequestDto
        );
        //then
        mvc.perform(patch("/api/comment/" + commentNo)
                        .content(body) //HTTP Body에 데이터를 담는다
                        .contentType(MediaType.APPLICATION_JSON) //보내는 데이터의 타입을 명시
                )
                .andExpect(status().isCreated())
                .andExpect(content().string("{\"id\":2,\"content\":\"NEW 테스트\",\"username\":\"testuser\"}"));
    }

    @Test
    @DisplayName("카드 댓글 삭제 테스트")
    @WithMockCustomUser
    void deleteComment () throws Exception {
        // given
        // when
        Long commentNo = 2L;
        //then
        mvc.perform(delete("/api/comment/" + commentNo)
                        .contentType(MediaType.APPLICATION_JSON) //보내는 데이터의 타입을 명시
                )
                .andExpect(status().isOk())
                .andExpect(content().string("{\"msg\":\"댓글 삭제 완료\",\"statusCode\":200}"));
    }
}
