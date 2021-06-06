package model

import "go.mongodb.org/mongo-driver/bson/primitive"

type Reaction struct {
	ID                 primitive.ObjectID `json:"_id,omitempty" bson:"_id,omitempty"`
	ReactionCreator    User               `json:"reaction_creator,omitempty" bson:"reaction_creator,omitempty"`
	ReactionCreatorRef interface{}        `json:"reaction_creator_ref,omitempty" bson:"reaction_creator_ref,omitempty"`
	ReactionType       string             `json:"reaction_type,omitempty" bson:"reaction_type,omitempty"`
	CreationTime       string             `json:"creation_time,omitempty" bson:"creation_time,omitempty"`
}
