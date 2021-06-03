package com.xws.users.users.model;

import javax.persistence.*;

@Entity
public class VerificationRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String realName;

    @Column
    private String realSurname;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    private UserCategory category;

    @Column
    private String imageOfOfficialDocument;

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
}
