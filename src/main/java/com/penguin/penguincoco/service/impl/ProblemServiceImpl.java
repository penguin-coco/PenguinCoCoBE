package com.penguin.penguincoco.service.impl;

import com.penguin.penguincoco.common.exception.EntityNotFoundException;
import com.penguin.penguincoco.dao.domain.course.Course;
import com.penguin.penguincoco.dao.domain.problem.Problem;
import com.penguin.penguincoco.dao.domain.problem.ProblemInfo;
import com.penguin.penguincoco.dao.domain.problem.TestCase;
import com.penguin.penguincoco.dao.repository.ProblemRepository;
import com.penguin.penguincoco.dao.repository.base.BaseRepository;
import com.penguin.penguincoco.service.ProblemService;
import com.penguin.penguincoco.service.base.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ProblemServiceImpl extends BaseServiceImpl<Problem, Long> implements ProblemService {

    private ProblemRepository problemRepository;

    public ProblemServiceImpl(ProblemRepository problemRepository) {
        this.problemRepository = problemRepository;
    }

    @Override
    public BaseRepository<Problem, Long> getBaseRepository() {
        return problemRepository;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void update(Long problemId, String name, String type,
                          String category, String[] tag,
                          String description, String inputDesc,
                          String outputDesc,
                          String[] pattern, List<TestCase> testCases,
                          Date deadline) throws EntityNotFoundException {
        Problem problem = findById(problemId);
        problem.setName(name);
        problem.setType(type);
        problem.setCategory(category);
        problem.setTag(tag);
        problem.setDescription(description);
        problem.setInputDesc(inputDesc);
        problem.setOutputDesc(outputDesc);
        problem.setTestCases(testCases);
        problem.setDeadline(deadline);
        problem.setPattern(pattern);
        problemRepository.save(problem);
    }

    @Override
    public List<Problem> findByCourse(Course course) {
        return problemRepository.findByCourse(course);
    }

    @Override
    public ProblemInfo getInfo(Long problemId) throws EntityNotFoundException {
        Optional<Problem> instance = problemRepository.findById(problemId);
        Problem problem = instance.orElseThrow(EntityNotFoundException::new);
        List<TestCase> testCases = problem.getTestCases();
        ProblemInfo problemInfo = new ProblemInfo(problem.getName(),
                problem.getType(), problem.getCategory(), problem.getTag(),
                problem.getRate(), problem.getDescription(),
                problem.getInputDesc(), problem.getOutputDesc(),
                testCases, problem.getDeadline(),
                problem.getCorrectNum(), problem.getIncorrectNum(),
                problem.getCorrectRate(), problem.getBestStudentAccount(),
                problem.getPattern());
        return problemInfo;
    }

    @Override
    public List<Problem> findByType(String type) {
        return problemRepository.findByType(type);
    }

    @Override
    public List<Problem> findByCourseAndType(Course course, String type) {
        return problemRepository.findByCourseAndType(course, type);
    }

    @Transactional
    @Override
    public int updateRateByProblemId(Long problemId, double rate) {
        return problemRepository.updateRateByProblemId(problemId, rate);
    }

    @Override
    public int countByBestStudentAccountAndCourse(String account, Course course) {
        return problemRepository.countByBestStudentAccountAndCourse(account, course);
    }

}
