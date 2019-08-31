package com.penguin.penguincoco.service.impl;

import com.penguin.penguincoco.common.exception.EntityNotFoundException;
import com.penguin.penguincoco.dao.domain.admin.Admin;
import com.penguin.penguincoco.dao.repository.AdminRepository;
import com.penguin.penguincoco.dao.repository.base.BaseRepository;
import com.penguin.penguincoco.service.AdminService;
import com.penguin.penguincoco.service.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl extends BaseServiceImpl<Admin, Long> implements AdminService {

    private AdminRepository adminRepository;

    @Autowired
    public AdminServiceImpl(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    @Override
    public BaseRepository<Admin, Long> getBaseRepository() {
        return adminRepository;
    }

    @Override
    public boolean existByAccount(String account) {
        return adminRepository.existsByAccount(account);
    }

    @Override
    public Admin findByAccount(String account) throws EntityNotFoundException {
        return adminRepository.findByAccount(account).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public int updatePasswordByAccount(String account, String oriPassword, String newPassword) {
        return adminRepository.updatePasswordByAccountAndPassword(account, oriPassword, newPassword);
    }

}
