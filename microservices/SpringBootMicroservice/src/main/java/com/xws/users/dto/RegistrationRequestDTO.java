package com.xws.users.dto;

public class RegistrationRequestDTO {

    private Long id;
    private String name;
    private String surname;
    private String validEmail;
    private String linkToWebSite;

    public RegistrationRequestDTO() {

    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getValidEmail() {
        return validEmail;
    }

    public String getLinkToWebSite() {
        return linkToWebSite;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setValidEmail(String validEmail) {
        this.validEmail = validEmail;
    }

    public void setLinkToWebSite(String linkToWebSite) {
        this.linkToWebSite = linkToWebSite;
    }
}
