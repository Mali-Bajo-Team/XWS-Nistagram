package MongoDB

import (
	"content_service/model"
	"context"
	"go.mongodb.org/mongo-driver/bson"
	"go.mongodb.org/mongo-driver/bson/primitive"
	"go.mongodb.org/mongo-driver/mongo"
	"go.mongodb.org/mongo-driver/mongo/options"
	"log"
	"time"
)

type PostStore struct {
	ourClient *mongo.Client
}

func NewPostStore() (*PostStore, error){
	postStoreRef := &PostStore{}

	ctx, _ := context.WithTimeout(context.Background(), 10*time.Second)
	clientOptions := options.Client().ApplyURI("mongodb://localhost:27017")
	postStoreRef.ourClient, _ = mongo.Connect(ctx, clientOptions)

	return postStoreRef, nil
}

func (postStoreRef *PostStore) CreatePost(post model.RegularPost) *mongo.InsertOneResult{
	collectionPosts := postStoreRef.ourClient.Database("content-service-db").Collection("posts")
	ctx, _ := context.WithTimeout(context.Background(), 10*time.Second)
	result, _ := collectionPosts.InsertOne(ctx, post)
	return result
}

func (postStoreRef *PostStore) CreateStory(story model.Story) *mongo.InsertOneResult{
	collectionStories := postStoreRef.ourClient.Database("content-service-db").Collection("stories")
	ctx, _ := context.WithTimeout(context.Background(), 10*time.Second)
	result, _ := collectionStories.InsertOne(ctx, story)
	return result
}

func (postStoreRef *PostStore) UpdatePostComments(comment model.Comment, postID string) *mongo.UpdateResult {
	collectionPosts := postStoreRef.ourClient.Database("content-service-db").Collection("posts")
	// convert id string to ObjectId
	objectId, err := primitive.ObjectIDFromHex(postID)
	if err != nil{
		log.Println("Invalid id")
	}

	var postComments []model.Comment
	postComments = append(postComments, comment)

	update := bson.M{
		"$addToSet": bson.M{
			"comments": bson.M{"$each": postComments},
		},
	}

	result, err := collectionPosts.UpdateOne(
		context.Background(),
		bson.M{"_id": objectId},
		update,
	)
	if err != nil {
		log.Fatal(err)
	}

	return result
}

func (postStoreRef *PostStore) GetPostByID(postID string) *mongo.SingleResult {
	collectionPosts := postStoreRef.ourClient.Database("content-service-db").Collection("posts")
	// convert id string to ObjectId
	objectId, err := primitive.ObjectIDFromHex(postID)
	if err != nil{
		log.Println("Invalid id")
	}

	result := collectionPosts.FindOne(
		context.Background(),
		bson.M{"_id": objectId},
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

func (postStoreRef *PostStore) CloseConnection() error{
	return postStoreRef.ourClient.Disconnect(context.Background())
}