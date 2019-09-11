package com.penguin.penguincoco.config.security.login;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

public class LoginFailureHandlerImpl implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        Map<String, String> result = Map.of("error", "帳號或密碼輸入錯誤");
        ObjectMapper mapper = new ObjectMapper();

        httpServletResponse.setContentType("application/json;charset=UTF-8");
        httpServletResponse.setStatus(HttpServletResponse.SC_NOT_FOUND);
        httpServletResponse.getWriter().write(mapper.writeValueAsString(result));
        httpServletResponse.getWriter().flush();
        httpServletResponse.getWriter().close();
    }
}
