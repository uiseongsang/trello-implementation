package com.winner.trelloimplementation.controllertest;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.winner.trelloimplementation.WithMockCustomUser;
import com.winner.trelloimplementation.card.dto.CardRequestDto;
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
public class CardControllerTest {

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
    @DisplayName("카드 생성 테스트")
    @WithMockCustomUser
    void createCard () throws Exception {
        // given
        String title = "newCard";
        String description = "newDesc";
        String deadline = "newDead";
        String color = "newColor";
        CardRequestDto cardRequestDto = CardRequestDto.builder()
                .title(title)
                .description(description)
                .deadline(deadline)
                .color(color)
                .build();
        // when
        Long columnNo = 7L;
        String body = mapper.writeValueAsString(
                cardRequestDto
        );
        //then
        mvc.perform(post("/api/column/" + columnNo +"/card")
                        .content(body) //HTTP Body에 데이터를 담는다
                        .contentType(MediaType.APPLICATION_JSON) //보내는 데이터의 타입을 명시
                )
                .andExpect(status().isCreated())
                .andExpect(content().string("{\"id\":4,\"title\":\"newCard\",\"description\":null,\"deadline\":null,\"color\":null,\"position\":2}"));
    }

    @Test
    @DisplayName("카드 조회 테스트")
    @WithMockCustomUser
    void getOneCard () throws Exception {
        // given
        // when
        Long cardNo = 4L;
        //then
        mvc.perform(get("/api/card/" + cardNo)
                        .contentType(MediaType.APPLICATION_JSON) //보내는 데이터의 타입을 명시
                )
                .andExpect(status().isOk())
                .andExpect(content().string("{\"id\":4,\"title\":\"newCard\",\"description\":null,\"deadline\":null,\"color\":null,\"commentList\":[],\"cardMemberList\":[]}"));
    }

    @Test
    @DisplayName("카드 제목 변경 테스트")
    @WithMockCustomUser
    void updateCardTitle () throws Exception {
        // given
        String title = "updateCardTitle";
        CardRequestDto cardRequestDto = CardRequestDto.builder()
                .title(title)
                .build();
        // when
        Long cardNo = 4L;
        String body = mapper.writeValueAsString(
                cardRequestDto
        );
        //then
        mvc.perform(patch("/api/card/" + cardNo + "/title")
                        .content(body) //HTTP Body에 데이터를 담는다
                        .contentType(MediaType.APPLICATION_JSON) //보내는 데이터의 타입을 명시
                )
                .andExpect(status().isOk())
                .andExpect(content().string("{\"msg\":\"카드 제목 적용 완료\",\"statusCode\":200}"));
    }

    @Test
    @DisplayName("카드 마감기한 변경 테스트")
    @WithMockCustomUser
    void updateCardDeadLine () throws Exception {
        // given
        String deadline = "2023-08-11";
        CardRequestDto cardRequestDto = CardRequestDto.builder()
                .deadline(deadline)
                .build();
        // when
        Long cardNo = 4L;
        String body = mapper.writeValueAsString(
                cardRequestDto
        );
        //then
        mvc.perform(patch("/api/card/" + cardNo + "/deadline")
                        .content(body) //HTTP Body에 데이터를 담는다
                        .contentType(MediaType.APPLICATION_JSON) //보내는 데이터의 타입을 명시
                )
                .andExpect(status().isOk())
                .andExpect(content().string("{\"msg\":\"마감 설정 완료\",\"statusCode\":200}"));
    }

    @Test
    @DisplayName("카드 마감기한 변경 테스트")
    @WithMockCustomUser
    void updateCardDescription () throws Exception {
        // given
        String description = "ㅎㅎ 카드임";
        CardRequestDto cardRequestDto = CardRequestDto.builder()
                .description(description)
                .build();
        // when
        Long cardNo = 4L;
        String body = mapper.writeValueAsString(
                cardRequestDto
        );
        //then
        mvc.perform(patch("/api/card/" + cardNo + "/description")
                        .content(body) //HTTP Body에 데이터를 담는다
                        .contentType(MediaType.APPLICATION_JSON) //보내는 데이터의 타입을 명시
                )
                .andExpect(status().isOk())
                .andExpect(content().string("{\"msg\":\"본문 작성 완료\",\"statusCode\":200}"));
    }

    @Test
    @DisplayName("카드 색깔 변경 테스트")
    @WithMockCustomUser
    void updateCardColor () throws Exception {
        // given
        String color = "빨강";
        CardRequestDto cardRequestDto = CardRequestDto.builder()
                .color(color)
                .build();
        // when
        Long cardNo = 4L;
        String body = mapper.writeValueAsString(
                cardRequestDto
        );
        //then
        mvc.perform(patch("/api/card/" + cardNo + "/color")
                        .content(body) //HTTP Body에 데이터를 담는다
                        .contentType(MediaType.APPLICATION_JSON) //보내는 데이터의 타입을 명시
                )
                .andExpect(status().isOk())
                .andExpect(content().string("{\"msg\":\"카드 색상 적용 완료\",\"statusCode\":200}"));
    }

    @Test
    @DisplayName("카드 컬럼 변경 테스트")
    @WithMockCustomUser
    void updateCardColumn () throws Exception {
        // given
        Long column = 2L;
        CardRequestDto cardRequestDto = CardRequestDto.builder()
                .column(column)
                .build();
        // when
        Long cardNo = 4L;
        String body = mapper.writeValueAsString(
                cardRequestDto
        );
        //then
        mvc.perform(patch("/api/card/" + cardNo)
                        .content(body) //HTTP Body에 데이터를 담는다
                        .contentType(MediaType.APPLICATION_JSON) //보내는 데이터의 타입을 명시
                )
                .andExpect(status().isOk())
                .andExpect(content().string("{\"msg\":\"카드 이동 완료\",\"statusCode\":200}"));
    }

    @Test
    @DisplayName("카드 위치 변경 테스트")
    @WithMockCustomUser
    void updateCardPosition () throws Exception {
        // given
        Long cardNo = 2L;
        Long column = 7L;
        Long changeCardNo = 2L;
        // when
        // then
        mvc.perform(patch("/api/column/" + column + "/card/" + cardNo + "/change-card/" + changeCardNo)
                        .contentType(MediaType.APPLICATION_JSON) //보내는 데이터의 타입을 명시
                )
                .andExpect(status().isOk())
                .andExpect(content().string("{\"msg\":\"카드 바꿈 완료\",\"statusCode\":200}"));
    }

    @Test
    @DisplayName("카드 삭제 테스트")
    @WithMockCustomUser
    void deleteCard () throws Exception {
        // given
        Long cardNo = 4L;
        Long column = 2L;
        // when
        // then
        mvc.perform(delete("/api/column/" + column +"/card/" + cardNo)
                        .contentType(MediaType.APPLICATION_JSON) //보내는 데이터의 타입을 명시
                )
                .andExpect(status().isOk())
                .andExpect(content().string("{\"msg\":\"카드 삭제 완료\",\"statusCode\":200}"));
    }


}
