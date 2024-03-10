package com.virtusa.rbac.repository;

import com.virtusa.rbac.entity.ForgotPasswordEmail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ForgotPasswordEmailRepository extends JpaRepository<ForgotPasswordEmail, String> {
}
