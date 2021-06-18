package com.xws.users.dto;

public class IsVerifiedUserDTO {
    private boolean IsVerifiedUser;

    public IsVerifiedUserDTO(){}

    public IsVerifiedUserDTO(boolean isVerifiedUser){
        this.IsVerifiedUser = isVerifiedUser;
    }

    public boolean isVerifiedUser() {
        return IsVerifiedUser;
    }

    public void setVerifiedUser(boolean verifiedUser) {
        IsVerifiedUser = verifiedUser;
    }
}
