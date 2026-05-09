package com.hewak.blog._core.interceptor;


import com.hewak.blog._core.errors.Exception401;
import com.hewak.blog.user.UserResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

// 로그인 인터셉터
@Component
public class LoginInterceptor implements HandlerInterceptor {
    //TODO
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        HttpSession session = request.getSession();
        UserResponse.SessionDTO sessionUser = (UserResponse.SessionDTO) session.getAttribute("sessionUser");;
        if (sessionUser == null){
            throw new Exception401("로그인이 필요한 서비스입니다.");
        }

        return HandlerInterceptor.super.preHandle(request, response, handler);
    } // end of preHandle
}
