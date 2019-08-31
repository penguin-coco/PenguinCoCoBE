package com.penguin.penguincoco.dao.repository;

import com.penguin.penguincoco.dao.domain.copy.Copy;
import com.penguin.penguincoco.dao.domain.problem.Problem;
import com.penguin.penguincoco.dao.repository.base.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CopyRepository extends BaseRepository<Copy, Long> {

    List<Copy> findByStudentOneAccountOrStudentTwoAccountEquals(String account1, String account2);

    List<Copy> findByProblemAndStudentTwoAccount(Problem problem, String account);

    List<Copy> findByProblem(Problem problem);
}
