package model

import "go.mongodb.org/mongo-driver/bson/primitive"

type InappropriatePost struct {
	ID                   primitive.ObjectID `json:"_id,omitempty" bson:"_id,omitempty"`
	PostID               string             `json:"post_id,omitempty" bson:"post_id,omitempty"`
	PostCreatorUsername  string             `json:"post_creator_username,omitempty" bson:"post_creator_username,omitempty"`
	PostReporterUsername string             `json:"post_reporter_username,omitempty" bson:"post_reporter_username,omitempty"`
	// TODO: Find better way to save a date attribute
	TimeStamp      string             `json:"time_stamp,omitempty" bson:"time_stamp,omitempty"`
	Message        string             `json:"message,omitempty" bson:"message,omitempty"`
}
