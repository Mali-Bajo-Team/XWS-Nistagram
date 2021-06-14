package usecase

import (
	"github.com/gorilla/mux"
)

func InitializeRouter(router *mux.Router, server *ContentServer) {
	InitializeFileRouter(router, server)
	InitializeUserRouter(router, server)
	InitializePostRouter(router, server)
}


