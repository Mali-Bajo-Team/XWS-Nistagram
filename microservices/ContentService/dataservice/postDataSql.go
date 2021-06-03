package dataservice

import (
	"content_service/model"
	"fmt"
	"gorm.io/driver/postgres"
	"gorm.io/gorm"
)

type SqlPostStore struct {
	database *gorm.DB
}

func NewSqlPostStore() (*SqlPostStore, error) {
	postStoreRef := &SqlPostStore{}

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
	postStoreRef.database.AutoMigrate(&model.OldPost{}, &model.Tag{})

	return postStoreRef, nil
}

func (postStoreRef *SqlPostStore) CreatePost(title string, text string, tags []string) int {
	post := model.OldPost{
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

func (postStoreRef *SqlPostStore) GetPost(id int) (model.OldPost, error) {
	var post model.OldPost
	result := postStoreRef.database.Preload("Tags").Find(&post, id)

	if result.RowsAffected > 0 {
		return post, nil
	}

	return model.OldPost{}, fmt.Errorf("post with id=%d not found", id)
}

func (postStoreRef *SqlPostStore) DeletePost(id int) error {
	result := postStoreRef.database.Delete(&model.OldPost{}, id)
	if result.RowsAffected > 0 {
		return nil
	}

	return fmt.Errorf("post with id=%d not found", id)
}

// GetAllPosts GetAllTasks returns all the tasks in the store, in arbitrary order.
func (postStoreRef *SqlPostStore) GetAllPosts() []model.OldPost {
	var posts []model.OldPost
	postStoreRef.database.Preload("Tags").Find(&posts)

	return posts
}

func (postStoreRef *SqlPostStore) Close() error {
	db, err := postStoreRef.database.DB()
	if err != nil {
		return err
	}

	db.Close()
	return nil
}
