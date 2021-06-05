package model

import "go.mongodb.org/mongo-driver/bson/primitive"

type Story struct {
	ID                primitive.ObjectID `json:"id,omitempty" bson:"id,omitempty"`
	MyPost            Post               `json:"my_post,omitempty" bson:"my_post,omitempty"`
	IsForCloseFriends bool               `json:"is_for_close_friends,omitempty" bson:"is_for_close_friends,omitempty"`
}
