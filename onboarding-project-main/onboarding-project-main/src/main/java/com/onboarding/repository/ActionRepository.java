package com.onboarding.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.onboarding.model.ActionEntity;

public interface ActionRepository extends JpaRepository<ActionEntity, Integer>{
	
	public List<ActionEntity> findByTypeIn(List<String> types);

}
