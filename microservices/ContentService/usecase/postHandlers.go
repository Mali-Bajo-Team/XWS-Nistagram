package usecase

import (
	"content_service/model"
	"encoding/json"
	"github.com/gorilla/mux"
	"go.mongodb.org/mongo-driver/bson/primitive"
	"net/http"
)

func InitializePostRouter(router *mux.Router, server *ContentServer) {
	router.HandleFunc("/post/", server.CreatePostEndpoint).Methods("POST")
	router.HandleFunc("/story/", server.CreateStoryEndpoint).Methods("POST")
	router.HandleFunc("/post/{postid}", server.GetEntirePostEndpoint).Methods("GET")
	router.HandleFunc("/story/{storyid}", server.GetEntireStoryEndpoint).Methods("GET")

	// TODO: Find some better naming for reaction-undo-reaction
	router.HandleFunc("/post/reaction/{id}", server.CreatePostReactionEndpoint).Methods("POST")
	router.HandleFunc("/post/unreaction/{id}", server.DeletePostReactionEndpoint).Methods("POST")
	router.HandleFunc("/post/comment/{id}", server.CreatePostCommentEndpoint).Methods("POST")
	router.HandleFunc("/post/comment/{id}", server.GetPostCommentsEndpoint).Methods("GET")

	router.HandleFunc("/post/inappropriate/", server.CreateInappropriatePostEndpoint).Methods("POST")
	router.HandleFunc("/story/inappropriate/", server.CreateInappropriateStoryEndpoint).Methods("POST")


	router.HandleFunc("/post/search/location/", server.SearchByLocation).Methods("POST")
	router.HandleFunc("/post/search/hashtag/{hashtag}", server.SearchByHashtag).Methods("GET")
}

func (contentServerRef *ContentServer) CreatePostEndpoint(response http.ResponseWriter, request *http.Request) {
	response.Header().Set("content-type", "application/json")
	var post model.RegularPost
	_ = json.NewDecoder(request.Body).Decode(&post)
	var documentId = contentServerRef.postStore.CreatePost(post)
	post.ID = documentId.InsertedID.(primitive.ObjectID)
	contentServerRef.postStore.UpdateUserPosts(post)
	renderJSON(response, post)
}

func (contentServerRef *ContentServer) GetEntirePostEndpoint(response http.ResponseWriter, request *http.Request) {
	response.Header().Set("content-type", "application/json")
	params := mux.Vars(request)
	renderJSON(response, contentServerRef.postStore.GetEntirePost(params["postid"]))
}

func (contentServerRef *ContentServer) CreateInappropriatePostEndpoint(response http.ResponseWriter, request *http.Request) {
	response.Header().Set("content-type", "application/json")
	var inappropriatePost model.InappropriatePost
	_ = json.NewDecoder(request.Body).Decode(&inappropriatePost)
	var documentId = contentServerRef.postStore.CreateInappropriatePost(inappropriatePost)
	inappropriatePost.ID = documentId.InsertedID.(primitive.ObjectID)
	renderJSON(response, inappropriatePost)
}

func (contentServerRef *ContentServer) CreateInappropriateStoryEndpoint(response http.ResponseWriter, request *http.Request) {
	response.Header().Set("content-type", "application/json")
	var inappropriateStory model.InappropriateStory
	_ = json.NewDecoder(request.Body).Decode(&inappropriateStory)
	var documentId = contentServerRef.postStore.CreateInappropriateStory(inappropriateStory)
	inappropriateStory.ID = documentId.InsertedID.(primitive.ObjectID)
	renderJSON(response, inappropriateStory)
}

func (contentServerRef *ContentServer) GetEntireStoryEndpoint(response http.ResponseWriter, request *http.Request) {
	response.Header().Set("content-type", "application/json")
	params := mux.Vars(request)
	renderJSON(response, contentServerRef.postStore.GetEntireStory(params["storyid"]))
}

func (contentServerRef *ContentServer) CreatePostCommentEndpoint(response http.ResponseWriter, request *http.Request) {
	response.Header().Set("content-type", "application/json")
	var comment model.Comment
	_ = json.NewDecoder(request.Body).Decode(&comment)
	params := mux.Vars(request)
	contentServerRef.postStore.UpdatePostComments(comment, params["id"])
	renderJSON(response, comment)
}

func (contentServerRef *ContentServer) CreatePostReactionEndpoint(response http.ResponseWriter, request *http.Request) {
	response.Header().Set("content-type", "application/json")
	var reaction model.Reaction
	_ = json.NewDecoder(request.Body).Decode(&reaction)
	params := mux.Vars(request)
	contentServerRef.postStore.CreatePostReaction(reaction, params["id"])
	renderJSON(response, reaction)
}

func (contentServerRef *ContentServer) DeletePostReactionEndpoint(response http.ResponseWriter, request *http.Request) {
	response.Header().Set("content-type", "application/json")
	var reaction model.Reaction
	_ = json.NewDecoder(request.Body).Decode(&reaction)
	params := mux.Vars(request)
	contentServerRef.postStore.DeletePostReaction(reaction, params["id"])
	renderJSON(response, reaction)
}

func (contentServerRef *ContentServer) GetPostCommentsEndpoint(response http.ResponseWriter, request *http.Request) {
	response.Header().Set("content-type", "application/json")
	params := mux.Vars(request)
	var result = contentServerRef.postStore.GetPostByID(params["id"])
	var post model.RegularPost
	err := result.Decode(&post)
	if err != nil {
		return
	}
	renderJSON(response, post.Comments)
}

func (contentServerRef *ContentServer) CreateStoryEndpoint(response http.ResponseWriter, request *http.Request) {
	response.Header().Set("content-type", "application/json")
	var story model.Story
	_ = json.NewDecoder(request.Body).Decode(&story)
	var documentId = contentServerRef.postStore.CreateStory(story)
	story.ID = documentId.InsertedID.(primitive.ObjectID)
	contentServerRef.postStore.UpdateUserStories(story)
	renderJSON(response, story)
}

func (contentServerRef *ContentServer) GetUserPostsByIDEndpoint(response http.ResponseWriter, request *http.Request) {
	response.Header().Set("content-type", "application/json")
	params := mux.Vars(request)
	objectID, _ := primitive.ObjectIDFromHex(params["id"])
	var result = contentServerRef.postStore.GetUserByID(objectID)
	var user model.User
	err := result.Decode(&user)
	if err != nil {
		return
	}
	renderJSON(response, user.Posts)
}

func (contentServerRef *ContentServer) GetUserStoriesByIDEndpoint(response http.ResponseWriter, request *http.Request) {
	response.Header().Set("content-type", "application/json")
	params := mux.Vars(request)
	objectID, _ := primitive.ObjectIDFromHex(params["id"])
	var result = contentServerRef.postStore.GetUserByID(objectID)
	var user model.User
	err := result.Decode(&user)
	if err != nil {
		return
	}
	renderJSON(response, user.Stories)
}

func (contentServerRef *ContentServer) SearchByLocation(response http.ResponseWriter, request *http.Request) {
	response.Header().Set("content-type", "application/json")
	var coordinates model.Coordinates
	_ = json.NewDecoder(request.Body).Decode(&coordinates)

	var result = contentServerRef.postStore.GetPostsByLocation(coordinates.Longitude, coordinates.Latitude)

	renderJSON(response, result)
}

func (contentServerRef *ContentServer) SearchByHashtag(response http.ResponseWriter, request *http.Request) {
	response.Header().Set("content-type", "application/json")
	params := mux.Vars(request)

	var result = contentServerRef.postStore.GetPostsByHashtag(params["hashtag"])

	renderJSON(response, result)
}
