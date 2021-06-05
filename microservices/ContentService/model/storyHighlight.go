package model

import "go.mongodb.org/mongo-driver/bson/primitive"

type StoryHighlight struct {
	ID            primitive.ObjectID `json:"id,omitempty" bson:"id,omitempty"`
	Name          string             `json:"name,omitempty" bson:"name,omitempty"`

	CoverImage    Content            `json:"cover_image,omitempty" bson:"cover_image,omitempty"`
	CoverImageRef interface{}        `json:"cover_image_ref,omitempty" bson:"cover_image_ref,omitempty"`
	Stories       []Story            `json:"stories,omitempty" bson:"stories,omitempty"`
	StoriesRef    []interface{}      `json:"stories_ref,omitempty" bson:"stories_ref,omitempty"`
}
