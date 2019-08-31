package com.penguin.penguincoco.dao.repository;

import com.penguin.penguincoco.dao.domain.problem.Problem;
import com.penguin.penguincoco.dao.domain.team.Team;
import com.penguin.penguincoco.dao.repository.base.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeamRepository extends BaseRepository<Team, Long> {

    boolean existsByAccount(String account);
    Optional<Team> findByAccount(String account);
    List<Team> findByProblem(Problem problem);
    Optional<Team> findByProblemAndAccount(Problem problem, String account);
    boolean existsByProblemAndAccount(Problem problem, String account);
}
