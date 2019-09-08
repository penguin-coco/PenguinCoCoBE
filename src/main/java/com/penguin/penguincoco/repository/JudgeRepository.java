package com.penguin.penguincoco.repository;

import com.penguin.penguincoco.model.judge.Judge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JudgeRepository extends JpaRepository<Judge, Long> {
}
