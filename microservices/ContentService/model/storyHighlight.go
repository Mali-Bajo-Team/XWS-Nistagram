package model

import "go.mongodb.org/mongo-driver/bson/primitive"

type StoryHighlight struct {
	ID         primitive.ObjectID      `json:"id,omitempty" bson:"id,omitempty"`
	Name       string                  `json:"name,omitempty" bson:"name,omitempty"`
	CoverImage Content                 `json:"cover_image,omitempty" bson:"cover_image,omitempty"`
	Content    []StoryHighlightContent `json:"content,omitempty" bson:"content,omitempty"`
}
