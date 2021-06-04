package dataservice

import (
	"content_service/model"
	"context"
	"go.mongodb.org/mongo-driver/mongo"
	"time"
)

func (postStoreRef *PostStore) CreateContents(content []model.Content) *mongo.InsertManyResult {
	var contents []interface{}

	for _, content := range content {
		contents = append(contents, content)
	}

	collectionContents := postStoreRef.ourClient.Database("content-service-db").Collection("contents")
	ctx, _ := context.WithTimeout(context.Background(), 5*time.Second)
	result, _ := collectionContents.InsertMany(ctx, contents)
	return result
}