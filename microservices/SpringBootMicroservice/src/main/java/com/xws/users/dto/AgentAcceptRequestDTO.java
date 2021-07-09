package com.xws.users.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

public class AgentAcceptRequestDTO {
    @NotEmpty(message = "Username is required.")
    private String username;

    public AgentAcceptRequestDTO(){

    }

    public AgentAcceptRequestDTO(@NotEmpty(message = "Username is required.") String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
