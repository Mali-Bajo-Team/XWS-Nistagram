package main

import (
	"content_service/model"
	"encoding/json"
	"io"
	"net/http"
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
