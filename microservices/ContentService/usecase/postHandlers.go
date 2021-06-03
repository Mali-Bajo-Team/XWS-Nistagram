package usecase

import (
	"content_service/model"
	"fmt"
	"github.com/gorilla/mux"
	"io/ioutil"
	"log"
	"mime"
	"net/http"
	"strconv"
)

func InitializeRouter(router *mux.Router, server *ContentServer) {

	router.HandleFunc("/upload/", server.UploadFileHandler).Methods("POST")

	router.HandleFunc("/post/", server.CreatePostHandler).Methods("POST")
	router.HandleFunc("/post/", server.GetAllPostsHandler).Methods("GET")
	router.HandleFunc("/post/{id:[0-9]+}/", server.GetPostHandler).Methods("GET")
	router.HandleFunc("/post/{id:[0-9]+}/", server.DeletePostHandler).Methods("DELETE")
}

func (contentServerRef *ContentServer) UploadFileHandler(responseWriter http.ResponseWriter, request *http.Request){
	log.Println("Uploading content")
	// Parse our multipart form, 10 << 20 specifies a maximum
	// upload of 10 MB files.
	request.ParseMultipartForm(10 << 20)
	// FormFile returns the first file for the given key `myFile`
	// it also returns the FileHeader so we can get the Filename,
	// the Header and the size of the file
	file, handler, err := request.FormFile("myFile")
	if err != nil {
		fmt.Println("Error Retrieving the File")
		fmt.Println(err)
		return
	}
	defer file.Close()

	printUploadedFile(handler)
	tempFile := createFile(err)
	defer tempFile.Close()

	// read all of the contents of our uploaded file into a
	// byte array
	fileBytes, err := ioutil.ReadAll(file)
	if err != nil {
		fmt.Println(err)
	}
	// write this byte array to our temporary file
	tempFile.Write(fileBytes)
	// return that we have successfully uploaded our file!
	log.Println("Successfully Uploaded File")
}




func (contentServerRef *ContentServer) CreatePostHandler(responseWriter http.ResponseWriter, request *http.Request) {

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

	id := contentServerRef.postStore.CreatePost(requestPost.Title, requestPost.Text, requestPost.Tags)
	renderJSON(responseWriter, model.ResponseId{Id: id})
}

func (contentServerRef *ContentServer) GetAllPostsHandler(responseWriter http.ResponseWriter, request *http.Request) {
	allTasks := contentServerRef.postStore.GetAllPosts()
	renderJSON(responseWriter, allTasks)
}

func (contentServerRef *ContentServer) GetPostHandler(responseWriter http.ResponseWriter, request *http.Request) {
	id, _ := strconv.Atoi(mux.Vars(request)["id"])
	task, err := contentServerRef.postStore.GetPost(id)

	if err != nil {
		http.Error(responseWriter, err.Error(), http.StatusNotFound)
		return
	}

	renderJSON(responseWriter, task)
}

func (contentServerRef *ContentServer) DeletePostHandler(responseWriter http.ResponseWriter, request *http.Request) {
	id, _ := strconv.Atoi(mux.Vars(request)["id"])
	err := contentServerRef.postStore.DeletePost(id)

	if err != nil {
		http.Error(responseWriter, err.Error(), http.StatusNotFound)
	}
}
