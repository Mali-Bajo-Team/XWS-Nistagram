package com.xws.adds.model.events;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("Rejected")
public class InfluencerRejectedCampaignEvent extends AddEvent {

}
