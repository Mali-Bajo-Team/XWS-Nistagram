package com.xws.users.users.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.xws.users.users.model.enums.RequestStatus;
import com.xws.users.users.model.roles.RegularUser;

@Entity
public class VerificationRequest {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String realName;

	@Column(nullable = false)
	private String realSurname;

	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	private UserCategory category;

	@Column(nullable = false)
	private String imageOfOfficialDocument;

	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	private RegularUser requester;

	@Column
	private RequestStatus requestStatus = RequestStatus.PENDING;

	public RequestStatus getRequestStatus() {
		return requestStatus;
	}

	public void setRequestStatus(RequestStatus requestStatus) {
		this.requestStatus = requestStatus;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getRealSurname() {
		return realSurname;
	}

	public void setRealSurname(String realSurname) {
		this.realSurname = realSurname;
	}

	public UserCategory getCategory() {
		return category;
	}

	public void setCategory(UserCategory category) {
		this.category = category;
	}

	public String getImageOfOfficialDocument() {
		return imageOfOfficialDocument;
	}

	public void setImageOfOfficialDocument(String imageOfOfficialDocument) {
		this.imageOfOfficialDocument = imageOfOfficialDocument;
	}

	public RegularUser getRequester() {
		return requester;
	}

	public void setRequester(RegularUser requester) {
		this.requester = requester;
	}

}
