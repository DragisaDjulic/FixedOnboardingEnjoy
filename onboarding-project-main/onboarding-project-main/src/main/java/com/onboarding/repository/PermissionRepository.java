package com.onboarding.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.onboarding.model.PermissionEntity;

public interface PermissionRepository extends JpaRepository<PermissionEntity, Integer>{

	PermissionEntity findByName(String string);

}
