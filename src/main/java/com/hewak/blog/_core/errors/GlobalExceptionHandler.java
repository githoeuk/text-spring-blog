package com.hewak.blog._core.errors;


import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception400.class)
    public String ex400(Exception400 e, HttpServletRequest request, Model model){
        log.warn("=== 400 Bad Request 에러 발생 ===");
        log.warn("요청 URL : {}", request.getRequestURL());
        log.warn("에러 메세지 : {} ", e.getMessage());

        model.addAttribute("msg", e.getMessage());
        return "err/400";
    } // end of ex400

    @ExceptionHandler(Exception401.class)
    @ResponseBody
    public String ex401(Exception401 e, HttpServletRequest request){
        String script = """
            <script>
                alert('%s');
                location.href='/login-form';
            </script>
            """.formatted(e.getMessage());
        return script;
    } // end of ex401


    @ExceptionHandler(Exception403.class)
    @ResponseBody
    public String ex403(Exception403 e, HttpServletRequest request){

        String script = "<script>alert(' " + e.getMessage() + " ');" +
                "history.back();" +
                "</script>";

        return script;
    } // end of ex403


    @ExceptionHandler(Exception404.class)
    public String ex404(Exception404 e, HttpServletRequest request, Model model){
        log.warn("=== 404 Not found 에러 발생 ===");
        log.warn("요청 URL : {}", request.getRequestURL());
        log.warn("에러 메세지 : {} ", e.getMessage());

        model.addAttribute("msg", e.getMessage());
        return "err/404";
    } // end of ex400


    @ExceptionHandler(Exception500.class)
    public String ex500(Exception500 e, HttpServletRequest request, Model model){
        log.warn("=== 500 Internal Server Error 에러 발생 ===");
        log.warn("요청 URL : {}", request.getRequestURL());
        log.warn("에러 메세지 : {} ", e.getMessage());

        model.addAttribute("msg", e.getMessage());
        return "err/500";
    } // end of ex400


    @ExceptionHandler(RuntimeException.class)
    public String handleRuntimeException(RuntimeException e, HttpServletRequest request, Model model){

        log.warn("=== 예상치 못한 런타임 에러 발생 ===");
        log.warn("요청 URL : {} ", request.getRequestURL() );
        log.warn("에러 메세지 : {} ", e.getMessage());

        model.addAttribute( "msg", "시스템 오류가 발생했습니다. 관리자에게 문의해주세요");
        return "err/500";

    }

} // end of class
