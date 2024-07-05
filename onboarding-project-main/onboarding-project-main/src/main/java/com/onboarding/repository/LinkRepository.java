package com.onboarding.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.onboarding.model.LinkEntity;

public interface LinkRepository extends JpaRepository<LinkEntity, Long> {

}
