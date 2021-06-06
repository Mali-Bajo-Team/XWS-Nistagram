package model

import "go.mongodb.org/mongo-driver/bson/primitive"

type RegularPost struct {
	ID        primitive.ObjectID `json:"_id,omitempty" bson:"_id,omitempty"`
	MyPost    Post               `json:"my_post,omitempty" bson:"my_post,omitempty"`
	Comments  []Comment          `json:"comments,omitempty" bson:"comments,omitempty"`
	Reactions []Reaction         `json:"reactions,omitempty" bson:"reactions,omitempty"`
}
