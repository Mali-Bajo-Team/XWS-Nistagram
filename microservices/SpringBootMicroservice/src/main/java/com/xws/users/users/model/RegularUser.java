package com.xws.users.users.model;

import javax.persistence.Column;

public class RegularUser extends UserAccount{
    @Column(name = "bio")
    String bio;
}
