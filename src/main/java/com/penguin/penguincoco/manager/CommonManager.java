package com.penguin.penguincoco.manager;


import com.penguin.penguincoco.common.exception.EntityNotFoundException;

public interface CommonManager {

    String findUserAuthority(String account, String password) throws EntityNotFoundException;

    int updateUserPassword(String account, String oriPassword, String newPassword, String userType);
}
