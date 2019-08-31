package com.penguin.penguincoco.manager.impl;

import com.penguin.penguincoco.common.exception.EntityNotFoundException;
import com.penguin.penguincoco.dao.domain.course.Course;
import com.penguin.penguincoco.dao.domain.judge.HistoryCode;
import com.penguin.penguincoco.dao.domain.judge.Judge;
import com.penguin.penguincoco.dao.domain.problem.Problem;
import com.penguin.penguincoco.dao.domain.student.Student;
import com.penguin.penguincoco.manager.ProblemManager;
import com.penguin.penguincoco.service.CourseService;
import com.penguin.penguincoco.service.JudgeService;
import com.penguin.penguincoco.service.ProblemService;
import com.penguin.penguincoco.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProblemManagerImpl implements ProblemManager {

    private ProblemService problemService;
    private CourseService courseService;
    private JudgeService judgeService;
    private StudentService studentService;

    @Autowired
    public ProblemManagerImpl(ProblemService problemService,
                              CourseService courseService,
                              JudgeService judgeService,
                              StudentService studentService) {
        this.problemService = problemService;
        this.courseService = courseService;
        this.judgeService = judgeService;
        this.studentService = studentService;
    }

    // 根據課程Id、學生account，取得學生擁有的題目資訊(含作答及未作答)
    @Override
    public List<Map<String, Object>> getStudentProblemInfo(Long courseId, String type, boolean isJudge, String account) throws EntityNotFoundException {
        Course course = courseService.findById(courseId);
        Student student = studentService.findByAccount(account);
        List<Problem> problems;
        List<Problem> realProblems = new ArrayList<>();
        if (type.equals("全部")) {
            problems = problemService.findByCourse(course);
        }
        else {
            problems = problemService.findByCourseAndType(course, type);
        }
        if (isJudge) {
            for (Problem problem : problems) {
                boolean isExist = judgeService.existByProblemAndStudent(problem, student);
                if (isExist) {
                    realProblems.add(problem);
                }
            }
        }
        else {
            for (Problem problem : problems) {
                boolean isExist = judgeService.existByProblemAndStudent(problem, student);
                if (!isExist) {
                    realProblems.add(problem);
                }
            }
        }
        List<Map<String, Object>> results = new ArrayList<>();
        for (Problem problem : realProblems) {
            Map<String, Object> map = new HashMap<>();
            map.put("problemId", String.valueOf(problem.getId()));
            map.put("name", problem.getName());
            map.put("type", problem.getType());
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            map.put("deadline", df.format(problem.getDeadline()));
            map.put("rate", String.valueOf(problem.getRate()));
            map.put("tag", problem.getTag());
            results.add(map);
        }
        return results;
    }

    @Override
    public List<Map<String, Object>> getStudentsData(Long courseId) throws EntityNotFoundException {
        Course course = courseService.findById(courseId);
        List<Student> students = course.getStudents();
        List<Problem> problems = course.getProblems();

        List<Map<String, Object>> result = new ArrayList<>();
        for (Student student : students) {
            Map<String, Object> studentResult = new HashMap<>();
            String studentId = student.getAccount();
            String studentName = student.getName();
            String studentClass = student.getStudentClass();
            studentResult.put("studentId", studentId);
            studentResult.put("studentName", studentName);
            studentResult.put("studentClass", studentClass);

            List<Map<String, Object>> problemList = new ArrayList<>();
            for (Problem problem : problems) {
                Map<String, Object> problemResult = new HashMap<>();
                String name = problem.getName();
                String type = problem.getType();
                String date = new SimpleDateFormat("yyyy-MM-dd").format(problem.getDeadline());
                List<HistoryCode> historyCode = new ArrayList<>();
                boolean isJudge = judgeService.existByProblemAndStudent(problem, student);
                if (isJudge) {
                    Judge judge = judgeService.findByProblemAndStudent(problem, student);
                    historyCode = judge.getHistoryCodes();
                }
                problemResult.put("name", name);
                problemResult.put("type", type);
                problemResult.put("date", date);
                problemResult.put("historyCode", historyCode);
                problemList.add(problemResult);
            }
            studentResult.put("problems", problemList);
            result.add(studentResult);
        }
        return result;
    }
}
