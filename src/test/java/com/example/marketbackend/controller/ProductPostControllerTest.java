package com.example.marketbackend.controller;

import com.example.marketbackend.dto.Response;
import com.example.marketbackend.dto.post.request.ProductPostWriteRequest;
import com.example.marketbackend.dto.user.request.UserSignInRequest;
import com.example.marketbackend.dto.user.request.UserSignUpRequest;
import com.example.marketbackend.dto.user.response.UserSignInResponse;
import com.example.marketbackend.entity.ProductPost;
import com.example.marketbackend.service.ProductPostService;
import com.example.marketbackend.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Order;
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
class ProductPostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService userService;

    @Autowired
    private ProductPostService productPostService;

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
    void write() throws Exception {
        ProductPostWriteRequest request = new ProductPostWriteRequest("title", 10_000, "content",
                "category", true, new String[]{"imageUrl1", "imageUrl2"});

        String token = getToken();

        mockMvc.perform(
                        post("/api/product/post/write")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(new ObjectMapper().writeValueAsString(request))
                                .header("Authorization", "Bearer " + token)
                )
                .andExpect(status().isOk())
                .andDo(
                        MockMvcRestDocumentation.document("product-post-write",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                requestFields(
                                    fieldWithPath("title").type(JsonFieldType.STRING).description("제목"),
                                    fieldWithPath("price").type(JsonFieldType.NUMBER).description("가격"),
                                    fieldWithPath("content").type(JsonFieldType.STRING).description("내용"),
                                    fieldWithPath("category").type(JsonFieldType.STRING).description("카테고리"),
                                    fieldWithPath("deal").type(JsonFieldType.BOOLEAN).description("흥정 가능 여부"),
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
    void getPostDetail() throws Exception{
        String token = getToken();

        makePost();

        mockMvc.perform(
                        get("/api/product/post/{postId}", 2L)
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("Authorization", "Bearer " + token)
                )
                .andExpect(status().isOk())
                .andDo(
                        MockMvcRestDocumentation.document("product-post-get",
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
                                        subsectionWithPath("result.post_info").description("포스트 상세 정보"),
                                        fieldWithPath("result.post_info.title").description("포스트 제목"),
                                        fieldWithPath("result.post_info.content").description("포스트 내용"),
                                        fieldWithPath("result.post_info.category").description("포스트 카테고리"),
                                        fieldWithPath("result.post_info.hit_count").description("포스트 조회수"),
                                        fieldWithPath("result.post_info.favorite_count").description("포스트 좋아요 수"),
                                        fieldWithPath("result.post_info.chatroom_count").description("포스트 채팅 수"),
                                        fieldWithPath("result.post_info.images").description("포스트 이미지"),
                                        fieldWithPath("result.post_info.created_time").description("포스트 작성일"),
                                        fieldWithPath("result.post_info.price").description("물건 가격"),
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

    @Test
    void getPosts() throws Exception {
        String token = getToken();

        for(int i = 0; i < 3; i++) {
            makePost();
        }

        mockMvc.perform(
                        get("/api/product/post/list")
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("address", "address")
                                .param("page", "0")
                )
                .andExpect(status().isOk())
                .andDo(
                        MockMvcRestDocumentation.document("product-post-list",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                requestParameters(
                                        parameterWithName("address").description("주소"),
                                        parameterWithName("page").description("페이지 번호")
                                ),
                                responseFields(
                                        fieldWithPath("message").description("응답 메시지"),
                                        subsectionWithPath("result").description("응답 결과"),
                                        fieldWithPath("result.count").description("포스트 개수"),
                                        subsectionWithPath("result.posts[]").type(JsonFieldType.ARRAY).description("포스트 결과"),
                                        fieldWithPath("result.posts[].id").type(JsonFieldType.NUMBER).description("작성글 고유 ID"),
                                        fieldWithPath("result.posts[].title").description("제목"),
                                        fieldWithPath("result.posts[].category").description("카테고리"),
                                        fieldWithPath("result.posts[].price").type(JsonFieldType.NUMBER).description("가격"),
                                        fieldWithPath("result.posts[].address").description("주소"),
                                        fieldWithPath("result.posts[].favorite_count").type(JsonFieldType.NUMBER).description("관심 수"),
                                        fieldWithPath("result.posts[].chatroom_count").type(JsonFieldType.NUMBER).description("채팅방 수"),
                                        fieldWithPath("result.posts[].thumbnail").description("썸네일 이미지"),
                                        fieldWithPath("result.posts[].created_time").description("작성 시간")
                                )
                        )
                );
    }

    @Test
    void getPostsByCategory() throws Exception {
        String token = getToken();

        for(int i = 0; i < 3; i++) {
            makePost();
        }

        mockMvc.perform(
                        get("/api/product/post/list/{category}", "category")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andDo(
                        MockMvcRestDocumentation.document("product-post-list-category",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                pathParameters(
                                        parameterWithName("category").description("카테고리")
                                ),
                                responseFields(
                                        fieldWithPath("message").description("응답 메시지"),
                                        subsectionWithPath("result").description("응답 결과"),
                                        fieldWithPath("result.count").description("포스트 개수"),
                                        subsectionWithPath("result.posts[]").type(JsonFieldType.ARRAY).description("포스트 결과"),
                                        fieldWithPath("result.posts[].id").type(JsonFieldType.NUMBER).description("작성글 고유 ID"),
                                        fieldWithPath("result.posts[].title").description("제목"),
                                        fieldWithPath("result.posts[].category").description("카테고리"),
                                        fieldWithPath("result.posts[].price").type(JsonFieldType.NUMBER).description("가격"),
                                        fieldWithPath("result.posts[].address").description("주소"),
                                        fieldWithPath("result.posts[].favorite_count").type(JsonFieldType.NUMBER).description("관심 수"),
                                        fieldWithPath("result.posts[].chatroom_count").type(JsonFieldType.NUMBER).description("채팅방 수"),
                                        fieldWithPath("result.posts[].thumbnail").description("썸네일 이미지"),
                                        fieldWithPath("result.posts[].created_time").description("작성 시간")
                                )
                        )
                );
    }

    @Test
    void getMyPosts() throws Exception {
        String token = getToken();

        for(int i = 0; i < 3; i++) {
            makePost();
        }

        mockMvc.perform(
                        get("/api/product/post/my")
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("page", "0")
                                .header("Authorization", "Bearer " + token)
                )
                .andExpect(status().isOk())
                .andDo(
                        MockMvcRestDocumentation.document("product-post-list-my",
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
                                        subsectionWithPath("result.posts[]").type(JsonFieldType.ARRAY).description("포스트 결과"),
                                        fieldWithPath("result.posts[].id").type(JsonFieldType.NUMBER).description("작성글 고유 ID"),
                                        fieldWithPath("result.posts[].title").description("제목"),
                                        fieldWithPath("result.posts[].category").description("카테고리"),
                                        fieldWithPath("result.posts[].price").type(JsonFieldType.NUMBER).description("가격"),
                                        fieldWithPath("result.posts[].address").description("주소"),
                                        fieldWithPath("result.posts[].favorite_count").type(JsonFieldType.NUMBER).description("관심 수"),
                                        fieldWithPath("result.posts[].chatroom_count").type(JsonFieldType.NUMBER).description("채팅방 수"),
                                        fieldWithPath("result.posts[].thumbnail").description("썸네일 이미지"),
                                        fieldWithPath("result.posts[].created_time").description("작성 시간")
                                )
                        )
                );

    }

    @Test
    void getMyPost() throws Exception {
        String token = getToken();

        for(int i = 0; i < 3; i++) {
            makePost();
        }

        mockMvc.perform(
                        get("/api/product/post/{postId}/edit", 2L)
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("Authorization", "Bearer " + token)
                )
                .andExpect(status().isOk())
                .andDo(
                        MockMvcRestDocumentation.document("product-post-my-post",
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
                                        fieldWithPath("result.title").description("포스트 제목"),
                                        fieldWithPath("result.price").description("포스트 가격"),
                                        fieldWithPath("result.content").description("포스트 내용"),
                                        fieldWithPath("result.category").description("포스트 카테고리"),
                                        fieldWithPath("result.deal").description("포스트 가격 제안 여부"),
                                        subsectionWithPath("result.images[]").description("포스트 사진")
                                )
                        )
                );
    }

    @Test
    void updateMyPost() throws Exception {
        String token = getToken();

        for(int i = 0; i < 3; i++) {
            makePost();
        }

        ProductPostWriteRequest request =
                new ProductPostWriteRequest("newTitle", 20_000, "newContent",
                        "newCategory",  false, new String[]{"newImageUr1l", "newImageUrl2"});

        mockMvc.perform(
                        put("/api/product/post/{postId}/edit", 2L)
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("Authorization", "Bearer " + token)
                                .content(new ObjectMapper().writeValueAsString(request))
                )
                .andExpect(status().isOk())
                .andDo(
                        MockMvcRestDocumentation.document("product-post-my-post-edit",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                requestHeaders(
                                        headerWithName("Authorization").description("인증 토큰")
                                ),
                                requestFields(
                                        fieldWithPath("title").description("새로운 제목"),
                                        fieldWithPath("price").description("새로운 가격"),
                                        fieldWithPath("content").description("새로운 내용"),
                                        fieldWithPath("category").description("새로운 카테고리"),
                                        fieldWithPath("deal").description("가격 제안 여부"),
                                        fieldWithPath("images").description("새로운 이미지 URL 배열")
                                ),
                                responseFields(
                                        fieldWithPath("message").description("응답 메시지")
                                )
                        )
                );
    }

    @Test
    void deleteMyPost() throws Exception {
        String token = getToken();

        for(int i = 0; i < 3; i++) {
            makePost();
        }

        mockMvc.perform(
                        delete("/api/product/post/{postId}/delete", 2L)
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("Authorization", "Bearer " + token)
                )
                .andExpect(status().isOk())
                .andDo(
                        MockMvcRestDocumentation.document("product-post-my-post-delete",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                requestHeaders(
                                        headerWithName("Authorization").description("인증 토큰")
                                ),
                                responseFields(
                                        fieldWithPath("message").description("응답 메시지")
                                )
                        )
                );
    }

    @Test
    void searchPost() throws Exception {
        String token = getToken();

        for(int i = 0; i < 3; i++) {
            makePost();
        }

        mockMvc.perform(
                        get("/api/product/post/search")
                                .param("address", "address")
                                .param("keyword", "title")
                                .param("page", "0")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andDo(
                        MockMvcRestDocumentation.document("product-post-list-search",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                requestParameters(
                                        parameterWithName("address").description("주소"),
                                        parameterWithName("keyword").description("키워드"),
                                        parameterWithName("page").description("페이지 번호").optional()
                                ),
                                responseFields(
                                        fieldWithPath("message").description("응답 메시지"),
                                        subsectionWithPath("result").description("응답 결과"),
                                        fieldWithPath("result.count").description("포스트 개수"),
                                        subsectionWithPath("result.posts[]").type(JsonFieldType.ARRAY).description("포스트 결과"),
                                        fieldWithPath("result.posts[].id").type(JsonFieldType.NUMBER).description("작성글 고유 ID"),
                                        fieldWithPath("result.posts[].title").description("제목"),
                                        fieldWithPath("result.posts[].category").description("카테고리"),
                                        fieldWithPath("result.posts[].price").type(JsonFieldType.NUMBER).description("가격"),
                                        fieldWithPath("result.posts[].address").description("주소"),
                                        fieldWithPath("result.posts[].favorite_count").type(JsonFieldType.NUMBER).description("관심 수"),
                                        fieldWithPath("result.posts[].chatroom_count").type(JsonFieldType.NUMBER).description("채팅방 수"),
                                        fieldWithPath("result.posts[].thumbnail").description("썸네일 이미지"),
                                        fieldWithPath("result.posts[].created_time").description("작성 시간")
                                )
                        )
                );
    }
}