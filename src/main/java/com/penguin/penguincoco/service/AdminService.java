package com.penguin.penguincoco.service;

import com.penguin.penguincoco.common.exception.EntityNotFoundException;
import com.penguin.penguincoco.dao.domain.admin.Admin;
import com.penguin.penguincoco.service.base.BaseService;

public interface AdminService extends BaseService<Admin, Long> {

    boolean existByAccount(String account);

    Admin findByAccount(String account) throws EntityNotFoundException;

    int updatePasswordByAccount(String account, String oriPassword, String newPassword);

}
