package com.hewak.blog.user;

import com.hewak.blog._core.errors.Exception400;
import com.hewak.blog._core.errors.Exception401;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;


public class UserRequest {

    // 로그인
    @Data
    @NoArgsConstructor
    public static class LoginDTO{

        private String username;
        private String password;

        public void validate(){
            if (username == null || username.trim().isEmpty()){
                throw new Exception401("사용자 이름을 입력하세요 ");
            }
            if (password == null || password.trim().isEmpty()){
                throw  new Exception401("비밀번호를 입력하세요");
            }
        } // end of validate

    } // end of LoginDTO

    // 회원가입
    @Data
    @NoArgsConstructor
    public static class JoinDTO{

        private String username;
        private String password;
        private String email;

        public User toEntity(){
            return User.builder()
                    .username(username)
                    .password(password)
                    .email(email)
                    .build();
        }

        public void validate(){
            if (username == null || username.trim().isEmpty()){
                throw new Exception400("사용자 이름은 필수사항입니다 ");
            }
            if (password == null || password.trim().isEmpty()){
                throw new Exception400("비밀번호은 필수사항입니다");
            }
            if (password.length() < 3){
                throw new Exception400("비밀번호는 4글자 이상이어야 합니다.");
            }
            if (email == null || email.trim().isEmpty()){
                throw new Exception400("이메일은 필수사항입니다");
            }
            if (!email.contains("@")){
                throw new Exception400("올바른 이메일 형식이 아닙니다.");
            }
        } // end of validate

    } // end of JoinDTO

    // 회원정보수정
    @Data
    @NoArgsConstructor
    public static class UpdateDTO{
        private String password;

        public void validate(){
            if (password == null || password.trim().isEmpty()){
                throw new Exception400("비밀번호는 필수사항입니다.");
            }
            if (password.length() <3){
                throw new Exception400("비밀번호는 4글자 이상이어야 합니다.");
            }
        }

    } // end of UpdateDTO


} // end of class
