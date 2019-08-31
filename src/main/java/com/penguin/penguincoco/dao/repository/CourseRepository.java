package com.penguin.penguincoco.dao.repository;

import com.penguin.penguincoco.dao.domain.course.Course;
import com.penguin.penguincoco.dao.domain.course.CourseInfo;
import com.penguin.penguincoco.dao.repository.base.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepository extends BaseRepository<Course, Long> {

    Optional<Course> findByName(String name);

    @Query(value = "select new com.penguin.penguincoco.dao.domain.course.CourseInfo(c.id, c.name, c.semester) from Course c")
    List<CourseInfo> getAllCourseInfo();
}
