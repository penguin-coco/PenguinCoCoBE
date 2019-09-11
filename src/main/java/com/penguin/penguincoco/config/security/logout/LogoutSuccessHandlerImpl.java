package com.penguin.penguincoco.config.security.logout;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class LogoutSuccessHandlerImpl implements LogoutSuccessHandler {

    @Override
    public void onLogoutSuccess(HttpServletRequest httpServletRequest,
                                HttpServletResponse httpServletResponse,
                                Authentication authentication) throws IOException, ServletException {
        HttpSession session = httpServletRequest.getSession();
        session.invalidate();

        ObjectMapper mapper = new ObjectMapper();
        Map<String, String> result = new HashMap<>();

        httpServletResponse.setContentType("application/json;charset=UTF-8");
        httpServletResponse.setStatus(HttpServletResponse.SC_NO_CONTENT);
        httpServletResponse.getWriter().write(mapper.writeValueAsString(result));
        httpServletResponse.getWriter().flush();
        httpServletResponse.getWriter().close();
    }

}
