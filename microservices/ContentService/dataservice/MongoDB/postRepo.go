package MongoDB

import (
	"content_service/model"
	"context"
	"log"
	"os"
	"time"

	"go.mongodb.org/mongo-driver/bson"
	"go.mongodb.org/mongo-driver/bson/primitive"
	"go.mongodb.org/mongo-driver/mongo"
	"go.mongodb.org/mongo-driver/mongo/options"
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

func (postStoreRef *PostStore) GetEntirePost(postID string) model.RegularPost {
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

	var post model.RegularPost
	result.Decode(&post)

	return post
}

func (postStoreRef *PostStore) GetAllInappropriatePosts() []model.InappropriatePost {
	collectionInappropriatePosts := postStoreRef.ourClient.Database("content-service-db").Collection("inappropriatePosts")

	cursor, err := collectionInappropriatePosts.Find(
		context.Background(),
		bson.M{},
	)

	if err != nil {
		log.Fatal(err)
	}
	defer cursor.Next(context.Background())
	var inappropriatePosts []model.InappropriatePost
	for cursor.Next(context.Background()) {
		var inappropriatePost model.InappropriatePost
		if err = cursor.Decode(&inappropriatePost); err != nil {
			log.Fatal(err)
		}
		inappropriatePosts = append(inappropriatePosts, inappropriatePost)
	}

	return inappropriatePosts
}
func (postStoreRef *PostStore) GetAllInappropriateStories() []model.InappropriateStory {
	collectionInappropriateStories := postStoreRef.ourClient.Database("content-service-db").Collection("inappropriateStories")

	cursor, err := collectionInappropriateStories.Find(
		context.Background(),
		bson.M{},
	)

	if err != nil {
		log.Fatal(err)
	}
	defer cursor.Next(context.Background())
	var inappropriateStories []model.InappropriateStory
	for cursor.Next(context.Background()) {
		var inappropriateStory model.InappropriateStory
		if err = cursor.Decode(&inappropriateStory); err != nil {
			log.Fatal(err)
		}
		inappropriateStories = append(inappropriateStories, inappropriateStory)
	}

	return inappropriateStories
}

func (postStoreRef *PostStore) CreateInappropriatePost(inappropriatePost model.InappropriatePost) *mongo.InsertOneResult {
	// Todo: Add time stamp creation
	collectionInappropriatePosts := postStoreRef.ourClient.Database("content-service-db").Collection("inappropriatePosts")
	ctx, _ := context.WithTimeout(context.Background(), 10*time.Second)
	result, _ := collectionInappropriatePosts.InsertOne(ctx, inappropriatePost)
	return result
}

func (postStoreRef *PostStore) CreateInappropriateStory(inappropriateStory model.InappropriateStory) *mongo.InsertOneResult {
	// Todo: Add time stamp creation
	collectionInappropriateStories := postStoreRef.ourClient.Database("content-service-db").Collection("inappropriateStories")
	ctx, _ := context.WithTimeout(context.Background(), 10*time.Second)
	result, _ := collectionInappropriateStories.InsertOne(ctx, inappropriateStory)
	return result
}

func (postStoreRef *PostStore) GetEntireStory(storyID string) model.Story {
	collectionStories := postStoreRef.ourClient.Database("content-service-db").Collection("stories")
	// convert id string to ObjectId
	objectId, err := primitive.ObjectIDFromHex(storyID)
	if err != nil {
		log.Println("Invalid id")
	}

	result := collectionStories.FindOne(
		context.Background(),
		bson.M{"_id": objectId},
	)

	var story model.Story
	result.Decode(&story)

	return story
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

func (postStoreRef *PostStore) CreatePostReaction(reaction model.Reaction) *mongo.UpdateResult {
	collectionPosts := postStoreRef.ourClient.Database("content-service-db").Collection("posts")
	collectionReactions := postStoreRef.ourClient.Database("content-service-db").Collection("reactions")

	collectionReactions.InsertOne(context.Background(), reaction)

	var post model.RegularPost
	postStoreRef.GetPostByID(reaction.PostID).Decode(&post)
	newLikeCount := post.LikeCount
	newDislikeCount := post.DislikeCount

	if reaction.ReactionType == "like" {
		newLikeCount = newLikeCount + 1
	}
	if reaction.ReactionType == "dislike" {
		newDislikeCount = newDislikeCount + 1
	}

	update := bson.M{
		"$set": bson.M{
			"like_count":    newLikeCount,
			"dislike_count": newDislikeCount,
		},
	}

	result, err := collectionPosts.UpdateOne(
		context.Background(),
		bson.M{"_id": post.ID},
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

	storyHighlight.ID = primitive.NewObjectID()

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

	collection.ID = primitive.NewObjectID()

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

func (postStoreRef *PostStore) GetUserCollections(userID string) []model.Collection {
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

	return userTemp.Collections
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

func (postStoreRef *PostStore) AddPostToCollection(post model.RegularPost, userID string, collectionID string) *mongo.InsertOneResult {
	// TODO: Improve this with more sophisticated way
	collectionUsers := postStoreRef.ourClient.Database("content-service-db").Collection("users")
	// convert id string to ObjectId
	objectUserID, err := primitive.ObjectIDFromHex(userID)
	if err != nil {
		log.Println("Invalid id")
	}

	objectCollectionID, err := primitive.ObjectIDFromHex(collectionID)
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
	for idx, collection := range userTemp.Collections {
		if collection.ID == objectCollectionID {
			userTemp.Collections[idx].RegularPosts = append(userTemp.Collections[idx].RegularPosts, post)
			break
		}
	}

	retVal, _ := collectionUsers.InsertOne(context.Background(), userTemp)
	return retVal
}

func (postStoreRef *PostStore) GetPostReaction(username string, postID string) *model.Reaction {
	collectionReactions := postStoreRef.ourClient.Database("content-service-db").Collection("reactions")

	result := collectionReactions.FindOne(
		context.Background(),
		bson.M{"creator_username": username, "post_id": postID},
	)

	if result.Err() == mongo.ErrNoDocuments {
		return nil
	}

	var reaction model.Reaction
	result.Decode(&reaction)

	return &reaction
}

func (postStoreRef *PostStore) DeletePostReaction(username string, postID string) *mongo.UpdateResult {
	collectionPosts := postStoreRef.ourClient.Database("content-service-db").Collection("posts")
	collectionReactions := postStoreRef.ourClient.Database("content-service-db").Collection("reactions")

	deleteResult := collectionReactions.FindOneAndDelete(
		context.Background(),
		bson.M{"creator_username": username, "post_id": postID},
	)

	var reaction model.Reaction
	deleteResult.Decode(&reaction)

	var post model.RegularPost
	postStoreRef.GetPostByID(postID).Decode(&post)
	newLikeCount := post.LikeCount
	newDislikeCount := post.DislikeCount

	if reaction.ReactionType == "like" {
		newLikeCount = newLikeCount - 1
	}
	if reaction.ReactionType == "dislike" {
		newDislikeCount = newDislikeCount - 1
	}

	update := bson.M{
		"$set": bson.M{
			"like_count":    newLikeCount,
			"dislike_count": newDislikeCount,
		},
	}

	result, err := collectionPosts.UpdateOne(
		context.Background(),
		bson.M{"_id": post.ID},
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

	var userStories []model.UserStory
	var userStory model.UserStory
	userStory.StoryID = story.ID
	userStory.FirstContent = story.MyPost.Content[0]
	userStories = append(userStories, userStory)

	update := bson.M{
		"$push": bson.M{
			"stories": bson.M{"$each": userStories, "$position": 0},
		},
	}

	result, err := collectionUsers.UpdateOne(
		context.Background(),
		bson.M{"username": story.MyPost.CreatorUsername},
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

func (postStoreRef *PostStore) GetPostsByLocation(longitude float64, latitude float64) []model.RegularPost {
	collectionPosts := postStoreRef.ourClient.Database("content-service-db").Collection("posts")
	var result []model.RegularPost

	mod := mongo.IndexModel{
		Keys: bson.M{
			"my_post.post_location": "2dsphere",
		}, Options: nil,
	}

	collectionPosts.Indexes().CreateOne(context.Background(), mod)

	cursor, err := collectionPosts.Find(
		context.Background(),
		bson.M{
			"my_post.post_location": bson.M{
				"$nearSphere": bson.M{
					"$geometry": bson.M{
						"type":        "Point",
						"coordinates": []float64{longitude, latitude},
					},
					"$maxDistance": 3000,
				},
			},
		})
	if err != nil {
		log.Fatal(err)
	}

	if err = cursor.All(context.Background(), &result); err != nil {
		log.Fatal(err)
	}

	return result
}

func (postStoreRef *PostStore) GetPostsByHashtag(hashtag string) []model.RegularPost {
	collectionPosts := postStoreRef.ourClient.Database("content-service-db").Collection("posts")
	var result []model.RegularPost

	cursor, err := collectionPosts.Find(
		context.Background(),
		bson.M{"my_post.hashtags": hashtag},
	)
	if err != nil {
		log.Fatal(err)
	}

	if err = cursor.All(context.Background(), &result); err != nil {
		log.Fatal(err)
	}

	return result
}

func (postStoreRef *PostStore) GetReactions(username string, reactionType string) []model.Reaction {
	collectionReactions := postStoreRef.ourClient.Database("content-service-db").Collection("reactions")
	var result []model.Reaction

	queryOptions := options.Find()
	queryOptions.SetSort(bson.D{{"timestamp", -1}})

	cursor, err := collectionReactions.Find(
		context.Background(),
		bson.M{"creator_username": username, "reaction_type": reactionType},
		queryOptions,
	)
	if err != nil {
		log.Fatal(err)
	}

	if err = cursor.All(context.Background(), &result); err != nil {
		log.Fatal(err)
	}

	return result
}

func (postStoreRef *PostStore) GetPostsByCreators(usernames []string, page int64) []model.RegularPost {
	collectionPosts := postStoreRef.ourClient.Database("content-service-db").Collection("posts")
	var result []model.RegularPost

	queryOptions := options.Find()
	queryOptions.SetSort(bson.D{{"my_post.timestamp", -1}})
	queryOptions.SetLimit(10)
	queryOptions.SetSkip(10 * page)

	cursor, err := collectionPosts.Find(
		context.Background(),
		bson.M{"my_post.creator_username": bson.M{"$in": usernames}},
		queryOptions,
	)
	if err != nil {
		log.Fatal(err)
	}

	if err = cursor.All(context.Background(), &result); err != nil {
		log.Fatal(err)
	}

	return result
}

func (postStoreRef *PostStore) GetStoriesByCreators(usernames []string, page int64) []model.Story {
	collectionPosts := postStoreRef.ourClient.Database("content-service-db").Collection("stories")
	var result []model.Story

	queryOptions := options.Find()
	queryOptions.SetSort(bson.D{{"my_post.timestamp", -1}})
	queryOptions.SetLimit(10)
	queryOptions.SetSkip(10 * page)

	diff := 24 * time.Hour
	then := time.Now().Add(-diff)

	cursor, err := collectionPosts.Find(
		context.Background(),
		bson.M{"my_post.creator_username": bson.M{"$in": usernames},
			"my_post.timestamp": bson.M{"$gte": then}},
		queryOptions,
	)
	if err != nil {
		log.Fatal(err)
	}

	if err = cursor.All(context.Background(), &result); err != nil {
		log.Fatal(err)
	}

	return result
}
