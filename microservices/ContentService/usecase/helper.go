package usecase

import (
	"content_service/model"
	"encoding/json"
	"fmt"
	"io"
	"io/ioutil"
	"mime/multipart"
	"net/http"
	"os"
	"strconv"
	"time"
)

// renderJSON renders 'v' as JSON and writes it as a response into w.
func renderJSON(responseWriter http.ResponseWriter, v interface{}) {
	marshalJson, err := json.Marshal(v)
	if err != nil {
		http.Error(responseWriter, err.Error(), http.StatusInternalServerError)
		return
	}
	responseWriter.Header().Set("Content-Type", "application/json")
	responseWriter.Write(marshalJson)
}

func decodeBody(reader io.Reader) (*model.RequestPost, error) {
	decoder := json.NewDecoder(reader)
	decoder.DisallowUnknownFields()
	var requestPost model.RequestPost
	if err := decoder.Decode(&requestPost); err != nil {
		return nil, err
	}
	return &requestPost, nil
}

// Create a temporary file within our post-content directory that follows a
// particular naming pattern
func createFile(err error) *os.File {
	now := time.Now()
	nanos := now.UnixNano()
	millis := nanos / 1000000

	tempFile, err := ioutil.TempFile("post-content", "post-*-"+strconv.FormatInt(millis, 10)+".png")
	if err != nil {
		fmt.Println(err)
	}
	return tempFile
}


func printUploadedFile(handler *multipart.FileHeader) {
	fmt.Printf("Uploaded File: %+v\n", handler.Filename)
	fmt.Printf("File Size: %+v\n", handler.Size)
	fmt.Printf("MIME Header: %+v\n", handler.Header)
}