package model

import "go.mongodb.org/mongo-driver/bson/primitive"

type Post struct {
	ID        	primitive.ObjectID 	`json:"_id,omitempty" bson:"_id,omitempty"`
	Title 		string				`json:"title,omitempty" bson:"title,omitempty"`
	Description string 				`json:"description,omitempty" bson:"description,omitempty" `
	IsAdd 		bool				`json:"is_add,omitempty" bson:"is_add,omitempty"`
	AddLink		string				`json:"add_link,omitempty" bson:"add_link,omitempty"`
	TimeStamp 	string				`json:"time_stamp,omitempty" bson:"time_stamp,omitempty"`
}