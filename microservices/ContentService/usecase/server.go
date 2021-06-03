package usecase

import (
	"content_service/dataservice"
	"context"
	"go.mongodb.org/mongo-driver/mongo"
	"go.mongodb.org/mongo-driver/mongo/options"
	"time"
)

var OurClient *mongo.Client

type ContentServer struct {
	postStore *dataservice.PostStore
}

const name = "post_service"

func NewContentServer() (*ContentServer, error) {
	store, err := dataservice.NewPostStore()
	if err != nil {
		return nil, err
	}

	return &ContentServer{
		postStore: store,
	}, nil
}

func (contentServerRef *ContentServer) OpenConnectionToMongoDB() {
	ctx, _ := context.WithTimeout(context.Background(), 10*time.Second)
	clientOptions := options.Client().ApplyURI("mongodb://localhost:27017")
	OurClient, _ = mongo.Connect(ctx, clientOptions)

}

func (contentServerRef *ContentServer) CloseConnectionToMongoDB() error {
	return OurClient.Disconnect(context.Background())
}

func (contentServerRef *ContentServer) CloseConnectionToPostgreSQL() error {
	return contentServerRef.postStore.Close()
}

