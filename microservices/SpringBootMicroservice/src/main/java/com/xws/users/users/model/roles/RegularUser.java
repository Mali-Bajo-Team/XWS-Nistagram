package com.xws.users.users.model.roles;

import com.xws.users.users.model.FollowRequest;
import com.xws.users.users.model.PrivacySettings;
import com.xws.users.users.model.UserCategory;
import com.xws.users.users.model.enums.ProfileStatus;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@DiscriminatorValue("RegularUser")
public class RegularUser extends UserAccount {
    @Column
    private String bio;

    @Column
    private String profileImagePath;

    @Column
    private Date dateOfBirth;

    @Column(nullable = false)
    private String gender;

    @Column(nullable = false)
    private ProfileStatus profileStatus;

    @OneToOne(fetch = FetchType.LAZY, optional = true)
    private UserCategory userCategory;

    @Column
    private String phoneNumber;

    @Column(name = "linkToWebSite")
    private String linkToWebSite;

    @OneToOne(fetch = FetchType.LAZY, optional = true)
    private PrivacySettings privacySettings;

    @OneToMany(fetch = FetchType.LAZY)
    private Set<FollowRequest> requests;

    @OneToMany(fetch = FetchType.LAZY)
    private Set<FollowRequest> accountsToFollow;

    @OneToMany(fetch = FetchType.LAZY)
    private List<RegularUser> inRelationships;

    @OneToMany(fetch = FetchType.LAZY)
    private List<RegularUser> outRelationships;

    public void setUserCategory(UserCategory userCategory) {
        this.userCategory = userCategory;
    }

    public PrivacySettings getPrivacySettings() {
        return privacySettings;
    }

    public void setPrivacySettings(PrivacySettings privacySettings) {
        this.privacySettings = privacySettings;
    }

    public Set<FollowRequest> getRequests() {
        return requests;
    }

    public void setRequests(Set<FollowRequest> requests) {
        this.requests = requests;
    }

    public Set<FollowRequest> getAccountsToFollow() {
        return accountsToFollow;
    }

    public void setAccountsToFollow(Set<FollowRequest> accountsToFollow) {
        this.accountsToFollow = accountsToFollow;
    }

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

    public ProfileStatus getProfileStatus() {
        return profileStatus;
    }

    public void setProfileStatus(ProfileStatus profileStatus) {
        this.profileStatus = profileStatus;
    }

    public UserCategory getUserCategory() {
        return userCategory;
    }

    public void setUserCategory(String UserCategory) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RegularUser)) return false;
        if (!super.equals(o)) return false;
        RegularUser that = (RegularUser) o;
        return Objects.equals(bio, that.bio) && Objects.equals(profileImagePath, that.profileImagePath) && Objects.equals(dateOfBirth, that.dateOfBirth) && Objects.equals(gender, that.gender) && profileStatus == that.profileStatus && Objects.equals(userCategory, that.userCategory) && Objects.equals(phoneNumber, that.phoneNumber) && Objects.equals(linkToWebSite, that.linkToWebSite);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), bio, profileImagePath, dateOfBirth, gender, profileStatus, userCategory, phoneNumber, linkToWebSite);
    }
}
