package com.penguin.penguincoco.service.impl;

import com.penguin.penguincoco.common.exception.EntityNotFoundException;
import com.penguin.penguincoco.dao.domain.problem.Problem;
import com.penguin.penguincoco.dao.domain.team.Team;
import com.penguin.penguincoco.dao.repository.ProblemRepository;
import com.penguin.penguincoco.dao.repository.TeamRepository;
import com.penguin.penguincoco.dao.repository.base.BaseRepository;
import com.penguin.penguincoco.service.TeamService;
import com.penguin.penguincoco.service.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class TeamServiceImpl extends BaseServiceImpl<Team, Long> implements TeamService {

    private TeamRepository teamRepository;

    @Autowired
    private ProblemRepository problemRepository;

    @Autowired
    public TeamServiceImpl(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    @Override
    public BaseRepository<Team, Long> getBaseRepository() {
        return teamRepository;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void createTeam(String problemId, List<Map<String, String>> pairs) throws EntityNotFoundException {
        List<Team> teams = new ArrayList<>();
        Problem problem = problemRepository.findById(Long.parseLong(problemId)).orElseThrow(EntityNotFoundException::new);
        Set<String> correctAccounts = new HashSet<>();
        for (Map<String, String> pair : pairs) {
            String correctAccount = pair.get("correctAccount");
            if (!correctAccounts.contains(correctAccount)) {
                Team team = new Team(problem, correctAccount, new ArrayList<>(), new ArrayList<>(), null);
                teams.add(team);
                correctAccounts.add(correctAccount);
            }
        }
        pairs.forEach(pair -> {
            String correctAccount = pair.get("correctAccount");
            String correctedAccount = pair.get("correctedAccount");
            teams.forEach(team -> {
                if (correctAccount.equals(team.getAccount())) {
                    List<String> correctedAccountList = team.getCorrectedAccount();
                    correctedAccountList.add(correctedAccount);
                    team.setCorrectedAccount(correctedAccountList);
                }
            });
        });
        saveAll(teams);
    }


    @Override
    public boolean existByAccount(String account) {
        return teamRepository.existsByAccount(account);
    }

    @Override
    public Team findByAccount(String account) throws EntityNotFoundException {
        return teamRepository.findByAccount(account).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public List<Team> findByProblem(Problem problem) {
        return teamRepository.findByProblem(problem);
    }

    @Override
    public Team findByProblemAndAccount(Problem problem, String account) throws EntityNotFoundException {
        return teamRepository.findByProblemAndAccount(problem, account).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public boolean existsByProblemAndAccount(Problem problem, String account) {
        return teamRepository.existsByProblemAndAccount(problem, account);
    }

}
