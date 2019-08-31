package com.penguin.penguincoco.dao.repository;

import com.penguin.penguincoco.dao.domain.assistant.Assistant;
import com.penguin.penguincoco.dao.domain.assistant.AssistantInfo;
import com.penguin.penguincoco.dao.repository.base.BaseRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AssistantRepository extends BaseRepository<Assistant, Long> {

    boolean existsByAccount(String account);

    Optional<Assistant> findByAccount(String account);

    Optional<Assistant> findByName(String name);

    @Modifying(clearAutomatically = true)
    @Query("update Assistant set password=:newPassword where account=:account AND password=:oriPassword")
    int updatePasswordByAccountAndPassword(@Param("account") String account,
                                           @Param("oriPassword") String oriPassword,
                                           @Param("newPassword") String newPassword);

    @Query("select new com.penguin.penguincoco.dao.domain.assistant.AssistantInfo(a.account, a.name) from Assistant a inner join a.courses as c where c.id=:courseId")
    List<AssistantInfo> findAssistantInfoByCourseId(@Param("courseId") Long courseId);

    @Query("select new com.penguin.penguincoco.dao.domain.assistant.AssistantInfo(a.account, a.name) from Assistant a inner join a.courses as c where not c.id=:courseId")
    List<AssistantInfo> findunassignAssistantInfoByCourseId(@Param("courseId") Long courseId);
}
