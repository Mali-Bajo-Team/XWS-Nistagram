package usecase

import (
	"content_service/dataservice/MongoDB"
	"content_service/tracer"
	"github.com/opentracing/opentracing-go"
	"io"
)

type ContentServer struct {
	postStore *MongoDB.PostStore
	tracer opentracing.Tracer
	closer io.Closer
}

const name = "content_service"

func NewContentServer() (*ContentServer, error) {
	postStore, err := MongoDB.NewPostStore()
	if err != nil {
		return nil, err
	}

	tracer, closer := tracer.Init(name)
	opentracing.SetGlobalTracer(tracer)
	return &ContentServer{
		postStore: postStore,
		tracer: tracer,
		closer: closer,
	}, nil
}


func (contentServerRef *ContentServer) CloseConnectionToMongoDB() error {
	return contentServerRef.postStore.CloseConnection()
}

func (contentServerRef *ContentServer) GetTracer() opentracing.Tracer {
	return contentServerRef.tracer
}

func (contentServerRef *ContentServer) GetCloser() io.Closer {
	return contentServerRef.closer
}

func (contentServerRef *ContentServer) CloseTracer() error {
	return contentServerRef.closer.Close()
}