package usecase

import (
	"content_service/dataservice"
)

type PostServer struct {
	store  *dataservice.PostStore
}

const name = "post_service"

func NewPostServer() (*PostServer, error) {
	store, err := dataservice.New()
	if err != nil {
		return nil, err
	}

	return &PostServer{
		store:  store,
	}, nil
}

func (postServerRef *PostServer) CloseDB() error {
	return postServerRef.store.Close()
}