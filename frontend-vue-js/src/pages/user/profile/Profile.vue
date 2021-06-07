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
                  v-model="form.name"
                  prepend-icon="mdi-account"
                  label="Name"
                ></v-text-field>
                <v-text-field
                  v-model="form.surname"
                  prepend-icon="mdi-account"
                  label="Surname"
                ></v-text-field>
                <v-text-field
                  v-model="form.username"
                  prepend-icon="mdi-account-circle"
                  label="Username"
                ></v-text-field>
                <v-text-field
                  v-model="form.email"
                  prepend-icon="mdi-email"
                  label="Email"
                ></v-text-field>
                <v-text-field
                  v-model="form.phonenumber"
                  prepend-icon="mdi-phone"
                  label="Phone number"
                ></v-text-field>
             
              <p>Date of birth</p>
              <v-date-picker
                 v-model="form.birthayDate"
                label="Date of birth"
                class="ml-4"
              ></v-date-picker>

                <v-text-field
                  v-model="form.gender"
                  prepend-icon="mdi-human-male-female"
                  label="Gender"
                ></v-text-field>
                <v-text-field
                  v-model="form.webSite"
                  prepend-icon="mdi-web"
                  label="Web site"
                ></v-text-field>
                <v-textarea
                  v-model="form.bio"
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
                <v-btn color="primary" text  @click="saveChanges()"> Save </v-btn>
              </v-card-actions>
            </v-card>
          </v-dialog>
        </v-col>
        <!--End of the column for edit profile button-->

        <!--Column for add content button-->
        <v-col class="text-right mr-5 mb-5">
          <!--Button for adding new content-->
          <v-dialog width="700px" v-model="openedContenDialog">
            <v-tabs v-model="tabs" icons-and-text>
              <v-tabs-slider></v-tabs-slider>
              <v-tab>Regular posts<v-icon>mdi-camera</v-icon></v-tab>
              <v-tab>Stories<v-icon>mdi-camera-iris</v-icon></v-tab>
              <v-tab>Highlights<v-icon>mdi-star-circle-outline</v-icon></v-tab>
              <v-tab>Saved<v-icon>mdi-check-circle</v-icon></v-tab>
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

              <!--Tab for highlights-->
              <v-tab-item>
                  <v-card>
                    <v-card-title class="headline">
                        Choose a cover photo for album
                    </v-card-title>
                  <v-card-text>
                    <!--Stepper-->
                    <v-stepper v-model="e3">
                      <v-stepper-header>
                        <v-stepper-step :complete="e3 > 1" step="1">
                          Cover photo
                        </v-stepper-step>

                        <v-divider></v-divider>

                        <v-stepper-step :complete="e3 > 2" step="2">
                          Name
                        </v-stepper-step>

                        <v-divider></v-divider>

                        <v-stepper-step step="3"> Confirmation </v-stepper-step>
                      </v-stepper-header>

                      <v-stepper-items>
                        <!--Step 1-->
                        <v-stepper-content step="1">
                          <!--File input for cover image-->
                          <v-file-input
                            small-chips
                            accept="image/png, image/jpeg, image/bmp"
                            label="Choose a photo or video"
                            prepend-icon="mdi-camera"
                            @change="onFileSelected"
                          ></v-file-input>
                          <!--End of file input-->

                          <v-btn
                            color="primary"
                            @click="createContent(), (e3 = 2)"
                          >
                            Continue
                          </v-btn>

                          <v-btn text> Cancel </v-btn>
                        </v-stepper-content>
                        <!--End of step 1-->

                        <!--Step 2-->
                        <v-stepper-content step="2">
                            <!--Name of album-->
                            <v-text-field
                                prepend-icon="mdi-image-album"
                                label="Name of album"
                            ></v-text-field>
                            <!--End of the name of album-->
                         
                          
                          <v-btn
                            color="primary"
                            @click="(e3 = 3)"
                          >
                            Continue
                          </v-btn>

                          <v-btn text> Cancel </v-btn>
                        </v-stepper-content>
                        <!--End of step 2-->

                        <!--Step 3-->
                        <v-stepper-content step="3">
                          <h3>
                            Congratulations, you have successfully chosen the
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
              <!--End of tab for highlights-->

               <!--Tab for saved content-->
              <v-tab-item>
                  <v-card>
                    <v-card-title class="headline">
                        Choose a cover photo for album
                    </v-card-title>
                  <v-card-text>
                    <!--Stepper-->
                    <v-stepper v-model="e4">
                      <v-stepper-header>
                        <v-stepper-step :complete="e4 > 1" step="1">
                          Cover photo
                        </v-stepper-step>

                        <v-divider></v-divider>

                        <v-stepper-step :complete="e4 > 2" step="2">
                          Name
                        </v-stepper-step>

                        <v-divider></v-divider>

                        <v-stepper-step step="3"> Confirmation </v-stepper-step>
                      </v-stepper-header>

                      <v-stepper-items>
                        <!--Step 1-->
                        <v-stepper-content step="1">
                          <!--File input for cover image-->
                          <v-file-input
                            small-chips
                            accept="image/png, image/jpeg, image/bmp"
                            label="Choose a photo or video"
                            prepend-icon="mdi-camera"
                            @change="onFileSelected"
                          ></v-file-input>
                          <!--End of file input-->

                          <v-btn
                            color="primary"
                            @click="createContent(), (e4 = 2)"
                          >
                            Continue
                          </v-btn>

                          <v-btn text> Cancel </v-btn>
                        </v-stepper-content>
                        <!--End of step 1-->

                        <!--Step 2-->
                        <v-stepper-content step="2">
                            <!--Name of album-->
                            <v-text-field
                                prepend-icon="mdi-image-album"
                                label="Name of album"
                            ></v-text-field>
                            <!--End of the name of album-->
                         
                          
                          <v-btn
                            color="primary"
                            @click="(e4 = 3)"
                          >
                            Continue
                          </v-btn>

                          <v-btn text> Cancel </v-btn>
                        </v-stepper-content>
                        <!--End of step 2-->

                        <!--Step 3-->
                        <v-stepper-content step="3">
                          <h3>
                            Congratulations, you have successfully chosen the
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
              <!--End of tab for saved content-->


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
          <v-tab>Highlights<v-icon>mdi-star-circle-outline</v-icon></v-tab>
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
                    :key="post.post_id"
                    :cols="post.flex"
                  >
                    <!-- Image previw -->
                    <v-card v-if="post.type == 'image'">
                      <v-img
                        :src="getImageUrl(post)"
                        class="white--text align-end"
                        gradient="to bottom, rgba(0,0,0,.1), rgba(0,0,0,.5)"
                        height="200px"
                      >
                      </v-img>
                      <v-card-actions>
                        <v-spacer></v-spacer>

                        <!--Dialog for choosing photo-->
                        <v-dialog width="600px">
                          <template v-slot:activator="{ on, attrs }">
                            <v-btn v-bind="attrs" v-on="on" icon>
                              <v-icon right> mdi-plus-circle </v-icon>
                            </v-btn>
                          </template>
                          <!--Card for comments-->
                          <v-card>
                            <v-card-title>
                              <v-img
                                lazy-src="https://picsum.photos/id/11/10/6"
                                src="https://picsum.photos/id/11/500/300"
                              ></v-img>
                            </v-card-title>
                            <v-card-text>
                              <v-row>
                                <v-col>
                                  <!--The number of likes and comments-->
                                  <v-icon medium>mdi-heart</v-icon>700
                                  <v-icon medium right>mdi-comment</v-icon>10
                                </v-col>

                                <v-col class="text-right mr-5 mb-5">
                                  <!--Button for likes-->
                                  <v-btn
                                    class="mx-2"
                                    fab
                                    x-small
                                    color="primary"
                                    ><v-icon>mdi-thumb-up</v-icon>
                                  </v-btn>
                                  <!--End of button for likes-->

                                  <!--Button for dislikes-->
                                  <v-btn
                                    class="mx-2"
                                    fab
                                    x-small
                                    color="primary"
                                    ><v-icon>mdi-thumb-down</v-icon>
                                  </v-btn>
                                  <!--End of button for dislikes-->

                                  <!--Dialog for adding comment-->
                                  <v-dialog width="600px">
                                    <template v-slot:activator="{ on, attrs }">
                                      <v-btn
                                        class="mx-2"
                                        fab
                                        x-small
                                        v-bind="attrs"
                                        v-on="on"
                                        color="primary"
                                        ><v-icon>mdi-pencil</v-icon>
                                      </v-btn>
                                    </template>
                                    <v-card>
                                      <v-card-title>
                                        Add a new comment to the post
                                      </v-card-title>
                                      <v-card-text>
                                        <!--Field for comments-->
                                        <v-textarea
                                          outlined
                                          name="input-7-4"
                                          no-resize
                                          rows="3"
                                          label="Comment"
                                          clearable
                                          clear-icon="mdi-close-circle"
                                        ></v-textarea>
                                        <!--End of field for comments-->
                                      </v-card-text>
                                      <v-card-actions>
                                        <!--Buttons to confirm or cancel action-->
                                        <v-spacer></v-spacer>
                                        <v-btn color="error" text>
                                          Cancel
                                        </v-btn>
                                        <v-btn color="primary" text>
                                          Confirm
                                        </v-btn>
                                      </v-card-actions>
                                    </v-card>
                                  </v-dialog>
                                  <!--End of dialog for adding comments-->
                                </v-col>
                              </v-row>

                              <br /><br />
                              <!--Expansion panels for showing comments-->
                              <v-expansion-panels>
                                <v-expansion-panel
                                  v-for="(item, i) in 1"
                                  :key="i"
                                >
                                  <v-expansion-panel-header>
                                    Show all comments
                                  </v-expansion-panel-header>
                                  <v-expansion-panel-content
                                    v-for="comment in comments"
                                    :key="comment.id"
                                  >
                                    <h3>{{ comment.username }}</h3>
                                    {{ comment.description }}
                                  </v-expansion-panel-content>
                                </v-expansion-panel>
                              </v-expansion-panels>
                              <!--End of expansion panels-->
                            </v-card-text>
                          </v-card>
                        </v-dialog>
                        <!--End of dialog for choosing photo-->

                        <v-btn icon>
                          <v-icon>mdi-bookmark</v-icon>
                        </v-btn>
                      </v-card-actions>
                    </v-card>
                    <!-- End of the image previw -->

                    <!-- Video previw -->
                    <v-card v-if="post.type == 'video'">
                      <video
                        controls
                        :src="getImageUrl(post)"
                        class="white--text align-end"
                        gradient="to bottom, rgba(0,0,0,.1), rgba(0,0,0,.5)"
                        height="200px"
                        width="100%"
                      ></video>
                      <v-card-actions>
                        <v-spacer></v-spacer>

                        <!--Dialog for choosing photo-->
                        <v-dialog width="600px">
                          <template v-slot:activator="{ on, attrs }">
                            <v-btn v-bind="attrs" v-on="on" icon>
                              <v-icon right> mdi-plus-circle </v-icon>
                            </v-btn>
                          </template>
                          <!--Card for comments-->
                          <v-card>
                            <v-card-title>
                              <v-img
                                lazy-src="https://picsum.photos/id/11/10/6"
                                src="https://picsum.photos/id/11/500/300"
                              ></v-img>
                            </v-card-title>
                            <v-card-text>
                              <v-row>
                                <v-col>
                                  <!--The number of likes and comments-->
                                  <v-icon medium>mdi-heart</v-icon>700
                                  <v-icon medium right>mdi-comment</v-icon>10
                                </v-col>

                                <v-col class="text-right mr-5 mb-5">
                                  <!--Button for likes-->
                                  <v-btn
                                    class="mx-2"
                                    fab
                                    x-small
                                    color="primary"
                                    ><v-icon>mdi-thumb-up</v-icon>
                                  </v-btn>
                                  <!--End of button for likes-->

                                  <!--Button for dislikes-->
                                  <v-btn
                                    class="mx-2"
                                    fab
                                    x-small
                                    color="primary"
                                    ><v-icon>mdi-thumb-down</v-icon>
                                  </v-btn>
                                  <!--End of button for dislikes-->

                                  <!--Dialog for adding comment-->
                                  <v-dialog width="600px">
                                    <template v-slot:activator="{ on, attrs }">
                                      <v-btn
                                        class="mx-2"
                                        fab
                                        x-small
                                        v-bind="attrs"
                                        v-on="on"
                                        color="primary"
                                        ><v-icon>mdi-pencil</v-icon>
                                      </v-btn>
                                    </template>
                                    <v-card>
                                      <v-card-title>
                                        Add a new comment to the post
                                      </v-card-title>
                                      <v-card-text>
                                        <!--Field for comments-->
                                        <v-textarea
                                          outlined
                                          name="input-7-4"
                                          no-resize
                                          rows="3"
                                          label="Comment"
                                          clearable
                                          clear-icon="mdi-close-circle"
                                        ></v-textarea>
                                        <!--End of field for comments-->
                                      </v-card-text>
                                      <v-card-actions>
                                        <!--Buttons to confirm or cancel action-->
                                        <v-spacer></v-spacer>
                                        <v-btn color="error" text>
                                          Cancel
                                        </v-btn>
                                        <v-btn color="primary" text>
                                          Confirm
                                        </v-btn>
                                      </v-card-actions>
                                    </v-card>
                                  </v-dialog>
                                  <!--End of dialog for adding comments-->
                                </v-col>
                              </v-row>

                              <br /><br />
                              <!--Expansion panels for showing comments-->
                              <v-expansion-panels>
                                <v-expansion-panel
                                  v-for="(item, i) in 1"
                                  :key="i"
                                >
                                  <v-expansion-panel-header>
                                    Show all comments
                                  </v-expansion-panel-header>
                                  <v-expansion-panel-content
                                    v-for="comment in comments"
                                    :key="comment.id"
                                  >
                                    <h3>{{ comment.username }}</h3>
                                    {{ comment.description }}
                                  </v-expansion-panel-content>
                                </v-expansion-panel>
                              </v-expansion-panels>
                              <!--End of expansion panels-->
                            </v-card-text>
                          </v-card>
                        </v-dialog>
                        <!--End of dialog for choosing photo-->

                        <v-btn icon>
                          <v-icon>mdi-bookmark</v-icon>
                        </v-btn>
                      </v-card-actions>
                    </v-card>
                    <!-- End of the video previw -->
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
                  <v-col
                    v-for="story in stories"
                    :key="story.post_id"
                    :cols="story.flex"
                  >
                    <!-- Image previw -->
                    <v-card v-if="story.type == 'image'">
                      <v-img
                        :src="getImageUrl(story)"
                        class="white--text align-end"
                        gradient="to bottom, rgba(0,0,0,.1), rgba(0,0,0,.5)"
                        height="200px"
                      >
                      </v-img>
                      <v-card-actions>
                        <v-spacer></v-spacer>

                        <v-btn icon>
                          <v-icon>mdi-share-variant</v-icon>
                        </v-btn>
                      </v-card-actions>
                    </v-card>
                    <!-- End of the image previw -->

                    <!-- Video previw -->
                    <v-card v-if="story.type == 'video'">
                      <video
                        controls
                        :src="getImageUrl(story)"
                        class="white--text align-end"
                        gradient="to bottom, rgba(0,0,0,.1), rgba(0,0,0,.5)"
                        height="200px"
                        width="100%"
                      ></video>
                      <v-card-actions>
                        <v-spacer></v-spacer>

                        <v-btn icon>
                          <v-icon>mdi-share-variant</v-icon>
                        </v-btn>
                      </v-card-actions>
                    </v-card>
                    <!-- End of the video previw -->
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
                            <v-col
                               
                            >
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
                                                                <v-col>
                                                                    My perfect holiday
                                                                </v-col>
                                                                <v-col class="text-right">
                                                                    <v-dialog
                                                                        width="600px"
                                                                    >
                                                                        <template v-slot:activator="{ on, attrs }"> 
                                                                            <!--Button for adding stories to album-->
                                                                            <v-btn
                                                                                dark
                                                                                x-small 
                                                                                fab 
                                                                                color="primary"
                                                                                v-bind="attrs"
                                                                                v-on="on"
                                                                                    
                                                                            >
                                                                                <v-icon>
                                                                                    mdi-plus
                                                                                </v-icon>
                                                                                
                                                                            </v-btn>
                                                                            <!--End of button for adding stories to album-->
                                                                        </template>
                                                                        <v-card>
                                                                            <v-card-title>
                                                                                Choose a story you want to highlight
                                                                            </v-card-title>
                                                                            <v-col>
                                                                                <!--Stories-->
                                                                                <v-card class="mb-5">
                                                                                    
                
                                                                                
                                                                                        <v-img
                                                                                            src="https://picsum.photos/350/165?random"
                                                                                            class="white--text align-end"
                                                                                            gradient="to bottom, rgba(0,0,0,.1), rgba(0,0,0,.5)"
                                                                                            height="300px"
                                                                                            
                                                                                        >
                                                                                            
                                                                                        </v-img>
                                                                                        
                                                                                    <v-card-actions>
                                                                                        <v-btn
                                                                                                color="primary"
                                                                                        >
                                                                                            CHOOSE
                                                                                        </v-btn>
                                                                                    </v-card-actions>
                                                                                    
                                                                                    
                                                                                </v-card>
                                                                                <v-card>
                
                                                                                
                                                                                        <v-img
                                                                                            src="https://picsum.photos/350/165?random"
                                                                                            class="white--text align-end"
                                                                                            gradient="to bottom, rgba(0,0,0,.1), rgba(0,0,0,.5)"
                                                                                            height="300px"
                                                                                        >
                                                                                            
                                                                                        </v-img>
                                                                                        
                                                                                    <v-card-actions>
                                                                                        <v-btn
                                                                                                color="primary"
                                                                                        >
                                                                                            CHOOSE
                                                                                        </v-btn>
                                                                                    </v-card-actions>
                                                                                    
                                                                                    
                                                                                </v-card>
                                                                            </v-col>
                                                                        </v-card>
                                                                    </v-dialog>
                                                                </v-col>
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
                                                                <v-sheet
                                                                    :color="colors[i]"
                                                                    height="100%"
                                                                >
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

          <!--Tab for saved/favorites-->
            <v-tab-item>
                <v-card class="mx-auto" max-width="500">
                    <v-container fluid>
                        <v-row dense>
                            <v-col
                               
                            >
                                <!-- Image preview -->
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
                                                    <!--Button for showing collection of favorites-->
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
                                                    <!--Card for favorites-->
                                                    <v-card>
                                                        <v-card-title>
                                                            <v-row>
                                                                <v-col>
                                                                    My perfect holiday
                                                                </v-col>
                                                                <v-col class="text-right">
                                                                    <v-dialog
                                                                        width="600px"
                                                                    >
                                                                        <template v-slot:activator="{ on, attrs }"> 
                                                                            <!--Button for adding saved content to album-->
                                                                            <v-btn
                                                                                dark
                                                                                x-small 
                                                                                fab 
                                                                                color="primary"
                                                                                v-bind="attrs"
                                                                                v-on="on"
                                                                                    
                                                                            >
                                                                                <v-icon>
                                                                                    mdi-plus
                                                                                </v-icon>
                                                                                
                                                                            </v-btn>
                                                                            <!--End of button for adding saved content to album-->
                                                                        </template>
                                                                        <v-card>
                                                                            <v-card-title>
                                                                                Choose a saved content you want to add to collection
                                                                            </v-card-title>
                                                                            <v-col>
                                                                                <!--Saved images-->
                                                                                <v-card class="mb-5">
                                                                                    
                
                                                                                
                                                                                        <v-img
                                                                                            src="https://picsum.photos/350/165?random"
                                                                                            class="white--text align-end"
                                                                                            gradient="to bottom, rgba(0,0,0,.1), rgba(0,0,0,.5)"
                                                                                            height="300px"
                                                                                            
                                                                                        >
                                                                                            
                                                                                        </v-img>
                                                                                        
                                                                                    <v-card-actions>
                                                                                        <v-btn
                                                                                                color="primary"
                                                                                        >
                                                                                            CHOOSE
                                                                                        </v-btn>
                                                                                    </v-card-actions>
                                                                                    
                                                                                    
                                                                                </v-card>
                                                                                <v-card>
                
                                                                                
                                                                                        <v-img
                                                                                            src="https://picsum.photos/350/165?random"
                                                                                            class="white--text align-end"
                                                                                            gradient="to bottom, rgba(0,0,0,.1), rgba(0,0,0,.5)"
                                                                                            height="300px"
                                                                                        >
                                                                                            
                                                                                        </v-img>
                                                                                        
                                                                                    <v-card-actions>
                                                                                        <v-btn
                                                                                                color="primary"
                                                                                        >
                                                                                            CHOOSE
                                                                                        </v-btn>
                                                                                    </v-card-actions>
                                                                                    
                                                                                    
                                                                                </v-card>
                                                                            </v-col>
                                                                        </v-card>
                                                                    </v-dialog>
                                                                </v-col>
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
                                                                <v-sheet
                                                                    :color="colors[i]"
                                                                    height="100%"
                                                                >
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
                                                    <!--End for card for favorites-->
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
import { getParsedToken } from "./../../../util/token";
export default {
  data() {
    return {
      comments: [
        {
          id: 1,
          description: "Amazing photo!<3 <3 <3",
          username: "anaperisic12",
        },
        {
          id: 2,
          description: "WOW!Love this!!!",
          username: "pipidugacarapa25",
        },
      ],
      menu: false,
      colors: [
          'indigo',
          'warning',
          'pink darken-2',
          'red lighten-1',
          'deep-purple accent-4',
        ],
        slides: [
          'First',
          'Second',
          'Third',
          'Fourth',
          'Fifth',
        ],
      snackbar: false,
      snackbarText: "",
      loading: false,
      tabs: null,
      tabs2: null,
      e1: 1,
      e2: 1,
      e3: 1,
      e4: 1,
      my_post: {
        title: "",
        // location: null,
        description: "",
        is_add: false,
        add_link: "/",
        content: [],
      },
      user_id: "",
      isForCloseFriends: false,
      regularUser: {},
      form: {
        email: "",
        password: "",
        showPassword: false,
        repeatPassword: "",
        showRepeatPassword: false,
        name: "",
        surname: "",
        username: "",
        phonenumber: "",
        birthayDate:  "", 
        gender: "", 
        webSite: "",
        biography: "",
        photoUrl: ""
      },
      openedContenDialog: null,
      selectedFiles: [],
      user: null,
      posts: [],
      stories: [],
    };
  },
  computed: {},
  mounted() {
     this.axios
      .get(
        process.env.VUE_APP_BACKEND_URL +
          process.env.VUE_APP_PROFILE_ENDPOINT,
          {
            headers: {
              Authorization: "Bearer " + localStorage.getItem("JWT-CPIS"),
          }
          }
      )
      .then((resp) => {
        this.regularUser = resp.data;
        this.form.email = this.regularUser.email;
        this.form.name = this.regularUser.name;
        this.form.surname = this.regularUser.surname;
        this.form.username = this.regularUser.username;
        this.form.phonenumber = this.regularUser.phonenumber;
        this.form.birthayDate = new Date(this.regularUser.birthdaydate).toISOString().substr(0, 10),
        this.form.gender = this.regularUser.gender;
        this.form.biography = this.regularUser.bio;
        this.form.photoUrl = this.regularUser.photoUrl;
        this.form.webSite = this.regularUser.website;
        this.form.bio = this.regularUser.bio;
      })
      .catch((error) => {
        alert("Error: " + error);
      });
    // Get user by username and his posts
    axios
      .get(
        process.env.VUE_APP_BACKEND_URL +
          process.env.VUE_APP_USER +
          process.env.VUE_APP_USER_USERNAME +
          "/" +  getParsedToken().sub, // TODO: Fix endpoint to read from header user-username}
          {
            headers: {
              Authorization: "Bearer " + localStorage.getItem("JWT-CPIS"),
            }
          }
      )
      .then((res) => {
        this.user = res.data;
        this.user_id = res.data._id;
        if (this.user.posts != null) {
          this.posts = [];
          this.user.posts.forEach((post) => {
            this.posts.push({
              _id: post.post_id,
              path: post.first_content.path,
              type: post.first_content.type,
              flex: 6,
            });
          });
        }

        if (this.user.stories != null) {
          this.stories = [];
          this.user.stories.forEach((story) => {
            this.stories.push({
              _id: story.post_id,
              path: story.first_content.path,
              type: story.first_content.type,
              flex: 6,
            });
          });
        }
      });
  },
  methods: {
    saveChanges() {
      axios.all([
      axios.put(process.env.VUE_APP_BACKEND_URL + process.env.VUE_APP_PROFILE_ENDPOINT, {
          username: getParsedToken().sub,
          name: this.form.name,
          email:  this.form.email,
          surname: this.form.surname,
          phonenumber : this.form.phonenumber,
          birthdaydate : this.form.birthayDate,
          gender : this.form.gender,
          website : this.form.webSite,
          bio : this.form.bio,
          newusername: this.form.username,
        },
        {
          headers: {
           Authorization: "Bearer " + localStorage.getItem("JWT-CPIS"),
          }
        },
        ),
      axios.post(process.env.VUE_APP_BACKEND_URL + process.env.VUE_APP_UPDATEUSER_CONTENTENDPOINT, {
          _id: this.user_id,
          username: getParsedToken().sub,
          new_username: this.form.username,
          },
          {
            headers: {
            Authorization: "Bearer " + localStorage.getItem("JWT-CPIS"),
          }
          },
      ),
    ])
    .then((resp) => {
          alert("Successfully changed.");
           this.regularUser = resp.data;
           this.form.username = this.regularUser.username;
           this.form.name = this.regularUser.name;
           this.form.surname = this.regularUser.surname;
           this.form.phonenumber = this.regularUser.phonenumber;
           this.form.dateOfBirth = this.regularUser.dateOfBirth;
           this.form.gender = this.regularUser.gender;
           this.form.linkToWebSite = this.regularUser.linkToWebSite;
           this.form.bio = this.regularUser.bio;
        })
        .catch((error) => {
          alert("Error: " + error);
      });
    },
    getImageUrl(post) {
      return (
        process.env.VUE_APP_BACKEND_URL +
        process.env.VUE_APP_FILE_ENDPOINT +
        post.path
      );
    },
    createPost() {
      this.my_post.post_creator_ref = this.user._id;
      axios
        .post(
          process.env.VUE_APP_BACKEND_URL + process.env.VUE_APP_POST + "/",
          {
            my_post: this.my_post,
          },
          {
            headers: {
              Authorization: "Bearer " + localStorage.getItem("JWT-CPIS"),
            }
          }
        )
        .then((res) => {
          console.log(res);
        });
    },
    createStory() {
      this.my_post.post_creator_ref = this.user._id;
      axios
        .post(
          process.env.VUE_APP_BACKEND_URL + process.env.VUE_APP_STORY + "/",
          {
            my_post: this.my_post,
            is_for_close_friends: this.isForCloseFriends,
          },
          {
            headers: {
              Authorization: "Bearer " + localStorage.getItem("JWT-CPIS"),
            }
          }
        )
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

      axios
        .post(
          process.env.VUE_APP_BACKEND_URL +
            process.env.VUE_APP_FILE_ENDPOINT,
          fd
        )
        .then((res) => {
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