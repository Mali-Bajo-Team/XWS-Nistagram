package usecase

import (
	"content_service/dataservice"
)

type ContentServer struct {
	sqlPostStore *dataservice.SqlPostStore
	postStore *dataservice.PostStore
	personStore  *dataservice.PersonStore
}

const name = "post_service"

func NewContentServer() (*ContentServer, error) {
	sqlPostStore, err := dataservice.NewSqlPostStore()
	if err != nil {
		return nil, err
	}

	personStore, err := dataservice.NewPersonStore()
	if err != nil {
		return nil, err
	}


	postStore, err := dataservice.NewPostStore()
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

