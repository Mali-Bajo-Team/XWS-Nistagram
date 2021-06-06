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


func (postStoreRef *PostStore) CreateManyContent(content []model.Content) *mongo.InsertManyResult {
	var insertableContent []interface{}

	for _, oneContent := range content {
		insertableContent = append(insertableContent, oneContent)
	}

	collectionContent := postStoreRef.ourClient.Database("content-service-db").Collection("content")
	ctx, _ := context.WithTimeout(context.Background(), 5*time.Second)
	result, _ := collectionContent.InsertMany(ctx, insertableContent)
	return result
}

func (postStoreRef *PostStore) CreateOneContent(content model.Content) *mongo.InsertOneResult {
	collectionContent := postStoreRef.ourClient.Database("content-service-db").Collection("content")
	ctx, _ := context.WithTimeout(context.Background(), 5*time.Second)
	result, _ := collectionContent.InsertOne(ctx, content)
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

func (postStoreRef *PostStore) UpdateUserStories(story model.Story) *mongo.UpdateResult {
	collectionUsers := postStoreRef.ourClient.Database("content-service-db").Collection("users")
	// convert id string to ObjectId
	objectId, err := primitive.ObjectIDFromHex(story.MyPost.PostCreatorRef)
	if err != nil{
		log.Println("Invalid id")
	}

	var userStories []model.UserStory
	var userStory model.UserStory
	userStory.StoryID = story.ID
	userStory.FirstContent = story.MyPost.Content[0]
	userStories = append(userStories, userStory)

	update := bson.M{
		"$addToSet": bson.M{
			"stories": bson.M{"$each": userStories},
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

