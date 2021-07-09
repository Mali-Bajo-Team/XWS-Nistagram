package com.xws.adds.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.xws.adds.model.Influencer;

public interface IInfluencerRepository extends JpaRepository<Influencer, Long> {

	Influencer findByUsername(String username);

}
