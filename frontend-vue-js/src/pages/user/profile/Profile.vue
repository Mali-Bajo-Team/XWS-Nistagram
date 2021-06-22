<template>
  <v-row :key="componentKey" class="pa-5">
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
            :src="getImageUrlByPATH(form.photoUrl)"
          >
          </v-img>

          <!--Username and description-->
          <h3 class="text-justify">
            {{ form.username }}
            <v-icon v-if="isVerifiedUserVar">mdi-check-circle</v-icon>
          </h3>
          <div class="font-weight-medium text-justify">
            {{ form.bio }}
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
          <h3>{{ followers.length }}</h3>
          Followers
        </v-col>
        <!--End of the number of followers-->

        <!--Column for the number of following-->
        <v-col class="pa-4 mt-2">
          <h3>{{ following.length }}</h3>
          Following
        </v-col>
        <!--Column for the number of following-->
      </v-row>
      <!-- End of profile image, number of posts, followers, following -->

      <!-- Edit profile, add regular posts, stories, highlights, collections -->
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
                Edit profile info
              </v-btn>
            </template>
            <v-card>
              <!--Edit profile fields-->
              <v-card-title class="headline"> Edit your profile </v-card-title>
              <v-card-text>
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
                <v-btn color="primary" text @click="saveChanges()">
                  Save
                </v-btn>
              </v-card-actions>
            </v-card>
          </v-dialog>
        </v-col>
        <!--End of the column for edit profile button-->
        <v-col class="pa-3">
          <!--Dialog and button for editing profile picture-->
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
                Edit privacy settings
              </v-btn>
            </template>
            <v-card>
              <!--Edit privacy settings-->
              <v-card-title class="headline">
                Edit privacy settings
              </v-card-title>
              <p class="ml-5" v-if="form.isprivate == true">
                Your profile is private
              </p>
              <p class="ml-5" v-if="form.isprivate == false">
                Your profile is public
              </p>
              <p class="ml-5" v-if="form.isallowedmessages == true">
                You allowed messages from unfollowed profiles
              </p>
              <p class="ml-5" v-if="form.isallowedmessages == false">
                You baned messages from unfollowed profiles
              </p>
              <p class="ml-5" v-if="form.isallowedtags == true">
                You allowed tags from unfollowed profiles
              </p>
              <p class="ml-5" v-if="form.isallowedtags == false">
                You baned tags from unfollowed profiles
              </p>

              <v-card-text>
                <v-btn
                  outlined
                  rounded
                  color="primary"
                  text
                  @click="setPublicPrivacySettings()"
                >
                  SET PUBLIC
                </v-btn>
                <v-btn
                  class="ml-5"
                  outlined
                  rounded
                  color="primary"
                  text
                  @click="setPrivatePrivacySettings()"
                >
                  SET PRIVATE
                </v-btn>
              </v-card-text>
              <v-card-text>
                <v-btn
                  class="mb-5"
                  outlined
                  rounded
                  color="primary"
                  text
                  @click="allowMessagesPrivacySettings()"
                >
                  ALLOW MESSAGES FROM UNFOLLOWED PROFILES
                </v-btn>
                <v-btn
                  outlined
                  rounded
                  color="primary"
                  text
                  @click="banMessagesPrivacySettings()"
                >
                  BAN MESSAGES FROM UNFOLLOWED PROFILES
                </v-btn>
              </v-card-text>
              <v-card-text>
                <v-btn
                  outlined
                  rounded
                  color="primary"
                  text
                  @click="allowTagsPrivacySettings()"
                >
                  ALLOW TAGS
                </v-btn>
                <v-btn
                  class="ml-5"
                  outlined
                  rounded
                  color="primary"
                  text
                  @click="banTagsPrivacySettings()"
                >
                  BAN TAGS
                </v-btn>
              </v-card-text>
              <!--End of edit privacy settings-->
              <v-card-actions>
                <v-spacer></v-spacer>
              </v-card-actions>
            </v-card>
          </v-dialog>
        </v-col>
        <!--End of the column for edit profile button-->
        <v-col class="pa-3">
          <!--Dialog and button for editing profile picture-->
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
                Edit profile picture
              </v-btn>
            </template>
            <v-card>
              <!--Edit profile picture-->
              <v-card-title class="headline">
                Edit your profile picture
              </v-card-title>
              <v-card-text>
                <v-file-input
                  small-chips
                  multiple
                  show-size
                  counter
                  accept="image/png, image/jpeg, image/bmp"
                  label="Choose a profile"
                  prepend-icon="mdi-camera"
                  @change="onFileSelected"
                ></v-file-input>
              </v-card-text>
              <!--End of edit profile picture-->
              <v-card-actions>
                <v-spacer></v-spacer>
                <v-btn color="error" text> Cancel </v-btn>
                <v-btn color="primary" text @click="saveProfilePicture()">
                  Save
                </v-btn>
              </v-card-actions>
            </v-card>
          </v-dialog>
        </v-col>
        <!--Column for add regular posts, stories, highlights, collections-->
        <v-col class="text-right mr-5 mb-5">
          <!--Dialog for adding regular posts, stories, highlights, collections -->
          <v-dialog width="700px" v-model="openedContenDialog">
            <v-tabs v-model="tabs" icons-and-text>
              <v-tabs-slider></v-tabs-slider>
              <v-tab>Regular posts<v-icon>mdi-camera</v-icon></v-tab>
              <v-tab>Stories<v-icon>mdi-camera-iris</v-icon></v-tab>
              <v-tab>Highlights<v-icon>mdi-star-circle-outline</v-icon></v-tab>
              <v-tab>Collections<v-icon>mdi-check-circle</v-icon></v-tab>
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
                          <v-text-field
                            label="Choose a location"
                            prepend-icon="mdi-map-marker-star"
                            v-model="my_post.location_name"
                          >
                          </v-text-field>
                          <!--End of location-->

                          <!--Tagging-->
                          <br />
                          <v-text-field
                            label="Hashtags"
                            prepend-icon="mdi-tag"
                            v-model="hashtagText"
                          ></v-text-field>
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
                            @click="createPostWithGeocode(), (e1 = 3)"
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
                          <v-text-field
                            label="Choose a location"
                            prepend-icon="mdi-map-marker-star"
                            v-model="my_post.location_name"
                          >
                          </v-text-field>
                          <!--End of location-->

                          <!--Tagging-->
                          <br />
                          <v-text-field
                            label="Hashtags"
                            prepend-icon="mdi-tag"
                            v-model="hashtagText"
                          ></v-text-field>
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
                            @click="createStoryWithGeocode(), (e2 = 3)"
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
                            @click="
                              (openedContenDialog = false), forceRerender()
                            "
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
                            v-model="new_album_name"
                            prepend-icon="mdi-image-album"
                            label="Name of album"
                          ></v-text-field>
                          <!--End of the name of album-->

                          <v-btn
                            color="primary"
                            @click="createHighlight(), (e3 = 3)"
                          >
                            Continue
                          </v-btn>

                          <v-btn text> Cancel </v-btn>
                        </v-stepper-content>
                        <!--End of step 2-->

                        <!--Step 3-->
                        <v-stepper-content step="3">
                          <h3>
                            Congratulations, you have successfully created new
                            one story highlight. Go and add some stories on it !
                          </h3>

                          <v-spacer></v-spacer>
                          <br />
                          <v-btn
                            color="primary"
                            @click="
                              (openedContenDialog = false), forceRerender()
                            "
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

              <!--Tab for collections-->
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
                            v-model="new_album_name"
                            prepend-icon="mdi-image-album"
                            label="Name of album"
                          ></v-text-field>
                          <!--End of the name of album-->

                          <v-btn
                            color="primary"
                            @click="createCollection(), (e4 = 3)"
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
                            @click="
                              (openedContenDialog = false), forceRerender()
                            "
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
              <!--End of tab for collections-->
            </v-tabs-items>
          </v-dialog>
          <!-- End of the dialog for adding regular posts, stories, highlights, collections -->

          <!-- Dialog for verification request -->
          <v-dialog width="600px" v-model="openedVerificationDialog">
            <template v-slot:activator="{ on, attrs }">
              <v-btn dark small fab color="blue" v-bind="attrs" v-on="on">
                <v-icon dark> mdi-star-plus </v-icon>
              </v-btn>
            </template>
            <!-- Dialog content -->

            <v-card>
              <v-card-title class="headline">
                Create verification request
              </v-card-title>
              <v-card-text>
                <!--Stepper-->
                <v-stepper v-model="e1">
                  <v-stepper-header>
                    <v-stepper-step :complete="e1 > 1" step="1">
                      Official document
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

                      <v-btn color="primary" @click="createContent(), (e1 = 2)">
                        Continue
                      </v-btn>

                      <v-btn text> Cancel </v-btn>
                    </v-stepper-content>
                    <!--End of step 1-->

                    <!--Step 2-->
                    <v-stepper-content step="2">
                      <!-- Chose a realName -->
                      <v-text-field
                        v-model="verificationRequest.realName"
                        label="Choose a real name"
                      >
                      </v-text-field>
                      <!-- End of realName-->
                      <!-- Chose a realSurname -->
                      <v-text-field
                        v-model="verificationRequest.realSurname"
                        label="Choose a real surname"
                      >
                      </v-text-field>
                      <!-- End of realName-->

                      <!-- User categories -->
                      <v-select
                        v-model="verificationRequest.category"
                        :items="userCategories"
                        label="User category"
                        outlined
                      ></v-select>
                      <!-- End of the user categories -->

                      <v-btn
                        color="primary"
                        @click="createVerificationRequest(), (e1 = 3)"
                      >
                        Continue
                      </v-btn>

                      <v-btn text @click="openedVerificationDialog = false">
                        Close
                      </v-btn>
                    </v-stepper-content>
                    <!--End of step 2-->

                    <!--Step 3-->
                    <v-stepper-content step="3">
                      <h3>
                        Congratulations, you have successfully created
                        verification request !
                      </h3>

                      <v-spacer></v-spacer>
                      <br />
                      <v-btn
                        color="primary"
                        @click="openedVerificationDialog = false"
                      >
                        Close
                      </v-btn>
                    </v-stepper-content>
                    <!--End of step 3-->
                  </v-stepper-items>
                </v-stepper>
              </v-card-text>
            </v-card>

            <!-- End of the dialog content -->
          </v-dialog>
          <!-- End of the dialog for verification request -->
        </v-col>
        <!--End of the column for add regular posts, stories, highlights, collections-->
      </v-row>
      <!-- End of the edit profile, add regular posts, stories, highlights, collections -->

      <!-- Posts, stories, highlights, saved, followers, following, verification requests -->
      <v-row>
        <v-tabs v-model="tabs2" icons-and-text background-color="transparent">
          <v-tabs-slider></v-tabs-slider>
          <v-tab>Posts<v-icon>mdi-camera</v-icon></v-tab>
          <v-tab>Stories<v-icon>mdi-camera-iris</v-icon></v-tab>
          <v-tab>Highlights<v-icon>mdi-star-circle-outline</v-icon></v-tab>
          <v-tab>Saved<v-icon>mdi-check-circle</v-icon></v-tab>
          <v-tab>Liked<v-icon>mdi-check-circle</v-icon></v-tab>
          <v-tab>Disliked<v-icon>mdi-check-circle</v-icon></v-tab>
          <v-tab>Followers<v-icon>mdi-tag</v-icon></v-tab>
          <v-tab>Following<v-icon>mdi-tag</v-icon></v-tab>
        </v-tabs>
        <v-tabs-items v-model="tabs2">
          <!--Tab for photos and videos-->
          <v-tab-item>
            <v-card class="mx-auto" max-width="500">
              <v-container fluid>
                <v-row dense>
                  <v-col
                    v-for="post in posts"
                    :key="post._id"
                    cols="6"
                  >
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

                        <v-btn @click="addToSaved(post)" icon>
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
                  <v-col
                    v-for="story in stories"
                    :key="story.post_id"
                    cols="6"
                  >
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
              <v-container v-if="user" fluid>
                <v-row dense>
                  <v-col
                    v-for="storyHighlight in user.story_highlights"
                    :key="storyHighlight._id"
                    :cols="6"
                  >
                    <!-- Image previw -->
                    <v-card>
                      <v-img
                        :src="
                          getImageUrlByPATH(storyHighlight.cover_image.path)
                        "
                        class="white--text align-end"
                        gradient="to bottom, rgba(0,0,0,.1), rgba(0,0,0,.5)"
                        height="200px"
                      >
                      </v-img>
                      <v-card-text>
                        <v-row>
                          <v-col>
                            <h3>{{ storyHighlight.name }}</h3>
                          </v-col>
                          <v-col class="text-right mr-5 mb-5">
                            <!-- Dialog to preview highlight -->
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
                                    <v-col> {{ storyHighlight.name }} </v-col>
                                    <v-col class="text-right">
                                      <v-dialog
                                        width="600px"
                                        v-model="
                                          openedAddStoryToHighlightDialog
                                        "
                                      >
                                        <template
                                          v-slot:activator="{ on, attrs }"
                                        >
                                          <!--Button for adding stories to album-->
                                          <v-btn
                                            dark
                                            x-small
                                            fab
                                            color="primary"
                                            v-bind="attrs"
                                            v-on="on"
                                          >
                                            <v-icon> mdi-plus </v-icon>
                                          </v-btn>
                                          <!--End of button for adding stories to album-->
                                        </template>
                                        <v-card>
                                          <v-card-title>
                                            Choose a story you want to highlight
                                          </v-card-title>
                                          <v-col>
                                            <!--Stories-->
                                            <v-card
                                              v-for="tempStory in stories"
                                              :key="tempStory._id"
                                              class="mb-5"
                                            >
                                              <v-img
                                                :src="
                                                  getImageUrlByPATH(
                                                    tempStory.path
                                                  )
                                                "
                                                class="white--text align-end"
                                                gradient="to bottom, rgba(0,0,0,.1), rgba(0,0,0,.5)"
                                                height="300px"
                                              >
                                              </v-img>

                                              <v-card-actions>
                                                <v-btn
                                                  @click="
                                                    addStoryToHighlight(
                                                      tempStory,
                                                      storyHighlight._id
                                                    )
                                                  "
                                                  color="primary"
                                                >
                                                  CHOOSE
                                                </v-btn>
                                              </v-card-actions>
                                            </v-card>
                                            <!-- End of the stories -->
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
                                    v-for="(slide, i) in storyHighlight.content"
                                    :key="i"
                                  >
                                    <video
                                      v-if="
                                        getStoryByID(slide.story_id).type ==
                                        'video'
                                      "
                                      controls
                                      :src="
                                        getImageUrlByPATH(
                                          getStoryByID(slide.story_id).path
                                        )
                                      "
                                      class="white--text align-end"
                                      gradient="to bottom, rgba(0,0,0,.1), rgba(0,0,0,.5)"
                                      height="200px"
                                      width="100%"
                                    ></video>

                                    <v-img
                                      v-if="
                                        getStoryByID(slide.story_id).type ==
                                        'image'
                                      "
                                      :src="
                                        getImageUrlByPATH(
                                          getStoryByID(slide.story_id).path
                                        )
                                      "
                                    ></v-img>
                                  </v-carousel-item>
                                </v-carousel>
                                <!--End of list of photos-->
                              </v-card>
                              <!--End for card for highlights-->
                            </v-dialog>
                            <!-- End of the highlight preview -->
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
              <v-container v-if="user" fluid>
                <v-row dense>
                  <!-- Saved collection -->
                  <v-col :cols="6">
                    <v-card>
                      <v-img
                        src="https://picsum.photos/350/165?random"
                        class="white--text align-end"
                        gradient="to bottom, rgba(0,0,0,.1), rgba(0,0,0,.5)"
                        height="200px"
                      >
                      </v-img>
                      <v-card-text>
                        <v-row>
                          <v-col>
                            <h3>Saved</h3>
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
                                    <v-col> Saved favorites posts </v-col>
                                  </v-row>
                                </v-card-title>
                                <!--List of photos-->
                                <v-carousel
                                  v-if="user"
                                  cycle
                                  height="400"
                                  hide-delimiter-background
                                  show-arrows-on-hover
                                >
                                  <v-carousel-item
                                    v-for="(tempRegularPost, i) in user.saved
                                      .regular_posts"
                                    :key="i"
                                  >
                                    <video
                                      v-if="
                                        tempRegularPost.my_post.content[0]
                                          .type == 'video'
                                      "
                                      controls
                                      :src="
                                        getImageUrlByPATH(
                                          tempRegularPost.my_post.content[0]
                                            .path
                                        )
                                      "
                                      class="white--text align-end"
                                      gradient="to bottom, rgba(0,0,0,.1), rgba(0,0,0,.5)"
                                      height="200px"
                                      width="100%"
                                    ></video>

                                    <v-img
                                      v-if="
                                        tempRegularPost.my_post.content[0]
                                          .type == 'image'
                                      "
                                      :src="
                                        getImageUrlByPATH(
                                          tempRegularPost.my_post.content[0]
                                            .path
                                        )
                                      "
                                    ></v-img>
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
                  <!-- End of the saved collection -->
                  <!-- Custom made collections -->
                  <v-col
                    v-for="tempCollection in user.collections"
                    :key="tempCollection._id"
                    :cols="6"
                  >
                    <v-card>
                      <v-img
                        :src="
                          getImageUrlByPATH(tempCollection.cover_image.path)
                        "
                        class="white--text align-end"
                        gradient="to bottom, rgba(0,0,0,.1), rgba(0,0,0,.5)"
                        height="200px"
                      >
                      </v-img>
                      <v-card-text>
                        <v-row>
                          <v-col>
                            <h3>{{ tempCollection.name }}</h3>
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
                                    <v-col> {{ tempCollection.name }} </v-col>
                                    <v-col class="text-right">
                                      <v-dialog width="600px">
                                        <template
                                          v-slot:activator="{ on, attrs }"
                                        >
                                          <!--Button for adding saved content to album-->
                                          <v-btn
                                            dark
                                            x-small
                                            fab
                                            color="primary"
                                            v-bind="attrs"
                                            v-on="on"
                                          >
                                            <v-icon> mdi-plus </v-icon>
                                          </v-btn>
                                          <!--End of button for adding saved content to album-->
                                        </template>
                                        <v-card>
                                          <v-card-title>
                                            Choose a saved content you want to
                                            add to collection
                                          </v-card-title>
                                          <v-col>
                                            <!--Stories-->
                                            <v-card
                                              v-for="tempPost in posts"
                                              :key="tempPost._id"
                                              class="mb-5"
                                            >
                                              <v-img
                                                :src="
                                                  getImageUrlByPATH(
                                                    tempPost.path
                                                  )
                                                "
                                                class="white--text align-end"
                                                gradient="to bottom, rgba(0,0,0,.1), rgba(0,0,0,.5)"
                                                height="300px"
                                              >
                                              </v-img>

                                              <v-card-actions>
                                                <v-btn
                                                  @click="
                                                    addPostToCollection(
                                                      tempPost,
                                                      tempCollection._id
                                                    )
                                                  "
                                                  color="primary"
                                                >
                                                  CHOOSE
                                                </v-btn>
                                              </v-card-actions>
                                            </v-card>
                                            <!-- End of the stories -->
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
                                    v-for="(
                                      regularPostTemp, i
                                    ) in tempCollection.regular_posts"
                                    :key="i"
                                  >
                                    <video
                                      v-if="
                                        getPostByID(regularPostTemp._id).type ==
                                        'video'
                                      "
                                      controls
                                      :src="
                                        getImageUrlByPATH(
                                          getPostByID(regularPostTemp._id).path
                                        )
                                      "
                                      class="white--text align-end"
                                      gradient="to bottom, rgba(0,0,0,.1), rgba(0,0,0,.5)"
                                      height="200px"
                                      width="100%"
                                    ></video>

                                    <v-img
                                      v-if="
                                        getPostByID(regularPostTemp._id).type ==
                                        'image'
                                      "
                                      :src="
                                        getImageUrlByPATH(
                                          getPostByID(regularPostTemp._id).path
                                        )
                                      "
                                    ></v-img>
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
                  <!-- End of the custom made collections -->
                </v-row>
              </v-container>
            </v-card>
          </v-tab-item>
          <!--End of tab for saved/favorites-->

          <!--Tab for liked posts-->
          <v-tab-item>
            <reactionComponent reactiontype="like"></reactionComponent>
          </v-tab-item>
          <!--End of tab for liked posts-->

          <!--Tab for disliked posts-->
          <v-tab-item>
            <reactionComponent reactiontype="dislike"></reactionComponent>
          </v-tab-item>
          <!--End of tab for liked posts-->

          <!--Tab for followers-->
          <v-tab-item>
            <v-card v-if="followers.length > 0" flat>
              <v-list>
                <v-list-item-group color="primary">
                  <v-list-item v-for="(user, i) in followers" :key="i">
                    <v-list-item-avatar>
                      <v-img :src="user.profileImagePath"></v-img>
                    </v-list-item-avatar>
                    <v-list-item-content>
                      <v-list-item-title
                        v-text="user.username"
                      ></v-list-item-title>
                    </v-list-item-content>

                    <v-list-item-action>
                      <v-btn
                        outlined
                        rounded
                        medium
                        color="primary"
                        v-if="!user.isClose"
                        @click="addToClose(user)"
                      >
                        <v-icon left> mdi-plus </v-icon>
                        Add to close friends
                      </v-btn>
                      <v-btn
                        outlined
                        rounded
                        medium
                        color="danger"
                        v-if="user.isClose"
                        @click="removeFromClose(user)"
                      >
                        <v-icon left> mdi-plus </v-icon>
                        Remove from close friends
                      </v-btn>
                    </v-list-item-action>
                  </v-list-item>
                </v-list-item-group>
              </v-list>
            </v-card>
          </v-tab-item>
          <!--End of tab for followers-->

          <!--Tab for following-->
          <v-tab-item>
            <v-card v-if="following.length > 0" flat>
              <v-list>
                <v-list-item-group color="primary">
                  <v-list-item
                    v-for="(user, i) in following"
                    :key="i"
                    @click="userClicked(user)"
                  >
                    <v-list-item-avatar>
                      <v-img :src="user.profileImagePath"></v-img>
                    </v-list-item-avatar>
                    <v-list-item-content>
                      <v-list-item-title
                        v-text="user.username"
                      ></v-list-item-title>
                    </v-list-item-content>
                  </v-list-item>
                </v-list-item-group>
              </v-list>
            </v-card>
          </v-tab-item>
          <!--End of tab for following-->
          
        </v-tabs-items>
      </v-row>
      <!-- End of posts, highlights, stories, saved, followers, following, verification requests -->
    </v-col>

    <v-spacer></v-spacer>
  </v-row>
</template>

<script>
import axios from "axios";
import { getParsedToken } from "./../../../util/token";
import postComponent from "../../../components/Post.vue";
import storyComponent from "../../../components/Story.vue";
import reactionComponent from "../../../components/Reactions.vue";

export default {
  components: {
    postComponent,
    storyComponent,
    reactionComponent
  },
  data() {
    return {
      isVerifiedUserVar: false,
      verificationRequests: [],
      userCategories: [
        "Influencer",
        "Sports",
        "News/Media",
        "Business",
        "Brand",
        "Organization",
      ], // TODO: Make API to get real one categories
      verificationRequest: {
        realName: "",
        realSurname: "",
        category: "",
        imageOfOfficialDocument: "",
      },
      openedVerificationDialog: false,
      componentKey: 0,
      forceUpdateKey: {
        dialogForPostDetails: 0,
      },
      followers: [],
      following: [],
      menu: false,
      colors: [
        "indigo",
        "warning",
        "pink darken-2",
        "red lighten-1",
        "deep-purple accent-4",
      ],
      slides: ["First", "Second", "Third", "Fourth", "Fifth"],
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
        location_name: "",
        hashtags: "",
        description: "",
        is_add: false,
        add_link: "/",
        content: [],
      },
      hashtagText: "",
      profileContent: [],
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
        birthayDate: "",
        gender: "",
        webSite: "",
        biography: "",
        photoUrl: "",
        isprivate: null,
        isallowedmessages: null,
        isallowedtags: null,
      },
      openedContenDialog: null,
      openedNewCommentDialog: null,
      openedAddStoryToHighlightDialog: null,
      selectedFiles: [],
      user: null,
      posts: [],
      stories: [],
      newCommentContent: "",
      entirePost: null,
      entireStory: null,
      liked: "",
      disliked: "",
      new_album_name: "",
      new_file_content: "",
    };
  },
  computed: {},
  mounted() {    
    this.axios
      .get(
        process.env.VUE_APP_BACKEND_URL + process.env.VUE_APP_FOLLOWER_ENDPOINT,
        {
          headers: {
            Authorization: "Bearer " + localStorage.getItem("JWT-CPIS"),
          },
        }
      )
      .then((response) => {
        let users = response.data;
        for (let user of users) {
          if (user.profileImagePath) {
            user.profileImagePath =
              process.env.VUE_APP_BACKEND_URL +
              process.env.VUE_APP_FILE_ENDPOINT +
              user.profileImagePath;
          } else {
            user.profileImagePath =
              "https://icon-library.com/images/default-user-icon/default-user-icon-4.jpg";
          }
        }
        this.followers = users;
      })
      .catch((error) => {
        console.log(error);
      });

    this.axios
      .get(
        process.env.VUE_APP_BACKEND_URL +
          process.env.VUE_APP_FOLLOWING_ENDPOINT,
        {
          headers: {
            Authorization: "Bearer " + localStorage.getItem("JWT-CPIS"),
          },
        }
      )
      .then((response) => {
        let users = response.data;
        for (let user of users) {
          if (user.profileImagePath) {
            user.profileImagePath =
              process.env.VUE_APP_BACKEND_URL +
              process.env.VUE_APP_FILE_ENDPOINT +
              user.profileImagePath;
          } else {
            user.profileImagePath =
              "https://icon-library.com/images/default-user-icon/default-user-icon-4.jpg";
          }
        }
        this.following = users;
      })
      .catch((error) => {
        console.log(error);
      });

    // Get user profile
    this.axios
      .get(
        process.env.VUE_APP_BACKEND_URL + process.env.VUE_APP_PROFILE_ENDPOINT,
        {
          headers: {
            Authorization: "Bearer " + localStorage.getItem("JWT-CPIS"),
          },
        }
      )
      .then((resp) => {
        console.log(
          "----------------------------- profile start ------------------------"
        );
        console.log(resp);
        this.regularUser = resp.data;
        this.form.email = this.regularUser.email;
        this.form.name = this.regularUser.name;
        this.form.surname = this.regularUser.surname;
        this.form.username = this.regularUser.username;
        this.form.phonenumber = this.regularUser.phonenumber;
        (this.form.birthayDate = new Date(this.regularUser.birthdaydate)
          .toISOString()
          .substr(0, 10)),
          (this.form.gender = this.regularUser.gender);
        this.form.biography = this.regularUser.bio;
        this.form.photoUrl = this.regularUser.photoUrl;
        this.form.webSite = this.regularUser.website;
        this.form.bio = this.regularUser.bio;
        this.form.isprivate = this.regularUser.isprivate;
        this.form.isallowedmessages = this.regularUser.isallowedmessages;
        this.form.isallowedtags = this.regularUser.isallowedtags;
        // check is user verified
        axios
          .get(
            process.env.VUE_APP_BACKEND_URL +
              process.env.VUE_APP_VERIFIED_VERIFICATION_REQUEST +
              this.regularUser.username,
            {
              headers: {
                Authorization: "Bearer " + localStorage.getItem("JWT-CPIS"),
              },
            }
          )
          .then((res) => {
            this.isVerifiedUserVar = res.data.verifiedUser;
          });

        console.log(
          "----------------------------- profile end ------------------------"
        );
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
          "/" +
          getParsedToken().sub, // TODO: Fix endpoint to read from header user-username}
        {
          headers: {
            Authorization: "Bearer " + localStorage.getItem("JWT-CPIS"),
          },
        }
      )
      .then((res) => {
        this.user = res.data;
        this.user_id = res.data._id;
        console.log("user id: " + this.user._id);
        if (this.user.posts != null) {
          this.posts = [];
          this.user.posts.forEach((post) => {
            this.posts.push({
              _id: post.post_id,
              path: post.first_content.path,
              type: post.first_content.type
            });
          });
        }

        if (this.user.stories != null) {
          this.stories = [];
          this.user.stories.forEach((story) => {
            console.log(story);
            this.stories.push({
              _id: story.story_id,
              path: story.first_content.path,
              type: story.first_content.type
            });
          });
        }

        // Get a user highlights
        this.axios
          .get(
            process.env.VUE_APP_BACKEND_URL +
              process.env.VUE_APP_CONTENT_HIGHLIGHTS +
              "/" +
              this.user._id,
            {
              headers: {
                Authorization: "Bearer " + localStorage.getItem("JWT-CPIS"),
              },
            }
          )
          .then((res) => {
            console.log("------ highlight start ------ ");
            console.log(res);
            this.user.story_highlights = res.data;
            console.log("------ highlight end ------ ");
          })
          .catch((error) => {
            console.log(error);
          });

        // Get a user saved collection
        this.axios
          .get(
            process.env.VUE_APP_BACKEND_URL +
              process.env.VUE_APP_CONTENT_USER_SAVED +
              this.user._id,
            {
              headers: {
                Authorization: "Bearer " + localStorage.getItem("JWT-CPIS"),
              },
            }
          )
          .then((res) => {
            console.log("------ saved start ------ ");
            console.log(res);
            this.user.saved.regular_posts = res.data;
            console.log("------ saved end ------ ");
          })
          .catch((error) => {
            console.log(error);
          });
      });
  },
  methods: {
    acceptVerificationRequest(verification) {
      this.axios
        .post(
          process.env.VUE_APP_BACKEND_URL +
            process.env.VUE_APP_ACCEPT_VERIFICATION_REQUEST +
            verification.id,
          {},
          {
            headers: {
              Authorization: "Bearer " + localStorage.getItem("JWT-CPIS"),
            },
          }
        )
        .then((res) => {
          console.log(res);
          for (let verificationRequest of this.verificationRequests) {
            if (verificationRequest.id == verification.id) {
              this.verificationRequests.pop(verificationRequest);
              break;
            }
          }
        })
        .catch((error) => {
          console.log(error);
        });
    },
    rejectVerificationRequest(verification) {
      this.axios
        .post(
          process.env.VUE_APP_BACKEND_URL +
            process.env.VUE_APP_REJECT_VERIFICATION_REQUEST +
            verification.id,
          {},
          {
            headers: {
              Authorization: "Bearer " + localStorage.getItem("JWT-CPIS"),
            },
          }
        )
        .then((res) => {
          console.log(res);
          for (let verificationRequest of this.verificationRequests) {
            if (verificationRequest.id == verification.id) {
              this.verificationRequests.pop(verificationRequest);
              break;
            }
          }
        })
        .catch((error) => {
          console.log(error);
        });
    },
    createVerificationRequest() {
      this.verificationRequest.imageOfOfficialDocument =
        this.new_file_content[0].path;

      this.axios
        .post(
          process.env.VUE_APP_BACKEND_URL +
            process.env.VUE_APP_VERIFICATION_REQUEST,
          this.verificationRequest,
          {
            headers: {
              Authorization: "Bearer " + localStorage.getItem("JWT-CPIS"),
            },
          }
        )
        .then((res) => {
          console.log(res);
        })
        .catch((error) => {
          console.log(error);
        });
    },
    forceRerender() {
      // Posto nisam nasao kako ide re-mount, odradim ovo trenutno
      location.reload();
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
    getStoryByID(storyID) {
      for (let tempStory of this.stories) {
        if (tempStory._id == storyID) {
          return tempStory;
        }
      }
      return null;
    },
    getPostByID(postID) {
      for (let tempPost of this.posts) {
        if (tempPost._id == postID) {
          return tempPost;
        }
      }
      return null;
    },
    addPostToCollection(post, collectionID) {
      console.log("post: " + post + " collectionID: " + collectionID);
      this.axios
        .post(
          process.env.VUE_APP_BACKEND_URL +
            process.env.VUE_APP_CONTENT_USER_COLLECTION +
            collectionID +
            "/" +
            this.user._id,
          post,
          {
            headers: {
              Authorization: "Bearer " + localStorage.getItem("JWT-CPIS"),
            },
          }
        )
        .then((res) => {
          console.log(
            "------- start of the added new post to collection -----------"
          );
          console.log(res);
          console.log(
            "------- end of the added new post to collection -----------"
          );
          this.forceRerender();
        })
        .catch((error) => {
          console.log(error);
        });
    },
    addStoryToHighlight(story, highlightID) {
      this.axios
        .post(
          process.env.VUE_APP_BACKEND_URL +
            process.env.VUE_APP_CONTENT_HIGHLIGHT +
            "/" +
            highlightID +
            "/" +
            this.user._id,
          story,
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

      this.openedAddStoryToHighlightDialog = false;
    },
    createCollection() {
      this.axios
        .post(
          process.env.VUE_APP_BACKEND_URL +
            process.env.VUE_APP_CONTENT_USER_COLLECTIONS +
            this.user._id,
          {
            name: this.new_album_name,
            cover_image: this.new_file_content[0],
          },
          {
            headers: {
              Authorization: "Bearer " + localStorage.getItem("JWT-CPIS"),
            },
          }
        )
        .then((res) => {
          console.log("------- start of the created collection -----------");
          console.log(res);
          console.log("------- end of the created collection -----------");
        })
        .catch((error) => {
          console.log(error);
        });
    },
    createHighlight() {
      console.log(this.new_file_content);
      this.axios
        .post(
          process.env.VUE_APP_BACKEND_URL +
            process.env.VUE_APP_CONTENT_HIGHLIGHTS +
            "/" +
            this.user._id,
          {
            name: this.new_album_name,
            cover_image: this.new_file_content[0],
          },
          {
            headers: {
              Authorization: "Bearer " + localStorage.getItem("JWT-CPIS"),
            },
          }
        )
        .then((res) => {
          console.log(res);
        })
        .catch((error) => {
          console.log(error);
        });
    },
    addToClose(user) {
      this.axios
        .post(
          process.env.VUE_APP_BACKEND_URL +
            process.env.VUE_APP_ADD_CLOSE_FRIEND +
            user.username,
          {},
          {
            headers: {
              Authorization: "Bearer " + localStorage.getItem("JWT-CPIS"),
            },
          }
        )
        .then(() => {
          user.isClose = true;
        })
        .catch((error) => {
          console.log(error);
        });
    },
    removeFromClose(user) {
      this.axios
        .post(
          process.env.VUE_APP_BACKEND_URL +
            process.env.VUE_APP_REMOVE_CLOSE_FRIEND +
            user.username,
          {},
          {
            headers: {
              Authorization: "Bearer " + localStorage.getItem("JWT-CPIS"),
            },
          }
        )
        .then(() => {
          user.isClose = false;
        })
        .catch((error) => {
          console.log(error);
        });
    },
    userClicked(user) {
      this.$router.push("/user/" + user.username);
    },
    getEntirePost(postID) {
      // get entire post
      axios
        .get(
          process.env.VUE_APP_BACKEND_URL +
            process.env.VUE_APP_CONTENT_POST +
            postID,
          {
            headers: {
              Authorization: "Bearer " + localStorage.getItem("JWT-CPIS"),
            },
          }
        )
        .then((res) => {
          this.entirePost = res.data;
        });
    },
    getEntireStory(storyID) {
      // get entire post
      axios
        .get(
          process.env.VUE_APP_BACKEND_URL +
            process.env.VUE_APP_CONTENT_STORY +
            storyID,
          {
            headers: {
              Authorization: "Bearer " + localStorage.getItem("JWT-CPIS"),
            },
          }
        )
        .then((res) => {
          this.entireStory = res.data;
        });
    },
    saveChanges() {
      axios
        .all([
          axios.put(
            process.env.VUE_APP_BACKEND_URL +
              process.env.VUE_APP_PROFILE_ENDPOINT,
            {
              username: getParsedToken().sub,
              name: this.form.name,
              email: this.form.email,
              surname: this.form.surname,
              phonenumber: this.form.phonenumber,
              birthdaydate: this.form.birthayDate,
              gender: this.form.gender,
              website: this.form.webSite,
              bio: this.form.bio,
              newusername: this.form.username,
            },
            {
              headers: {
                Authorization: "Bearer " + localStorage.getItem("JWT-CPIS"),
              },
            }
          ),
          axios.post(
            process.env.VUE_APP_BACKEND_URL +
              process.env.VUE_APP_UPDATEUSER_CONTENTENDPOINT,
            {
              _id: this.user_id,
              username: getParsedToken().sub,
              new_username: this.form.username,
            },
            {
              headers: {
                Authorization: "Bearer " + localStorage.getItem("JWT-CPIS"),
              },
            }
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
    getImageUrlByPATH(path) {
      if (path)
        return (
          process.env.VUE_APP_BACKEND_URL +
          process.env.VUE_APP_FILE_ENDPOINT +
          path
        );
      else
        return "https://icon-library.com/images/default-user-icon/default-user-icon-4.jpg";
    },
    createPostWithGeocode: function () {
      if (!this.my_post.location_name) {
        this.createPost();
      } else {
        this.axios
          .get("https://nominatim.openstreetmap.org/search", {
            params: {
              format: "json",
              q: this.my_post.location_name,
            },
          })
          .then((response) => {
            if (response.data.length == 0) {
              console.log("Geocoding failed, creating post without location.");
              this.createPost();
            } else {
              this.my_post.post_location = {
                type: "Point",
                coordinates: [
                  parseFloat(response.data[0].lon),
                  parseFloat(response.data[0].lat),
                ],
              };
              this.createPost();
            }
          })
          .catch(() => {
            console.log("Geocoding failed, creating post without location.");
            this.createPost();
          });
      }
    },
    createPost() {
      if (this.hashtagText) {
        this.my_post.hashtags = this.hashtagText.split(",");
      }
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
            },
          }
        )
        .then((res) => {
          console.log(res);
        });
    },
    createStoryWithGeocode: function () {
      if (!this.my_post.location_name) {
        this.createStory();
      } else {
        this.axios

          .get("https://nominatim.openstreetmap.org/search", {
            params: {
              format: "json",
              q: this.my_post.location_name,
            },
          })
          .then((response) => {
            if (response.data.length == 0) {
              console.log("Geocoding failed, creating post without location.");
              this.createStory();
            } else {
              this.my_post.post_location = {
                type: "Point",
                coordinates: [
                  parseFloat(response.data[0].lon),
                  parseFloat(response.data[0].lat),
                ],
              };
              this.createStory();
            }
          })
          .catch(() => {
            console.log("Geocoding failed, creating post without location.");
            this.createStory();
          });
      }
    },
    createStory() {
      this.my_post.post_creator_ref = this.user._id;
      if (this.hashtagText) {
        this.my_post.hashtags = this.hashtagText.split(",");
      }
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
            },
          }
        )
        .then((res) => {
          console.log(res);
        });
    },
    createContent() {
      const fd = new FormData();

      const parent = this.selectedFiles;
      for (let selectedFile of parent) {
        if (selectedFile.type.includes("image")) {
          fd.append("image", selectedFile, selectedFile.name);
        } else {
          fd.append("video", selectedFile, selectedFile.name);
        }
      }

      axios
        .post(
          process.env.VUE_APP_BACKEND_URL + process.env.VUE_APP_FILE_ENDPOINT,
          fd
        )
        .then((res) => {
          console.log(res);
          this.new_file_content = res.data;
          this.my_post.content = res.data;
        });
    },
    saveProfilePicture() {
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
          process.env.VUE_APP_BACKEND_URL + process.env.VUE_APP_FILE_ENDPOINT,
          fd
        )
        .then((res) => {
          console.log(res);
          this.profileContent = res.data;
          axios
            .post(
              process.env.VUE_APP_BACKEND_URL +
                process.env.VUE_APP_USER_UPDATE_PROFILEIMAGE,
              {
                username: getParsedToken().sub,
                profileimagepath: this.profileContent[0].path,
              },
              {
                headers: {
                  Authorization: "Bearer " + localStorage.getItem("JWT-CPIS"),
                },
              }
            )
            .then((res) => {
              console.log(res);
            });
        });
    },
    setPrivatePrivacySettings() {
      this.axios
        .post(
          process.env.VUE_APP_BACKEND_URL +
            process.env.VUE_APP_SETRIVATE_ENDPOINT +
            getParsedToken().sub,
          {},
          {
            headers: {
              Authorization: "Bearer " + localStorage.getItem("JWT-CPIS"),
            },
          }
        )
        .then((res) => {
          this.form.isprivate = res.data;
        });
    },
    setPublicPrivacySettings() {
      this.axios
        .post(
          process.env.VUE_APP_BACKEND_URL +
            process.env.VUE_APP_SETPUBLIC_ENDPOINT +
            getParsedToken().sub,
          {},
          {
            headers: {
              Authorization: "Bearer " + localStorage.getItem("JWT-CPIS"),
            },
          }
        )
        .then((res) => {
          this.form.isprivate = res.data;
        });
    },
    allowMessagesPrivacySettings() {
      this.axios
        .post(
          process.env.VUE_APP_BACKEND_URL +
            process.env.VUE_APP_ALLOWMESSAGES_ENDPOINT +
            getParsedToken().sub,
          {},
          {
            headers: {
              Authorization: "Bearer " + localStorage.getItem("JWT-CPIS"),
            },
          }
        )
        .then((res) => {
          this.form.isallowedmessages = res.data;
        });
    },
    banMessagesPrivacySettings() {
      this.axios
        .post(
          process.env.VUE_APP_BACKEND_URL +
            process.env.VUE_APP_BANMESSAGES_ENDPOINT +
            getParsedToken().sub,
          {},
          {
            headers: {
              Authorization: "Bearer " + localStorage.getItem("JWT-CPIS"),
            },
          }
        )
        .then((res) => {
          this.form.isallowedmessages = res.data;
        });
    },
    allowTagsPrivacySettings() {
      this.axios
        .post(
          process.env.VUE_APP_BACKEND_URL +
            process.env.VUE_APP_ALLOWTAGS_ENDPOINT +
            getParsedToken().sub,
          {},
          {
            headers: {
              Authorization: "Bearer " + localStorage.getItem("JWT-CPIS"),
            },
          }
        )
        .then((res) => {
          this.form.isallowedtags = res.data;
        });
    },
    banTagsPrivacySettings() {
      this.axios
        .post(
          process.env.VUE_APP_BACKEND_URL +
            process.env.VUE_APP_BANTAGS_ENDPOINT +
            getParsedToken().sub,
          {},
          {
            headers: {
              Authorization: "Bearer " + localStorage.getItem("JWT-CPIS"),
            },
          }
        )
        .then((res) => {
          this.form.isallowedtags = res.data;
        });
    },
    onFileSelected(event) {
      this.selectedFiles = event;
      console.log(this.selectedFiles);
    },
  },
};
</script>