package com.penguin.penguincoco.api.base;

import javax.servlet.http.HttpSession;

public class BaseApi {

    private final String LOGGED_IN = "logged_in";
    private final String USER_TYPE = "user_type";

    protected void setUserAccount(String account, HttpSession session) {
        session.setAttribute(LOGGED_IN, account);
    }

    protected String getUserAccount(HttpSession session) {
        return session.getAttribute(LOGGED_IN).toString();
    }

    protected String getUserType(HttpSession session) {
        return session.getAttribute(USER_TYPE).toString();
    }

    protected void setUserType(String type, HttpSession session) {
        session.setAttribute(USER_TYPE, type);
    }

    protected boolean isLogin(HttpSession session) {
        return session.getAttribute(LOGGED_IN) != null;
    }

    protected void destroySession(HttpSession session) {
        session.invalidate();
    }
}
