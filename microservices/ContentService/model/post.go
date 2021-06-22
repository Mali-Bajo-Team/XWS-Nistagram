package model

import "time"

type Post struct {
	Title       string    `json:"title,omitempty" bson:"title,omitempty"`
	Description string    `json:"description,omitempty" bson:"description,omitempty" `
	IsAdd       bool      `json:"is_add,omitempty" bson:"is_add,omitempty"`
	AddLink     string    `json:"add_link,omitempty" bson:"add_link,omitempty"`
	TimeStamp   time.Time `json:"timestamp,omitempty" bson:"timestamp,omitempty"`

	Content      []Content     `json:"content,omitempty" bson:"content,omitempty"`
	Tagged       []User        `json:"tagged,omitempty" bson:"tagged,omitempty"`
	TaggedRef    []interface{} `json:"tagged_ref,omitempty" bson:"tagged_ref,omitempty"`
	Hashtags     []string      `json:"hashtags,omitempty" bson:"hashtags,omitempty"`
	PostLocation Location      `json:"post_location,omitempty" bson:"post_location,omitempty"`
	LocationName string        `json:"location_name,omitempty" bson:"location_name,omitempty"`

	CreatorUsername string `json:"creator_username,omitempty" bson:"creator_username,omitempty"`
}
