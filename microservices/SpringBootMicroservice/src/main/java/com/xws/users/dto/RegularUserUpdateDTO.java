package com.xws.users.dto;

import com.xws.users.users.model.roles.RegularUser;

import java.util.Date;

public class RegularUserUpdateDTO {
    private String name;
    private String surname;
    private String username;
    private String email;
    private String phonenumber;
    private Date birthdaydate;
    private String gender;
    private String website;
    private String bio;
    private String newusername;
    private String photoUrl;
    private boolean isprivate;
    private boolean isallowedmessages;
    private boolean isallowedtags;

    public RegularUserUpdateDTO() {
    }

    public RegularUserUpdateDTO(RegularUser regularUser) {
        this.name = regularUser.getName();
        this.surname = regularUser.getSurname();
        this.username = regularUser.getUsername();
        this.email = regularUser.getEmail();
        this.phonenumber = regularUser.getPhoneNumber();
        this.birthdaydate = regularUser.getDateOfBirth();
        this.gender = regularUser.getGender();
        this.website = regularUser.getLinkToWebSite();
        this.bio = regularUser.getBio();
        this.photoUrl = regularUser.getProfileImagePath();
        this.isprivate = regularUser.getPrivacySettings().isPrivate();
        this.isallowedmessages = regularUser.getPrivacySettings().isAllowMessagesFromNotFollowed();
        this.isallowedtags = regularUser.getPrivacySettings().isAllowTags();
    }

    public String getNewusername() {
        return newusername;
    }

    public void setNewusername(String newusername) {
        this.newusername = newusername;
    }

    public String getBio() { return bio; }

    public void setBio(String bio) { this.bio = bio; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public Date getBirthdaydate() {
        return birthdaydate;
    }

    public void setBirthdaydate(Date birthdaydate) {
        this.birthdaydate = birthdaydate;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getWebsite() {
        return website;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public boolean isIsprivate() {
        return isprivate;
    }

    public void setIsprivate(boolean isprivate) {
        this.isprivate = isprivate;
    }

    public boolean isIsallowedmessages() {
        return isallowedmessages;
    }

    public void setIsallowedmessages(boolean isallowedmessages) {
        this.isallowedmessages = isallowedmessages;
    }

    public boolean isIsallowedtags() {
        return isallowedtags;
    }

    public void setIsallowedtags(boolean isallowedtags) {
        this.isallowedtags = isallowedtags;
    }
}
