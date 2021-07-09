package com.xws.adds.service;

import java.util.List;

import com.xws.adds.dto.MultipleCampaignDTO;
import com.xws.adds.dto.MultipleCampaignUpdateDTO;
import com.xws.adds.dto.OneTimeCampaignDTO;
import com.xws.adds.model.AddCampaign;

public interface ICampaignService {

	AddCampaign createOneTimeCampaign(OneTimeCampaignDTO campaign, String username);

	AddCampaign createMultipleCampaign(MultipleCampaignDTO campaign, String username);

	List<AddCampaign> getCampaigns(String username);

	void deleteCampaign(Long id, String username);

	AddCampaign updateCampaign(Long id, String username, MultipleCampaignUpdateDTO update);

}
