package com.penguin.penguincoco.service.impl;

import com.penguin.penguincoco.common.exception.EntityNotFoundException;
import com.penguin.penguincoco.dao.domain.course.Course;
import com.penguin.penguincoco.dao.domain.student.Student;
import com.penguin.penguincoco.dao.domain.student.StudentInfo;
import com.penguin.penguincoco.dao.repository.StudentRepository;
import com.penguin.penguincoco.dao.repository.base.BaseRepository;
import com.penguin.penguincoco.service.StudentService;
import com.penguin.penguincoco.service.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StudentServiceImpl extends BaseServiceImpl<Student, Long> implements StudentService {

    private StudentRepository studentRepository;

    @Autowired
    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public BaseRepository<Student, Long> getBaseRepository() {
        return studentRepository;
    }

    @Override
    public boolean existByAccount(String account) {
        return studentRepository.existsByAccount(account);
    }

    @Override
    public Student findByAccount(String account) throws EntityNotFoundException {
        return studentRepository.findByAccount(account).orElseThrow(EntityNotFoundException::new);
    }

    @Transactional
    @Override
    public int updatePasswordByAccount(String account, String oriPassword, String newPassword) {
        return studentRepository.updatePasswordByAccountAndPassword(account, oriPassword, newPassword);
    }

    @Override
    public List<Course> findCoursesByAccount(String account) throws EntityNotFoundException {
        Student student = findByAccount(account);
        return student.getCourses();
    }

    @Override
    public List<Map<String, String>> getCourseIdAndCourseName(String account) throws EntityNotFoundException {
        List<Map<String, String>> list = new ArrayList<>();
        List<Course> courses = findCoursesByAccount(account);
        for (Course course : courses) {
            Map<String, String> map = new HashMap<>();
            map.put("courseId", course.getId().toString());
            map.put("courseName", course.getName());
            list.add(map);
        }
        return list;
    }

    @Override
    public List<Student> findByStudentClass(String studentClass) {
        return studentRepository.findByStudentClass(studentClass);
    }

    @Override
    public List<String> findDistinctStudentClass() {
        return studentRepository.findDistinctStudentClass();
    }

    @Override
    public void saveAllStudent(List<Map<String, String>> studentDatas) {
        List<Student> students = new ArrayList<>();
        for (Map<String, String> studentData : studentDatas) {
            String account = studentData.get("account");
            String password = "0000";
            String name = studentData.get("name");
            String studentClass = studentData.get("studentClass");

            Student student = new Student(account, password, name,
                    studentClass, new ArrayList<>(), new ArrayList<>(),
                    new ArrayList<>());
            students.add(student);
        }
        saveAll(students);
    }

    @Override
    public List<String> findDistinctStudentClassByCourseId(Long courseId) {
        return studentRepository.findDistinctStudentClassByCourseId(courseId);
    }

    @Override
    public List<String> findStudentAccountByCourseId(Long courseId) {
        return studentRepository.findAccountByCourseId(courseId);
    }

    @Override
    public List<StudentInfo> findByAccounts(List<String> accounts) {
        return studentRepository.findByAccountIn(accounts);
    }
}
