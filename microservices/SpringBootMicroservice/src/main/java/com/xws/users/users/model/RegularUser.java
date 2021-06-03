package com.xws.users.users.model;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@DiscriminatorValue("RegularUser")
public class RegularUser extends UserAccount{
    @Column
    private String bio;

    @Column
    private String profileImagePath;

    @Column(nullable = false)
    private Date dateOfBirth;

    @Column(nullable = false)
    private String gender;

    @Column(nullable = false)
    private ProfileStatus profileStatus;

    @Column
    private String userCategory;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(name = "linkToWebSite")
    private String linkToWebSite;

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

    public String getUserCategory() {
        return userCategory;
    }

    public void setUserCategory(String userCategory) {
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
