package com.penguin.penguincoco.manager;

import com.penguin.penguincoco.common.exception.EntityExistsException;
import com.penguin.penguincoco.common.exception.EntityNotFoundException;
import com.penguin.penguincoco.dao.domain.course.Course;
import com.penguin.penguincoco.dao.domain.feedback.Feedback;
import com.penguin.penguincoco.dao.domain.problem.Problem;
import com.penguin.penguincoco.dao.domain.problem.TestCase;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface CourseManager {

    Course createCourse(String account, String courseName, String semester, String studentClass, List<String> taList) throws EntityNotFoundException;

    void deleteCourseById(Long id) throws EntityNotFoundException;

    void mapStudentListToCourse(Long courseId, List<String> accounts) throws EntityNotFoundException, EntityExistsException;

    void deleteStudentListFromCourse(Long courseId, List<String> accounts) throws EntityNotFoundException;

    void mapAssistantListToCourse(Long courseId, List<String> accounts) throws EntityNotFoundException;

    void deleteAssistantListFromCourse(Long courseId, List<String> accounts) throws EntityNotFoundException;

    List<Map<String, Object>> getCoursesInfo(String account) throws EntityNotFoundException;

    Map<String, String> createProblem(Long courseId, String name, String type,
                                      String category, String[] tag, String description,
                                      String inputDesc, String outputDesc, String[] pattern,
                                      List<TestCase> testCases, Date deadline) throws EntityNotFoundException;

    List<Problem> findByCourseId(Long courseId) throws EntityNotFoundException;

    Map<String, String> findStudentCourseInfo(Long courseId, String account) throws EntityNotFoundException;

    Feedback addFeedback(Long courseId, String account, String content) throws ParseException, EntityNotFoundException;

    List<Map<String, String>> findFeedbacksByCourseId(Long courseId) throws EntityNotFoundException;

    List<Map<String, String>> getStudentCoursesInfo(String account) throws EntityNotFoundException;

    void editCourse(String account, Long courseId, String courseName, String semester, List<String> taList)  throws EntityNotFoundException;

    List<Map<String, Object>> getCoursesInfoByAssistantAccount(String account) throws EntityNotFoundException;

    List<String> findAllStudentAccountByCourseId(Long courseId);
}
