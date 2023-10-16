package com.example.marketbackend.controller;

import com.example.marketbackend.dto.Response;
import com.example.marketbackend.dto.post.request.ProductPostWriteRequest;
import com.example.marketbackend.dto.user.request.UserSignInRequest;
import com.example.marketbackend.dto.user.request.UserSignUpRequest;
import com.example.marketbackend.dto.user.response.UserSignInResponse;
import com.example.marketbackend.entity.Chat;
import com.example.marketbackend.entity.ChatRoom;
import com.example.marketbackend.entity.ProductPost;
import com.example.marketbackend.repository.*;
import com.example.marketbackend.service.*;
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
import java.util.Optional;

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
class FavoriteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService userService;

    @Autowired
    private ProductPostService productPostService;

    @Autowired
    private FavoriteService favoriteService;

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

        ProductPostWriteRequest request = new ProductPostWriteRequest("title", 10_000, "content", "category", true, new String[]{"imageUrl1", "imageUrl2"});

        productPostService.write(request);
    }

    @Test
    void addFavorite() throws Exception {
        String token = getToken();

        makePost();

        mockMvc.perform(
                        post("/api/product/like/{postId}", 2L)
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("Authorization", "Bearer " + token)
                )
                .andExpect(status().isOk())
                .andDo(
                        MockMvcRestDocumentation.document("product-post-like-add",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                requestHeaders(
                                        headerWithName("Authorization").description("인증 토큰")
                                ),
                                pathParameters(
                                        parameterWithName("postId").description("포스트 ID")
                                ),
                                responseFields(
                                        fieldWithPath("message").description("응답 메시지"),
                                        subsectionWithPath("result").description("응답 결과"),
                                        fieldWithPath("result.favorite_count").description("좋아요 수")
                                )
                        )
                );
    }

    @Test
    void deleteFavorite() throws Exception {
        String token = getToken();

        makePost();

        mockMvc.perform(
                        delete("/api/product/like/{postId}", 2L)
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("Authorization", "Bearer " + token)
                )
                .andExpect(status().isOk())
                .andDo(
                        MockMvcRestDocumentation.document("product-post-like-delete",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                requestHeaders(
                                        headerWithName("Authorization").description("인증 토큰")
                                ),
                                pathParameters(
                                        parameterWithName("postId").description("포스트 ID")
                                ),
                                responseFields(
                                        fieldWithPath("message").description("응답 메시지"),
                                        subsectionWithPath("result").description("응답 결과"),
                                        fieldWithPath("result.favorite_count").description("좋아요 수")
                                )
                        )
                );
    }

    @Test
    void getFavoritePosts() throws Exception {
        String token = getToken();

        makePost();

        UserDetailsService userDetailsService = Mockito.mock(UserDetailsService.class);

        UserDetails userDetails = User.withUsername("1")
                .password("encodedPassword")
                .roles("USER")
                .build();

        Mockito.when(userDetailsService.loadUserByUsername("userId")).thenReturn(userDetails);

        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities()));

        favoriteService.likePost(2L);

        mockMvc.perform(
                        get("/api/product/like/posts")
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("page", "0")
                                .header("Authorization", "Bearer " + token)
                )
                .andExpect(status().isOk())
                .andDo(
                        MockMvcRestDocumentation.document("product-post-like-posts",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                requestHeaders(
                                        headerWithName("Authorization").description("인증 토큰")
                                ),
                                requestParameters(
                                        parameterWithName("page").description("페이지 번호")
                                ),
                                responseFields(
                                        fieldWithPath("message").description("응답 메시지"),
                                        subsectionWithPath("result").description("응답 결과"),
                                        fieldWithPath("result.count").description("포스트 개수"),
                                        subsectionWithPath("result.posts[]").description("좋아요 수"),
                                        fieldWithPath("result.posts[].id").description("중고글 ID"),
                                        fieldWithPath("result.posts[].title").description("중고글 제목"),
                                        fieldWithPath("result.posts[].address").description("중고글 주소"),
                                        fieldWithPath("result.posts[].price").description("중고글 가격"),
                                        fieldWithPath("result.posts[].created_time").description("중고글 작성일"),
                                        fieldWithPath("result.posts[].favorite_count").description("좋아요 수"),
                                        fieldWithPath("result.posts[].chatroom_count").description("채팅방 수"),
                                        fieldWithPath("result.posts[].thumbnail").description("썸네일 이미지")
                                )
                        )
                );
    }
}