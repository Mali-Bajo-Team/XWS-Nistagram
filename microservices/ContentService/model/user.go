package model

import "go.mongodb.org/mongo-driver/bson/primitive"

type User struct {
	ID           primitive.ObjectID `json:"id,omitempty" bson:"id,omitempty"`
	Username     string             `json:"username,omitempty" bson:"username,omitempty"`
	ProfileImage string             `json:"profile_image,omitempty" bson:"profile_image,omitempty"`
	IsPrivate    bool               `json:"is_private,omitempty" bson:"is_private,omitempty"`

	//
	///** @pdRoleInfo migr=no name=Collection assc=association12 mult=0..* */
	//public Collection[] collection;
	///** @pdRoleInfo migr=no name=Post assc=association7 mult=0..* */
	//public Post[] posts;
	///** @pdRoleInfo migr=no name=RegularPost assc=association10 mult=0..* */
	//public RegularPost[] favorites;
	///** @pdRoleInfo migr=no name=StoryHighlight assc=association13 mult=0..* */
	//public StoryHighlight[] storyHighlights;
}
