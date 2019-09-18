package com.penguin.penguincoco.config.security.login;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.session.FindByIndexNameSessionRepository;
import org.springframework.session.Session;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class LoginSuccessHandlerImpl implements AuthenticationSuccessHandler {

    private final String LOGGED_IN = "logged_in";

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest,
                                        HttpServletResponse httpServletResponse,
                                        Authentication authentication) throws IOException {
        String account = authentication.getName();
        Collection authorityCollection = authentication.getAuthorities();
        String authority = authorityCollection.iterator().next().toString().replace("ROLE_", "");

        HttpSession session = httpServletRequest.getSession();
        session.setAttribute(LOGGED_IN, account);

        ObjectMapper mapper = new ObjectMapper();
        Map<String, String> result = new HashMap<>();
        result.put("authority", authority);

        httpServletResponse.setContentType("application/json;charset=UTF-8");
        httpServletResponse.setStatus(HttpServletResponse.SC_OK);
        httpServletResponse.getWriter().write(mapper.writeValueAsString(result));
        httpServletResponse.getWriter().flush();
        httpServletResponse.getWriter().close();
    }
}
