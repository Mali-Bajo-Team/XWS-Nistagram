package com.xws.adds.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.xws.adds.model.AddConsumer;

public interface IAddConsumerRepository  extends JpaRepository<AddConsumer, Long> {

	AddConsumer findByUsername(String username);
	
}
