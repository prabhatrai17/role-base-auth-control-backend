package com.virtusa.rbac.repository;

import com.virtusa.rbac.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface ProfileRepository extends JpaRepository<Profile,Long> {

    public Optional<Profile> findByProfileName(String name);
    Optional<Profile> findByProfileId(Long profileId);
    @Query(value = "select role_id from profile_role_table where profile_id= :profileId", nativeQuery = true)
    Optional<Set<Integer>> findRolesByProfileId(Long profileId);



}
