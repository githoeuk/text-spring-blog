package com.hewak.blog.user;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // 프로필 화면 요청
    @GetMapping("/user/update-form")
    public String updateForm(HttpSession session, Model model){
        UserResponse.SessionDTO sessionUser = (UserResponse.SessionDTO) session.getAttribute("sessionUser");
        UserResponse.JoinDTO joinDTO = userService.findByUser(sessionUser.getId());
        model.addAttribute("user",joinDTO);
        return "/user/update-form";
    }

    // 프로필 수정 요청
    @PostMapping("/user/update")
    public String  updateProc(UserRequest.UpdateDTO updateDTO,
                              HttpSession session){

        updateDTO.validate();
        UserResponse.SessionDTO sessionUser =
                (UserResponse.SessionDTO) session.getAttribute("sessionUser");;
        userService.update(sessionUser.getId(),updateDTO,session);
        return "redirect:/";
    }

    // 로그인 화면 요청
    // 주소 설계 : http://localhost:8080/login-form
    @GetMapping("/login-form")
    public String loginForm(){
        return "user/login-form";
    }

    // 로그인 기능 요청
    @PostMapping("/login")
    public String loginProc(UserRequest.LoginDTO loginDTO,
                            HttpSession session){

        loginDTO.validate();
        UserResponse.SessionDTO sessionDTO = userService.login(loginDTO);
        session.setAttribute("sessionUser",sessionDTO);
        return "redirect:/";
    }

    // 로그아웃 기능 요청
    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "redirect:/";
    }

    //회원가입 화면 요청
    // http://localhost:8080/join-form
    @GetMapping("/join-form")
    public String JoinForm(){
        return "user/join-form";
    }

    //회원가입 기능 요청
    @PostMapping("/join")
    public String joinProc(UserRequest.JoinDTO joinDTO){

        joinDTO.validate();
        userService.Join(joinDTO);
        return "redirect:/login-form";
    }

} // end of class
