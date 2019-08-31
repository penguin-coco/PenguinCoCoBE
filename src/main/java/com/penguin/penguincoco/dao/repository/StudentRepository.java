package com.penguin.penguincoco.dao.repository;

import com.penguin.penguincoco.dao.domain.student.Student;
import com.penguin.penguincoco.dao.domain.student.StudentInfo;
import com.penguin.penguincoco.dao.repository.base.BaseRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends BaseRepository<Student, Long> {

    boolean existsByAccount(String account);

    Optional<Student> findByAccount(String account);

    Optional<Student> findByName(String name);

    @Modifying(clearAutomatically = true)
    @Query("update Student set password=:newPassword where account=:account AND password=:oriPassword")
    int updatePasswordByAccountAndPassword(@Param("account") String account,
                                           @Param("oriPassword") String oriPassword,
                                           @Param("newPassword") String newPassword);


    List<Student> findByStudentClass(String studentClass);

    @Query("select distinct s.studentClass from Student s")
    List<String> findDistinctStudentClass();

    @Query("select distinct s.studentClass from Student s inner join s.courses as c where c.id=:courseId")
    List<String> findDistinctStudentClassByCourseId(@Param("courseId") Long courseId);

    @Query("select s.account from Student s inner join s.courses as c where c.id=:courseId")
    List<String> findAccountByCourseId(@Param("courseId") Long courseId);

    List<StudentInfo> findByAccountIn(List<String> accounts);
}
