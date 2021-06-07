package model

type Reaction struct {
	ReactionCreatorRef string             `json:"reaction_creator_ref,omitempty" bson:"reaction_creator_ref,omitempty"`
	ReactionType       string             `json:"reaction_type,omitempty" bson:"reaction_type,omitempty"`
	CreationTime       string             `json:"creation_time,omitempty" bson:"creation_time,omitempty"`
}
