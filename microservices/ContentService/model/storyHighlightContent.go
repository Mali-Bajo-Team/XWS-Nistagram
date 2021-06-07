package model

type StoryHighlightContent struct {
	StoryID        string    `json:"story_id,omitempty" bson:"story_id,omitempty"`
	IsPrivateStory bool      `json:"is_private_story,omitempty" bson:"is_private_story,omitempty"`
	Content        []Content `json:"content,omitempty" bson:"content,omitempty"`
}
