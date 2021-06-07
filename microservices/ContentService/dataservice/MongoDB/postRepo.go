package MongoDB

import (
	"content_service/model"
	"context"
	"go.mongodb.org/mongo-driver/bson"
	"go.mongodb.org/mongo-driver/bson/primitive"
	"go.mongodb.org/mongo-driver/mongo"
	"go.mongodb.org/mongo-driver/mongo/options"
	"log"
	"os"
	"time"
)

type PostStore struct {
	ourClient *mongo.Client
}

func NewPostStore() (*PostStore, error) {
	postStoreRef := &PostStore{}

	mongoPath, present := os.LookupEnv("MONGO_PATH")
	if !present {
		mongoPath = "mongodb://localhost:27017"
	}

	log.Println(mongoPath)

	ctx, _ := context.WithTimeout(context.Background(), 10*time.Second)
	clientOptions := options.Client().ApplyURI(mongoPath)
	client, err := mongo.Connect(ctx, clientOptions)
	postStoreRef.ourClient = client
	if err != nil {
		log.Println(err)
		return nil, err
	}

	return postStoreRef, nil
}

// TODO: Return models instead result's when you get a time :), like GetUserStoryHighlights ret value

func (postStoreRef *PostStore) CreatePost(post model.RegularPost) *mongo.InsertOneResult {
	collectionPosts := postStoreRef.ourClient.Database("content-service-db").Collection("posts")
	ctx, _ := context.WithTimeout(context.Background(), 10*time.Second)
	result, _ := collectionPosts.InsertOne(ctx, post)
	return result
}

func (postStoreRef *PostStore) CreateStory(story model.Story) *mongo.InsertOneResult {
	collectionStories := postStoreRef.ourClient.Database("content-service-db").Collection("stories")
	ctx, _ := context.WithTimeout(context.Background(), 10*time.Second)
	result, _ := collectionStories.InsertOne(ctx, story)
	return result
}

func (postStoreRef *PostStore) UpdatePostComments(comment model.Comment, postID string) *mongo.UpdateResult {
	collectionPosts := postStoreRef.ourClient.Database("content-service-db").Collection("posts")
	// convert id string to ObjectId
	objectId, err := primitive.ObjectIDFromHex(postID)
	if err != nil {
		log.Println("Invalid id")
	}

	var postComments []model.Comment
	postComments = append(postComments, comment)

	update := bson.M{
		"$addToSet": bson.M{
			"comments": bson.M{"$each": postComments},
		},
	}

	result, err := collectionPosts.UpdateOne(
		context.Background(),
		bson.M{"_id": objectId},
		update,
	)
	if err != nil {
		log.Fatal(err)
	}

	return result
}

func (postStoreRef *PostStore) CreatePostReaction(reaction model.Reaction, postID string) *mongo.UpdateResult {
	collectionPosts := postStoreRef.ourClient.Database("content-service-db").Collection("posts")
	// convert id string to ObjectId
	objectId, err := primitive.ObjectIDFromHex(postID)
	if err != nil {
		log.Println("Invalid id")
	}

	var postReactions []model.Reaction
	postReactions = append(postReactions, reaction)

	update := bson.M{
		"$addToSet": bson.M{
			"reactions": bson.M{"$each": postReactions},
		},
	}

	result, err := collectionPosts.UpdateOne(
		context.Background(),
		bson.M{"_id": objectId},
		update,
	)
	if err != nil {
		log.Fatal(err)
	}

	return result
}

func (postStoreRef *PostStore) CreateStoryHighlight(storyHighlight *model.StoryHighlight, userID string) *mongo.UpdateResult {
	collectionUsers := postStoreRef.ourClient.Database("content-service-db").Collection("users")
	// convert id string to ObjectId
	objectId, err := primitive.ObjectIDFromHex(userID)
	if err != nil {
		log.Println("Invalid id")
	}

	storyHighlight.ID =  primitive.NewObjectID()

	var storyHighlights []model.StoryHighlight
	storyHighlights = append(storyHighlights, *storyHighlight)

	update := bson.M{
		"$addToSet": bson.M{
			"story_highlights": bson.M{"$each": storyHighlights},
		},
	}

	result, err := collectionUsers.UpdateOne(
		context.Background(),
		bson.M{"_id": objectId},
		update,
	)
	if err != nil {
		log.Fatal(err)
	}

	return result
}

func (postStoreRef *PostStore) CreateCollection(collection *model.Collection, userID string) *mongo.UpdateResult {
	collectionUsers := postStoreRef.ourClient.Database("content-service-db").Collection("users")
	// convert id string to ObjectId
	objectId, err := primitive.ObjectIDFromHex(userID)
	if err != nil {
		log.Println("Invalid id")
	}

	collection.ID =  primitive.NewObjectID()

	var collections []model.Collection
	collections = append(collections, *collection)

	update := bson.M{
		"$addToSet": bson.M{
			"collections": bson.M{"$each": collections},
		},
	}

	result, err := collectionUsers.UpdateOne(
		context.Background(),
		bson.M{"_id": objectId},
		update,
	)
	if err != nil {
		log.Fatal(err)
	}

	return result
}

func (postStoreRef *PostStore) AddPostToSaved(regularPost model.RegularPost, userID string) *mongo.UpdateResult {
	collectionUsers := postStoreRef.ourClient.Database("content-service-db").Collection("users")
	// convert id string to ObjectId
	objectId, err := primitive.ObjectIDFromHex(userID)
	if err != nil {
		log.Println("Invalid id")
	}

	var regularPosts []model.RegularPost
	regularPosts = append(regularPosts, regularPost)

	update := bson.M{
		"$addToSet": bson.M{
			"saved.regular_posts": bson.M{"$each": regularPosts},
		},
	}

	result, err := collectionUsers.UpdateOne(
		context.Background(),
		bson.M{"_id": objectId},
		update,
	)
	if err != nil {
		log.Fatal(err)
	}

	return result
}

func (postStoreRef *PostStore) GetSavedPosts(userID string) []model.RegularPost {
	collectionUsers := postStoreRef.ourClient.Database("content-service-db").Collection("users")
	objectId, err := primitive.ObjectIDFromHex(userID)
	if err != nil {
		log.Println("Invalid id")
	}

	result := collectionUsers.FindOne(
		context.Background(),
		bson.M{"_id": objectId},
	)

	var userTemp model.User
	result.Decode(&userTemp)

	return userTemp.Saved.RegularPosts
}

func (postStoreRef *PostStore) GetUserStoryHighlights(userID string) []model.StoryHighlight {
	collectionUsers := postStoreRef.ourClient.Database("content-service-db").Collection("users")
	// convert id string to ObjectId
	objectId, err := primitive.ObjectIDFromHex(userID)
	if err != nil {
		log.Println("Invalid id")
	}

	result := collectionUsers.FindOne(
		context.Background(),
		bson.M{"_id": objectId},
	)
	if err != nil {
		log.Fatal(err)
	}

	var userTemp model.User
	result.Decode(&userTemp)

	return userTemp.StoryHighlights
}

func (postStoreRef *PostStore) AddStoryContentOnStoryHighlight(story model.Story, userID string, highlightID string) *mongo.InsertOneResult {
	collectionUsers := postStoreRef.ourClient.Database("content-service-db").Collection("users")
	// convert id string to ObjectId
	objectUserID, err := primitive.ObjectIDFromHex(userID)
	if err != nil {
		log.Println("Invalid id")
	}

	objectHighlightID, err := primitive.ObjectIDFromHex(highlightID)
	if err != nil {
		log.Println("Invalid id")
	}

	result := collectionUsers.FindOne(
		context.Background(),
		bson.M{"_id": objectUserID},
	)
	var userTemp model.User
	result.Decode(&userTemp)

	_, _ = collectionUsers.DeleteOne(
		context.Background(),
		bson.M{"_id": objectUserID},
	)
	for idx, highlight := range userTemp.StoryHighlights {
		if highlight.ID == objectHighlightID {
			var storyHighlightContent model.StoryHighlightContent
			storyHighlightContent.StoryID = story.ID.Hex()
			storyHighlightContent.IsPrivateStory = story.IsForCloseFriends
			storyHighlightContent.Content = story.MyPost.Content
			highlight.Content = append(highlight.Content, storyHighlightContent)
			userTemp.StoryHighlights[idx].Content = append(userTemp.StoryHighlights[idx].Content, storyHighlightContent)
			break
		}
	}

	retVal, _ := collectionUsers.InsertOne(context.Background(), userTemp)
	return retVal
}

func (postStoreRef *PostStore) DeletePostReaction(reaction model.Reaction, postID string) *mongo.UpdateResult {
	collectionPosts := postStoreRef.ourClient.Database("content-service-db").Collection("posts")
	// convert id string to ObjectId
	objectId, err := primitive.ObjectIDFromHex(postID)
	if err != nil {
		log.Println("Invalid id")
	}

	var postReactions []model.Reaction
	postReactions = append(postReactions, reaction)
	//reactionCreatorID,_:= primitive.ObjectIDFromHex(reaction.ReactionCreatorRef)

	update := bson.M{
		"$pull": bson.M{
			"reactions": bson.M{"$in": postReactions},
		},

	}

	result, err := collectionPosts.UpdateOne(
		context.Background(),
		bson.M{"_id": objectId},
		update,
	)
	if err != nil {
		log.Fatal(err)
	}

	return result
}

func (postStoreRef *PostStore) GetPostByID(postID string) *mongo.SingleResult {
	collectionPosts := postStoreRef.ourClient.Database("content-service-db").Collection("posts")
	// convert id string to ObjectId
	objectId, err := primitive.ObjectIDFromHex(postID)
	if err != nil {
		log.Println("Invalid id")
	}

	result := collectionPosts.FindOne(
		context.Background(),
		bson.M{"_id": objectId},
	)
	if err != nil {
		log.Fatal(err)
	}

	return result
}

func (postStoreRef *PostStore) UpdateUserStories(story model.Story) *mongo.UpdateResult {
	collectionUsers := postStoreRef.ourClient.Database("content-service-db").Collection("users")
	// convert id string to ObjectId
	objectId, err := primitive.ObjectIDFromHex(story.MyPost.PostCreatorRef)
	if err != nil {
		log.Println("Invalid id")
	}

	var userStories []model.UserStory
	var userStory model.UserStory
	userStory.StoryID = story.ID
	userStory.FirstContent = story.MyPost.Content[0]
	userStories = append(userStories, userStory)

	update := bson.M{
		"$addToSet": bson.M{
			"stories": bson.M{"$each": userStories},
		},
	}

	result, err := collectionUsers.UpdateOne(
		context.Background(),
		bson.M{"_id": objectId},
		update,
	)
	if err != nil {
		log.Fatal(err)
	}

	return result
}

func (postStoreRef *PostStore) CloseConnection() error {
	return postStoreRef.ourClient.Disconnect(context.Background())
}
