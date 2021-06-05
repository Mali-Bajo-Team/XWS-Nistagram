package usecase

import (
	"content_service/model"
	"encoding/json"
	"fmt"
	"io"
	"math/rand"
	"mime/multipart"
	"net/http"
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

func createUniqueName() string {
	now := time.Now()
	nanos := now.UnixNano()
	millis := nanos / 1000000

	rand.Seed(time.Now().UTC().UnixNano())

	return randomString(12) + "_" + strconv.FormatInt(millis, 10)
}

func printUploadedFile(handler *multipart.FileHeader) {
	fmt.Printf("Uploaded File: %+v\n", handler.Filename)
	fmt.Printf("File Size: %+v\n", handler.Size)
	fmt.Printf("MIME Header: %+v\n", handler.Header)
}

func randomString(len int) string {

	bytes := make([]byte, len)

	for i := 0; i < len; i++ {
		bytes[i] = byte(randInt(97, 122))
	}

	return string(bytes)
}

func randInt(min int, max int) int {

	return min + rand.Intn(max-min)
}