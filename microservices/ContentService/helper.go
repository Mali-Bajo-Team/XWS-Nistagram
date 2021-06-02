package main

import (
	"encoding/json"
	"io"
	"net/http"
)

// renderJSON renders 'v' as JSON and writes it as a response into w.
func renderJSON(w http.ResponseWriter, v interface{}) {
	js, err := json.Marshal(v)
	if err != nil {
		http.Error(w, err.Error(), http.StatusInternalServerError)
		return
	}
	w.Header().Set("Content-Type", "application/json")
	w.Write(js)
}

func decodeBody(r io.Reader) (*RequestPost, error) {
	dec := json.NewDecoder(r)
	dec.DisallowUnknownFields()
	var rt RequestPost
	if err := dec.Decode(&rt); err != nil {
		return nil, err
	}
	return &rt, nil
}
