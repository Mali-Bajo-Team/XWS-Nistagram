package usecase

import (
	"content_service/dataservice/MongoDB"
)

type ContentServer struct {
	postStore *MongoDB.PostStore
}

func NewContentServer() (*ContentServer, error) {
	postStore, err := MongoDB.NewPostStore()
	if err != nil {
		return nil, err
	}

	return &ContentServer{
		postStore: postStore,
	}, nil
}


func (contentServerRef *ContentServer) CloseConnectionToMongoDB() error {
	return contentServerRef.postStore.CloseConnection()
}
