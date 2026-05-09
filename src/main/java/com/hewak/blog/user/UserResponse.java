package com.hewak.blog.user;

import lombok.Data;

// 사용자 응답
public class UserResponse {


    // 회원 가입
    @Data
    public static class JoinDTO{

        private Integer id;
        private String username;
        private String email;

        public JoinDTO(User user){
            this.id = user.getId();
            this.username = user.getUsername();
            this.email = user.getEmail();
        }

    } // end of JoinDTO

    // 로그인 관련 DTO
    // 회원 수정
    @Data
    public static class SessionDTO {

        private Integer id;
        private String username;
        private String email;

        public SessionDTO(User user){
            this.id = user.getId();
            this.username = user.getUsername();
            this.email = user.getEmail();
        }
    } // end of SessionDTO


} // end of class
