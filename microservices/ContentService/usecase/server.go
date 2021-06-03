package usecase

import (
	"content_service/dataservice"
)

type ContentServer struct {
	postStore *dataservice.PostStore
	personStore *dataservice.PersonStore
}

const name = "post_service"

func NewContentServer() (*ContentServer, error) {
	postStore, err := dataservice.NewPostStore()
	if err != nil {
		return nil, err
	}

	personStore, err := dataservice.NewPersonStore()
	if err != nil {
		return nil, err
	}

	return &ContentServer{
		postStore: postStore,
		personStore: personStore,
	}, nil
}


func (contentServerRef *ContentServer) CloseConnectionToMongoDB() error {
	return contentServerRef.personStore.CloseConnection()
}

func (contentServerRef *ContentServer) CloseConnectionToPostgreSQL() error {
	return contentServerRef.postStore.Close()
}

