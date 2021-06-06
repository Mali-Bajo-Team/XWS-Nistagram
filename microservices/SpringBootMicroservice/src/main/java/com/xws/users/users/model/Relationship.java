package com.xws.users.users.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.xws.users.users.model.enums.RelationshipType;
import com.xws.users.users.model.roles.RegularUser;

@Entity
public class Relationship {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	private RelationshipType relationshipType;

	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	private RegularUser from;

	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	private RegularUser towards;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public RelationshipType getRelationshipType() {
		return relationshipType;
	}

	public void setRelationshipType(RelationshipType relationshipType) {
		this.relationshipType = relationshipType;
	}

	public RegularUser getFrom() {
		return from;
	}

	public void setFrom(RegularUser from) {
		this.from = from;
	}

	public RegularUser getTowards() {
		return towards;
	}

	public void setTowards(RegularUser towards) {
		this.towards = towards;
	}

}
