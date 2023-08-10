package com.winner.trelloimplementation.controllertest;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.winner.trelloimplementation.WithMockCustomUser;
import com.winner.trelloimplementation.column.dto.ColumnRequestDto;
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
public class ColumnControllerTest {

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
    @DisplayName("컬럼 생성 테스트")
    @WithMockCustomUser
    void createColumn () throws Exception {
        // given
        String title = "testColumn";
        ColumnRequestDto columnRequestDto = ColumnRequestDto.builder()
                .title(title)
                .build();
        // when
        Long boardNo = 3L;
        String body = mapper.writeValueAsString(
                columnRequestDto
        );
        Long lastPosition = 1L;
        //then
        mvc.perform(post("/api/board/" + boardNo + "/column")
                        .content(body) //HTTP Body에 데이터를 담는다
                        .param("position", String.valueOf(lastPosition))
                        .contentType(MediaType.APPLICATION_JSON) //보내는 데이터의 타입을 명시
                )
                .andExpect(status().isCreated())
                .andExpect(content().string("{\"msg\":\"컬럼이 생성 되었습니다\",\"statusCode\":201}"));
    }

    @Test
    @DisplayName("컬럼 수정 테스트")
    @WithMockCustomUser
    void updateColumn () throws Exception {
        // given
        String title = "updateColumn";
        ColumnRequestDto columnRequestDto = ColumnRequestDto.builder()
                .title(title)
                .build();
        // when
        String body = mapper.writeValueAsString(
                columnRequestDto
        );
        Long columnNo = 7L;
        //then
        mvc.perform(put("/api/column/" + columnNo)
                        .content(body) //HTTP Body에 데이터를 담는다
                        .contentType(MediaType.APPLICATION_JSON) //보내는 데이터의 타입을 명시
                )
                .andExpect(status().isAccepted())
                .andExpect(content().string("{\"msg\":\"컬럼이 수정 되었습니다\",\"statusCode\":202}"));
    }

    @Test
    @DisplayName("컬럼 조회 테스트")
    @WithMockCustomUser
    void getOneColumn () throws Exception {
        // given
        // when
        Long columnNo = 7L;
        //then
        mvc.perform(get("/api/column/" + columnNo)
                        .contentType(MediaType.APPLICATION_JSON) //보내는 데이터의 타입을 명시
                )
                .andExpect(status().isOk())
                .andExpect(content().string("{\"id\":7,\"title\":\"updateColumn\",\"cardList\":[]}"));
    }

    @Test
    @DisplayName("컬럼 이동 테스트")
    @WithMockCustomUser
    void moveColumn () throws Exception {
        // given
        // when
        Long boardNo = 3L;
        Long currentPosition = 3L;
        Long newPosition = 1L;
        //then
        mvc.perform(patch(  "/api/board/" + boardNo + "/column/" + currentPosition + "/change-column/" + newPosition)
                        .contentType(MediaType.APPLICATION_JSON) //보내는 데이터의 타입을 명시
                )
                .andExpect(status().isOk())
                .andExpect(content().string("{\"msg\":\"컬럼이 이동이 되었습니다\",\"statusCode\":200}"));
    }

    @Test
    @DisplayName("컬럼 삭제 테스트")
    @WithMockCustomUser
    void deleteColumn () throws Exception {
        // given
        // when
        Long columnNo = 6L;
        //then
        mvc.perform(delete(  "/api/column/" + columnNo)
                        .contentType(MediaType.APPLICATION_JSON) //보내는 데이터의 타입을 명시
                )
                .andExpect(status().isOk())
                .andExpect(content().string("{\"msg\":\"컬럼이 삭제 되었습니다\",\"statusCode\":200}"));
    }
}
