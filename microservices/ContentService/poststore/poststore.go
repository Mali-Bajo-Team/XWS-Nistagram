package poststore

import (
	"fmt"
	"gorm.io/driver/postgres"
	"gorm.io/gorm"
)

type PostStore struct {
	database *gorm.DB
}

func New() (*PostStore, error) {
	ts := &PostStore{}

	// TODO: Add this to some config file

	host := "localhost"
	dbport :=  "5432"
	user := "postgres"
	password := "root"
	dbname := "test"

	dsn := fmt.Sprintf("host=%s user=%s password=%s dbname=%s port=%s sslmode=disable TimeZone=Asia/Shanghai", host, user, password, dbname, dbport)
	db, err := gorm.Open(postgres.Open(dsn), &gorm.Config{})
	if err != nil {
		return nil, err
	}
	ts.database = db
	ts.database.AutoMigrate(&Post{}, &Tag{})

	return ts, nil
}

func (postStoreRef *PostStore) CreatePost(title string, text string, tags []string) int {
	post := Post{
		Text:  text,
		Title: title}

	newTags := []Tag{}
	for _, tag := range tags {
		newTags = append(newTags, Tag{Name: tag})
	}
	post.Tags = newTags

	postStoreRef.database.Create(&post)

	return int(post.ID)
}

func (postStoreRef *PostStore) GetPost(id int) (Post, error) {
	var post Post
	result := postStoreRef.database.Preload("Tags").Find(&post, id)

	if result.RowsAffected > 0 {
		return post, nil
	}

	return Post{}, fmt.Errorf("post with id=%d not found", id)
}

func (postStoreRef *PostStore) DeletePost(id int) error {
	result := postStoreRef.database.Delete(&Post{}, id)
	if result.RowsAffected > 0 {
		return nil
	}

	return fmt.Errorf("post with id=%d not found", id)
}

// GetAllPosts GetAllTasks returns all the tasks in the store, in arbitrary order.
func (postStoreRef *PostStore) GetAllPosts() []Post {
	var posts []Post
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
