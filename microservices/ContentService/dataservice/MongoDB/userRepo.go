package MongoDB

import (
	"content_service/model"
	"context"
	"go.mongodb.org/mongo-driver/bson"
	"go.mongodb.org/mongo-driver/bson/primitive"
	"go.mongodb.org/mongo-driver/mongo"
	"log"
	"time"
)

func (postStoreRef *PostStore) CreateUser(user model.User) *mongo.InsertOneResult{
	collectionUsers := postStoreRef.ourClient.Database("content-service-db").Collection("users")
	ctx, _ := context.WithTimeout(context.Background(), 10*time.Second)
	result, _ := collectionUsers.InsertOne(ctx, user)
	return result
}

func (postStoreRef *PostStore) GetUserByID(objectID primitive.ObjectID) *mongo.SingleResult {
	collectionUsers := postStoreRef.ourClient.Database("content-service-db").Collection("users")
	result := collectionUsers.FindOne(
		context.Background(),
		bson.M{"_id": objectID},
	)
	return result
}

func (postStoreRef *PostStore) GetUserByUsername(username string) *mongo.SingleResult {
	collectionUsers := postStoreRef.ourClient.Database("content-service-db").Collection("users")
	result := collectionUsers.FindOne(
		context.Background(),
		bson.M{"username": username},
	)
	return result
}

func (postStoreRef *PostStore) UpdateUserPosts(post model.RegularPost) *mongo.UpdateResult {
	collectionUsers := postStoreRef.ourClient.Database("content-service-db").Collection("users")
	// convert id string to ObjectId
	objectId, err := primitive.ObjectIDFromHex(post.MyPost.PostCreatorRef)
	if err != nil{
		log.Println("Invalid id")
	}

	var userPosts []model.UserPost
	var userPost model.UserPost
	userPost.PostID = post.ID
	userPost.FirstContent = post.MyPost.Content[0]
	userPosts = append(userPosts, userPost)

	update := bson.M{
		"$addToSet": bson.M{
			"posts": bson.M{"$each": userPosts},
		},
	}

	result, err := collectionUsers.UpdateOne(
		context.Background(),
		bson.M{"_id": objectId},
		update,
	)
	if err != nil {
		log.Fatal(err)
	}

	return result
}

