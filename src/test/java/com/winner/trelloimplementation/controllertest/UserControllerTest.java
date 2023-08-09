package com.winner.trelloimplementation.controllertest;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.winner.trelloimplementation.WithMockCustomUser;
import com.winner.trelloimplementation.common.security.UserDetailsServiceImpl;
import com.winner.trelloimplementation.user.dto.LoginRequestDto;
import com.winner.trelloimplementation.user.dto.ProfileRequestDto;
import com.winner.trelloimplementation.user.dto.SignoutRequestDto;
import com.winner.trelloimplementation.user.dto.SignupRequestDto;
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
public class UserControllerTest {

    @Autowired
    ObjectMapper mapper;

    @Autowired
    MockMvc mvc;

    @Autowired
    private WebApplicationContext ctx;

    private static final String BASE_URL = "/api/user";

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @BeforeAll
    public void setup() {
        this.mvc = MockMvcBuilders
                .webAppContextSetup(ctx)
                .addFilters(new CharacterEncodingFilter("UTF-8", true))  // 한글 필터 추가
                .apply(springSecurity())
                .alwaysDo(print())
                .build();
    }

    @Test
    @DisplayName("회원 가입 테스트")
    void signupTest () throws Exception {
        // given
        String username = "testuser";
        String nickname = "testnick";
        String password = "testpass";
        String email = "test@test.com";
        String introduction = "testintro";
        // when
        SignupRequestDto signupRequestDto = new SignupRequestDto();
        signupRequestDto.setUsername(username);
        signupRequestDto.setNickname(nickname);
        signupRequestDto.setPassword(password);
        signupRequestDto.setEmail(email);
        signupRequestDto.setIntroduction(introduction);

        String body = mapper.writeValueAsString(
                signupRequestDto
        );
        //then
        mvc.perform(post(BASE_URL + "/signup")
                        .content(body) //HTTP Body에 데이터를 담는다
                        .contentType(MediaType.APPLICATION_JSON) //보내는 데이터의 타입을 명시
                )
                .andExpect(status().isCreated())
                .andExpect(content().string("{\"msg\":\"회원가입 성공\",\"statusCode\":201}"));
    }

    @Test
    @WithMockCustomUser
    @DisplayName("회원 탈퇴 테스트")
    void signOutTest () throws Exception {
        // given
        String password = "testpass";
        SignoutRequestDto signoutRequestDto = new SignoutRequestDto();
        signoutRequestDto.setPassword(password);
        // when
        String body = mapper.writeValueAsString(
                signoutRequestDto
        );
        // then
        mvc.perform(delete(BASE_URL + "/signout")
                        .content(body) //HTTP Body에 데이터를 담는다
                        .contentType(MediaType.APPLICATION_JSON) //보내는 데이터의 타입을 명시
                )
                .andExpect(status().isOk())
                .andExpect(content().string("{\"msg\":\"회원탈퇴 성공\",\"statusCode\":200}"));
    }


    @Test
    @DisplayName("로그인 테스트")
    void loginTest () throws Exception {
        // given
        String username = "testuser1";
        String password = "testpass1";

        // when
        LoginRequestDto loginRequestDto = new LoginRequestDto();
        loginRequestDto.setUsername(username);
        loginRequestDto.setPassword(password);

        String body = mapper.writeValueAsString(
            loginRequestDto
        );

        // then
        mvc.perform(post(BASE_URL + "/login")
                        .content(body) //HTTP Body에 데이터를 담는다
                        .contentType(MediaType.APPLICATION_JSON) //보내는 데이터의 타입을 명시
                )
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("로그 아웃 테스트")
    @WithMockCustomUser
    void logoutTest () throws Exception {
        // given
        // when
        // then
        mvc.perform(get(BASE_URL + "/logout")
                        .contentType(MediaType.APPLICATION_JSON) //보내는 데이터의 타입을 명시
                )
                .andExpect(status().isOk())
                .andExpect(content().string("{\"msg\":\"로그아웃 성공\",\"statusCode\":200}"));
    }

    @Test
    @DisplayName("프로필 보기 테스트")
    @WithMockCustomUser
    void getInfoTest () throws Exception {
        // given
        // when
        // then
        mvc.perform(get(BASE_URL + "/info")
                        .contentType(MediaType.APPLICATION_JSON) //보내는 데이터의 타입을 명시
                )
                .andExpect(status().isOk())
                .andExpect(content().string(
                        "{\"username\":\"testuser\",\"nickname\":\"testnick\",\"email\":\"test@test.com\",\"introduction\":\"testintro\"}")
                );
    }

    @Test
    @DisplayName("프로필 수정 테스트")
    @WithMockCustomUser
    void updateInfoTest () throws Exception {
        // given
        String nickname = "nicktest";
        String introduction = "introtest";
        // when
        ProfileRequestDto profileRequestDto = new ProfileRequestDto();
        profileRequestDto.setNickname(nickname);
        profileRequestDto.setIntroduction(introduction);

        String body = mapper.writeValueAsString(
                profileRequestDto
        );
        // then
        mvc.perform(put(BASE_URL + "/info")
                        .content(body) //HTTP Body에 데이터를 담는다
                        .contentType(MediaType.APPLICATION_JSON) //보내는 데이터의 타입을 명시
                )
                .andExpect(status().isOk())
                .andExpect(content().string("{\"msg\":\"프로필 수정 성공\",\"statusCode\":200}"));
    }

}
