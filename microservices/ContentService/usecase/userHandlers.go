package usecase

import (
	"content_service/model"
	"content_service/tracer"
	"context"
	"encoding/json"
	"fmt"
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

func (contentServerRef *ContentServer) DeleteUserPostEndpoint(response http.ResponseWriter, request *http.Request) {
	span := tracer.StartSpanFromRequest("DeleteUserPostEndpoint", contentServerRef.tracer, request)
	defer span.Finish()

	span.LogFields(
		tracer.LogString("handler", fmt.Sprintf("handling DeleteUserPostEndpoint at %s\n", request.URL.Path)),
	)

	response.Header().Set("content-type", "application/json")
	params := mux.Vars(request)
	contentServerRef.postStore.DeleteUserPost(params["username"],params["postid"])
	var result = contentServerRef.postStore.GetUserByUsername(params["username"])
	var user model.User
	err := result.Decode(&user)
	if err != nil {
		return
	}
	ctx := tracer.ContextWithSpan(context.Background(), span)
	renderJSONWithContext(ctx, response, user)
}

func (contentServerRef *ContentServer) DeleteInappropriatePostEndpoint(response http.ResponseWriter, request *http.Request) {
	span := tracer.StartSpanFromRequest("DeleteInappropriatePostEndpoint", contentServerRef.tracer, request)
	defer span.Finish()

	span.LogFields(
		tracer.LogString("handler", fmt.Sprintf("handling DeleteInappropriatePostEndpoint at %s\n", request.URL.Path)),
	)
	response.Header().Set("content-type", "application/json")
	params := mux.Vars(request)
	del1,_ :=contentServerRef.postStore.DeleteInappropriatePost(params["inappropriatepostid"],params["postid"])
	ctx := tracer.ContextWithSpan(context.Background(), span)
	renderJSONWithContext(ctx, response, del1)
}

func (contentServerRef *ContentServer) CreateUserEndpoint(response http.ResponseWriter, request *http.Request) {
	span := tracer.StartSpanFromRequest("CreateUserEndpoint", contentServerRef.tracer, request)
	defer span.Finish()

	span.LogFields(
		tracer.LogString("handler", fmt.Sprintf("handling user creating at %s\n", request.URL.Path)),
	)

	response.Header().Set("content-type", "application/json")
	var user model.User
	_ = json.NewDecoder(request.Body).Decode(&user)
	var documentId = contentServerRef.postStore.CreateUser(user)
	user.ID = documentId.InsertedID.(primitive.ObjectID)
	ctx := tracer.ContextWithSpan(context.Background(), span)
	renderJSONWithContext(ctx, response, user)
}

func (contentServerRef *ContentServer) UpdateUserEndpoint(response http.ResponseWriter, request *http.Request) {
	span := tracer.StartSpanFromRequest("UpdateUserEndpoint", contentServerRef.tracer, request)
	defer span.Finish()

	span.LogFields(
		tracer.LogString("handler", fmt.Sprintf("handling UpdateUserEndpoint at %s\n", request.URL.Path)),
	)

	response.Header().Set("content-type", "application/json")

	var userupdate model.UserUpdate

	_ = json.NewDecoder(request.Body).Decode(&userupdate)
	contentServerRef.postStore.UpdateUser(userupdate)
	ctx := tracer.ContextWithSpan(context.Background(), span)
	renderJSONWithContext(ctx, response, userupdate)
}

func (contentServerRef *ContentServer) GetUserByIDEndpoint(response http.ResponseWriter, request *http.Request) {
	span := tracer.StartSpanFromRequest("GetUserByIDEndpoint", contentServerRef.tracer, request)
	defer span.Finish()

	span.LogFields(
		tracer.LogString("handler", fmt.Sprintf("handling GetUserByIDEndpoint at %s\n", request.URL.Path)),
	)

	response.Header().Set("content-type", "application/json")
	params := mux.Vars(request)
	objectID, _ := primitive.ObjectIDFromHex(params["id"])
	var result = contentServerRef.postStore.GetUserByID(objectID)
	var user model.User
	err := result.Decode(&user)
	if err != nil {
		return
	}
	ctx := tracer.ContextWithSpan(context.Background(), span)
	renderJSONWithContext(ctx, response, user)
}

func (contentServerRef *ContentServer) GetUserByUsernameEndpoint(response http.ResponseWriter, request *http.Request) {
	span := tracer.StartSpanFromRequest("GetUserByUsernameEndpoint", contentServerRef.tracer, request)
	defer span.Finish()

	span.LogFields(
		tracer.LogString("handler", fmt.Sprintf("handling GetUserByUsernameEndpoint at %s\n", request.URL.Path)),
	)

	response.Header().Set("content-type", "application/json")
	params := mux.Vars(request)
	var result = contentServerRef.postStore.GetUserByUsername(params["username"])
	var user model.User
	err := result.Decode(&user)
	if err != nil {
		return
	}
	ctx := tracer.ContextWithSpan(context.Background(), span)
	renderJSONWithContext(ctx, response, user)
}

func (contentServerRef *ContentServer) CreateUserHighlightEndpoint(response http.ResponseWriter, request *http.Request) {
	span := tracer.StartSpanFromRequest("CreateUserHighlightEndpoint", contentServerRef.tracer, request)
	defer span.Finish()

	span.LogFields(
		tracer.LogString("handler", fmt.Sprintf("handling CreateUserHighlightEndpoint at %s\n", request.URL.Path)),
	)

	response.Header().Set("content-type", "application/json")
	var storyHighlight model.StoryHighlight
	_ = json.NewDecoder(request.Body).Decode(&storyHighlight)
	params := mux.Vars(request)
	contentServerRef.postStore.CreateStoryHighlight(&storyHighlight, params["userid"])
	ctx := tracer.ContextWithSpan(context.Background(), span)
	renderJSONWithContext(ctx, response, storyHighlight)
}

func (contentServerRef *ContentServer) CreateUserCollectionsEndpoint(response http.ResponseWriter, request *http.Request) {
	span := tracer.StartSpanFromRequest("CreateUserCollectionsEndpoint", contentServerRef.tracer, request)
	defer span.Finish()

	span.LogFields(
		tracer.LogString("handler", fmt.Sprintf("handling CreateUserCollectionsEndpoint at %s\n", request.URL.Path)),
	)

	response.Header().Set("content-type", "application/json")
	var collection model.Collection
	_ = json.NewDecoder(request.Body).Decode(&collection)
	params := mux.Vars(request)
	contentServerRef.postStore.CreateCollection(&collection, params["userid"])
	ctx := tracer.ContextWithSpan(context.Background(), span)
	renderJSONWithContext(ctx, response, collection)
}

func (contentServerRef *ContentServer) AddPostToUserSavedEndpoint(response http.ResponseWriter, request *http.Request) {
	span := tracer.StartSpanFromRequest("AddPostToUserSavedEndpoint", contentServerRef.tracer, request)
	defer span.Finish()

	span.LogFields(
		tracer.LogString("handler", fmt.Sprintf("handling AddPostToUserSavedEndpoint at %s\n", request.URL.Path)),
	)
	response.Header().Set("content-type", "application/json")
	// TODO: Think about change to new object instead of entire regular post
	var regularPost model.RegularPost
	_ = json.NewDecoder(request.Body).Decode(&regularPost)
	params := mux.Vars(request)
	contentServerRef.postStore.AddPostToSaved(regularPost, params["userid"])
	ctx := tracer.ContextWithSpan(context.Background(), span)
	renderJSONWithContext(ctx, response, regularPost)
}

func (contentServerRef *ContentServer) GetUserHighlightsEndpoint(response http.ResponseWriter, request *http.Request) {
	span := tracer.StartSpanFromRequest("GetUserHighlightsEndpoint", contentServerRef.tracer, request)
	defer span.Finish()

	span.LogFields(
		tracer.LogString("handler", fmt.Sprintf("handling GetUserHighlightsEndpoint at %s\n", request.URL.Path)),
	)
	response.Header().Set("content-type", "application/json")
	params := mux.Vars(request)
	ctx := tracer.ContextWithSpan(context.Background(), span)
	renderJSONWithContext(ctx, response, contentServerRef.postStore.GetUserStoryHighlights(params["userid"]))
}

func (contentServerRef *ContentServer) GetUserCollectionsEndpoint(response http.ResponseWriter, request *http.Request) {
	span := tracer.StartSpanFromRequest("GetUserCollectionsEndpoint", contentServerRef.tracer, request)
	defer span.Finish()

	span.LogFields(
		tracer.LogString("handler", fmt.Sprintf("handling GetUserCollectionsEndpoint at %s\n", request.URL.Path)),
	)
	response.Header().Set("content-type", "application/json")
	params := mux.Vars(request)
	ctx := tracer.ContextWithSpan(context.Background(), span)
	renderJSONWithContext(ctx, response, contentServerRef.postStore.GetUserCollections(params["userid"]))
}

func (contentServerRef *ContentServer) GetUserSavedPostsEndpoint(response http.ResponseWriter, request *http.Request) {
	span := tracer.StartSpanFromRequest("GetUserSavedPostsEndpoint", contentServerRef.tracer, request)
	defer span.Finish()

	span.LogFields(
		tracer.LogString("handler", fmt.Sprintf("handling GetUserSavedPostsEndpoint at %s\n", request.URL.Path)),
	)
	response.Header().Set("content-type", "application/json")
	params := mux.Vars(request)
	ctx := tracer.ContextWithSpan(context.Background(), span)
	renderJSONWithContext(ctx, response, contentServerRef.postStore.GetSavedPosts(params["userid"]))
}

func (contentServerRef *ContentServer) UpdateUserHighlightEndpoint(response http.ResponseWriter, request *http.Request) {
	span := tracer.StartSpanFromRequest("UpdateUserHighlightEndpoint", contentServerRef.tracer, request)
	defer span.Finish()

	span.LogFields(
		tracer.LogString("handler", fmt.Sprintf("handling UpdateUserHighlightEndpoint at %s\n", request.URL.Path)),
	)
	response.Header().Set("content-type", "application/json")
	var story model.Story
	_ = json.NewDecoder(request.Body).Decode(&story)
	params := mux.Vars(request)
	contentServerRef.postStore.AddStoryContentOnStoryHighlight(story, params["userid"], params["highlightid"])
	ctx := tracer.ContextWithSpan(context.Background(), span)
	renderJSONWithContext(ctx, response, story)
}

func (contentServerRef *ContentServer) UpdateUserCollectionEndpoint(response http.ResponseWriter, request *http.Request) {
	span := tracer.StartSpanFromRequest("UpdateUserCollectionEndpoint", contentServerRef.tracer, request)
	defer span.Finish()

	span.LogFields(
		tracer.LogString("handler", fmt.Sprintf("handling UpdateUserCollectionEndpoint at %s\n", request.URL.Path)),
	)
	response.Header().Set("content-type", "application/json")
	var post model.RegularPost
	_ = json.NewDecoder(request.Body).Decode(&post)
	params := mux.Vars(request)
	contentServerRef.postStore.AddPostToCollection(post, params["userid"], params["collectionid"])
	ctx := tracer.ContextWithSpan(context.Background(), span)
	renderJSONWithContext(ctx, response, post)
}
