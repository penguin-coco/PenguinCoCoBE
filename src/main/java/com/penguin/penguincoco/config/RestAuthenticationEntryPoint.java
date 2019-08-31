package com.penguin.penguincoco.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.penguin.penguincoco.common.message.ApiMessageCode;
import com.penguin.penguincoco.common.message.Message;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RestAuthenticationEntryPoint
        implements AuthenticationEntryPoint {

    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException) throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        Message message = new Message(ApiMessageCode.NOT_LOGIN_ERROR, "");
        String jsonMessage = mapper.writeValueAsString(message);
        response.setContentType("application/json;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write(jsonMessage);
        response.getWriter().flush();
        response.getWriter().close();
    }
}