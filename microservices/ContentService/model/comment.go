package model

type Comment struct {
	Content  string `json:"content,omitempty" bson:"content,omitempty"`
	Username string `json:"username,omitempty" bson:"username,omitempty"`
}
