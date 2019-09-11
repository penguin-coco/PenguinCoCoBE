package com.penguin.penguincoco.repository;

import com.penguin.penguincoco.model.assistant.Assistant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AssistantRepository extends JpaRepository<Assistant, Long> {

    Optional<Assistant> findByAccount(String account);
}
