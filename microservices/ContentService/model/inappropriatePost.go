package model

import "go.mongodb.org/mongo-driver/bson/primitive"

type InappropriatePost struct {
	ID             primitive.ObjectID `json:"_id,omitempty" bson:"_id,omitempty"`
	PostID         primitive.ObjectID `json:"post_id,omitempty" bson:"post_id,omitempty"`
	PostCreatorID  primitive.ObjectID `json:"post_creator_id,omitempty" bson:"post_creator_id,omitempty"`
	PostReporterID primitive.ObjectID `json:"post_reporter_id,omitempty" bson:"post_reporter_id,omitempty"`
	TimeStamp      string             `json:"time_stamp,omitempty" bson:"time_stamp,omitempty"`
	Message        string             `json:"message,omitempty" bson:"message,omitempty"`
}
