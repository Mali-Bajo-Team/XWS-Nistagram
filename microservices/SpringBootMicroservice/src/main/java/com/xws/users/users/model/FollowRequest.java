package com.xws.users.users.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;

import com.xws.users.users.model.roles.RegularUser;

@Entity
public class FollowRequest {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column
	private Date dateOfCreation;

	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	private RegularUser requster;

	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	private RegularUser accountToFollow;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDateOfCreation() {
		return dateOfCreation;
	}

	public void setDateOfCreation(Date dateOfCreation) {
		this.dateOfCreation = dateOfCreation;
	}

	public RegularUser getRequster() {
		return requster;
	}

	public void setRequster(RegularUser requster) {
		this.requster = requster;
	}

	public RegularUser getAccountToFollow() {
		return accountToFollow;
	}

	public void setAccountToFollow(RegularUser accountToFollow) {
		this.accountToFollow = accountToFollow;
	}

}
