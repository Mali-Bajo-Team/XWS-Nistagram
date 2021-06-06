package MongoDB

import (
	"content_service/model"
	"context"
	"go.mongodb.org/mongo-driver/mongo"
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
