package main

import (
	"github.com/gorilla/mux"
	"mime"
	"net/http"
	"strconv"
)

func (ts *postServer) createPostHandler(w http.ResponseWriter, req *http.Request) {

	// Enforce a JSON Content-Type.
	contentType := req.Header.Get("Content-Type")
	mediatype, _, err := mime.ParseMediaType(contentType)
	if err != nil {
		http.Error(w, err.Error(), http.StatusBadRequest)
		return
	}
	if mediatype != "application/json" {
		http.Error(w, "expect application/json Content-Type", http.StatusUnsupportedMediaType)
		return
	}

	rt, err := decodeBody(req.Body)
	if err != nil {
		http.Error(w, err.Error(), http.StatusBadRequest)
		return
	}

	id := ts.store.CreatePost(rt.Title, rt.Text, rt.Tags)
	renderJSON(w, ResponseId{Id: id})
}

func (ts *postServer) getAllPostsHandler(w http.ResponseWriter, req *http.Request) {
	allTasks := ts.store.GetAllPosts()
	renderJSON(w, allTasks)
}

func (ts *postServer) getPostHandler(w http.ResponseWriter, req *http.Request) {
	id, _ := strconv.Atoi(mux.Vars(req)["id"])
	task, err := ts.store.GetPost(id)

	if err != nil {
		http.Error(w, err.Error(), http.StatusNotFound)
		return
	}

	renderJSON(w, task)
}

func (ts *postServer) deletePostHandler(w http.ResponseWriter, req *http.Request) {
	id, _ := strconv.Atoi(mux.Vars(req)["id"])
	err := ts.store.DeletePost(id)

	if err != nil {
		http.Error(w, err.Error(), http.StatusNotFound)
	}
}
