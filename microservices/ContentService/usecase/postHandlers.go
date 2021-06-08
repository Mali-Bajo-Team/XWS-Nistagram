package usecase

import (
	"content_service/model"
	"encoding/json"
	"github.com/gorilla/mux"
	"go.mongodb.org/mongo-driver/bson/primitive"
	"io"
	"mime/multipart"
	"net/http"
	"os"
)

func InitializeRouter(router *mux.Router, server *ContentServer) {
	router.HandleFunc("/file/", server.UploadFileEndpoint).Methods("POST")
	router.HandleFunc("/file/{name}", server.GetFileEndpoint).Methods("GET")

	router.HandleFunc("/post/", server.CreatePostEndpoint).Methods("POST")
	router.HandleFunc("/post/{postid}", server.GetEntirePostEndpoint).Methods("GET")
	// TODO: Find some better naming
	router.HandleFunc("/user/highlights/{userid}", server.CreateUserHighlightEndpoint).Methods("POST")
	router.HandleFunc("/user/highlights/{userid}", server.GetUserHighlightsEndpoint).Methods("GET")
	router.HandleFunc("/user/highlight/{highlightid}/{userid}", server.UpdateUserHighlightEndpoint).Methods("POST")

	router.HandleFunc("/user/collections/{userid}", server.CreateUserCollectionsEndpoint).Methods("POST")
	router.HandleFunc("/user/collections/{userid}", server.GetUserCollectionsEndpoint).Methods("GET")
	router.HandleFunc("/user/collection/{collectionid}/{userid}", server.UpdateUserCollectionEndpoint).Methods("POST")

	router.HandleFunc("/user/saved/{userid}", server.AddPostToSavedEndpoint).Methods("POST")
	router.HandleFunc("/user/saved/{userid}", server.GetSavedPostsEndpoint).Methods("GET")


	// TODO: Find some better naming for reaction-undo-reaction
	router.HandleFunc("/post/reaction/{id}", server.CreatePostReactionEndpoint).Methods("POST")
	router.HandleFunc("/post/unreaction/{id}", server.DeletePostReactionEndpoint).Methods("POST")
	router.HandleFunc("/post/comment/{id}", server.CreatePostCommentEndpoint).Methods("POST")
	router.HandleFunc("/post/comment/{id}", server.GetPostCommentsEndpoint).Methods("GET")

	router.HandleFunc("/user/", server.CreateUserEndpoint).Methods("POST")
	router.HandleFunc("/user/update/", server.UpdateUserEndpoint).Methods("POST")
	router.HandleFunc("/user/id/{id}", server.GetUserByIDEndpoint).Methods("GET")
	router.HandleFunc("/user/username/{username}", server.GetUserByUsernameEndpoint).Methods("GET")
	router.HandleFunc("/user/post/{id}", server.GetUserPostsByIDEndpoint).Methods("GET")
	router.HandleFunc("/user/story/{id}", server.GetUserStoriesByIDEndpoint).Methods("GET")
	router.HandleFunc("/story/", server.CreateStoryEndpoint).Methods("POST")

	router.HandleFunc("/post/search/location/", server.SearchByLocation).Methods("POST")	
	router.HandleFunc("/post/search/hashtag/{hashtag}", server.SearchByHashtag).Methods("GET")
}

func (contentServerRef *ContentServer) UploadFileEndpoint(responseWriter http.ResponseWriter, request *http.Request) {
	var allContent []interface{}
	const _24K = (1 << 10) * 24
	request.ParseMultipartForm(_24K)
	for _, fileHeaders := range request.MultipartForm.File {
		for _, hdr := range fileHeaders {
			// open uploaded
			var infile multipart.File
			infile, err := hdr.Open()
			// open destination
			var outfile *os.File
			var filePath = createUniqueName() + "-" + hdr.Filename
			if outfile, err = os.Create("./post-content/" + filePath); nil != err {
				return
			}
			content := initializeContent(filePath, hdr)
			documentId := contentServerRef.postStore.CreateOneContent(content)
			content.ID = documentId.InsertedID.(primitive.ObjectID)

			io.Copy(outfile, infile)
			allContent = append(allContent, content)
		}
	}
	renderJSON(responseWriter, allContent)
}

func (contentServerRef *ContentServer) GetFileEndpoint(response http.ResponseWriter, request *http.Request) {
	params := mux.Vars(request)
	http.ServeFile(response, request, "./post-content/"+params["name"])
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

func (contentServerRef *ContentServer) AddPostToSavedEndpoint(response http.ResponseWriter, request *http.Request) {
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

func (contentServerRef *ContentServer) GetSavedPostsEndpoint(response http.ResponseWriter, request *http.Request) {
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



