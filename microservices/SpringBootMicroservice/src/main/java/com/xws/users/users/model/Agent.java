package com.xws.users.users.model;

import javax.persistence.*;
import java.util.Set;

@Entity
@DiscriminatorValue("Agent")
public class Agent extends UserAccount{

    @OneToOne(fetch = FetchType.LAZY, optional = true)
    private RegistrationRequest registrationRequest;
}
