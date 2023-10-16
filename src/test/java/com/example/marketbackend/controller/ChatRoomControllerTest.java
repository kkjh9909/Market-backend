package com.example.marketbackend.controller;

import com.example.marketbackend.dto.Response;
import com.example.marketbackend.dto.post.request.ProductPostWriteRequest;
import com.example.marketbackend.dto.user.request.UserSignInRequest;
import com.example.marketbackend.dto.user.request.UserSignUpRequest;
import com.example.marketbackend.dto.user.response.UserSignInResponse;
import com.example.marketbackend.entity.Chat;
import com.example.marketbackend.entity.ChatRoom;
import com.example.marketbackend.entity.ProductPost;
import com.example.marketbackend.repository.ChatRepository;
import com.example.marketbackend.repository.ChatRoomRepository;
import com.example.marketbackend.repository.ProductPostRepository;
import com.example.marketbackend.repository.UserRepository;
import com.example.marketbackend.service.ChatRoomService;
import com.example.marketbackend.service.ChatService;
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
class ChatRoomControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService userService;

    @Autowired
    private ProductPostService productPostService;

    @Autowired
    private ChatRoomService chatRoomService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private ChatRoomRepository chatRoomRepository;

    public String getToken() {
        signup();

        Response response = userService.signIn(new UserSignInRequest("userId2", "password"));

        UserSignInResponse result = (UserSignInResponse) response.getResult();

        return result.getAccessToken();
    }

    public void signup() {
        UserSignUpRequest request = new UserSignUpRequest("userId", "password", "username", "nickname", "profileImage", "address");

        userService.signUp(request);
    }

    public void makePost() {
        UserDetailsService userDetailsService = Mockito.mock(UserDetailsService.class);

        UserDetails userDetails = User.withUsername("2")
                .password("encodedPassword")
                .roles("USER")
                .build();

        Mockito.when(userDetailsService.loadUserByUsername("userId2")).thenReturn(userDetails);

        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities()));

        ProductPostWriteRequest request = new ProductPostWriteRequest("title", 10_000, "content", "category", true, new String[]{"imageUrl1", "imageUrl2"});

        productPostService.write(request);
    }

    @Test
    @Transactional
    void getChatRoom() throws Exception {

        userService.signUp(new UserSignUpRequest("userId2", "password", "username", "nickname", "profile", "address"));

        String token = getToken();
        makePost();

        mockMvc.perform(
                        get("/api/chatroom")
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("postId", "3")
                                .param("receiverId", "2")
                                .header("Authorization", "Bearer " + token)
                )
                .andExpect(status().isOk())
                .andDo(
                        MockMvcRestDocumentation.document("chatroom-get",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                requestHeaders(
                                        headerWithName("Authorization").description("인증 토큰")
                                ),
                                requestParameters(
                                        parameterWithName("postId").description("중고글 ID"),
                                        parameterWithName("receiverId").description("수신자 ID")
                                ),
                                responseFields(
                                        fieldWithPath("message").description("응답 메시지"),
                                        subsectionWithPath("result").description("응답 결과"),
                                        fieldWithPath("result.chatroom_number").description("채팅방 ID"),
                                        fieldWithPath("result.my_id").description("내 ID")
                                )
                        )
                );
    }

    public void createChatRoom() {
        UserDetailsService userDetailsService = Mockito.mock(UserDetailsService.class);

        UserDetails userDetails = User.withUsername("1")
                .password("encodedPassword")
                .roles("USER")
                .build();

        Mockito.when(userDetailsService.loadUserByUsername("userId2")).thenReturn(userDetails);

        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities()));

        chatRoomService.getChatRoomNum(3, 2);
    }

    @Test
    void getChatRoomList() throws Exception {

        userService.signUp(new UserSignUpRequest("userId2", "password", "username", "nickname", "profile", "address"));

        String token = getToken();
        makePost();

        createChatRoom();

        Optional<ChatRoom> chatRoom = chatRoomRepository.findById(6L);
        Optional<com.example.marketbackend.entity.User> user = userRepository.findById(1L);

        Chat chat = new Chat("message", user.get(), chatRoom.get());
        chatRepository.save(chat);

        mockMvc.perform(
                        get("/api/chatroom/list")
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("page", "0")
                                .header("Authorization", "Bearer " + token)
                )
                .andExpect(status().isOk())
                .andDo(
                        MockMvcRestDocumentation.document("chatroom-list-get",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                requestHeaders(
                                        headerWithName("Authorization").description("인증 토큰")
                                ),
                                requestParameters(
                                        parameterWithName("page").description("페이지 번호").optional()
                                ),
                                responseFields(
                                        fieldWithPath("message").description("응답 메시지"),
                                        subsectionWithPath("result").description("응답 결과"),
                                        fieldWithPath("result.count").description("채팅방 수"),
                                        subsectionWithPath("result.chat_rooms[]").type(JsonFieldType.ARRAY).description("채팅방 목록"),
                                        fieldWithPath("result.chat_rooms[].id").description("채팅방 번호"),
                                        fieldWithPath("result.chat_rooms[].last_message").description("마지막 채팅 메시지"),
                                        fieldWithPath("result.chat_rooms[].last_message_time").description("마지막 채팅 시간"),
                                        fieldWithPath("result.chat_rooms[].other_nickname").description("상대방 닉네임"),
                                        fieldWithPath("result.chat_rooms[].other_profile").description("상대방 프로필 이미지"),
                                        fieldWithPath("result.chat_rooms[].other_address").description("상대방 주소")

                                )
                        )
                );
    }
}