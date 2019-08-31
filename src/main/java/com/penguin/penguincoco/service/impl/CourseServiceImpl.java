package com.penguin.penguincoco.service.impl;

import com.penguin.penguincoco.dao.domain.course.Course;
import com.penguin.penguincoco.dao.domain.course.CourseInfo;
import com.penguin.penguincoco.dao.repository.CourseRepository;
import com.penguin.penguincoco.dao.repository.base.BaseRepository;
import com.penguin.penguincoco.service.CourseService;
import com.penguin.penguincoco.service.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseServiceImpl extends BaseServiceImpl<Course, Long> implements CourseService {

    private CourseRepository courseRepository;

    @Autowired
    public CourseServiceImpl(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Override
    public BaseRepository<Course, Long> getBaseRepository() {
        return courseRepository;
    }

    @Override
    public List<CourseInfo> getAllCourseInfo() {
        return courseRepository.getAllCourseInfo();
    }

}
