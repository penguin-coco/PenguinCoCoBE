package com.penguin.penguincoco.service;

import com.penguin.penguincoco.dao.domain.copy.Copy;
import com.penguin.penguincoco.dao.domain.problem.Problem;
import com.penguin.penguincoco.service.base.BaseService;

import java.util.List;

public interface CopyService extends BaseService<Copy, Long> {

    List<Copy> findByProblemAndStudentTwoAccount(Problem problem, String account);

    List<Copy> findByProblem(Problem problem);
}
