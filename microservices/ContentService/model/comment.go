package model

type Comment struct {
	Content        string             `json:"content,omitempty" bson:"content,omitempty"`
	CreatorRef     string             `json:"creator_ref,omitempty" bson:"creator_ref,omitempty"`
}
