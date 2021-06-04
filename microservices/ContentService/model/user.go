package model

import "go.mongodb.org/mongo-driver/bson/primitive"

type User struct {
	ID             primitive.ObjectID `json:"id,omitempty" bson:"id,omitempty"`
	Username       string             `json:"username,omitempty" bson:"username,omitempty"`
	ProfileImage   string             `json:"profile_image,omitempty" bson:"profile_image,omitempty"`
	IsPrivate      bool               `json:"is_private,omitempty" bson:"is_private,omitempty"`

	Posts          []Post             `json:"posts,omitempty" bson:"posts,omitempty"`
	PostsRef       []interface{}      `json:"posts_ref,omitempty" bson:"posts_ref,omitempty"`
	Collections    []Collection       `json:"collections,omitempty" bson:"collections,omitempty"`
	CollectionsRef []interface{}      `json:"collections_ref,omitempty" bson:"collections_ref,omitempty"`
	Favorites      []RegularPost      `json:"favorites,omitempty" bson:"favorites,omitempty"`
	FavoritesRef   []interface{}      `json:"favorites_ref,omitempty" bson:"favorites_ref,omitempty"`


	//public StoryHighlight[] storyHighlights;
}
