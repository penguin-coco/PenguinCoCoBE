package com.penguin.penguincoco.dao.repository;

import com.penguin.penguincoco.dao.domain.teacher.Teacher;
import com.penguin.penguincoco.dao.repository.base.BaseRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TeacherRepository extends BaseRepository<Teacher, Long> {

    boolean existsByAccount(String account);

    Optional<Teacher> findByAccount(String account);

    Optional<Teacher> findByName(String name);

    @Modifying(clearAutomatically = true)
    @Query("update Teacher set password=:newPassword where account=:account AND password=:oriPassword")
    int updatePasswordByAccountAndPassword(@Param("account") String account,
                                           @Param("oriPassword") String oriPassword,
                                           @Param("newPassword") String newPassword);

}
