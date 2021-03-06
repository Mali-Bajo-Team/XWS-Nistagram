package model

import (
	"go.mongodb.org/mongo-driver/bson/primitive"
)

type UserUpdate struct {
	ID                 primitive.ObjectID `json:"_id,omitempty" bson:"_id,omitempty"`
	Username           string             `json:"username,omitempty" bson:"username,omitempty"`
	NewUsername        string             `json:"new_username,omitempty" bson:"new_username,omitempty"`
	ProfileImage       string             `json:"profile_image,omitempty" bson:"profile_image,omitempty"`
	IsPrivate          bool               `json:"is_private,omitempty" bson:"is_private,omitempty"`
	Posts              []UserPost         `json:"posts,omitempty" bson:"posts,omitempty"`
	Stories            []UserStory        `json:"stories,omitempty" bson:"stories,omitempty"`
	Collections        []Collection       `json:"collections,omitempty" bson:"collections,omitempty"`
	CollectionsRef     []interface{}      `json:"collections_ref,omitempty" bson:"collections_ref,omitempty"`
	Favorites          []RegularPost      `json:"favorites,omitempty" bson:"favorites,omitempty"`
	FavoritesRef       []interface{}      `json:"favorites_ref,omitempty" bson:"favorites_ref,omitempty"`
	StoryHighlights    []StoryHighlight   `json:"story_highlights,omitempty" bson:"story_highlights,omitempty"`
	StoryHighlightsRef []interface{}      `json:"story_highlights_ref,omitempty" bson:"story_highlights_ref,omitempty"`
}
