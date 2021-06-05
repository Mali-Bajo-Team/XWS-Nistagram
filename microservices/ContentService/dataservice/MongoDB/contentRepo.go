package MongoDB

import (
	"content_service/model"
	"context"
	"go.mongodb.org/mongo-driver/mongo"
	"time"
)


func (postStoreRef *PostStore) CreateContent(contents []model.Content) *mongo.InsertManyResult {
	var insertableContents []interface{}

	for _, content := range contents {
		insertableContents = append(insertableContents, content)
	}

	collectionContents := postStoreRef.ourClient.Database("content-service-db").Collection("content")
	ctx, _ := context.WithTimeout(context.Background(), 5*time.Second)
	result, _ := collectionContents.InsertMany(ctx, insertableContents)
	return result
}