package model

import (
	"go.mongodb.org/mongo-driver/bson/primitive"
)

type User struct {
	ID              primitive.ObjectID `json:"_id,omitempty" bson:"_id,omitempty"`
	Username        string             `json:"username,omitempty" bson:"username,omitempty"`
	ProfileImage    string             `json:"profile_image,omitempty" bson:"profile_image,omitempty"`
	IsPrivate       bool               `json:"is_private,omitempty" bson:"is_private,omitempty"`
	Posts           []UserPost         `json:"posts,omitempty" bson:"posts,omitempty"`
	Stories         []UserStory        `json:"stories,omitempty" bson:"stories,omitempty"`
	Collections     []Collection       `json:"collections,omitempty" bson:"collections,omitempty"`
	Saved           Collection         `json:"saved,omitempty" bson:"saved,omitempty"`
	StoryHighlights []StoryHighlight   `json:"story_highlights,omitempty" bson:"story_highlights,omitempty"`
}
