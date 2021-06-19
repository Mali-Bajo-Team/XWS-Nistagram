package model

import (
	"go.mongodb.org/mongo-driver/bson/primitive"
)

type FollowingUser struct {
	ID              primitive.ObjectID `json:"_id,omitempty" bson:"_id,omitempty"`
	Username        string             `json:"username,omitempty" bson:"username,omitempty"`
	ProfileImage    string             `json:"profile_image,omitempty" bson:"profile_image,omitempty"`
}
