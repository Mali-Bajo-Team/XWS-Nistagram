package com.xws.adds.service;

import java.util.List;

import com.xws.adds.model.CollaborationRequest;

public interface ICollaborationService {
	
	List<String> getCollaboratingInfluencers(String agentUsername);	
	List<String> getAvailableInfluencers(String agentUsername);
	void requestCollaboration(String agentUsername, String influencerUsername);
	List<CollaborationRequest> getCollaborationRequests(String influencerUsername);
	void acceptCollaborationRequest(String influencerUsername, Long requestId);
	void rejectCollaborationRequest(String influencerUsername, Long requestId);

}
