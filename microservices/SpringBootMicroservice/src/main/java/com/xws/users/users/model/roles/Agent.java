package com.xws.users.users.model.roles;

import com.xws.users.users.model.RegistrationRequest;

import javax.persistence.*;

@Entity
@DiscriminatorValue("Agent")
public class Agent extends UserAccount {

    @OneToOne(fetch = FetchType.LAZY, optional = true)
    private RegistrationRequest registrationRequest;
}
