package com.virtusa.rbac.repository;


import com.virtusa.rbac.entity.Profile;
import com.virtusa.rbac.entity.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile,Integer>
{
    UserProfile findByProfile(Profile profile);
}
