package com.example.marketbackend.controller;

import com.example.marketbackend.dto.Response;
import com.example.marketbackend.dto.neighbor.comment.request.NeighborCommentWriteRequest;
import com.example.marketbackend.dto.neighbor.post.request.NeighborPostWriteRequest;
import com.example.marketbackend.dto.post.request.ProductPostWriteRequest;
import com.example.marketbackend.dto.user.request.UserSignInRequest;
import com.example.marketbackend.dto.user.request.UserSignUpRequest;
import com.example.marketbackend.dto.user.response.UserSignInResponse;
import com.example.marketbackend.entity.ProductPost;
import com.example.marketbackend.service.NeighborCommentService;
import com.example.marketbackend.service.NeighborPostService;
import com.example.marketbackend.service.ProductPostService;
import com.example.marketbackend.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.security.core.userdetails.User;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class NeighborCommentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService userService;

    @Autowired
    private NeighborPostService neighborPostService;

    @Autowired
    private NeighborCommentService neighborCommentService;

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

    public void makePost() {
        UserDetailsService userDetailsService = Mockito.mock(UserDetailsService.class);

        UserDetails userDetails = User.withUsername("1")
                .password("encodedPassword")
                .roles("USER")
                .build();

        Mockito.when(userDetailsService.loadUserByUsername("userId")).thenReturn(userDetails);

        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities()));

        NeighborPostWriteRequest request = new NeighborPostWriteRequest("title",  "content", "category", new String[]{"imageUrl1", "imageUrl2"});

        neighborPostService.write(request);
    }

    @Test
    void getPostList() throws Exception {
        String token = getToken();

        makePost();

        NeighborCommentWriteRequest request = new NeighborCommentWriteRequest("content", null);

        mockMvc.perform(
                        post("/api/neighbor/comment/{postId}/write", 2L)
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("Authorization", "Bearer " + token)
                                .content(new ObjectMapper().writeValueAsString(request))
                )
                .andExpect(status().isOk())
                .andDo(
                        MockMvcRestDocumentation.document("neighbor-comment-write",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                requestHeaders(
                                        headerWithName("Authorization").description("인증 토큰")
                                ),
                                pathParameters(
                                        parameterWithName("postId").description("포스트 ID")
                                ),
                                requestFields(
                                        fieldWithPath("content").description("댓글 내용"),
                                        fieldWithPath("parentId").description("부모 댓글 ID").optional()
                                ),
                                responseFields(
                                        fieldWithPath("message").description("응답 메시지"),
                                        subsectionWithPath("result").description("응답 결과"),
                                        subsectionWithPath("result.comment").description("댓글 결과"),
                                        fieldWithPath("result.comment.id").description("댓글 ID"),
                                        fieldWithPath("result.comment.nickname").description("작성자 닉네임"),
                                        fieldWithPath("result.comment.profile").description("작성자 프로필 이미지"),
                                        fieldWithPath("result.comment.content").description("내용"),
                                        fieldWithPath("result.comment.created_time").description("작성일"),
                                        fieldWithPath("result.comment.like_count").description("좋아요 수"),
                                        fieldWithPath("result.comment.my_comment").description("내 댓글 여부"),
                                        fieldWithPath("result.comment.is_like").description("좋아요 눌렀는지 여부")
                                )
                        )
                );
    }

    @Test
    void getCommentList() throws Exception {
        String token = getToken();

        makePost();

        NeighborCommentWriteRequest request = new NeighborCommentWriteRequest("content", null);

        neighborCommentService.write(2L, request);
        neighborCommentService.write(2L, request);

        mockMvc.perform(
                        get("/api/neighbor/comment/{postId}/list", 2L)
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("Authorization", "Bearer " + token)
                )
                .andExpect(status().isOk())
                .andDo(
                        MockMvcRestDocumentation.document("neighbor-comment-list-get",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                requestHeaders(
                                        headerWithName("Authorization").description("인증 토큰").optional()
                                ),
                                pathParameters(
                                        parameterWithName("postId").description("포스트 ID")
                                ),
                                responseFields(
                                        fieldWithPath("message").description("응답 메시지"),
                                        subsectionWithPath("result").description("응답 결과"),
                                        subsectionWithPath("result.count").description("댓글 개수"),
                                        fieldWithPath("result.comments[]").description("댓글 조회 결과"),
                                        fieldWithPath("result.comments[].id").description("댓글 ID"),
                                        fieldWithPath("result.comments[].nickname").description("작성자 닉네임"),
                                        fieldWithPath("result.comments[].profile").description("작성자 프로필"),
                                        fieldWithPath("result.comments[].content").description("내용"),
                                        fieldWithPath("result.comments[].replies[]").description("답글 목록"),
                                        fieldWithPath("result.comments[].like_count").description("좋아요 수"),
                                        fieldWithPath("result.comments[].created_time").description("작성일"),
                                        fieldWithPath("result.comments[].my_comment").description("내 댓글 여부"),
                                        fieldWithPath("result.comments[].is_like").description("좋아요 눌렀는지 여부")
                                )
                        )
                );
    }
}