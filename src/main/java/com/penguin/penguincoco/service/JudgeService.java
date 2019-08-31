package com.penguin.penguincoco.service;

import com.penguin.penguincoco.common.exception.EntityNotFoundException;
import com.penguin.penguincoco.dao.domain.course.Course;
import com.penguin.penguincoco.dao.domain.judge.Judge;
import com.penguin.penguincoco.dao.domain.problem.Problem;
import com.penguin.penguincoco.dao.domain.student.Student;
import com.penguin.penguincoco.service.base.BaseService;

import java.util.List;

public interface JudgeService extends BaseService<Judge, Long> {

    int countByProblemAndStudent(Problem problem, Student student);

    Judge findByProblemAndStudent(Problem problem, Student student) throws EntityNotFoundException;

    boolean existByProblemAndStudent(Problem problem, Student student);

    List<Judge> findByProblem(Problem problem);

    int updateRateByProblemAndStudent(double rate, Problem problem, Student student);

    double getAvgRateByProblem(Problem problem);

    List<Judge> findByStudent(Student student);

    int countByProblem(Problem problem);

    List<Judge> findByCourseAndStudent(Course course, Student student);
}
