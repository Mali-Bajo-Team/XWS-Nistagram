package model

import "go.mongodb.org/mongo-driver/bson/primitive"

type Content struct {
	ID        primitive.ObjectID `json:"_id,omitempty" bson:"_id,omitempty"`
	Path      string             `json:"path,omitempty" bson:"path,omitempty"`
	Type      string             `json:"type,omitempty" bson:"type,omitempty"`
}
