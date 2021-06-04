package model

import "go.mongodb.org/mongo-driver/bson/primitive"

type Post struct {
	ID        	primitive.ObjectID 	`json:"_id,omitempty" bson:"_id,omitempty"`
	Title 		string				`json:"title,omitempty" bson:"title,omitempty"`
	Description string 				`json:"description,omitempty" bson:"description,omitempty" `
	IsAdd 		bool				`json:"is_add,omitempty" bson:"is_add,omitempty"`
	AddLink		string				`json:"add_link,omitempty" bson:"add_link,omitempty"`
	TimeStamp 	string				`json:"time_stamp,omitempty" bson:"time_stamp,omitempty"`
	Contents 	[]Content			`json:"contents,omitempty" bson:"contents,omitempty"`


	///** @pdRoleInfo migr=no name=Content assc=association15 mult=1..* */
	//public Content[] content;
	///** @pdRoleInfo migr=no name=User assc=association17 mult=0..* */
	//public User[] tagged;
	///** @pdRoleInfo migr=no name=Hashtag assc=association4 mult=0..* */
	//public Hashtag[] tags;
	///** @pdRoleInfo migr=no name=Location assc=association5 mult=0..1 */
	//public Location location;
	///** @pdRoleInfo migr=no name=User assc=association7 mult=1..1 side=A */
	//public User postCreator;
}