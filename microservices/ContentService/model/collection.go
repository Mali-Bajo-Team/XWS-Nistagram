package model

import "go.mongodb.org/mongo-driver/bson/primitive"

type Collection struct {
	ID              primitive.ObjectID `json:"_id,omitempty" bson:"_id,omitempty"`
	Name            string             `json:"name,omitempty" bson:"name,omitempty"`

	CoverImage      Content            `json:"cover_image,omitempty" bson:"cover_image,omitempty"`
	CoverImageRef   interface{}        `json:"cover_image_ref,omitempty" bson:"cover_image_ref,omitempty"`
	RegularPosts    []RegularPost      `json:"regular_posts,omitempty" bson:"regular_posts,omitempty"`
	RegularPostsRef []interface{}      `json:"regular_posts_ref,omitempty" bson:"regular_posts_ref,omitempty"`
}
