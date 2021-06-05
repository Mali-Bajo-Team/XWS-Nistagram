package usecase

import (
	"content_service/dataservice"
	"content_service/dataservice/MongoDB"
)

type ContentServer struct {
	sqlPostStore *dataservice.SqlPostStore
	postStore *MongoDB.PostStore
	personStore  *MongoDB.PersonStore
}

const name = "post_service"

func NewContentServer() (*ContentServer, error) {
	sqlPostStore, err := dataservice.NewSqlPostStore()
	if err != nil {
		return nil, err
	}

	personStore, err := MongoDB.NewPersonStore()
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
		personStore:  personStore,
	}, nil
}


func (contentServerRef *ContentServer) CloseConnectionToMongoDB() error {
	return contentServerRef.personStore.CloseConnection()
}

func (contentServerRef *ContentServer) CloseConnectionToPostgreSQL() error {
	return contentServerRef.sqlPostStore.Close()
}

