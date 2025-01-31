package com.nyu.microservices.CitizenService.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nyu.microservices.CitizenService.Entity.Citizen;

public interface CitizenRepo extends JpaRepository<Citizen, Integer>{
	
	public List<Citizen> findByVaccinationCenterId(Integer id);

}
