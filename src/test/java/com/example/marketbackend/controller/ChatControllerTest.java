package com.example.marketbackend.controller;

import com.example.marketbackend.service.ChatService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
class ChatControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ChatService chatService;

    @Test
    void handleChatMessage() {
    }

    @Test
    void onConnect() {
    }

    @Test
    void onDisconnect() {
    }
}