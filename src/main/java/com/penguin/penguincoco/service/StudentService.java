package com.penguin.penguincoco.service;

import com.penguin.penguincoco.common.exception.EntityNotFoundException;
import com.penguin.penguincoco.dao.domain.course.Course;
import com.penguin.penguincoco.dao.domain.student.Student;
import com.penguin.penguincoco.dao.domain.student.StudentInfo;
import com.penguin.penguincoco.service.base.BaseService;

import java.util.List;
import java.util.Map;

public interface StudentService extends BaseService<Student, Long> {

    boolean existByAccount(String account);

    Student findByAccount(String account) throws EntityNotFoundException;

    int updatePasswordByAccount(String account, String oriPassword, String newPassword);

    List<Course> findCoursesByAccount(String account) throws EntityNotFoundException;

    List<Map<String, String>> getCourseIdAndCourseName(String account) throws EntityNotFoundException;

    List<Student> findByStudentClass(String studentClass);

    List<String> findDistinctStudentClass();

    void saveAllStudent(List<Map<String, String>> studentDatas);

    List<String> findDistinctStudentClassByCourseId(Long courseId);

    List<String> findStudentAccountByCourseId(Long courseId);

    List<StudentInfo> findByAccounts(List<String> accounts);
}
