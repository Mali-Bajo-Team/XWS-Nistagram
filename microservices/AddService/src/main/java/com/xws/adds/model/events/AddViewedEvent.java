package com.xws.adds.model.events;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("Viewed")
public class AddViewedEvent extends AddEvent {

	@Column
	private String consumerUsername;

	public String getConsumerUsername() {
		return consumerUsername;
	}

	public void setConsumerUsername(String consumerUsername) {
		this.consumerUsername = consumerUsername;
	}

}
