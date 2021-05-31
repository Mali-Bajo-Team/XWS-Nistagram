package com.xws.users.dto;

import com.xws.users.users.model.UserAccount;

public class UserAccDTO {
    private Long id;
    private String email;

    public UserAccDTO() {

    }

    public UserAccDTO(UserAccount user) { this(user.getId(), user.getEmail()); }

    public UserAccDTO(Long id, String email) {
        super();
        this.id = id;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }
}
