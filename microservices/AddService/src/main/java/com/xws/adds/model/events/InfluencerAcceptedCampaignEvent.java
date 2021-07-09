package com.xws.adds.model.events;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("Accepted")
public class InfluencerAcceptedCampaignEvent extends AddEvent {

}
