package MongoDB

import (
	"content_service/model"
	"context"
	"go.mongodb.org/mongo-driver/mongo"
	"go.mongodb.org/mongo-driver/mongo/options"
	"time"
)

type PersonStore struct {
	ourClient *mongo.Client
}

func NewPersonStore() (*PersonStore, error){
	personStoreRef := &PersonStore{}

	ctx, _ := context.WithTimeout(context.Background(), 10*time.Second)
	clientOptions := options.Client().ApplyURI("mongodb://localhost:27017")
	personStoreRef.ourClient, _ = mongo.Connect(ctx, clientOptions)

	return personStoreRef, nil
}

func (personStoreRef *PersonStore) CreatePerson(person model.Person) *mongo.InsertOneResult {
	collectionPeople := personStoreRef.ourClient.Database("thepolyglotdeveloper").Collection("people")
	ctx, _ := context.WithTimeout(context.Background(), 5*time.Second)
	result, _ := collectionPeople.InsertOne(ctx, person)
	return result
}

func (personStoreRef *PersonStore) CloseConnection() error{
	return personStoreRef.ourClient.Disconnect(context.Background())
}