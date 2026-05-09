package com.hewak.blog.board;


import com.hewak.blog._core.errors.Exception403;
import com.hewak.blog.user.User;
import com.hewak.blog.util.MyDataUtil;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Data
@Entity
@Table(name = "board_tb")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @CreationTimestamp
    private Timestamp createdAt;

    public String getTime(){
        return MyDataUtil.timestampFormat(createdAt);
    }

    public void update(BoardRequest.UpdateDTO updateDTO){

        this.title = updateDTO.getTitle();
        this.content = updateDTO.getContent();

    }

    public boolean isOwner(Integer sessionUserId){
        if (!this.user.getId().equals(sessionUserId)){
            throw new Exception403("권한이 없습니다.");
        }
        return true;
    }

} // end of class
