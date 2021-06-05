package usecase

import (
	"content_service/dataservice"
	"content_service/dataservice/MongoDB"
)

type ContentServer struct {
	sqlPostStore *dataservice.SqlPostStore
	postStore *MongoDB.PostStore
}

const name = "post_service"

func NewContentServer() (*ContentServer, error) {
	sqlPostStore, err := dataservice.NewSqlPostStore()
	if err != nil {
		return nil, err
	}

	postStore, err := MongoDB.NewPostStore()
	if err != nil {
		return nil, err
	}

	return &ContentServer{
		sqlPostStore: sqlPostStore,
		postStore: postStore,
	}, nil
}


func (contentServerRef *ContentServer) CloseConnectionToMongoDB() error {
	return contentServerRef.postStore.CloseConnection()
}

func (contentServerRef *ContentServer) CloseConnectionToPostgreSQL() error {
	return contentServerRef.sqlPostStore.Close()
}

