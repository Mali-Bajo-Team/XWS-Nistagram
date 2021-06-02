package poststore

import (
	"fmt"
	"gorm.io/driver/postgres"
	"gorm.io/gorm"
	//"os"
)

type PostStore struct {
	db *gorm.DB
}

func New() (*PostStore, error) {
	ts := &PostStore{}

	//host := os.Getenv("DBHOST")
	//user := os.Getenv("USER")
	//password := os.Getenv("PASSWORD")
	//dbname := os.Getenv("DBNAME")
	//dbport := os.Getenv("DBPORT")

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
	ts.db = db
	ts.db.AutoMigrate(&Post{}, &Tag{})

	return ts, nil
}

func (ts *PostStore) CreatePost(title string, text string, tags []string) int {
	post := Post{
		Text:  text,
		Title: title}

	newTags := []Tag{}
	for _, tag := range tags {
		newTags = append(newTags, Tag{Name: tag})
	}
	post.Tags = newTags

	ts.db.Create(&post)

	return int(post.ID)
}

func (ts *PostStore) GetPost(id int) (Post, error) {
	var post Post
	result := ts.db.Preload("Tags").Find(&post, id)

	if result.RowsAffected > 0 {
		return post, nil
	}

	return Post{}, fmt.Errorf("post with id=%d not found", id)
}

func (ts *PostStore) DeletePost(id int) error {
	result := ts.db.Delete(&Post{}, id)
	if result.RowsAffected > 0 {
		return nil
	}

	return fmt.Errorf("post with id=%d not found", id)
}

// GetAllPosts GetAllTasks returns all the tasks in the store, in arbitrary order.
func (ts *PostStore) GetAllPosts() []Post {
	var posts []Post
	ts.db.Preload("Tags").Find(&posts)

	return posts
}

func (ts *PostStore) Close() error {
	db, err := ts.db.DB()
	if err != nil {
		return err
	}

	db.Close()
	return nil
}
