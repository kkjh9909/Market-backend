package com.example.marketbackend.controller;

import com.example.marketbackend.dto.Response;
import com.example.marketbackend.dto.user.request.UserIdCheckRequest;
import com.example.marketbackend.dto.user.request.UserProfileEditRequest;
import com.example.marketbackend.dto.user.request.UserSignInRequest;
import com.example.marketbackend.dto.user.request.UserSignUpRequest;
import com.example.marketbackend.dto.user.response.UserSignInResponse;
import com.example.marketbackend.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.mockmvc.MockMvcOperationPreprocessorsConfigurer;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.PayloadDocumentation;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import java.util.logging.Filter;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService userService;

    @Test
    public void signUp() throws Exception {
        UserSignUpRequest request = new UserSignUpRequest("userId", "password", "username", "nickname", "profileImage", "address");

        mockMvc.perform(
                post("/api/user/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request))
        )
                .andExpect(status().isOk())
                .andDo(
                        MockMvcRestDocumentation.document("sign-up",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                requestFields(
                                        fieldWithPath("userId").description("유저 아이디"),
                                        fieldWithPath("password").description("비밀번호"),
                                        fieldWithPath("username").description("유저 이름"),
                                        fieldWithPath("nickname").description("닉네임"),
                                        fieldWithPath("profileImage").description("프로필 이미지"),
                                        fieldWithPath("address").description("주소")
                                ),
                                responseFields(
                                        fieldWithPath("message").description("응답 메시지"),
                                        subsectionWithPath("result").description("응답 결과"),
                                        fieldWithPath("result.user_id").description("사용자 ID")
                                )
                        )
                );
    }

    @Test
    public void signin() throws Exception {

        signup();

        UserSignInRequest request = new UserSignInRequest("userId", "password");

        mockMvc.perform(
                        post("/api/user/signin")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(new ObjectMapper().writeValueAsString(request))
                )
                .andExpect(status().isOk())
                .andDo(
                        MockMvcRestDocumentation.document("sign-in",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                responseFields(
                                        fieldWithPath("message").description("응답 메시지"),
                                        subsectionWithPath("result").description("응답 결과"),
                                        fieldWithPath("result.access_token").description("엑세스 토큰")
                                )
                        )
                );
    }

    @Test
    public void getProfile() throws Exception {

        String token = getToken();

        mockMvc.perform(
                        get("/api/user/profile")
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("Authorization", "Bearer " + token)
                )
                .andExpect(status().isOk())
                .andDo(
                        MockMvcRestDocumentation.document("profile",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                responseFields(
                                        fieldWithPath("message").description("응답 메시지"),
                                        subsectionWithPath("result").description("응답 결과"),
                                        fieldWithPath("result.image").description("유저 프로필 이미지"),
                                        fieldWithPath("result.nickname").description("닉네임")
                                )
                        )
                );
    }

    @Test
    public void getId() throws Exception {

        String token = getToken();

        mockMvc.perform(
                        get("/api/user/id")
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("Authorization", "Bearer " + token)
                )
                .andExpect(status().isOk())
                .andDo(
                        MockMvcRestDocumentation.document("id",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                responseFields(
                                        fieldWithPath("message").description("응답 메시지"),
                                        fieldWithPath("result").description("유저 ID")
                                )
                        )
                );
    }

    @Test
    public void checkId() throws Exception {
        UserIdCheckRequest request = new UserIdCheckRequest();
        request.setUserId("test");

        mockMvc.perform(
                        post("/api/user/check")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(new ObjectMapper().writeValueAsString(request))
                )
                .andExpect(status().isOk())
                .andDo(
                        MockMvcRestDocumentation.document("check-id",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                responseFields(
                                        fieldWithPath("message").description("응답 메시지")
                                )
                        )
                );
    }

    @Test
    public void validateToken() throws Exception {

        String token = getToken();

        mockMvc.perform(
                        get("/api/user/validate-token")
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("Authorization", "Bearer " + token)
                )
                .andExpect(status().isOk())
                .andDo(
                        MockMvcRestDocumentation.document("validate-token",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                requestHeaders(
                                        headerWithName("Authorization").description("인증 토큰").optional()
                                ),
                                responseFields(
                                        fieldWithPath("message").description("응답 메시지")
                                )
                        )
                );
    }

    @Test
    public void editProfile() throws Exception {

        String token = getToken();
        UserProfileEditRequest request = new UserProfileEditRequest("nickname", "address", "imageUrl");

        mockMvc.perform(
                        put("/api/user/profile")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(new ObjectMapper().writeValueAsString(request))
                                .header("Authorization", "Bearer " + token)
                )
                .andExpect(status().isOk())
                .andDo(
                        MockMvcRestDocumentation.document("edit-profile",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                requestHeaders(
                                        headerWithName("Authorization").description("인증 토큰").optional()
                                ),
                                requestFields(
                                        fieldWithPath("nickname").description("닉네임"),
                                        fieldWithPath("address").description("주소"),
                                        fieldWithPath("image").description("이미지 경로")
                                ),
                                responseFields(
                                        fieldWithPath("message").description("응답 메시지"),
                                        subsectionWithPath("result").description("응답 결과"),
                                        fieldWithPath("result.nickname").description("닉네임"),
                                        fieldWithPath("result.address").description("주소"),
                                        fieldWithPath("result.image").description("이미지 경로")
                                )
                        )
                );
    }

    public String getToken() {
        signup();

        Response response = userService.signIn(new UserSignInRequest("userId", "password"));

        UserSignInResponse result = (UserSignInResponse) response.getResult();

        return result.getAccessToken();
    }

    public void signup() {
        UserSignUpRequest request = new UserSignUpRequest("userId", "password", "username", "nickname", "profileImage", "address");

        userService.signUp(request);
    }
}