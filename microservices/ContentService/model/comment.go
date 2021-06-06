package model

import "go.mongodb.org/mongo-driver/bson/primitive"

type Comment struct {
	ID             primitive.ObjectID `json:"_id,omitempty" bson:"_id,omitempty"`
	CommentCreator User               `json:"comment_creator,omitempty" bson:"comment_creator,omitempty"`
	Content        string             `json:"content,omitempty" bson:"content,omitempty"`
}
