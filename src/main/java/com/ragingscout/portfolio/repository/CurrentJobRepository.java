package com.ragingscout.portfolio.repository;

import com.ragingscout.portfolio.entity.CurrentJob;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrentJobRepository extends JpaRepository<CurrentJob, Long> {
    CurrentJob findFirstByOrderByIdAsc();
}

