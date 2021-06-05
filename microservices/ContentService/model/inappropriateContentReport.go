package model

import "go.mongodb.org/mongo-driver/bson/primitive"

type InappropriateContentReport struct {
	ID              primitive.ObjectID `json:"_id,omitempty" bson:"_id,omitempty"`
	Timestamp       string             `json:"timestamp,omitempty" bson:"timestamp,omitempty"`
	Message         string             `json:"message,omitempty" bson:"message,omitempty"`

	Reporter        User               `json:"reporter,omitempty" bson:"reporter,omitempty"`
	ReporterRef     interface{}        `json:"reporter_ref,omitempty" bson:"reporter_ref,omitempty"`
	ReportedPost    Post               `json:"reported_post,omitempty" bson:"reported_post,omitempty"`
	ReportedPostRef Post               `json:"reported_post_ref,omitempty" bson:"reported_post_ref,omitempty"`
}
