package com.penguin.penguincoco.service;

import com.penguin.penguincoco.common.exception.EntityNotFoundException;
import com.penguin.penguincoco.dao.domain.problem.Problem;
import com.penguin.penguincoco.dao.domain.team.Team;
import com.penguin.penguincoco.service.base.BaseService;

import java.util.List;
import java.util.Map;

public interface TeamService extends BaseService<Team, Long> {

    public void createTeam(String problemId, List<Map<String, String>> pairs) throws EntityNotFoundException;

    public boolean existByAccount(String account);

    public Team findByAccount(String account) throws EntityNotFoundException;

    public List<Team> findByProblem(Problem problem);

    public Team findByProblemAndAccount(Problem problem, String account) throws EntityNotFoundException;

    public boolean existsByProblemAndAccount(Problem problem, String account);
}
