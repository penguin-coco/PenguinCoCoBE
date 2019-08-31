package com.penguin.penguincoco.service.impl;

import com.penguin.penguincoco.dao.domain.copy.Copy;
import com.penguin.penguincoco.dao.domain.problem.Problem;
import com.penguin.penguincoco.dao.repository.CopyRepository;
import com.penguin.penguincoco.dao.repository.base.BaseRepository;
import com.penguin.penguincoco.service.CopyService;
import com.penguin.penguincoco.service.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CopyServiceImpl extends BaseServiceImpl<Copy, Long> implements CopyService {

    private CopyRepository copyRepository;

    @Autowired
    public CopyServiceImpl(CopyRepository copyRepository) {
        this.copyRepository = copyRepository;
    }

    @Override
    public BaseRepository<Copy, Long> getBaseRepository() {
        return copyRepository;
    }

    @Override
    public List<Copy> findByProblemAndStudentTwoAccount(Problem problem, String account) {
        return copyRepository.findByProblemAndStudentTwoAccount(problem, account);
    }

    @Override
    public List<Copy> findByProblem(Problem problem) {
        return copyRepository.findByProblem(problem);
    }
}
