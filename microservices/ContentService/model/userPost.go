package model

import (
	"go.mongodb.org/mongo-driver/bson/primitive"
)

type UserPost struct {
	PostID       primitive.ObjectID `json:"post_id,omitempty" bson:"post_id,omitempty"`
	FirstContent Content      `json:"first_content,omitempty" bson:"first_content,omitempty"`
}
