package com.penguin.penguincoco.repository;

import com.penguin.penguincoco.model.copy.Copy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CopyRepository extends JpaRepository<Copy, Long> {
}
