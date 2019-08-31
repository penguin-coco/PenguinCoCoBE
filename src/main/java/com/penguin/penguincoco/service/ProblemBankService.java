package com.penguin.penguincoco.service;

import com.penguin.penguincoco.common.exception.EntityNotFoundException;
import com.penguin.penguincoco.dao.domain.problem.TestCase;
import com.penguin.penguincoco.dao.domain.problembank.ProblemBank;
import com.penguin.penguincoco.service.base.BaseService;

import java.util.List;
import java.util.Map;

public interface ProblemBankService extends BaseService<ProblemBank, Long> {

    void update(Long problemBankId, String name,
                String category, String[] tag, String description,
                String inputDesc, String outputDesc,
                List<TestCase> testCases) throws EntityNotFoundException;

    List<Map<String, Object>> getAllProblem();
}
