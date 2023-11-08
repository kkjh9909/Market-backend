package com.example.marketbackend.dto;

public class ResponseMessage {

    public static final String CHECK_ID_SUCCESS = "아이디 사용 가능";
    public static final String CHECK_ID_FAILED = "아이디 사용 불가능";
    public static final String VALIDATE_TOKEN = "유효한 토큰 인증";
    public static final String EDIT_PROFILE = "프로필 정보 수정 성공";
    public static final String SIGN_UP = "회원가입 성공";
    public static final String SIGN_IN = "로그인 성공";
    public static final String PRODUCT_POST_WRITE = "물건 판매글 작성 성공";
    public static final String POST_GET = "판매글 조회 성공";
    public static final String POSTS_GET = "판매글 목록 조회 성공";
    public static final String ADD_FAVORITE = "판매글 좋아요 성공";
    public static final String DELETE_FAVORITE = "판매글 좋아요 삭제 성공";
    public static final String CHAT_ROOM = "채팅방 생성 성공";
    public static final String CHATS_LIST = "채팅 목록 조회 성공";
    public static final String PROFILE_GET = "유저 정보 조회 성공";
    public static final String CHAT_ROOMS_GET = "채팅방 리스트 조회 성공";
    public static final String ID_GET = "유저 아이디 조회 성공";
    public static final String FAVORITE_POSTS_GET = "좋아요 누른 포스트 조회 성공";
    public static final String MY_POSTS_GET = "내 작성글 목록 조회 성공";
    public static final String MY_POST_GET = "내 작성글 조회 성공";
    public static final String MY_POST_UPDATE = "내 작성글 수정 성공";
    public static final String MY_POST_DELETE = "내 작성글 삭제 성공";
    public static final String POSTS_SEARCH = "게시글 검색 성공";


    public static final String NEIGHBOR_POSTS_GET = "일상 게시글 리스트 조회 성공";
    public static final String NEIGHBOR_POST_WRITE = "일상 게시글 작성 성공";
    public static final String NEIGHBOR_POST_GET = "일상 게시글 조회 성공";
    public static final String NEIGHBOR_ADD_LIKE = "일상 게시글 좋아요 성공";
    public static final String NEIGHBOR_DELETE_LIKE = "일상 게시글 좋아요 삭제 성공";
    public static final String NEIGHBOR_COMMENT_ADD = "일상 게시글 댓글 추가 성공";
    public static final String NEIGHBOR_COMMENT_GET = "일상 게시글 댓글 조회 성공";
    public static final String NEIGHBOR_COMMENT_ADD_LIKE = "일상 게시글 댓글 좋아요 성공";
    public static final String NEIGHBOR_COMMENT_DELETE_LIKE = "일상 게시글 댓글 삭제 성공";
}
