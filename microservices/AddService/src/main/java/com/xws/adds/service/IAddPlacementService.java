package com.xws.adds.service;

import com.xws.adds.model.AddCampaign;
import com.xws.adds.model.CampaignType;

public interface IAddPlacementService {

	AddCampaign getAdd(String username, CampaignType type);
	
}
