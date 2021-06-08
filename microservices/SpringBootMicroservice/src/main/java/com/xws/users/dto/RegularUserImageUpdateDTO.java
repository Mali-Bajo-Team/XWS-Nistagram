package com.xws.users.dto;

public class RegularUserImageUpdateDTO {
    private String profileimagepath;
    private String username;

    public RegularUserImageUpdateDTO() {
    }

    public RegularUserImageUpdateDTO(String profileimagepath, String username) {
        this.profileimagepath = profileimagepath;
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getProfileimagepath() {
        return profileimagepath;
    }

    public void setProfileimagepath(String profileimagepath) {
        this.profileimagepath = profileimagepath;
    }
}
