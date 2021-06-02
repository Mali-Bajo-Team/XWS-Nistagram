package main

import (
	"content_service/dataservice"
)

type postServer struct {
	store  *dataservice.PostStore
}

const name = "post_service"

func NewPostServer() (*postServer, error) {
	store, err := dataservice.New()
	if err != nil {
		return nil, err
	}

	return &postServer{
		store:  store,
	}, nil
}

func (postServerRef *postServer) CloseDB() error {
	return postServerRef.store.Close()
}