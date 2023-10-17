package com.example.marketbackend.controller;

import com.example.marketbackend.dto.Response;
import com.example.marketbackend.dto.neighbor.post.request.NeighborPostWriteRequest;
import com.example.marketbackend.dto.post.request.ProductPostWriteRequest;
import com.example.marketbackend.dto.user.request.UserSignInRequest;
import com.example.marketbackend.dto.user.request.UserSignUpRequest;
import com.example.marketbackend.dto.user.response.UserSignInResponse;
import com.example.marketbackend.entity.ProductPost;
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

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
class NeighborPostControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService userService;

    @Autowired
    private NeighborPostService neighborPostService;

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
    void writePost() throws Exception {
        NeighborPostWriteRequest request = new NeighborPostWriteRequest("title", "content", "category", new String[]{"imageUrl1", "imageUrl2"});

        String token = getToken();

        mockMvc.perform(
                        post("/api/neighbor/post/write")
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("Authorization", "Bearer " + token)
                                .content(new ObjectMapper().writeValueAsString(request))
                )
                .andExpect(status().isOk())
                .andDo(
                        MockMvcRestDocumentation.document("neighbor-post-write",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                requestHeaders(
                                        headerWithName("Authorization").description("인증 토큰")
                                ),
                                requestFields(
                                        fieldWithPath("title").type(JsonFieldType.STRING).description("제목"),
                                        fieldWithPath("content").type(JsonFieldType.STRING).description("내용"),
                                        fieldWithPath("category").type(JsonFieldType.STRING).description("카테고리"),
                                        fieldWithPath("images").type(JsonFieldType.ARRAY).description("이미지 배열")
                                ),
                                responseFields(
                                        fieldWithPath("message").description("응답 메시지"),
                                        subsectionWithPath("result").description("응답 결과"),
                                        fieldWithPath("result.post_id").description("포스트 ID")
                                )
                        )
                );
    }

    @Test
    void getPostList() throws Exception {
        NeighborPostWriteRequest request = new NeighborPostWriteRequest("title", "content", "category", new String[]{"imageUrl1", "imageUrl2"});

        String token = getToken();

        for(int i = 0; i < 3; i++) {
            makePost();
        }

        mockMvc.perform(
                        get("/api/neighbor/post/list")
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("category", "category")
                                .param("page", "0")
                )
                .andExpect(status().isOk())
                .andDo(
                        MockMvcRestDocumentation.document("neighbor-post-list-get",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                requestParameters(
                                        parameterWithName("category").description("카테고리"),
                                        parameterWithName("page").description("페이지 번호")
                                ),
                                responseFields(
                                        fieldWithPath("message").description("응답 메시지"),
                                        subsectionWithPath("result").description("응답 결과"),
                                        fieldWithPath("result.count").description("포스트 개수"),
                                        subsectionWithPath("result.posts[]").description("포스트 결과"),
                                        subsectionWithPath("result.posts[].id").description("포스트 ID"),
                                        subsectionWithPath("result.posts[].title").description("포스트 제목"),
                                        subsectionWithPath("result.posts[].content").description("포스트 내용"),
                                        subsectionWithPath("result.posts[].category").description("포스트 카테고리"),
                                        subsectionWithPath("result.posts[].hit_count").description("포스트 조회수"),
                                        subsectionWithPath("result.posts[].like_count").description("포스트 좋아요 수"),
                                        subsectionWithPath("result.posts[].time").description("포스트 작성일")

                                )
                        )
                );
    }

    @Test
    void getPostDetail() throws Exception {
        getToken();

        makePost();

        mockMvc.perform(
                        get("/api/neighbor/post/{postId}", 2L)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andDo(
                        MockMvcRestDocumentation.document("neighbor-post-get",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                pathParameters(
                                        parameterWithName("postId").description("포스트 ID")
                                ),
                                responseFields(
                                        fieldWithPath("message").description("응답 메시지"),
                                        subsectionWithPath("result").description("응답 결과"),
                                        subsectionWithPath("result.post_info").description("포스트 상세 정보"),
                                        fieldWithPath("result.post_info.id").description("포스트 ID"),
                                        fieldWithPath("result.post_info.title").description("포스트 제목"),
                                        fieldWithPath("result.post_info.content").description("포스트 내용"),
                                        fieldWithPath("result.post_info.category").description("포스트 카테고리"),
                                        fieldWithPath("result.post_info.hit_count").description("포스트 조회수"),
                                        fieldWithPath("result.post_info.like_count").description("포스트 좋아요 수"),
                                        fieldWithPath("result.post_info.images").description("포스트 이미지"),
                                        fieldWithPath("result.post_info.created_time").description("포스트 작성일"),
                                        subsectionWithPath("result.user_info").description("작성자 상세 정보"),
                                        fieldWithPath("result.user_info.id").description("작성자 고유 아이디"),
                                        fieldWithPath("result.user_info.nickname").description("작성자 닉네임"),
                                        fieldWithPath("result.user_info.address").description("작성자 주소"),
                                        fieldWithPath("result.user_info.profile").description("작성자 프로필 이미지"),
                                        fieldWithPath("result.user_info.my_post").description("내 게시글 유무"),
                                        fieldWithPath("result.user_info.is_like").description("게시글 좋아요 유무")
                                )
                        )
                );
    }
}