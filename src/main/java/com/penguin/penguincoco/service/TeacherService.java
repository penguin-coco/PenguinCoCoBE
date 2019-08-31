package com.penguin.penguincoco.service;

import com.penguin.penguincoco.common.exception.EntityNotFoundException;
import com.penguin.penguincoco.dao.domain.course.Course;
import com.penguin.penguincoco.dao.domain.teacher.Teacher;
import com.penguin.penguincoco.service.base.BaseService;

import java.util.List;

public interface TeacherService extends BaseService<Teacher, Long> {

    boolean existByAccount(String account);

    Teacher findByAccount(String account) throws EntityNotFoundException;

    int updatePasswordByAccount(String account, String oriPassword, String newPassword);

    List<Course> findCoursesByAccount(String account) throws EntityNotFoundException;

}
