package model

type RequestPost struct {
	Title string   `json:"title"`
	Text  string   `json:"text"`
	Tags  []string `json:"tags"`
}
