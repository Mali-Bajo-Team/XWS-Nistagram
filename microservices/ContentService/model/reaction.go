package model

import "time"

type Reaction struct {
	CreatorUsername string    `json:"creator_username,omitempty" bson:"creator_username,omitempty"`
	ReactionType    string    `json:"reaction_type,omitempty" bson:"reaction_type,omitempty"`
	CreationTime    time.Time `json:"creation_time,omitempty" bson:"creation_time,omitempty"`
	PostID          string    `json:"post_id,omitempty" bson:"post_id,omitempty"`
}
