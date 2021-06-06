package model

import (
	"go.mongodb.org/mongo-driver/bson/primitive"
)

type UserStory struct {
	StoryID      primitive.ObjectID `json:"story_id,omitempty" bson:"story_id,omitempty"`
	FirstContent Content      `json:"first_content,omitempty" bson:"first_content,omitempty"`
}
