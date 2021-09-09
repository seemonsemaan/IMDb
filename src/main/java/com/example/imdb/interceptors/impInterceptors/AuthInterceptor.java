package com.example.imdb.interceptors.impInterceptors;

import com.example.imdb.classes.LoginDTO;
import com.example.imdb.repositories.AuthRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AuthInterceptor implements HandlerInterceptor {
    @Autowired
    AuthRepository authRepository;

    @Override
    public boolean preHandle(
            HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        LoginDTO login = authRepository.getCurrentUser();
        if(login != null){
            int res = authRepository.login(login);
            if(res == 1) return true;
        }
        response.getWriter().write("Not authorized!");
        response.setStatus(401);
        return false;
    }
}
