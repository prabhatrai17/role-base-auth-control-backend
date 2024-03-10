package com.virtusa.rbac.repository;


import com.virtusa.rbac.entity.ApplicationDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ApplicationDetailsRepository extends JpaRepository<ApplicationDetails,Integer> {
    public Optional<ApplicationDetails> findByApplicationName(String name);
}
