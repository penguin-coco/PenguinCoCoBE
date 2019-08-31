package com.penguin.penguincoco.service.impl;

import com.penguin.penguincoco.common.exception.EntityNotFoundException;
import com.penguin.penguincoco.dao.domain.problem.TestCase;
import com.penguin.penguincoco.dao.domain.problembank.ProblemBank;
import com.penguin.penguincoco.dao.repository.ProblemBankRepository;
import com.penguin.penguincoco.dao.repository.base.BaseRepository;
import com.penguin.penguincoco.service.ProblemBankService;
import com.penguin.penguincoco.service.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProblemBankServiceImpl extends BaseServiceImpl<ProblemBank, Long> implements ProblemBankService {

    private ProblemBankRepository problemBankRepository;

    @Autowired
    public ProblemBankServiceImpl(ProblemBankRepository problemBankRepository) {
        this.problemBankRepository = problemBankRepository;
    }

    @Override
    public BaseRepository<ProblemBank, Long> getBaseRepository() {
        return problemBankRepository;
    }

    @Override
    public void update(Long problemBankId, String name, String category, String[] tag, String description, String inputDesc, String outputDesc, List<TestCase> testCases) throws EntityNotFoundException {
        ProblemBank problemBank = findById(problemBankId);
        problemBank.setName(name);
        problemBank.setCategory(category);
        problemBank.setDescription(description);
        problemBank.setTag(tag);
        problemBank.setInputDesc(inputDesc);
        problemBank.setOutputDesc(outputDesc);
        problemBank.setTestCases(testCases);
        save(problemBank);
    }

    @Override
    public List<Map<String, Object>> getAllProblem() {
        List<Map<String, Object>> result = new ArrayList<>();
        List<ProblemBank> problemBanks = findAll();

        problemBanks.forEach(problemBank -> {
            Map<String, Object> problemInfo = new HashMap<>();
            problemInfo.put("id", problemBank.getId());
            problemInfo.put("name", problemBank.getName());
            problemInfo.put("category", problemBank.getCategory());
            problemInfo.put("tag", problemBank.getTag());
            result.add(problemInfo);
        });
        return result;
    }
}
