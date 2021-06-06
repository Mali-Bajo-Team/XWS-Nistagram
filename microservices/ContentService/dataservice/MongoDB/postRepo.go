package MongoDB

import (
	"content_service/model"
	"context"
	"go.mongodb.org/mongo-driver/mongo"
	"go.mongodb.org/mongo-driver/mongo/options"
	"time"
	"os"
	"log"
)

type PostStore struct {
	ourClient *mongo.Client
}

func NewPostStore() (*PostStore, error){
	postStoreRef := &PostStore{}

	mongoPath, present := os.LookupEnv("MONGO_PATH")
	if !present {
		mongoPath = "mongodb://localhost:27017"
	}

	log.Println(mongoPath)

	ctx, _ := context.WithTimeout(context.Background(), 10*time.Second)
	clientOptions := options.Client().ApplyURI(mongoPath)
	client, err := mongo.Connect(ctx, clientOptions)
	postStoreRef.ourClient = client
	if err != nil {
		log.Println(err)
		return nil, err
	}

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

func (postStoreRef *PostStore) CloseConnection() error{
	return postStoreRef.ourClient.Disconnect(context.Background())
}