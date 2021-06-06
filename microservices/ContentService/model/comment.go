package model

import "go.mongodb.org/mongo-driver/bson/primitive"

type Comment struct {
	ID             primitive.ObjectID `json:"_id,omitempty" bson:"_id,omitempty"`
	CommentCreator User               `json:"comment_creator,omitempty" bson:"-"`
	Content        string             `json:"content,omitempty" bson:"content,omitempty"`
	CreatorRef     string             `json:"creator_ref,omitempty" bson:"creator_ref,omitempty"`
}
