package com.penguin.penguincoco.service;

import com.penguin.penguincoco.dao.domain.course.Course;
import com.penguin.penguincoco.dao.domain.course.CourseInfo;
import com.penguin.penguincoco.service.base.BaseService;

import java.util.List;

public interface CourseService extends BaseService<Course, Long> {

    List<CourseInfo> getAllCourseInfo();
}
