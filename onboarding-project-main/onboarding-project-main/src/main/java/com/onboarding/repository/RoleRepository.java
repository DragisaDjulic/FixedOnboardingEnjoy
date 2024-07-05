package com.onboarding.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.onboarding.model.UserRoleEntity;

@Repository
public interface RoleRepository extends JpaRepository<UserRoleEntity, Integer>{

}
