package main

import (
	ps "content_service/poststore"
)

type postServer struct {
	store  *ps.PostStore
}

const name = "post_service"

func NewPostServer() (*postServer, error) {
	store, err := ps.New()
	if err != nil {
		return nil, err
	}

	return &postServer{
		store:  store,
	}, nil
}

func (s *postServer) CloseDB() error {
	return s.store.Close()
}