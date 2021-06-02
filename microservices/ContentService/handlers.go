package main

import (
	"github.com/gorilla/mux"
	"mime"
	"net/http"
	"strconv"
)

func (postServerRef *postServer) createPostHandler(responseWriter http.ResponseWriter, request *http.Request) {

	// Enforce a JSON Content-Type.
	contentType := request.Header.Get("Content-Type")
	mediaType, _, err := mime.ParseMediaType(contentType)
	if err != nil {
		http.Error(responseWriter, err.Error(), http.StatusBadRequest)
		return
	}
	if mediaType != "application/json" {
		http.Error(responseWriter, "expect application/json Content-Type", http.StatusUnsupportedMediaType)
		return
	}

	requestPost, err := decodeBody(request.Body)
	if err != nil {
		http.Error(responseWriter, err.Error(), http.StatusBadRequest)
		return
	}

	id := postServerRef.store.CreatePost(requestPost.Title, requestPost.Text, requestPost.Tags)
	renderJSON(responseWriter, ResponseId{Id: id})
}

func (postServerRef *postServer) getAllPostsHandler(responseWriter http.ResponseWriter, request *http.Request) {
	allTasks := postServerRef.store.GetAllPosts()
	renderJSON(responseWriter, allTasks)
}

func (postServerRef *postServer) getPostHandler(responseWriter http.ResponseWriter, request *http.Request) {
	id, _ := strconv.Atoi(mux.Vars(request)["id"])
	task, err := postServerRef.store.GetPost(id)

	if err != nil {
		http.Error(responseWriter, err.Error(), http.StatusNotFound)
		return
	}

	renderJSON(responseWriter, task)
}

func (postServerRef *postServer) deletePostHandler(responseWriter http.ResponseWriter, request *http.Request) {
	id, _ := strconv.Atoi(mux.Vars(request)["id"])
	err := postServerRef.store.DeletePost(id)

	if err != nil {
		http.Error(responseWriter, err.Error(), http.StatusNotFound)
	}
}
