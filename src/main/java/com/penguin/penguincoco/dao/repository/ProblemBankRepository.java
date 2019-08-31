package com.penguin.penguincoco.dao.repository;

import com.penguin.penguincoco.dao.domain.problembank.ProblemBank;
import com.penguin.penguincoco.dao.repository.base.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProblemBankRepository extends BaseRepository<ProblemBank, Long> {

}
