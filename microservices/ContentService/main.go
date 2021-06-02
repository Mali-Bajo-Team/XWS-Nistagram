package main

import (
	"content_service/usecase"
	"context"
	"github.com/gorilla/mux"
	"log"
	"net/http"
	"os"
	"os/signal"
	"syscall"
	"time"
)

func main() {
	quit := make(chan os.Signal)
	signal.Notify(quit, os.Interrupt, syscall.SIGTERM)

	router := mux.NewRouter()
	router.StrictSlash(true)
	server, err := usecase.NewContentServer()
	if err != nil {
		log.Fatal(err.Error())
	}

	defer server.CloseDB()

	initializeRouter(router, server)

	// start server
	srv := &http.Server{Addr: "0.0.0.0:8000", Handler: router}
	go func() {
		log.Println("server starting")
		if err := srv.ListenAndServe(); err != nil {
			if err != http.ErrServerClosed {
				log.Fatal(err)
			}
		}
	}()

	<-quit

	log.Println("service shutting down ...")

	// gracefully stop server
	ctx, cancel := context.WithTimeout(context.Background(), 10*time.Second)
	defer cancel()

	if err := srv.Shutdown(ctx); err != nil {
		log.Fatal(err)
	}

	log.Println("server stopped")
}

func initializeRouter(router *mux.Router, server *usecase.ContentServer) {
	router.HandleFunc("/post/", server.CreatePostHandler).Methods("POST")
	router.HandleFunc("/post/", server.GetAllPostsHandler).Methods("GET")
	router.HandleFunc("/post/{id:[0-9]+}/", server.GetPostHandler).Methods("GET")
	router.HandleFunc("/post/{id:[0-9]+}/", server.DeletePostHandler).Methods("DELETE")
}

