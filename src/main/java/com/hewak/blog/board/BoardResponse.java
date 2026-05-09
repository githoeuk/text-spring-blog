package com.hewak.blog.board;

import com.hewak.blog.util.MyDataUtil;
import lombok.Data;

// 응답 DTO
public class BoardResponse {

    //
    @Data
    public static class ListDTO{
        private Integer id;
        private String title;
        private String username;
        private String createdAt;

        public ListDTO(Board board){
            this.id = board.getId();
            this.title = board.getTitle();

            if (board.getUser() != null){
                this.username = board.getUser().getUsername();
            }
            if (board.getCreatedAt() != null){
                this.createdAt = MyDataUtil.timestampFormat(board.getCreatedAt());
            }

        }
    } // end of ListDTO


    //
    @Data
    public static class DetailDTO{

        private Integer id;
        private String title;
        private String content;
        private String username;
        private Integer userId;

        public DetailDTO(Board board){
            this.id = board.getId();
            this.title = board.getTitle();
            this.content = board.getContent();

            if (board.getUser() != null){
                this.username = board.getUser().getUsername();
                this.userId = board.getUser().getId();
            }
        }

    } // end of DetailDTO

}  // end of class
