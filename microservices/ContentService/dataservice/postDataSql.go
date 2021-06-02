package dataservice

import (
	"content_service/model"
	"fmt"
	"gorm.io/driver/postgres"
	"gorm.io/gorm"
)

type PostStore struct {
	database *gorm.DB
}

func New() (*PostStore, error) {
	postStoreRef := &PostStore{}

	// TODO: Add this to some config file

	host := "localhost"
	port :=  "5432"
	user := "postgres"
	password := "root"
	dbname := "test"

	connectionString := fmt.Sprintf("host=%s user=%s password=%s dbname=%s port=%s sslmode=disable TimeZone=Asia/Shanghai", host, user, password, dbname, port)
	db, err := gorm.Open(postgres.Open(connectionString), &gorm.Config{})
	if err != nil {
		return nil, err
	}
	postStoreRef.database = db
	postStoreRef.database.AutoMigrate(&model.Post{}, &model.Tag{})

	return postStoreRef, nil
}

func (postStoreRef *PostStore) CreatePost(title string, text string, tags []string) int {
	post := model.Post{
		Text:  text,
		Title: title}

	newTags := []model.Tag{}
	for _, tag := range tags {
		newTags = append(newTags, model.Tag{Name: tag})
	}
	post.Tags = newTags

	postStoreRef.database.Create(&post)

	return int(post.ID)
}

func (postStoreRef *PostStore) GetPost(id int) (model.Post, error) {
	var post model.Post
	result := postStoreRef.database.Preload("Tags").Find(&post, id)

	if result.RowsAffected > 0 {
		return post, nil
	}

	return model.Post{}, fmt.Errorf("post with id=%d not found", id)
}

func (postStoreRef *PostStore) DeletePost(id int) error {
	result := postStoreRef.database.Delete(&model.Post{}, id)
	if result.RowsAffected > 0 {
		return nil
	}

	return fmt.Errorf("post with id=%d not found", id)
}

// GetAllPosts GetAllTasks returns all the tasks in the store, in arbitrary order.
func (postStoreRef *PostStore) GetAllPosts() []model.Post {
	var posts []model.Post
	postStoreRef.database.Preload("Tags").Find(&posts)

	return posts
}

func (postStoreRef *PostStore) Close() error {
	db, err := postStoreRef.database.DB()
	if err != nil {
		return err
	}

	db.Close()
	return nil
}
