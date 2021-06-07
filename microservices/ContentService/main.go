package main

import (
	"content_service/usecase"
	"context"
	"github.com/gorilla/mux"
	"github.com/rs/cors"

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

	defer server.CloseConnectionToMongoDB()

	usecase.InitializeRouter(router, server)
	handler := configCORS(router)
	//handler := configCORS(router)
	//fs:=http.FileServer(http.Dir("/post-content"))

	// start server
	srv := &http.Server{Addr: "0.0.0.0:8000", Handler: handler}
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

func configCORS(router *mux.Router) http.Handler {
	myCORS := cors.New(cors.Options{
		AllowedOrigins:   []string{"http://localhost:8081"},
		AllowCredentials: true,
	})
	handler := myCORS.Handler(router)
	return handler
}