package com.penguin.penguincoco.dao.repository;

import com.penguin.penguincoco.dao.domain.course.Course;
import com.penguin.penguincoco.dao.domain.problem.Problem;
import com.penguin.penguincoco.dao.repository.base.BaseRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProblemRepository extends BaseRepository<Problem, Long> {

    Optional<Problem> findByName(String name);

    void deleteByName(String name);

    List<Problem> findByCourse(Course course);

    List<Problem> findByType(String type);

    List<Problem> findByCourseAndType(Course course, String type);

    @Modifying(clearAutomatically = true)
    @Query("update Problem set rate=:rate where id=:problemId")
    int updateRateByProblemId(@Param("problemId") Long problemId,
                              @Param("rate") double rate);

    int countByBestStudentAccountAndCourse(String account, Course course);
}
