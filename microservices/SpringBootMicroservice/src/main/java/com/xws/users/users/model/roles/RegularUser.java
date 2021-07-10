package com.xws.users.users.model.roles;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.xws.users.users.model.PrivacySettings;
import com.xws.users.users.model.Relationship;
import com.xws.users.users.model.UserCategory;

@Entity
@DiscriminatorValue("RegularUser")
public class RegularUser extends UserAccount {
	
	private static final long serialVersionUID = -2124604854672473186L;

	@Column
	private String bio;

	@Column
	private String profileImagePath;

	@Temporal(TemporalType.DATE)
	@Column
	private Date dateOfBirth;

	@Column
	private String gender;

	@OneToOne(fetch = FetchType.LAZY, optional = true)
	private UserCategory userCategory;

	@Column
	private String phoneNumber;

	@Column(name = "linkToWebSite")
	private String linkToWebSite;

	@OneToOne(fetch = FetchType.EAGER, optional = true, cascade = CascadeType.ALL)
	private PrivacySettings privacySettings;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "towards")
	private List<Relationship> inRelationships;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "from")
	private List<Relationship> outRelationships;

	public RegularUser(){}

	public String getBio() {
		return bio;
	}

	public void setBio(String bio) {
		this.bio = bio;
	}

	public String getProfileImagePath() {
		return profileImagePath;
	}

	public void setProfileImagePath(String profileImagePath) {
		this.profileImagePath = profileImagePath;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public UserCategory getUserCategory() {
		return userCategory;
	}

	public void setUserCategory(UserCategory userCategory) {
		this.userCategory = userCategory;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getLinkToWebSite() {
		return linkToWebSite;
	}

	public void setLinkToWebSite(String linkToWebSite) {
		this.linkToWebSite = linkToWebSite;
	}

	public PrivacySettings getPrivacySettings() {
		return privacySettings;
	}

	public void setPrivacySettings(PrivacySettings privacySettings) {
		this.privacySettings = privacySettings;
	}

	public List<Relationship> getInRelationships() {
		return inRelationships;
	}

	public void setInRelationships(List<Relationship> inRelationships) {
		this.inRelationships = inRelationships;
	}

	public List<Relationship> getOutRelationships() {
		return outRelationships;
	}

	public void setOutRelationships(List<Relationship> outRelationships) {
		this.outRelationships = outRelationships;
	}

}
