package usecase

import (
	"content_service/model"
	"encoding/json"
	"github.com/gorilla/mux"
	"go.mongodb.org/mongo-driver/bson/primitive"
	"net/http"
)

func InitializeUserRouter(router *mux.Router, server *ContentServer) {
	router.HandleFunc("/user/", server.CreateUserEndpoint).Methods("POST")
	router.HandleFunc("/user/update/", server.UpdateUserEndpoint).Methods("POST")
	router.HandleFunc("/user/id/{id}", server.GetUserByIDEndpoint).Methods("GET")
	router.HandleFunc("/user/username/{username}", server.GetUserByUsernameEndpoint).Methods("GET")

	router.HandleFunc("/user/post/{id}", server.GetUserPostsByIDEndpoint).Methods("GET")
	router.HandleFunc("/user/story/{id}", server.GetUserStoriesByIDEndpoint).Methods("GET")

	// TODO: Find some better naming
	router.HandleFunc("/user/highlights/{userid}", server.CreateUserHighlightEndpoint).Methods("POST")
	router.HandleFunc("/user/highlights/{userid}", server.GetUserHighlightsEndpoint).Methods("GET")
	router.HandleFunc("/user/highlight/{highlightid}/{userid}", server.UpdateUserHighlightEndpoint).Methods("POST")

	router.HandleFunc("/user/collections/{userid}", server.CreateUserCollectionsEndpoint).Methods("POST")
	router.HandleFunc("/user/collections/{userid}", server.GetUserCollectionsEndpoint).Methods("GET")
	router.HandleFunc("/user/collection/{collectionid}/{userid}", server.UpdateUserCollectionEndpoint).Methods("POST")

	router.HandleFunc("/user/saved/{userid}", server.AddPostToUserSavedEndpoint).Methods("POST")
	router.HandleFunc("/user/saved/{userid}", server.GetUserSavedPostsEndpoint).Methods("GET")
}

func (contentServerRef *ContentServer) CreateUserEndpoint(response http.ResponseWriter, request *http.Request) {
	response.Header().Set("content-type", "application/json")

	var user model.User
	_ = json.NewDecoder(request.Body).Decode(&user)
	var documentId = contentServerRef.postStore.CreateUser(user)
	user.ID = documentId.InsertedID.(primitive.ObjectID)
	renderJSON(response, user)
}

func (contentServerRef *ContentServer) UpdateUserEndpoint(response http.ResponseWriter, request *http.Request) {
	response.Header().Set("content-type", "application/json")

	var userupdate model.UserUpdate

	_ = json.NewDecoder(request.Body).Decode(&userupdate)
	contentServerRef.postStore.UpdateUser(userupdate)

	renderJSON(response, userupdate)
}

func (contentServerRef *ContentServer) GetUserByIDEndpoint(response http.ResponseWriter, request *http.Request) {
	response.Header().Set("content-type", "application/json")
	params := mux.Vars(request)
	objectID, _ := primitive.ObjectIDFromHex(params["id"])
	var result = contentServerRef.postStore.GetUserByID(objectID)
	var user model.User
	err := result.Decode(&user)
	if err != nil {
		return
	}
	renderJSON(response, user)
}

func (contentServerRef *ContentServer) GetUserByUsernameEndpoint(response http.ResponseWriter, request *http.Request) {
	response.Header().Set("content-type", "application/json")
	params := mux.Vars(request)
	var result = contentServerRef.postStore.GetUserByUsername(params["username"])
	var user model.User
	err := result.Decode(&user)
	if err != nil {
		return
	}
	renderJSON(response, user)
}

func (contentServerRef *ContentServer) CreateUserHighlightEndpoint(response http.ResponseWriter, request *http.Request) {
	response.Header().Set("content-type", "application/json")
	var storyHighlight model.StoryHighlight
	_ = json.NewDecoder(request.Body).Decode(&storyHighlight)
	params := mux.Vars(request)
	contentServerRef.postStore.CreateStoryHighlight(&storyHighlight, params["userid"])
	renderJSON(response, storyHighlight)
}

func (contentServerRef *ContentServer) CreateUserCollectionsEndpoint(response http.ResponseWriter, request *http.Request) {
	response.Header().Set("content-type", "application/json")
	var collection model.Collection
	_ = json.NewDecoder(request.Body).Decode(&collection)
	params := mux.Vars(request)
	contentServerRef.postStore.CreateCollection(&collection, params["userid"])
	renderJSON(response, collection)
}

func (contentServerRef *ContentServer) AddPostToUserSavedEndpoint(response http.ResponseWriter, request *http.Request) {
	response.Header().Set("content-type", "application/json")
	// TODO: Think about change to new object instead of entire regular post
	var regularPost model.RegularPost
	_ = json.NewDecoder(request.Body).Decode(&regularPost)
	params := mux.Vars(request)
	contentServerRef.postStore.AddPostToSaved(regularPost, params["userid"])
	renderJSON(response, regularPost)
}

func (contentServerRef *ContentServer) GetUserHighlightsEndpoint(response http.ResponseWriter, request *http.Request) {
	response.Header().Set("content-type", "application/json")
	params := mux.Vars(request)
	renderJSON(response, contentServerRef.postStore.GetUserStoryHighlights(params["userid"]))
}

func (contentServerRef *ContentServer) GetUserCollectionsEndpoint(response http.ResponseWriter, request *http.Request) {
	response.Header().Set("content-type", "application/json")
	params := mux.Vars(request)
	renderJSON(response, contentServerRef.postStore.GetUserCollections(params["userid"]))
}

func (contentServerRef *ContentServer) GetUserSavedPostsEndpoint(response http.ResponseWriter, request *http.Request) {
	response.Header().Set("content-type", "application/json")
	params := mux.Vars(request)
	renderJSON(response, contentServerRef.postStore.GetSavedPosts(params["userid"]))
}

func (contentServerRef *ContentServer) UpdateUserHighlightEndpoint(response http.ResponseWriter, request *http.Request) {
	response.Header().Set("content-type", "application/json")
	var story model.Story
	_ = json.NewDecoder(request.Body).Decode(&story)
	params := mux.Vars(request)
	contentServerRef.postStore.AddStoryContentOnStoryHighlight(story, params["userid"], params["highlightid"])
	renderJSON(response, story)
}

func (contentServerRef *ContentServer) UpdateUserCollectionEndpoint(response http.ResponseWriter, request *http.Request) {
	response.Header().Set("content-type", "application/json")
	var post model.RegularPost
	_ = json.NewDecoder(request.Body).Decode(&post)
	params := mux.Vars(request)
	contentServerRef.postStore.AddPostToCollection(post, params["userid"], params["collectionid"])
	renderJSON(response, post)
}
