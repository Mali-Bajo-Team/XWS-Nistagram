package usecase

import (
	"github.com/gorilla/mux"
	"go.mongodb.org/mongo-driver/bson/primitive"
	"io"
	"mime/multipart"
	"net/http"
	"os"
)

func InitializeFileRouter(router *mux.Router, server *ContentServer) {
	router.HandleFunc("/file/", server.UploadFileEndpoint).Methods("POST")
	router.HandleFunc("/file/{name}", server.GetFileEndpoint).Methods("GET")
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
