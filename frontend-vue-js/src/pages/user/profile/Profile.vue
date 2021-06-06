<template>
  <v-row class="pa-5">
    <v-spacer></v-spacer>
    <v-col sm="8" md="12" lg="4" cols="12">
      <!-- Images, number of posts, followers, following -->
      <v-row>
        <!--First column-->
        <v-col sm="8" md="12" lg="4" cols="12">
          <!--Profile photo-->
          <v-img
            class="rounded-circle"
            aspect-ratio="1.1"
            src="@/assets/profilePhoto.jpg"
          >
          </v-img>

          <!--Username and description-->
          <h3 class="text-justify">ivana_mil_98</h3>
          <div class="font-weight-medium text-justify">
            Love is in the air. The sun is shining,I am smiling :).
          </div>
        </v-col>
        <!--End of the first column-->

        <!--Column for the number of posts-->
        <v-col class="pa-4 mt-2">
          <h3>250</h3>
          Posts
        </v-col>
        <!--End of the number of posts-->

        <!--Column for the number of followers-->
        <v-col class="pa-4 mt-2">
          <h3>1406</h3>
          Followers
        </v-col>
        <!--End of the number of followers-->

        <!--Column for the number of following-->
        <v-col class="pa-4 mt-2">
          <h3>950</h3>
          Following
        </v-col>
        <!--Column for the number of following-->
      </v-row>
      <!-- End of profile image, number of posts, followers, following -->

      <!-- Edit profile, add content -->
      <v-row>
        <!--Column for edit profile button-->
        <v-col class="pa-3">
          <!--Dialog and button for editing profile-->
          <v-dialog width="600px">
            <template v-slot:activator="{ on, attrs }">
              <v-btn
                outlined
                rounded
                medium
                color="primary"
                v-bind="attrs"
                v-on="on"
              >
                <v-icon left> mdi-pencil </v-icon>
                Edit profile
              </v-btn>
            </template>
            <v-card>
              <!--Edit profile fields-->
              <v-card-title class="headline"> Edit your profile </v-card-title>
              <v-card-text>
                <v-file-input
                  small-chips
                  accept="image/png, image/jpeg, image/bmp"
                  label="Choose a profile"
                  prepend-icon="mdi-camera"
                ></v-file-input>
                <v-text-field
                  prepend-icon="mdi-account"
                  label="Name"
                ></v-text-field>
                <v-text-field
                  prepend-icon="mdi-account"
                  label="Surname"
                ></v-text-field>
                <v-text-field
                  prepend-icon="mdi-account-circle"
                  label="Username"
                ></v-text-field>
                <v-text-field
                  prepend-icon="mdi-email"
                  label="Email"
                ></v-text-field>
                <v-text-field
                  prepend-icon="mdi-phone"
                  label="Phone number"
                ></v-text-field>
                <v-text-field
                  label="Birthday date"
                  prepend-icon="mdi-cake-variant"
                  readonly
                ></v-text-field>
                <v-text-field
                  prepend-icon="mdi-human-male-female"
                  label="Gender"
                ></v-text-field>
                <v-text-field
                  prepend-icon="mdi-web"
                  label="Web site"
                ></v-text-field>
                <v-textarea
                  outlined
                  name="input-7-4"
                  no-resize
                  rows="5"
                  label="Add your biography"
                  clearable
                  clear-icon="mdi-close-circle"
                ></v-textarea>
              </v-card-text>
              <!--End of edit profile fields-->
              <v-card-actions>
                <v-spacer></v-spacer>
                <v-btn color="error" text> Cancel </v-btn>
                <v-btn color="primary" text> Save </v-btn>
              </v-card-actions>
            </v-card>
          </v-dialog>
        </v-col>
        <!--End of the column for edit profile button-->

        <!--Column for add content button-->
        <v-col class="text-right mr-5 mb-5">
          <!--Button for adding new content-->
          <v-dialog width="600px" v-model="openedContenDialog">
            <v-tabs v-model="tabs" icons-and-text>
              <v-tabs-slider></v-tabs-slider>
              <v-tab>Regular posts<v-icon>mdi-camera</v-icon></v-tab>
              <v-tab>Stories<v-icon>mdi-camera-iris</v-icon></v-tab>
              <v-tab>Close friends<v-icon>mdi-star</v-icon></v-tab>
            </v-tabs>

            <template v-slot:activator="{ on, attrs }">
              <v-btn dark small fab color="primary" v-bind="attrs" v-on="on">
                <v-icon dark> mdi-plus </v-icon>
              </v-btn>
            </template>
            <v-tabs-items v-model="tabs">
              <!--Tab for adding photo or video-->
              <v-tab-item>
                <v-card>
                  <v-card-title class="headline">
                    Add a new photo or video
                  </v-card-title>
                  <v-card-text>
                    <!--Stepper-->
                    <v-stepper v-model="e1">
                      <v-stepper-header>
                        <v-stepper-step :complete="e1 > 1" step="1">
                          Photo/Video
                        </v-stepper-step>

                        <v-divider></v-divider>

                        <v-stepper-step :complete="e1 > 2" step="2">
                          Other information
                        </v-stepper-step>

                        <v-divider></v-divider>

                        <v-stepper-step step="3"> Confirmation </v-stepper-step>
                      </v-stepper-header>

                      <v-stepper-items>
                        <!--Step 1-->
                        <v-stepper-content step="1">
                          <!--File input for images and videos-->
                          <v-file-input
                            small-chips
                            multiple
                            show-size
                            counter
                            accept="image/png, image/jpeg, image/bmp"
                            label="Choose a photo or video"
                            prepend-icon="mdi-camera"
                            @change="onFileSelected"
                          ></v-file-input>
                          <!--End of file input-->

                          <v-btn
                            color="primary"
                            @click="createContent(), (e1 = 2)"
                          >
                            Continue
                          </v-btn>

                          <v-btn text> Cancel </v-btn>
                        </v-stepper-content>
                        <!--End of step 1-->

                        <!--Step 2-->
                        <v-stepper-content step="2">
                          <!-- Chose a title -->
                          <v-text-field
                            v-model="my_post.title"
                            label="Choose a title"
                            prepend-icon="mdi-home"
                          >
                          </v-text-field>
                          <!-- End of title-->
                          <!--Choose a location-->
                          <v-combobox
                            label="Choose a location"
                            prepend-icon="mdi-map-marker-star"
                          >
                          </v-combobox>
                          <!--End of location-->

                          <!--Tagging-->
                          <br />
                          <v-autocomplete
                            label="Tagging"
                            prepend-icon="mdi-tag"
                          ></v-autocomplete>
                          <!--End of tagging-->

                          <!--Add description-->
                          <v-textarea
                            v-model="my_post.description"
                            outlined
                            name="input-7-4"
                            no-resize
                            rows="3"
                            label="Add description"
                            clearable
                            clear-icon="mdi-close-circle"
                          ></v-textarea>
                          <!--End of description-->

                          <v-btn
                            color="primary"
                            @click="createPost(), (e1 = 3)"
                          >
                            Continue
                          </v-btn>

                          <v-btn text> Cancel </v-btn>
                        </v-stepper-content>
                        <!--End of step 2-->

                        <!--Step 3-->
                        <v-stepper-content step="3">
                          <h3>
                            Congratulations, you have successfully posted the
                            desired content!
                          </h3>

                          <v-spacer></v-spacer>
                          <br />
                          <v-btn
                            color="primary"
                            @click="openedContenDialog = false"
                          >
                            Close
                          </v-btn>
                        </v-stepper-content>
                        <!--End of step 3-->
                      </v-stepper-items>
                    </v-stepper>
                  </v-card-text>
                </v-card>
              </v-tab-item>
              <!--End of tab for adding photo or video-->

              <!--Tab for adding stories-->
              <v-tab-item>
                <v-card>
                  <v-card-title class="headline">
                    Add a new story
                  </v-card-title>
                  <v-card-text>
                    <!--Stepper-->
                    <v-stepper v-model="e2">
                      <v-stepper-header>
                        <v-stepper-step :complete="e2 > 1" step="1">
                          Photo/Video
                        </v-stepper-step>

                        <v-divider></v-divider>

                        <v-stepper-step :complete="e2 > 2" step="2">
                          Other information
                        </v-stepper-step>

                        <v-divider></v-divider>

                        <v-stepper-step step="3"> Confirmation </v-stepper-step>
                      </v-stepper-header>

                      <v-stepper-items>
                        <!--Step 1-->
                        <v-stepper-content step="1">
                          <!--File input for images and videos-->
                          <v-file-input
                            small-chips
                            multiple
                            show-size
                            counter
                            accept="image/png, image/jpeg, image/bmp"
                            label="Choose a photo or video"
                            prepend-icon="mdi-camera"
                            @change="onFileSelected"
                          ></v-file-input>
                          <!--End of file input-->

                          <v-btn
                            color="primary"
                            @click="createContent(), (e2 = 2)"
                          >
                            Continue
                          </v-btn>

                          <v-btn text> Cancel </v-btn>
                        </v-stepper-content>
                        <!--End of step 1-->

                        <!--Step 2-->
                        <v-stepper-content step="2">
                          <!--Choose a location-->
                          <v-combobox
                            label="Choose a location"
                            prepend-icon="mdi-map-marker-star"
                          >
                          </v-combobox>
                          <!--End of location-->

                          <!--Tagging-->
                          <br />
                          <v-autocomplete
                            label="Tagging"
                            prepend-icon="mdi-tag"
                          ></v-autocomplete>
                          <!--End of tagging-->

                          <!--Only available to close friends-->
                          <v-checkbox
                            v-model="isForCloseFriends"
                            label="Only available to close friends"
                          >
                          </v-checkbox>

                          <!--Add description-->
                          <v-textarea
                            outlined
                            name="input-7-4"
                            no-resize
                            rows="3"
                            label="Add description"
                            clearable
                            clear-icon="mdi-close-circle"
                          ></v-textarea>
                          <!--End of description-->

                          <v-btn
                            color="primary"
                            @click="createStory(), (e2 = 3)"
                          >
                            Continue
                          </v-btn>

                          <v-btn text> Cancel </v-btn>
                        </v-stepper-content>
                        <!--End of step 2-->

                        <!--Step 3-->
                        <v-stepper-content step="3">
                          <h3>
                            Congratulations, you have successfully posted the
                            desired content!
                          </h3>

                          <v-spacer></v-spacer>
                          <br />
                          <v-btn
                            color="primary"
                            @click="openedContenDialog = false"
                          >
                            Close
                          </v-btn>
                        </v-stepper-content>
                        <!--End of step 3-->
                      </v-stepper-items>
                    </v-stepper>
                  </v-card-text>
                </v-card>
              </v-tab-item>
              <!--End of tab for adding stories-->

              <!--Tab for close friends-->
              <v-tab-item>
                <v-card>
                  <v-card-title> Add a close friend </v-card-title>
                  <v-card-text>
                    <v-autocomplete
                      label="Choose a close friend"
                      prepend-icon="mdi-star-circle"
                    ></v-autocomplete>
                  </v-card-text>
                  <v-card-actions>
                    <v-spacer></v-spacer>
                    <v-btn color="error" text> Cancel </v-btn>
                    <v-btn color="primary" text> Add </v-btn>
                  </v-card-actions>
                </v-card>
              </v-tab-item>
              <!--End of tab for close friends-->
            </v-tabs-items>
          </v-dialog>
        </v-col>
        <!--End of the column for add content button-->
      </v-row>
      <!-- End of edit profile, add content -->

      <!-- Posts, stories, saved, tagged -->
      <v-row>
        <v-tabs v-model="tabs2" icons-and-text background-color="transparent">
          <v-tabs-slider></v-tabs-slider>
          <v-tab>Posts<v-icon>mdi-camera</v-icon></v-tab>
          <v-tab>Stories<v-icon>mdi-camera-iris</v-icon></v-tab>
          <v-tab>Saved<v-icon>mdi-check-circle</v-icon></v-tab>
          <v-tab>Tagged<v-icon>mdi-tag</v-icon></v-tab>
        </v-tabs>
        <v-tabs-items v-model="tabs2">
          <!--Tab for photos and videos-->
          <v-tab-item>
            <v-card class="mx-auto" max-width="500">
              <v-container fluid>
                <v-row dense>
                  <v-col
                    v-for="post in posts"
                    :key="post.title"
                    :cols="post.flex"
                  >
                    <v-card>
                      <v-img
                        :src="getImageUrl(post)"
                        class="white--text align-end"
                        gradient="to bottom, rgba(0,0,0,.1), rgba(0,0,0,.5)"
                        height="200px"
                      >
                        <v-card-title v-text="post.title"></v-card-title>
                      </v-img>

                      <v-card-actions>
                        <v-spacer></v-spacer>

                        <v-btn icon>
                          <v-icon>mdi-heart</v-icon>
                        </v-btn>

                        <v-btn icon>
                          <v-icon>mdi-bookmark</v-icon>
                        </v-btn>

                        <v-btn icon>
                          <v-icon>mdi-share-variant</v-icon>
                        </v-btn>
                      </v-card-actions>
                    </v-card>
                  </v-col>
                </v-row>
              </v-container>
            </v-card>
          </v-tab-item>
          <!--End of tab for photos and videos-->

          <!--Tab for stories-->
          <v-tab-item> STORIES </v-tab-item>
          <!--End of tab for stories-->

          <!--Tab for saved/favorites-->
          <v-tab-item> SAVED </v-tab-item>
          <!--End of tab for saved/favorites-->

          <!--Tab for tagged-->
          <v-tab-item> TAGGED </v-tab-item>
          <!--End of tab for tagged-->
        </v-tabs-items>
      </v-row>
      <!-- End of posts, stories, saved, tagged -->
    </v-col>

    <v-spacer></v-spacer>
  </v-row>
</template>

<script>
import axios from "axios";
export default {
  data() {
    return {
      snackbar: false,
      snackbarText: "",
      loading: false,
      tabs: null,
      tabs2: null,
      e1: 1,
      e2: 1,
      my_post: {
        title: "",
        // location: null,
        description: "",
        is_add: false,
        add_link: "/",
        content: [],
      },
      isForCloseFriends: false,
      form: {
        email: "",
        password: "",
        showPassword: false,
        repeatPassword: "",
        showRepeatPassword: false,
        name: "",
        surname: "",
        address: "",
        city: "",
        country: "",
        phone: "",
      },
      openedContenDialog: null,
      selectedFiles: [],
      user : null,
      posts: [
        {
          _id: "60bccf2be1239cd5cda4192d",
          title: "Pre-fab homes",
          path: "mxvxhrpqibyw_1622986539993-poveznik.png",
          type: "image",
          flex: 12,
        },
      ],
    };
  },
  computed: {},
  mounted() {
    axios
      .get(
        process.env.VUE_APP_BACKEND_URL +
          process.env.VUE_APP_USER +
          process.env.VUE_APP_USER_USERNAME +
          "/pero"
      )
      .then((res) => {
        console.log(res);
        this.user = res.data;
      });
  },
  methods: {
    getImageUrl(post) {
      return (
        process.env.VUE_APP_BACKEND_URL +
        process.env.VUE_APP_FILE_UPLOAD_ENDPOINT +
        post.path
      );
    },
    createPost() {
      // TODO: Move this to ENV !!!
      axios
        .post("http://localhost:8000/post/", {
          my_post: this.my_post,
        })
        .then((res) => {
          console.log(res);
        });
    },
    createStory() {
      // TODO: Move this to ENV !!!
      axios
        .post("http://localhost:8000/story/", {
          my_post: this.my_post,
          is_for_close_friends: this.isForCloseFriends,
        })
        .then((res) => {
          console.log(res);
        });
    },
    createContent() {
      const fd = new FormData();

      this.selectedFiles.forEach((selectedFile) => {
        if (selectedFile.type.includes("image")) {
          fd.append("image", selectedFile, selectedFile.name);
        } else {
          fd.append("video", selectedFile, selectedFile.name);
        }
      });

      // TODO: Move this to ENV !!!
      axios.post("http://localhost:8000/upload/", fd).then((res) => {
        console.log(res);
        this.my_post.content = res.data;
      });
    },
    onFileSelected(event) {
      this.selectedFiles = event;
      console.log(this.selectedFiles);
    },
  },
};
</script>