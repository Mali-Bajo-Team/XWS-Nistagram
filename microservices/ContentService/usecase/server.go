package usecase

import (
	"content_service/dataservice"
)

type ContentServer struct {
	postStore *dataservice.PostStore
}

const name = "post_service"

func NewPostServer() (*ContentServer, error) {
	store, err := dataservice.NewPostStore()
	if err != nil {
		return nil, err
	}

	return &ContentServer{
		postStore: store,
	}, nil
}

func (contentServerRef *ContentServer) CloseDB() error {
	return contentServerRef.postStore.Close()
}