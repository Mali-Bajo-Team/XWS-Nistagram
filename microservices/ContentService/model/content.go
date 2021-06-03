package model

type Content struct {
	Path 	string		`json:"path,omitempty" bson:"path,omitempty"`
	Type	int			`bson:"type" bson:"type"`
}