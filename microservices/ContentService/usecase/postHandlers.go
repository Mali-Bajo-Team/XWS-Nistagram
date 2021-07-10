package usecase

import (
	"content_service/model"
	"content_service/tracer"
	"context"
	"encoding/json"
	"fmt"
	"net/http"
	"os"
	"strconv"
	"time"

	"github.com/gorilla/mux"
	"go.mongodb.org/mongo-driver/bson/primitive"
)

func InitializePostRouter(router *mux.Router, server *ContentServer) {

	router.HandleFunc("/post/inappropriate/", server.CreateInappropriatePostEndpoint).Methods("POST")
	router.HandleFunc("/post/inappropriate/", server.GetInappropriatePostsEndpoint).Methods("GET")
	router.HandleFunc("/story/inappropriate/", server.CreateInappropriateStoryEndpoint).Methods("POST")
	router.HandleFunc("/story/inappropriate/", server.GetInappropriateStoryEndpoint).Methods("GET")

	// TODO: This should be delete method, but deadline is tomorrow, so fix this when you get a time :D
	router.HandleFunc("/post/inappropriate/delete/{inappropriatepostid}/{postid}", server.DeleteInappropriatePostEndpoint).Methods("GET")
	router.HandleFunc("/post/delete/{username}/{postid}", server.DeleteUserPostEndpoint).Methods("GET")

	router.HandleFunc("/post/feed/{page}", server.GetPostsForFeedEndpoint).Methods("GET")
	router.HandleFunc("/story/feed/{page}", server.GetStoriesForFeedEndpoint).Methods("GET")

	router.HandleFunc("/post/", server.CreatePostEndpoint).Methods("POST")
	router.HandleFunc("/story/", server.CreateStoryEndpoint).Methods("POST")
	router.HandleFunc("/post/{postid}", server.GetEntirePostEndpoint).Methods("GET")
	router.HandleFunc("/story/{storyid}", server.GetEntireStoryEndpoint).Methods("GET")

	// TODO: Find some better naming for reaction-undo-reaction
	router.HandleFunc("/post/reaction/{id}", server.CreatePostReactionEndpoint).Methods("POST")
	router.HandleFunc("/post/reaction/{id}", server.GetPostReactionEndpoint).Methods("GET")
	router.HandleFunc("/reaction/{type}", server.GetReactionsEndpoint).Methods("GET")
	router.HandleFunc("/post/unreaction/{id}", server.DeletePostReactionEndpoint).Methods("POST")
	router.HandleFunc("/post/comment/{id}", server.CreatePostCommentEndpoint).Methods("POST")
	router.HandleFunc("/post/comment/{id}", server.GetPostCommentsEndpoint).Methods("GET")

	router.HandleFunc("/post/search/location/", server.SearchByLocation).Methods("POST")
	router.HandleFunc("/post/search/hashtag/{hashtag}", server.SearchByHashtag).Methods("GET")
}

func (contentServerRef *ContentServer) CreatePostEndpoint(response http.ResponseWriter, request *http.Request) {
	span := tracer.StartSpanFromRequest("CreatePostEndpoint", contentServerRef.tracer, request)
	defer span.Finish()

	span.LogFields(
		tracer.LogString("handler", fmt.Sprintf("handling CreatePostEndpoint at %s\n", request.URL.Path)),
	)

	response.Header().Set("content-type", "application/json")
	var post model.RegularPost
	_ = json.NewDecoder(request.Body).Decode(&post)
	post.MyPost.CreatorUsername = request.Header.Get("user-username")
	post.MyPost.TimeStamp = time.Now()
	post.LikeCount = 0
	post.DislikeCount = 0
	var documentId = contentServerRef.postStore.CreatePost(post)
	post.ID = documentId.InsertedID.(primitive.ObjectID)
	contentServerRef.postStore.UpdateUserPosts(post)
	ctx := tracer.ContextWithSpan(context.Background(), span)
	renderJSONWithContext(ctx, response, post)
}

func (contentServerRef *ContentServer) GetEntirePostEndpoint(response http.ResponseWriter, request *http.Request) {
	span := tracer.StartSpanFromRequest("GetEntirePostEndpoint", contentServerRef.tracer, request)
	defer span.Finish()

	span.LogFields(
		tracer.LogString("handler", fmt.Sprintf("handling GetEntirePostEndpoint at %s\n", request.URL.Path)),
	)
	response.Header().Set("content-type", "application/json")
	params := mux.Vars(request)
	ctx := tracer.ContextWithSpan(context.Background(), span)
	renderJSONWithContext(ctx, response, contentServerRef.postStore.GetEntirePost(params["postid"]))
}

func (contentServerRef *ContentServer) GetInappropriatePostsEndpoint(response http.ResponseWriter, request *http.Request) {
	span := tracer.StartSpanFromRequest("GetInappropriatePostsEndpoint", contentServerRef.tracer, request)
	defer span.Finish()

	span.LogFields(
		tracer.LogString("handler", fmt.Sprintf("handling GetInappropriatePostsEndpoint at %s\n", request.URL.Path)),
	)
	response.Header().Set("content-type", "application/json")
	ctx := tracer.ContextWithSpan(context.Background(), span)
	renderJSONWithContext(ctx, response, contentServerRef.postStore.GetAllInappropriatePosts())
}

func (contentServerRef *ContentServer) GetInappropriateStoryEndpoint(response http.ResponseWriter, request *http.Request) {
	span := tracer.StartSpanFromRequest("GetInappropriateStoryEndpoint", contentServerRef.tracer, request)
	defer span.Finish()

	span.LogFields(
		tracer.LogString("handler", fmt.Sprintf("handling GetInappropriateStoryEndpoint at %s\n", request.URL.Path)),
	)
	response.Header().Set("content-type", "application/json")
	ctx := tracer.ContextWithSpan(context.Background(), span)
	renderJSONWithContext(ctx, response, contentServerRef.postStore.GetAllInappropriateStories())
}

func (contentServerRef *ContentServer) CreateInappropriatePostEndpoint(response http.ResponseWriter, request *http.Request) {
	span := tracer.StartSpanFromRequest("CreateInappropriatePostEndpoint", contentServerRef.tracer, request)
	defer span.Finish()

	span.LogFields(
		tracer.LogString("handler", fmt.Sprintf("handling CreateInappropriatePostEndpoint at %s\n", request.URL.Path)),
	)
	response.Header().Set("content-type", "application/json")
	var inappropriatePost model.InappropriatePost
	_ = json.NewDecoder(request.Body).Decode(&inappropriatePost)
	var documentId = contentServerRef.postStore.CreateInappropriatePost(inappropriatePost)
	inappropriatePost.ID = documentId.InsertedID.(primitive.ObjectID)
	ctx := tracer.ContextWithSpan(context.Background(), span)
	renderJSONWithContext(ctx, response, inappropriatePost)
}

func (contentServerRef *ContentServer) CreateInappropriateStoryEndpoint(response http.ResponseWriter, request *http.Request) {
	span := tracer.StartSpanFromRequest("CreateInappropriateStoryEndpoint", contentServerRef.tracer, request)
	defer span.Finish()

	span.LogFields(
		tracer.LogString("handler", fmt.Sprintf("handling CreateInappropriateStoryEndpoint at %s\n", request.URL.Path)),
	)
	response.Header().Set("content-type", "application/json")
	var inappropriateStory model.InappropriateStory
	_ = json.NewDecoder(request.Body).Decode(&inappropriateStory)
	var documentId = contentServerRef.postStore.CreateInappropriateStory(inappropriateStory)
	inappropriateStory.ID = documentId.InsertedID.(primitive.ObjectID)
	ctx := tracer.ContextWithSpan(context.Background(), span)
	renderJSONWithContext(ctx, response, inappropriateStory)
}

func (contentServerRef *ContentServer) GetEntireStoryEndpoint(response http.ResponseWriter, request *http.Request) {
	span := tracer.StartSpanFromRequest("GetEntireStoryEndpoint", contentServerRef.tracer, request)
	defer span.Finish()

	span.LogFields(
		tracer.LogString("handler", fmt.Sprintf("handling GetEntireStoryEndpoint at %s\n", request.URL.Path)),
	)
	response.Header().Set("content-type", "application/json")
	params := mux.Vars(request)
	ctx := tracer.ContextWithSpan(context.Background(), span)
	renderJSONWithContext(ctx, response, contentServerRef.postStore.GetEntireStory(params["storyid"]))
}

func (contentServerRef *ContentServer) CreatePostCommentEndpoint(response http.ResponseWriter, request *http.Request) {
	span := tracer.StartSpanFromRequest("CreatePostCommentEndpoint", contentServerRef.tracer, request)
	defer span.Finish()

	span.LogFields(
		tracer.LogString("handler", fmt.Sprintf("handling CreatePostCommentEndpoint at %s\n", request.URL.Path)),
	)
	response.Header().Set("content-type", "application/json")
	var comment model.Comment
	_ = json.NewDecoder(request.Body).Decode(&comment)
	comment.Username = request.Header.Get("user-username")
	params := mux.Vars(request)
	contentServerRef.postStore.UpdatePostComments(comment, params["id"])
	ctx := tracer.ContextWithSpan(context.Background(), span)
	renderJSONWithContext(ctx, response, comment)
}

func (contentServerRef *ContentServer) CreatePostReactionEndpoint(response http.ResponseWriter, request *http.Request) {
	span := tracer.StartSpanFromRequest("CreatePostReactionEndpoint", contentServerRef.tracer, request)
	defer span.Finish()

	span.LogFields(
		tracer.LogString("handler", fmt.Sprintf("handling CreatePostReactionEndpoint at %s\n", request.URL.Path)),
	)
	response.Header().Set("content-type", "application/json")
	var reaction model.Reaction
	_ = json.NewDecoder(request.Body).Decode(&reaction)
	params := mux.Vars(request)
	reaction.CreationTime = time.Now()
	reaction.PostID = params["id"]
	reaction.CreatorUsername = request.Header.Get("user-username")
	contentServerRef.postStore.CreatePostReaction(reaction)
	response.WriteHeader(http.StatusNoContent)
}

func (contentServerRef *ContentServer) DeletePostReactionEndpoint(response http.ResponseWriter, request *http.Request) {
	response.Header().Set("content-type", "application/json")
	params := mux.Vars(request)
	contentServerRef.postStore.DeletePostReaction(request.Header.Get("user-username"), params["id"])
	response.WriteHeader(http.StatusNoContent)
}

func (contentServerRef *ContentServer) GetPostReactionEndpoint(response http.ResponseWriter, request *http.Request) {
	span := tracer.StartSpanFromRequest("GetPostReactionEndpoint", contentServerRef.tracer, request)
	defer span.Finish()

	span.LogFields(
		tracer.LogString("handler", fmt.Sprintf("handling GetPostReactionEndpoint at %s\n", request.URL.Path)),
	)
	response.Header().Set("content-type", "application/json")
	params := mux.Vars(request)
	reaction := contentServerRef.postStore.GetPostReaction(request.Header.Get("user-username"), params["id"])
	if reaction == nil {
		response.WriteHeader(http.StatusNoContent)
	}
	ctx := tracer.ContextWithSpan(context.Background(), span)
	renderJSONWithContext(ctx, response, reaction)
}

func (contentServerRef *ContentServer) GetReactionsEndpoint(response http.ResponseWriter, request *http.Request) {
	span := tracer.StartSpanFromRequest("GetReactionsEndpoint", contentServerRef.tracer, request)
	defer span.Finish()

	span.LogFields(
		tracer.LogString("handler", fmt.Sprintf("handling GetReactionsEndpoint at %s\n", request.URL.Path)),
	)
	response.Header().Set("content-type", "application/json")
	params := mux.Vars(request)
	reactions := contentServerRef.postStore.GetReactions(request.Header.Get("user-username"), params["type"])
	ctx := tracer.ContextWithSpan(context.Background(), span)
	renderJSONWithContext(ctx, response, reactions)
}

func (contentServerRef *ContentServer) GetPostCommentsEndpoint(response http.ResponseWriter, request *http.Request) {
	span := tracer.StartSpanFromRequest("GetPostCommentsEndpoint", contentServerRef.tracer, request)
	defer span.Finish()

	span.LogFields(
		tracer.LogString("handler", fmt.Sprintf("handling GetPostCommentsEndpoint at %s\n", request.URL.Path)),
	)
	response.Header().Set("content-type", "application/json")
	params := mux.Vars(request)
	var result = contentServerRef.postStore.GetPostByID(params["id"])
	var post model.RegularPost
	err := result.Decode(&post)
	if err != nil {
		return
	}
	ctx := tracer.ContextWithSpan(context.Background(), span)
	renderJSONWithContext(ctx, response, post.Comments)
}

func (contentServerRef *ContentServer) CreateStoryEndpoint(response http.ResponseWriter, request *http.Request) {
	span := tracer.StartSpanFromRequest("CreateStoryEndpoint", contentServerRef.tracer, request)
	defer span.Finish()

	span.LogFields(
		tracer.LogString("handler", fmt.Sprintf("handling CreateStoryEndpoint at %s\n", request.URL.Path)),
	)
	response.Header().Set("content-type", "application/json")
	var story model.Story
	_ = json.NewDecoder(request.Body).Decode(&story)
	story.MyPost.CreatorUsername = request.Header.Get("user-username")
	story.MyPost.TimeStamp = time.Now()
	var documentId = contentServerRef.postStore.CreateStory(story)
	story.ID = documentId.InsertedID.(primitive.ObjectID)
	contentServerRef.postStore.UpdateUserStories(story)
	ctx := tracer.ContextWithSpan(context.Background(), span)
	renderJSONWithContext(ctx, response, story)
}

func (contentServerRef *ContentServer) GetUserPostsByIDEndpoint(response http.ResponseWriter, request *http.Request) {
	span := tracer.StartSpanFromRequest("GetUserPostsByIDEndpoint", contentServerRef.tracer, request)
	defer span.Finish()

	span.LogFields(
		tracer.LogString("handler", fmt.Sprintf("handling GetUserPostsByIDEndpoint at %s\n", request.URL.Path)),
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
	renderJSONWithContext(ctx, response, user.Posts)
}

func (contentServerRef *ContentServer) GetUserStoriesByIDEndpoint(response http.ResponseWriter, request *http.Request) {
	span := tracer.StartSpanFromRequest("GetUserStoriesByIDEndpoint", contentServerRef.tracer, request)
	defer span.Finish()

	span.LogFields(
		tracer.LogString("handler", fmt.Sprintf("handling GetUserStoriesByIDEndpoint at %s\n", request.URL.Path)),
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
	renderJSONWithContext(ctx, response, user.Stories)
}

func (contentServerRef *ContentServer) SearchByLocation(response http.ResponseWriter, request *http.Request) {
	span := tracer.StartSpanFromRequest("SearchByLocation", contentServerRef.tracer, request)
	defer span.Finish()

	span.LogFields(
		tracer.LogString("handler", fmt.Sprintf("handling SearchByLocation at %s\n", request.URL.Path)),
	)
	response.Header().Set("content-type", "application/json")
	var coordinates model.Coordinates
	_ = json.NewDecoder(request.Body).Decode(&coordinates)

	var result = contentServerRef.postStore.GetPostsByLocation(coordinates.Longitude, coordinates.Latitude)
	ctx := tracer.ContextWithSpan(context.Background(), span)
	renderJSONWithContext(ctx, response, result)
}

func (contentServerRef *ContentServer) SearchByHashtag(response http.ResponseWriter, request *http.Request) {
	span := tracer.StartSpanFromRequest("SearchByHashtag", contentServerRef.tracer, request)
	defer span.Finish()

	span.LogFields(
		tracer.LogString("handler", fmt.Sprintf("handling SearchByHashtag at %s\n", request.URL.Path)),
	)
	response.Header().Set("content-type", "application/json")
	params := mux.Vars(request)

	var result = contentServerRef.postStore.GetPostsByHashtag(params["hashtag"])
	ctx := tracer.ContextWithSpan(context.Background(), span)
	renderJSONWithContext(ctx, response, result)
}

func (contentServerRef *ContentServer) GetPostsForFeedEndpoint(response http.ResponseWriter, request *http.Request) {
	span := tracer.StartSpanFromRequest("GetPostsForFeedEndpoint", contentServerRef.tracer, request)
	defer span.Finish()

	span.LogFields(
		tracer.LogString("handler", fmt.Sprintf("handling GetPostsForFeedEndpoint at %s\n", request.URL.Path)),
	)
	response.Header().Set("content-type", "application/json")
	userService, present := os.LookupEnv("USER_SERVICE")
	if !present {
		userService = "http://user-service:8081"
	}

	params := mux.Vars(request)
	page, _ := strconv.ParseInt(params["page"], 10, 64)

	var client = &http.Client{Timeout: 10 * time.Second}
	req, _ := http.NewRequest("GET", userService+"/internal/users-for-feed", nil)
	req.Header.Add("Authorization", request.Header.Get("Authorization"))
	resp, _ := client.Do(req)

	var usernames []string
	_ = json.NewDecoder(resp.Body).Decode(&usernames)

	var result = contentServerRef.postStore.GetPostsByCreators(usernames, page)

	ctx := tracer.ContextWithSpan(context.Background(), span)
	renderJSONWithContext(ctx, response, result)
}

func (contentServerRef *ContentServer) GetStoriesForFeedEndpoint(response http.ResponseWriter, request *http.Request) {
	span := tracer.StartSpanFromRequest("GetStoriesForFeedEndpoint", contentServerRef.tracer, request)
	defer span.Finish()

	span.LogFields(
		tracer.LogString("handler", fmt.Sprintf("handling GetStoriesForFeedEndpoint at %s\n", request.URL.Path)),
	)
	response.Header().Set("content-type", "application/json")
	userService, present := os.LookupEnv("USER_SERVICE")
	if !present {
		userService = "http://user-service:8081"
	}

	params := mux.Vars(request)
	page, _ := strconv.ParseInt(params["page"], 10, 64)

	var client = &http.Client{Timeout: 10 * time.Second}
	req, _ := http.NewRequest("GET", userService+"/internal/users-for-feed", nil)
	req.Header.Add("Authorization", request.Header.Get("Authorization"))
	resp, _ := client.Do(req)

	var usernames []string
	_ = json.NewDecoder(resp.Body).Decode(&usernames)

	var result = contentServerRef.postStore.GetStoriesByCreators(usernames, page)

	ctx := tracer.ContextWithSpan(context.Background(), span)
	renderJSONWithContext(ctx, response, result)
}
