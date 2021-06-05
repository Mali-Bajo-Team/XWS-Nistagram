package model

import "go.mongodb.org/mongo-driver/bson/primitive"

type Hashtag struct {
	ID       primitive.ObjectID `json:"_id,omitempty" bson:"_id,omitempty"`
	Name     string             `json:"name,omitempty" bson:"name,omitempty"`
	Posts    []Post             `json:"posts,omitempty" bson:"posts,omitempty"`
	PostsRef []interface{}      `json:"posts_ref,omitempty" bson:"posts_ref,omitempty"`
}
