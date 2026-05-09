package com.hewak.blog.board;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Integer> {

    // 단건 조회
    @Query("""
            SELECT b FROM Board b join FETCH b.user WHERE b.id = :id
            """)
    Optional<Board> findByIdJoinUser(@Param("id") Integer id);

    // 전체 조회
    @Query("""
            SELECT b FROM Board b JOIN FETCH b.user ORDER BY b.id DESC
            """)
    List<Board> finaByAllJoinUser();

} // end of class
