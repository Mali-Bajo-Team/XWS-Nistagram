package model

import "go.mongodb.org/mongo-driver/bson/primitive"

type Location struct {
	ID        primitive.ObjectID `json:"_id,omitempty" bson:"_id,omitempty"`
	Country   string             `json:"country,omitempty" bson:"country,omitempty"`
	City      string             `json:"city,omitempty" bson:"city,omitempty"`
	Street    string             `json:"street,omitempty" bson:"street,omitempty"`
	Number    string             `json:"number,omitempty" bson:"number,omitempty"`
	Latitude  float64            `json:"latitude,omitempty" bson:"latitude,omitempty"`
	Longitude float64            `json:"longitude,omitempty" bson:"longitude,omitempty"`

	Posts     []Post             `json:"posts,omitempty" bson:"posts,omitempty"`
	PostsRef  []interface{}      `json:"posts_ref,omitempty" bson:"posts_ref,omitempty"`
}
