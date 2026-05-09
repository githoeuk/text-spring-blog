package com.hewak.blog.board;

import com.hewak.blog.user.UserResponse;
import org.springframework.ui.Model;
import com.hewak.blog.user.User;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;


    // 주소 설계 : http://localhost:8080/board/save-from
    // 게시글 작성 화면 요청
    @GetMapping("/board/save-form")
    public String SaveForm() {
        return "board/save-form";
    }


    // 게시글 작성 기능 요청
    @PostMapping("/board/save")
    public String saveProc(BoardRequest.SaveDTO saveDTO, HttpSession session) {
        UserResponse.SessionDTO sessionDTO =
                (UserResponse.SessionDTO) session.getAttribute("sessionUser");
        saveDTO.validate();
        boardService.save(saveDTO, sessionDTO.getId());
        return "redirect:/";
    }

    // 주소 설계 : http://localhost:8080
    // 게시글 목록
    @GetMapping({"/", "index"})
    public String list(Model model) {

        List<BoardResponse.ListDTO> boardList = boardService.findByAll();
        model.addAttribute("boardList", boardList);
        return "board/list";

    }

    // 주소 설계 : http://localhost:8080/board/1
    // 게시글 상세보기
    @GetMapping("/board/{id}")
    public String detailPage(@PathVariable(name = "id") Integer id, Model model) {

        BoardResponse.DetailDTO detailDTO = boardService.findById(id);
        model.addAttribute("board", detailDTO);
        return "board/detail";
    }

    // 주소 설계 : http://localhost:8080
    // 게시글 삭제
    @PostMapping("/board/{id}/delete")
    public String deleteProc(@PathVariable(name = "id") Integer id,
                             Model model,
                             HttpSession session) {
        UserResponse.SessionDTO sessionUser = (UserResponse.SessionDTO) session.getAttribute("sessionUser");
        boardService.deletePage(id, sessionUser.getId());

        return "redirect:/";
    }

    // 주소 설계 : http://localhost:8080/board/1/update-form
    // 게시글 수정화면 요청
    @GetMapping("/board/{id}/update-form")
    public String updateForm(@PathVariable(name = "id") Integer id,
                             Model model,
                             HttpSession session
    ) {
        UserResponse.SessionDTO sessionUser =
                (UserResponse.SessionDTO) session.getAttribute("sessionUser");

        BoardResponse.DetailDTO detailDTO =
                boardService.detailPage(id, sessionUser.getId());

        model.addAttribute("board", detailDTO);
        return "board/update-form";
    }

    // 게시글 수정기능
    @PostMapping("/board/{id}/update")
    public String updateProc(@PathVariable(name = "id") Integer id,
                             BoardRequest.UpdateDTO updateDTO) {
                updateDTO.validate();
        boardService.updatePage(updateDTO, id);

        return "redirect:/board/" + id;
    }

} // end of class
