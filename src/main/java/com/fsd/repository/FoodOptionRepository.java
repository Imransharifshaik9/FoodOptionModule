package com.fsd.repository;

import com.fsd.entity.FoodOption;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FoodOptionRepository extends JpaRepository<FoodOption, Long> {
	
	
	 List<FoodOption> findByEventId(Long eventId);
}
