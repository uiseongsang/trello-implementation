package com.winner.trelloimplementation.controllertest;


import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.winner.trelloimplementation.WithMockCustomUser;
import com.winner.trelloimplementation.card.dto.CardRequestDto;
import com.winner.trelloimplementation.cardMember.dto.CardMemberRequestDto;
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
public class CardMemberControllerTest {

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
    @DisplayName("카드 멤버 추가 테스트")
    @WithMockCustomUser
    void addCardMember () throws Exception {
        // given
        String email = "wjsdudals56@kakao.com";
        CardMemberRequestDto cardMemberRequestDto = CardMemberRequestDto.builder()
                .email(email)
                .build();
        // when
        Long cardNo = 2L;
        String body = mapper.writeValueAsString(
                cardMemberRequestDto
        );
        //then
        mvc.perform(post("/api/card/" + cardNo + "/member")
                        .content(body) //HTTP Body에 데이터를 담는다
                        .contentType(MediaType.APPLICATION_JSON) //보내는 데이터의 타입을 명시
                )
                .andExpect(status().isOk())
                .andExpect(content().string("{\"msg\":\"멤버 추가 성공\",\"statusCode\":200}"));
    }

    @Test
    @DisplayName("카드 멤버 삭제 테스트")
    @WithMockCustomUser
    void deleteCardMember () throws Exception {
        // given
        String email = "wjsdudals56@kakao.com";
        CardMemberRequestDto cardMemberRequestDto = CardMemberRequestDto.builder()
                .email(email)
                .build();
        // when
        Long cardNo = 2L;
        String body = mapper.writeValueAsString(
                cardMemberRequestDto
        );
        //then
        mvc.perform(delete("/api/card/" + cardNo + "/member")
                        .content(body) //HTTP Body에 데이터를 담는다
                        .contentType(MediaType.APPLICATION_JSON) //보내는 데이터의 타입을 명시
                )
                .andExpect(status().isOk())
                .andExpect(content().string("{\"msg\":\"멤버 삭제 성공\",\"statusCode\":200}"));
    }

}
