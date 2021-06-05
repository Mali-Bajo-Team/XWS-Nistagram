package MongoDB

import (
	"content_service/model"
	"context"
	"go.mongodb.org/mongo-driver/mongo"
	"time"
)

func (postStoreRef *PostStore) CreateRegularPost(post model.RegularPost) *mongo.InsertOneResult{
	collectionRegularPosts := postStoreRef.ourClient.Database("content-service-db").Collection("regular_posts")
	ctx, _ := context.WithTimeout(context.Background(), 10*time.Second)
	var insertManyResult = postStoreRef.CreateContents(post.MyPost.Contents)
	for _, contentId := range insertManyResult.InsertedIDs {
		//log.Println(contentId)
		post.MyPost.ContentsRef = append(post.MyPost.ContentsRef, contentId)
	}
	result, _ := collectionRegularPosts.InsertOne(ctx, post)
	return result
}