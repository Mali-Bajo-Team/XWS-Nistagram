<template>
  <v-row class="pa-5">
    <v-spacer></v-spacer>

    <v-col sm="8" md="12" lg="4" cols="12">
      <!-- Images, number of posts, followers, following -->
      <v-row>
        <!--First column-->
        <v-col sm="4" md="4" lg="4" cols="4">
          <!--Profile photo-->
          <v-img
            class="rounded-circle"
            aspect-ratio="1.1"
            :src="user.profileImagePath"
          >
          </v-img>

          <!--Username and description-->
          <h3 class="text-justify">{{ user.username }}</h3>
          <div class="font-weight-medium text-justify">
            {{ user.bio }}
          </div>
        </v-col>
        <!--End of the first column-->

        <!--Column for the number of posts-->
        <v-col class="pa-4 mt-2">
          <h3>{{ posts.length }}</h3>
          Posts
        </v-col>
        <!--End of the number of posts-->

        <!--Column for the number of followers-->
        <v-col class="pa-4 mt-2">
          <h3>{{ user.followerCount }}</h3>
          Followers
        </v-col>
        <!--End of the number of followers-->

        <!--Column for the number of following-->
        <v-col class="pa-4 mt-2">
          <h3>{{ user.followedCount }}</h3>
          Following
        </v-col>
        <!--Column for the number of following-->
      </v-row>
      <!-- End of profile image, number of posts, followers, following -->

      <!-- Edit profile, add content -->
      <v-row>
        <!--Folllow button-->
        <v-col class="pa-3">
          <v-btn
            outlined
            rounded
            medium
            color="primary"
            v-if="!followed && loggedIn && !isBlocked"
            @click="follow"
          >
            <v-icon left> mdi-plus </v-icon>
            Follow
          </v-btn>
          <v-btn
            outlined
            rounded
            medium
            color="danger"
            v-if="followed && loggedIn && !isBlocked"
            @click="unfollow"
          >
            <v-icon left> mdi-plus </v-icon>
            Unfollow
          </v-btn>
          <v-btn
            class="ml-5"
            outlined
            rounded
            medium
            color="primary"
            v-if="loggedIn && !isBlocked"
            @click="block"
          >
            <v-icon left> mdi-alert </v-icon>
            Block
          </v-btn>

          <v-btn
            class="ml-5"
            outlined
            rounded
            medium
            color="primary"
            v-if="loggedIn && !isBlocked && followed && !isMuted"
            @click="mute"
          >
            <v-icon left> mdi-access-point-remove </v-icon>
            Mute
          </v-btn>

          <v-btn
            class="ml-5"
            outlined
            rounded
            medium
            color="primary"
            v-if="loggedIn && !isBlocked && followed && isMuted"
            @click="unmute"
          >
            <v-icon left> mdi-access-point-remove </v-icon>
            Unmute
          </v-btn>
        </v-col>
        <!--End of the column for edit profile button-->
      </v-row>

      <!-- Posts, stories, saved, tagged -->
      <v-row v-if="!user.isPrivate && !isBlocked">
        <v-tabs v-model="tabs2" icons-and-text background-color="transparent">
          <v-tabs-slider></v-tabs-slider>
          <v-tab>Posts<v-icon>mdi-camera</v-icon></v-tab>
          <v-tab>Stories<v-icon>mdi-camera-iris</v-icon></v-tab>
          <v-tab>Highlights<v-icon>mdi-star-circle-outline</v-icon></v-tab>
        </v-tabs>
        <v-tabs-items v-model="tabs2">
          <!--Tab for photos and videos-->
          <v-tab-item>
            <v-card class="mx-auto" max-width="500">
              <v-container fluid>
                <v-row dense>
                  <v-col v-for="post in posts" :key="post._id" cols="6">
                    <!-- Post previw -->
                    <v-card>
                      <v-img
                        v-if="post.type == 'image'"
                        :src="getImageUrl(post)"
                        class="white--text align-end"
                        gradient="to bottom, rgba(0,0,0,.1), rgba(0,0,0,.5)"
                        height="200px"
                      >
                      </v-img>
                      <video
                        v-if="post.type == 'video'"
                        controls
                        :src="getImageUrl(post)"
                        class="white--text align-end"
                        gradient="to bottom, rgba(0,0,0,.1), rgba(0,0,0,.5)"
                        height="200px"
                        width="100%"
                      ></video>
                      <v-card-actions>
                        <v-spacer></v-spacer>

                        <!--Dialog for post details-->
                        <v-dialog width="600px">
                          <template v-slot:activator="{ on, attrs }">
                            <v-btn
                              v-bind="attrs"
                              v-on="on"
                              @click="getEntirePost(post._id)"
                              icon
                            >
                              <v-icon right> mdi-plus-circle </v-icon>
                            </v-btn>
                          </template>
                          <postComponent
                            v-if="entirePost"
                            :post="entirePost"
                          ></postComponent>
                        </v-dialog>
                        <!--End of dialog for post details-->

                        <v-btn v-if="loggedIn" @click="addToSaved(post)" icon>
                          <v-icon>mdi-bookmark</v-icon>
                        </v-btn>
                      </v-card-actions>
                    </v-card>
                    <!-- End of the post previw -->
                  </v-col>
                </v-row>
              </v-container>
            </v-card>
          </v-tab-item>
          <!--End of tab for photos and videos-->

          <!--Tab for stories-->
          <v-tab-item>
            <v-card class="mx-auto" max-width="500">
              <v-container fluid>
                <v-row dense>
                  <v-col v-for="story in stories" :key="story.post_id" cols="6">
                    <!-- Story previw -->
                    <v-card>
                      <v-img
                        v-if="story.type == 'image'"
                        :src="getImageUrl(story)"
                        class="white--text align-end"
                        gradient="to bottom, rgba(0,0,0,.1), rgba(0,0,0,.5)"
                        height="200px"
                      >
                      </v-img>
                      <video
                        v-if="story.type == 'video'"
                        controls
                        :src="getImageUrl(post)"
                        class="white--text align-end"
                        gradient="to bottom, rgba(0,0,0,.1), rgba(0,0,0,.5)"
                        height="200px"
                        width="100%"
                      ></video>
                      <v-card-actions>
                        <v-spacer></v-spacer>

                        <!--Dialog for story details-->
                        <v-dialog width="600px">
                          <template v-slot:activator="{ on, attrs }">
                            <v-btn
                              v-bind="attrs"
                              v-on="on"
                              @click="getEntireStory(story._id)"
                              icon
                            >
                              <v-icon right> mdi-plus-circle </v-icon>
                            </v-btn>
                          </template>
                          <storyComponent
                            v-if="entireStory"
                            :post="entireStory"
                          ></storyComponent>
                        </v-dialog>
                        <!--End of dialog for story details-->
                      </v-card-actions>
                    </v-card>
                    <!-- End of the story previw -->
                  </v-col>
                </v-row>
              </v-container>
            </v-card>
          </v-tab-item>
          <!--End of tab for stories-->

          <!--Tab for highlights-->
          <v-tab-item>
            <v-card class="mx-auto" max-width="500">
              <v-container fluid>
                <v-row dense>
                  <v-col>
                    <!-- Image previw -->
                    <v-card>
                      <v-img
                        src="https://picsum.photos/350/165?random"
                        class="white--text align-end"
                        gradient="to bottom, rgba(0,0,0,.1), rgba(0,0,0,.5)"
                        height="300px"
                      >
                      </v-img>
                      <v-card-text>
                        <v-row>
                          <v-col>
                            <h3>Perfect holiday</h3>
                          </v-col>
                          <v-col class="text-right mr-5 mb-5">
                            <v-dialog width="600px">
                              <!--Button for showing highlights-->
                              <template v-slot:activator="{ on, attrs }">
                                <v-btn
                                  dark
                                  x-small
                                  fab
                                  color="primary"
                                  v-bind="attrs"
                                  v-on="on"
                                >
                                  <v-icon dark> mdi-chevron-down </v-icon>
                                </v-btn>
                              </template>
                              <!--Card for highlights-->
                              <v-card>
                                <v-card-title>
                                  <v-row>
                                    <v-col> My perfect holiday </v-col>
                                    <v-col class="text-right"> </v-col>
                                  </v-row>
                                </v-card-title>
                                <!--List of photos-->
                                <v-carousel
                                  cycle
                                  height="400"
                                  hide-delimiter-background
                                  show-arrows-on-hover
                                >
                                  <v-carousel-item
                                    v-for="(slide, i) in slides"
                                    :key="i"
                                  >
                                    <v-sheet :color="colors[i]" height="100%">
                                      <v-row
                                        class="fill-height"
                                        align="center"
                                        justify="center"
                                      >
                                        <div class="text-h2">
                                          {{ slide }} Slide
                                        </div>
                                      </v-row>
                                    </v-sheet>
                                  </v-carousel-item>
                                </v-carousel>
                                <!--End of list of photos-->
                              </v-card>
                              <!--End for card for highlights-->
                            </v-dialog>
                          </v-col>
                        </v-row>
                      </v-card-text>
                    </v-card>
                  </v-col>
                </v-row>
              </v-container>
            </v-card>
          </v-tab-item>
          <!--End of tab for highlights-->
        </v-tabs-items>
      </v-row>
      <!-- End of posts, stories, saved, tagged -->
    </v-col>

    <v-spacer></v-spacer>

    <v-snackbar v-model="snackbar" :timeout="2000" bottom class="mb-5" right>
      {{ snackbarText }}

      <template v-slot:action="{ attrs }">
        <v-btn color="primary" text v-bind="attrs" @click="snackbar = false">
          Close
        </v-btn>
      </template>
    </v-snackbar>
  </v-row>
</template>

<script>
import { getToken, getUsernameFromToken } from "./../../../util/token";
import postComponent from "../../../components/Post.vue";
import storyComponent from "../../../components/Story.vue";

export default {
  components: {
    postComponent,
    storyComponent,
  },
  props: {
    username: {
      required: true,
    },
  },
  data: () => ({
    entirePost: null,
    entireStory: null,
    slides: ["First", "Second", "Third", "Fourth", "Fifth"],
    colors: [
      "indigo",
      "warning",
      "pink darken-2",
      "red lighten-1",
      "deep-purple accent-4",
    ],
    tabs2: null,
    snackbar: false,
    snackbarText: "",
    loggedIn: false,
    myusername: "",
    followed: true,
    isBlocked: false,
    isMuted: false,
    user: {},
    posts: [],
    stories: [],
  }),
  mounted() {
    this.load();
  },
  watch: {
    username: function () {
      this.load();
    },
  },
  methods: {
    load: function () {
      if (getToken()) {
        this.loggedIn = true;
        this.myusername = getUsernameFromToken();

        if (this.username === this.myusername) {
          this.$router.push("/profile");
        }

        this.axios
          .get(
            process.env.VUE_APP_BACKEND_URL +
              process.env.VUE_APP_ISMUTED_ENDPOINT +
              this.username,
            {
              headers: {
                Authorization: "Bearer " + getToken(),
              },
            }
          )
          .then((response) => {
            if (response.data === true) this.isMuted = true;
            else if (response.data === false) this.isMuted = false;
            else this.isMuted = null;
          })
          .catch((error) => {
            if (
              error.response &&
              error.response.data &&
              error.response.data.message
            )
              this.snackbarText = error.response.data.message;
            else if (error.message) this.snackbarText = error.message;
            else this.snackbarText = "An unknown error has occured.";
            this.snackbar = true;
          });

        this.axios
          .get(
            process.env.VUE_APP_BACKEND_URL +
              process.env.VUE_APP_RELATIONSHIP_ENDPOINT +
              this.username,
            {
              headers: {
                Authorization: "Bearer " + getToken(),
              },
            }
          )
          .then((response) => {
            if (response.data === "BLOCKED") this.isBlocked = true;
            else if (response.data === "NONE") this.followed = false;
            else this.followed = true;
          })
          .catch((error) => {
            if (
              error.response &&
              error.response.data &&
              error.response.data.message
            )
              this.snackbarText = error.response.data.message;
            else if (error.message) this.snackbarText = error.message;
            else this.snackbarText = "An unknown error has occured.";
            this.snackbar = true;
          });
      }

      this.axios
        .get(
          process.env.VUE_APP_BACKEND_URL +
            process.env.VUE_APP_USER_BY_USERNAME_ENDPOINT +
            this.username
        )
        .then((response) => {
          this.user = response.data;
          if (this.user.profileImagePath) {
            this.user.profileImagePath =
              process.env.VUE_APP_BACKEND_URL +
              process.env.VUE_APP_FILE_ENDPOINT +
              this.user.profileImagePath;
          } else {
            this.user.profileImagePath =
              "https://icon-library.com/images/default-user-icon/default-user-icon-4.jpg";
          }
        })
        .catch((error) => {
          if (
            error.response &&
            error.response.data &&
            error.response.data.message
          )
            this.snackbarText = error.response.data.message;
          else if (error.message) this.snackbarText = error.message;
          else this.snackbarText = "An unknown error has occured.";
          this.snackbar = true;
        });

      if (!this.user.isPrivate) {
        this.axios
          .get(
            process.env.VUE_APP_BACKEND_URL +
              process.env.VUE_APP_PUBLIC_CONTENT_USER +
              "/" +
              this.username
          )
          .then((res) => {
            if (res.data.posts != null) {
              this.posts = [];
              res.data.posts.forEach((post) => {
                this.posts.push({
                  _id: post.post_id,
                  path: post.first_content.path,
                  type: post.first_content.type
                });
              });
            }

            if (res.data.stories != null) {
              this.stories = [];
              res.data.stories.forEach((story) => {
                this.stories.push({
                  _id: story.story_id,
                  path: story.first_content.path,
                  type: story.first_content.type
                });
              });
            }
          });
      }
    },
    getImageUrl(post) {
      return (
        process.env.VUE_APP_BACKEND_URL +
        process.env.VUE_APP_FILE_ENDPOINT +
        post.path
      );
    },
    follow: function () {
      this.axios
        .post(
          process.env.VUE_APP_BACKEND_URL +
            process.env.VUE_APP_FOLLOW_ENDPOINT +
            this.username,
          {},
          {
            headers: {
              Authorization: "Bearer " + getToken(),
            },
          }
        )
        .then(() => {
          this.followed = true;
        })
        .catch((error) => {
          if (
            error.response &&
            error.response.data &&
            error.response.data.message
          )
            this.snackbarText = error.response.data.message;
          else if (error.message) this.snackbarText = error.message;
          else this.snackbarText = "An unknown error has occured.";
          this.snackbar = true;
        });
    },
    block: function () {
      this.axios
        .post(
          process.env.VUE_APP_BACKEND_URL +
            process.env.VUE_APP_BLOCK_ENDPOINT +
            this.username,
          {},
          {
            headers: {
              Authorization: "Bearer " + getToken(),
            },
          }
        )
        .then(() => {
          this.isBlocked = true;
        })
        .catch((error) => {
          if (
            error.response &&
            error.response.data &&
            error.response.data.message
          )
            this.snackbarText = error.response.data.message;
          else if (error.message) this.snackbarText = error.message;
          else this.snackbarText = "An unknown error has occured.";
          this.snackbar = true;
        });
    },
    mute: function () {
      this.axios
        .post(
          process.env.VUE_APP_BACKEND_URL +
            process.env.VUE_APP_MUTE_ENDPOINT +
            this.username,
          {},
          {
            headers: {
              Authorization: "Bearer " + getToken(),
            },
          }
        )
        .then(() => {
          this.isMuted = true;
        })
        .catch((error) => {
          if (
            error.response &&
            error.response.data &&
            error.response.data.message
          )
            this.snackbarText = error.response.data.message;
          else if (error.message) this.snackbarText = error.message;
          else this.snackbarText = "An unknown error has occured.";
          this.snackbar = true;
        });
    },
    unmute: function () {
      this.axios
        .post(
          process.env.VUE_APP_BACKEND_URL +
            process.env.VUE_APP_UNMUTE_ENDPOINT +
            this.username,
          {},
          {
            headers: {
              Authorization: "Bearer " + getToken(),
            },
          }
        )
        .then(() => {
          this.isMuted = false;
        })
        .catch((error) => {
          if (
            error.response &&
            error.response.data &&
            error.response.data.message
          )
            this.snackbarText = error.response.data.message;
          else if (error.message) this.snackbarText = error.message;
          else this.snackbarText = "An unknown error has occured.";
          this.snackbar = true;
        });
    },
    unfollow: function () {
      this.axios
        .post(
          process.env.VUE_APP_BACKEND_URL +
            process.env.VUE_APP_UNFOLLOW_ENDPOINT +
            this.username,
          {},
          {
            headers: {
              Authorization: "Bearer " + getToken(),
            },
          }
        )
        .then(() => {
          this.followed = false;
        })
        .catch((error) => {
          if (
            error.response &&
            error.response.data &&
            error.response.data.message
          )
            this.snackbarText = error.response.data.message;
          else if (error.message) this.snackbarText = error.message;
          else this.snackbarText = "An unknown error has occured.";
          this.snackbar = true;
        });
    },
    getEntirePost(postID) {
      // get entire post
      this.axios
        .get(
          process.env.VUE_APP_BACKEND_URL +
            process.env.VUE_APP_PUBLIC_POST +
            postID
        )
        .then((res) => {
          this.entirePost = res.data;
        });
    },
    getEntireStory(storyID) {
      // get entire post
      this.axios
        .get(
          process.env.VUE_APP_BACKEND_URL +
            process.env.VUE_APP_PUBLIC_STORY +
            storyID
        )
        .then((res) => {
          this.entireStory = res.data;
        });
    },
    addToSaved(post) {
      this.axios
        .post(
          process.env.VUE_APP_BACKEND_URL +
            process.env.VUE_APP_CONTENT_USER_SAVED +
            this.user._id,
          {
            _id: post._id,
            my_post: {
              content: [
                {
                  path: post.path,
                  type: post.type,
                },
              ],
            },
          },
          {
            headers: {
              Authorization: "Bearer " + localStorage.getItem("JWT-CPIS"),
            },
          }
        )
        .then((res) => {
          console.log(res);
          this.forceRerender();
        })
        .catch((error) => {
          console.log(error);
        });
    },
  },
};
</script>