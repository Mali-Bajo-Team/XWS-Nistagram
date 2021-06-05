package MongoDB

import (
	"content_service/model"
	"context"
	"go.mongodb.org/mongo-driver/mongo"
	"go.mongodb.org/mongo-driver/mongo/options"
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

func (postStoreRef *PostStore) CreateRegularPost(regularPost model.RegularPost) *mongo.InsertOneResult{
	collectionRegularPosts := postStoreRef.ourClient.Database("content-service-db").Collection("regular_posts")
	ctx, _ := context.WithTimeout(context.Background(), 10*time.Second)
	var insertManyResult = postStoreRef.CreateContents(regularPost.MyPost.Contents)
	for _, contentId := range insertManyResult.InsertedIDs {
		//log.Println(contentId)
		regularPost.MyPost.ContentsRef = append(regularPost.MyPost.ContentsRef, contentId)
	}
	result, _ := collectionRegularPosts.InsertOne(ctx, regularPost)
	return result
}

func (postStoreRef *PostStore) CreateStory(story model.Story) *mongo.InsertOneResult{
	collectionStories := postStoreRef.ourClient.Database("content-service-db").Collection("stories")
	ctx, _ := context.WithTimeout(context.Background(), 10*time.Second)
	var insertManyResult = postStoreRef.CreateContents(story.MyPost.Contents)
	for _, contentId := range insertManyResult.InsertedIDs {
		//log.Println(contentId)
		story.MyPost.ContentsRef = append(story.MyPost.ContentsRef, contentId)
	}
	result, _ := collectionStories.InsertOne(ctx, story)
	return result
}

func (postStoreRef *PostStore) CreatePost(post model.Post) *mongo.InsertOneResult {
	collectionPosts := postStoreRef.ourClient.Database("content-service-db").Collection("posts")
	ctx, _ := context.WithTimeout(context.Background(), 10*time.Second)
	var insertManyResult = postStoreRef.CreateContents(post.Contents)
	for _, contentId := range insertManyResult.InsertedIDs {
		//log.Println(contentId)
		post.ContentsRef = append(post.ContentsRef, contentId)
	}
	result, _ := collectionPosts.InsertOne(ctx, post)
	return result
}

func (postStoreRef *PostStore) CloseConnection() error{
	return postStoreRef.ourClient.Disconnect(context.Background())
}