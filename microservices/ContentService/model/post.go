package model

type Post struct {
	ID    int    `json:"id"`
	Title string `json:"title"`
	Text  string `json:"text"`
	Tags  []Tag  `gorm:"polymorphic:Owner;" json:"tags"`
}

