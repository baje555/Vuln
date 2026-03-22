package com.vulnlab.repository;

import com.vulnlab.entity.Flag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FlagRepository extends JpaRepository<Flag, Long> {
    Optional<Flag> findByLevel(Integer level);
}
