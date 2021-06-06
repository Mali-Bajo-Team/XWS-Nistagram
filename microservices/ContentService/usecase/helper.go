package usecase

import (
	"content_service/model"
	"encoding/json"
	"math/rand"
	"mime/multipart"
	"net/http"
	"strconv"
	"strings"
	"time"
)

// renderJSON renders 'v' as JSON and writes it as a response into w.
func renderJSON(responseWriter http.ResponseWriter, model interface{}) {
	marshalJson, err := json.Marshal(model)
	if err != nil {
		http.Error(responseWriter, err.Error(), http.StatusInternalServerError)
		return
	}
	responseWriter.Header().Set("Content-Type", "application/json")
	responseWriter.Write(marshalJson)
}

func initializeContent(filePath string, hdr *multipart.FileHeader) model.Content {
	var content model.Content
	content.Path = filePath
	if isVideoContent(hdr) {
		content.Type = "video"
	} else {
		content.Type = "image"
	}
	return content
}

func createUniqueName() string {
	now := time.Now()
	nanos := now.UnixNano()
	millis := nanos / 1000000

	rand.Seed(time.Now().UTC().UnixNano())

	return randomString(12) + "_" + strconv.FormatInt(millis, 10)
}

func isVideoContent(hdr *multipart.FileHeader) bool {
	var contentType = hdr.Header.Get("Content-Type")
	if strings.Contains(contentType, "video") {
		return true
	}
	return false
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