package com.ragingscout.portfolio.repository;

import com.ragingscout.portfolio.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {
    Profile findFirstByOrderByIdAsc();
}

