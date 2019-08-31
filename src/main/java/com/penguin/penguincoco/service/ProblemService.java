package com.penguin.penguincoco.service;

import com.penguin.penguincoco.common.exception.EntityNotFoundException;
import com.penguin.penguincoco.dao.domain.course.Course;
import com.penguin.penguincoco.dao.domain.problem.Problem;
import com.penguin.penguincoco.dao.domain.problem.ProblemInfo;
import com.penguin.penguincoco.dao.domain.problem.TestCase;
import com.penguin.penguincoco.service.base.BaseService;

import java.util.Date;
import java.util.List;

public interface ProblemService extends BaseService<Problem, Long> {

    void update(Long problemId, String name, String type,
                String category, String[] tag, String description,
                String inputDesc, String outputDesc,
                String[] pattern,
                List<TestCase> testCases, Date deadline) throws EntityNotFoundException;

    List<Problem> findByCourse(Course course);

    ProblemInfo getInfo(Long problemId) throws EntityNotFoundException;

    List<Problem> findByType(String type);

    List<Problem> findByCourseAndType(Course course, String type);

    int updateRateByProblemId(Long problemId, double rate);

    int countByBestStudentAccountAndCourse(String account, Course course);
}
