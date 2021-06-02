package model

type Tag struct {
	ID        int    `json:"id"`
	Name      string `json:"name"`
	OwnerID   int
	OwnerType string
}
