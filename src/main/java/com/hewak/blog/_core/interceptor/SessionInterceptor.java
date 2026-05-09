package com.hewak.blog._core.interceptor;

import com.hewak.blog.user.User;
import com.hewak.blog.user.UserResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
public class SessionInterceptor implements HandlerInterceptor {

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);

        if (modelAndView != null){
            HttpSession session = request.getSession(false);

            if (session != null){
                UserResponse.SessionDTO sessionUser =
                        (UserResponse.SessionDTO) session.getAttribute("sessionUser");

                modelAndView.addObject("sessionUser",sessionUser);
            }

        }
    } // end of postHandle

} // end of class
