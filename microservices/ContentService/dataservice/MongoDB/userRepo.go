package MongoDB

import (
	"content_service/model"
	"context"
	"log"
	"time"

	"go.mongodb.org/mongo-driver/bson"
	"go.mongodb.org/mongo-driver/bson/primitive"
	"go.mongodb.org/mongo-driver/mongo"
)

func (postStoreRef *PostStore) UpdateUser(user model.UserUpdate) *mongo.UpdateResult {
	collectionUsers := postStoreRef.ourClient.Database("content-service-db").Collection("users")
	ctx, _ := context.WithTimeout(context.Background(), 10*time.Second)
	update := bson.M{
		"$set": bson.M{
			"username": user.NewUsername,
		},
	}

	result, _ := collectionUsers.UpdateOne(ctx,
		bson.M{"_id": user.ID},
		update)

	return result
}

func (postStoreRef *PostStore) DeleteUserPost(username string, postId string) *mongo.UpdateResult {
	collectionUsers := postStoreRef.ourClient.Database("content-service-db").Collection("users")
	PostObjectId, _ := primitive.ObjectIDFromHex(postId)
	update := bson.M{
		"$pull": bson.M{
			"posts": bson.M{"post_id": PostObjectId},
		},
	}

	result, err := collectionUsers.UpdateOne(
		context.Background(),
		bson.M{"username": username},
		update,
	)
	if err != nil {
		log.Fatal(err)
	}

	return result
}


func (postStoreRef *PostStore) CreateUser(user model.User) *mongo.InsertOneResult {
	collectionUsers := postStoreRef.ourClient.Database("content-service-db").Collection("users")
	ctx, _ := context.WithTimeout(context.Background(), 10*time.Second)
	result, _ := collectionUsers.InsertOne(ctx, user)

	mod := mongo.IndexModel{
		Keys: bson.M{
			"username": 1,
		}, Options: nil,
	}

	collectionUsers.Indexes().CreateOne(context.Background(), mod)

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

	var userPosts []model.UserPost
	var userPost model.UserPost
	userPost.PostID = post.ID
	userPost.FirstContent = post.MyPost.Content[0]
	userPosts = append(userPosts, userPost)

	update := bson.M{
		"$push": bson.M{
			"posts": bson.M{"$each": userPosts, "$position": 0},
		},
	}

	result, err := collectionUsers.UpdateOne(
		context.Background(),
		bson.M{"username": post.MyPost.CreatorUsername},
		update,
	)
	if err != nil {
		log.Fatal(err)
	}

	return result
}
