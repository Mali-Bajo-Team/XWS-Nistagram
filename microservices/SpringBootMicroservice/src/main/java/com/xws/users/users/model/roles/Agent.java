package com.xws.users.users.model.roles;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("Agent")
public class Agent extends UserAccount {
	
	private static final long serialVersionUID = -1092631021400279490L;

}
