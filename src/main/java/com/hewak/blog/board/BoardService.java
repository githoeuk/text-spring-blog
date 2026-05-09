package com.hewak.blog.board;

import com.hewak.blog._core.errors.Exception403;
import com.hewak.blog._core.errors.Exception404;
import com.hewak.blog.user.User;
import com.hewak.blog.user.UserRepository;
import com.hewak.blog.user.UserResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardService {


    private final BoardRepository boardRepository;
    private final UserRepository userRepository;


    // 게시글 목록 조회
    public List<BoardResponse.ListDTO> findByAll(){
        log.info("게시글 목록 조회");
        List<Board> boardList = boardRepository.finaByAllJoinUser();
        log.info("게시글 목록 조회 완료 - 총 : {}", boardList.size());

        return boardList.stream()
                .map(BoardResponse.ListDTO::new)
                .collect(Collectors.toList());
    } // end of findByAll

    // 게시글 상세 조회
    public BoardResponse.DetailDTO findById(Integer id){

        log.info("게시글 상세 조회 - 게시글 번호 ; {} ",
                id);
        Board boardEntity = boardRepository.findByIdJoinUser(id).orElseThrow(() -> {
            log.warn("게시글 조회 실패 - Id : {} ", id);
            return new Exception404("해당 게시글을 찾을 수 없습니다.");
        });
        log.info("게시글 조회 완료 - 제목 : {} , 작성자 ; {} ",
                boardEntity.getTitle(), boardEntity.getUser().getUsername());

        return new BoardResponse.DetailDTO(boardEntity);
    } // end of findById

    // 게시글 상세 화면 요청
    public BoardResponse.DetailDTO detailPage(Integer id,Integer sessionUserId){

        log.info("게시글 상세 보기 요청 - 게시글 번호 : {}", id);

        BoardResponse.DetailDTO detailDTO = findById(id);

        if (!detailDTO.getUserId().equals(sessionUserId)){
            throw new Exception403("권한 없음");
        }
        log.info("게시글 상세 보기 완료 - 게시글 번호 : {}",id);

        return detailDTO;
    } // end of detailPage

    // 게시글 저장
    @Transactional
    public void save(BoardRequest.SaveDTO saveDTO, Integer sessionUserId){

        User sessionUser = userRepository.findById(sessionUserId).orElseThrow(() -> {
            return new Exception404("사용자 정보를 찾을 수 없습니다.");
        });

        log.info("게시글 저장 - 제목 : {} , 작성자 {} ",
                saveDTO.getTitle(),sessionUser.getUsername());

        Board boardEntity = saveDTO.toEntity(sessionUser);
        Board boardSavedEntity = boardRepository.save(boardEntity);

        log.info("게시글 저장 완료 - 제목 : {} , 내용 크기 : {}",
                boardSavedEntity.getTitle(),boardSavedEntity.getContent().length());
    } // end of save

    // 게시글 수정
    @Transactional
    public void updatePage(BoardRequest.UpdateDTO updateDTO, Integer id){

        log.info("게시글 수정 - 작성자 : {} , 제목 : {}",updateDTO.getUsername(),updateDTO.getTitle());

        Board boardEntity = boardRepository.findByIdJoinUser(id).orElseThrow(() -> {
            return new Exception404("해당 게시물을 찾을 수 없습니다.");
        });

        boardEntity.update(updateDTO);
        log.info("게시글 수정 완료- 작성자 : {} , 제목 : {}",updateDTO.getUsername(),updateDTO.getTitle());

    } // end of updatePage

    // 게시글 삭제
    @Transactional
    public void deletePage(Integer id, Integer sessionUserId){

        log.info("게시글 삭제 요청 - 게시글 ID : {}",id);
        Board boardEntity = boardRepository.findById(id).orElseThrow(() -> {
            return new Exception404("해당 게시물을 찾을 수 없습니다.");
        });

        boardEntity.isOwner(sessionUserId);
        boardRepository.deleteById(id);

        log.info("게시글 삭제 완료 - 게시글 ID : {}",id);

    } // end of deletePage

} // end of class
