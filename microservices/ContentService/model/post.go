package model

type Post struct {
	Title       string             `json:"title,omitempty" bson:"title,omitempty"`
	Description string             `json:"description,omitempty" bson:"description,omitempty" `
	IsAdd       bool               `json:"is_add,omitempty" bson:"is_add,omitempty"`
	AddLink     string             `json:"add_link,omitempty" bson:"add_link,omitempty"`
	TimeStamp   string             `json:"time_stamp,omitempty" bson:"time_stamp,omitempty"`

	Contents        []Content     `json:"content,omitempty" bson:"content,omitempty"`
	Tagged          []User        `json:"tagged,omitempty" bson:"tagged,omitempty"`
	TaggedRef       []interface{} `json:"tagged_ref,omitempty" bson:"tagged_ref,omitempty"`
	Tags            []Hashtag     `json:"tags,omitempty" bson:"tags,omitempty"`
	TagsRef         []interface{} `json:"tags_ref,omitempty" bson:"tags_ref,omitempty"`
	PostLocation    Location      `json:"post_location,omitempty" bson:"post_location,omitempty"`
	PostLocationRef string   `json:"post_location_ref,omitempty" bson:"post_location_ref,omitempty"`
	PostCreator     User          `json:"post_creator,omitempty" bson:"post_creator,omitempty"`
	PostCreatorRef  string   `json:"post_creator_ref,omitempty" bson:"post_creator_ref,omitempty"`
}
