package model

import "go.mongodb.org/mongo-driver/bson/primitive"

type InappropriateStory struct {
	ID              primitive.ObjectID `json:"_id,omitempty" bson:"_id,omitempty"`
	StoryID         string             `json:"story_id,omitempty" bson:"story_id,omitempty"`
	StoryCreatorID  string             `json:"story_creator_id,omitempty" bson:"story_creator_id,omitempty"`
	StoryReporterID string             `json:"story_reporter_id,omitempty" bson:"story_reporter_id,omitempty"`
	// TODO: Find better way to save a date attribute
	TimeStamp       string             `json:"time_stamp,omitempty" bson:"time_stamp,omitempty"`
	Message         string             `json:"message,omitempty" bson:"message,omitempty"`
}
