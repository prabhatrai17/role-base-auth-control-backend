package com.virtusa.rbac.repository;

import com.virtusa.rbac.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

//    @Query(value = "select * from ")
//    Optional<List<Role>> getRolesByRoleIds(List<Integer> roleIds);
     Optional<Role> findByRoleId(Integer roleId);
}
