package usecase

import (
	"content_service/model"
	"encoding/json"
	"github.com/gorilla/mux"
	"io"
	"mime/multipart"
	"net/http"
	"os"
	"strconv"
)

func InitializeRouter(router *mux.Router, server *ContentServer) {

	// TODO: Create content and return entire content entity
	router.HandleFunc("/upload/", server.UploadFileEndpoint).Methods("POST")

	router.HandleFunc("/post/", server.CreatePostEndpoint).Methods("POST")
	router.HandleFunc("/story/", server.CreateStoryEndpoint).Methods("POST")
}

func (contentServerRef *ContentServer) UploadFileEndpoint(res http.ResponseWriter, req *http.Request) {
	// parse request
	const _24K = (1 << 10) * 24
	req.ParseMultipartForm(_24K)
	for _, fileHeaders := range req.MultipartForm.File {
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
			contentServerRef.postStore.CreateOneContent(content)

			// 32K buffer copy
			var written int64
			if written, err = io.Copy(outfile, infile); nil != err {
				return
			}

			res.Write([]byte("uploaded file:" + hdr.Filename + ";length:" + strconv.Itoa(int(written))))
		}
	}
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