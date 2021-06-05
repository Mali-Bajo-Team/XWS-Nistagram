package usecase

import (
	"content_service/model"
	"encoding/json"
	"fmt"
	"github.com/gorilla/mux"
	"io/ioutil"
	"log"
	"net/http"
)

func InitializeRouter(router *mux.Router, server *ContentServer) {

	// TODO: Create content and return entire content entity
	router.HandleFunc("/upload/", server.UploadFileEndpoint).Methods("POST")

	router.HandleFunc("/post/", server.CreatePostEndpoint).Methods("POST")
	router.HandleFunc("/story/", server.CreateStoryEndpoint).Methods("POST")
}

func (contentServerRef *ContentServer) UploadFileEndpoint(responseWriter http.ResponseWriter, request *http.Request){
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

func (contentServerRef *ContentServer) CreatePostEndpoint(response http.ResponseWriter, request *http.Request) {
	response.Header().Set("content-type", "application/json")
	var regularPost model.RegularPost
	_ = json.NewDecoder(request.Body).Decode(&regularPost)
	var result = contentServerRef.postStore.CreatePost(regularPost)
	json.NewEncoder(response).Encode(result)
}

func (contentServerRef *ContentServer) CreateStoryEndpoint(response http.ResponseWriter, request *http.Request) {
	response.Header().Set("content-type", "application/json")
	var story model.Story
	_ = json.NewDecoder(request.Body).Decode(&story)
	var result = contentServerRef.postStore.CreateStory(story)
	json.NewEncoder(response).Encode(result)
}