package com.xws.users.users.model.roles;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("Administrator")
public class Administrator extends UserAccount {

	private static final long serialVersionUID = -2804878788051218520L;
	
}
