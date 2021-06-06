package MongoDB

import (
	"content_service/model"
	"context"
	"go.mongodb.org/mongo-driver/mongo"
	"time"
)

func (postStoreRef *PostStore) CreateUser(user model.User) *mongo.InsertOneResult{
	collectionUsers := postStoreRef.ourClient.Database("content-service-db").Collection("users")
	ctx, _ := context.WithTimeout(context.Background(), 10*time.Second)
	result, _ := collectionUsers.InsertOne(ctx, user)
	return result
}