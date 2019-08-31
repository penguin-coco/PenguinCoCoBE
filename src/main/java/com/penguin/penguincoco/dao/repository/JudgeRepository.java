package com.penguin.penguincoco.dao.repository;

import com.penguin.penguincoco.dao.domain.course.Course;
import com.penguin.penguincoco.dao.domain.judge.Judge;
import com.penguin.penguincoco.dao.domain.problem.Problem;
import com.penguin.penguincoco.dao.domain.student.Student;
import com.penguin.penguincoco.dao.repository.base.BaseRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JudgeRepository extends BaseRepository<Judge, Long> {

    int countByProblemAndStudent(Problem problem, Student student);

    Optional<Judge> findByProblemAndStudent(Problem problem, Student student);

    boolean existsByProblemAndStudent(Problem problem, Student student);

    List<Judge> findByProblem(Problem problem);

    @Modifying(clearAutomatically = true)
    @Query("update Judge set rate=:rate where problem=:problem AND student=:student")
    int updateRateByProblemAndStudent(@Param("rate") double rate,
                                      @Param("problem") Problem problem,
                                      @Param("student") Student student);

    @Query("select avg(j.rate) from Judge j where problem=:problem")
    double getAvgRateByProblem(@Param("problem") Problem problem);

    List<Judge> findByStudent(Student student);

    int countByProblem(Problem problem);

    @Query("select j from Judge j where j.problem.course=:course and j.student=:student")
    List<Judge> findByCourseAndStudent(@Param("course") Course course,
                                       @Param("student") Student student);
}
