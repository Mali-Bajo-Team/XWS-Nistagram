package com.xws.users.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.xws.users.users.model.Relationship;
import com.xws.users.users.model.roles.RegularUser;

public interface IRelationshipRepository extends JpaRepository<Relationship, Long> {

	Relationship findByFromAndTowards(RegularUser from, RegularUser towards);
	
}
