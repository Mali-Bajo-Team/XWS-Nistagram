package com.xws.users.dto;

import com.xws.users.users.model.VerificationRequest;

public class VerificationRequestDTO {

    private Long id;
    private String realName;
    private String realSurname;
    private String category;
    private String imageOfOfficialDocument;
    private String requesterUsername;

    public  VerificationRequestDTO(){

    }

    public VerificationRequestDTO(VerificationRequest verificationRequest) {
        super();
        this.id = verificationRequest.getId();
        this.realName = verificationRequest.getRealName();
        this.realSurname = verificationRequest.getRealSurname();
        this.category = verificationRequest.getCategory().getName();
        this.imageOfOfficialDocument = verificationRequest.getImageOfOfficialDocument();
        this.requesterUsername = verificationRequest.getRequester().getUsername();
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImageOfOfficialDocument() {
        return imageOfOfficialDocument;
    }

    public void setImageOfOfficialDocument(String imageOfOfficialDocument) {
        this.imageOfOfficialDocument = imageOfOfficialDocument;
    }

    public String getRequesterUsername() {
        return requesterUsername;
    }

    public void setRequesterUsername(String requesterUsername) {
        this.requesterUsername = requesterUsername;
    }
}
