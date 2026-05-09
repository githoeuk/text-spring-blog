package com.hewak.blog.board;

import com.hewak.blog._core.errors.Exception403;
import com.hewak.blog.user.User;
import lombok.Builder;
import lombok.Data;


// 요청 DTO
public class BoardRequest {

    // 게시글 저장 DTO
    @Data
    @Builder
    public static class SaveDTO{

        private String title;
        private String content;

        public Board toEntity(User user){
            return Board.builder()
                    .title(title)
                    .user(user)
                    .content(content)
                    .build();
        }

        // 유효성 검사
        public void validate(){
            if (title == null || title.trim().isEmpty()){
                throw new Exception403("제목을 입력해주세요");
            }
            if (content == null || content.trim().isEmpty()){
                throw new Exception403("내용을 입력해주세요");
            }
        }

    } // end of SaveDTO

    @Data
    public static class UpdateDTO{
        private String username;
        private String title;
        private String content;

        public void validate(){
            if (username == null || username.trim().isEmpty()){
                throw new Exception403("유저 이름을 입력해주세요");
            } else if (title == null || title.trim().isEmpty()) {
                throw new Exception403("제목을 입력해주세요");
            }else if (content == null || content.length() < 3){
                throw new Exception403("내용은 최소 3글자 이상이어야 합니다.");
            }
        }

    } // end of UpdateDTO


} // end of class
