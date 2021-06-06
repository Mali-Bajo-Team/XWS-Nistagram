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
	router.HandleFunc("/upload/", server.UploadFileEndpoint).Methods("POST")
	router.HandleFunc("/post/", server.CreatePostEndpoint).Methods("POST")
	router.HandleFunc("/user/", server.CreateUserEndpoint).Methods("POST")
	router.HandleFunc("/story/", server.CreateStoryEndpoint).Methods("POST")
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
			var filePath = "./post-content/" + createUniqueName() + "-" + hdr.Filename
			if outfile, err = os.Create(filePath); nil != err {
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

func (contentServerRef *ContentServer) CreateUserEndpoint(response http.ResponseWriter, request *http.Request) {
	response.Header().Set("content-type", "application/json")

	var user model.User
	_ = json.NewDecoder(request.Body).Decode(&user)
	var documentId = contentServerRef.postStore.CreateUser(user)
	user.ID = documentId.InsertedID.(primitive.ObjectID)
	renderJSON(response, user)
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

func (contentServerRef *ContentServer) CreateStoryEndpoint(response http.ResponseWriter, request *http.Request) {
	response.Header().Set("content-type", "application/json")
	var story model.Story
	_ = json.NewDecoder(request.Body).Decode(&story)
	var documentId = contentServerRef.postStore.CreateStory(story)
	story.ID = documentId.InsertedID.(primitive.ObjectID)
	contentServerRef.postStore.UpdateUserStories(story)
	renderJSON(response, story)
}
