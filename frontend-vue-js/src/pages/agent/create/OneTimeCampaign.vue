<template>
  <v-row class="pa-5">
    <v-spacer></v-spacer>
    <v-col sm="8" md="6" lg="4" xl="4" cols="12">
      <v-card>
        <v-card-title class="headline"> Add a new photo or video </v-card-title>
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

              <v-stepper-step :complete="e1 > 3" step="3"> Campaign information </v-stepper-step>

              <v-divider></v-divider>

              <v-stepper-step step="4"> Confirmation </v-stepper-step>
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
              </v-stepper-content>
              <!--End of step 1-->

              <!--Step 2-->
              <v-stepper-content step="2">
                <!-- Chose a title -->
                <v-text-field
                  v-model="campaign.post.title"
                  label="Choose a title"
                  prepend-icon="mdi-home"
                >
                </v-text-field>
                <!-- End of title-->

                <!--Add description-->
                <v-textarea
                  v-model="campaign.post.description"
                  outlined
                  name="input-7-4"
                  no-resize
                  rows="3"
                  label="Add description"
                  clearable
                  clear-icon="mdi-close-circle"
                ></v-textarea>
                <!--End of description-->

                <v-btn color="primary" @click="e1 = 3"> Continue </v-btn>
              </v-stepper-content>
              <!--End of step 2-->

              <!--Step 3-->
              <v-stepper-content step="3">
                <v-checkbox
                  v-model="campaign.isStory"
                  :label="`Is story`"
                ></v-checkbox>

                <v-text-field
                  v-model="campaign.exposureTime"
                  label="Choose exposure time"
                  prepend-icon="mdi-home"
                >
                </v-text-field>

                <v-text-field
                  v-model="campaign.interestGroup.minimumAge"
                  label="Choose minimum age"
                  prepend-icon="mdi-home"
                >
                </v-text-field>

                <v-text-field
                  v-model="campaign.interestGroup.maximumAge"
                  label="Choose maximum age"
                  prepend-icon="mdi-home"
                >
                </v-text-field>

                <v-text-field
                  v-model="campaign.interestGroup.gender"
                  label="Choose gender"
                  prepend-icon="mdi-home"
                >
                </v-text-field>

                <v-text-field
                  v-model="campaign.interestGroup.userCategory"
                  label="Choose user category"
                  prepend-icon="mdi-home"
                >
                </v-text-field>

                <v-btn color="primary" @click="submit(), (e1 = 4)">
                  Continue
                </v-btn>
              </v-stepper-content>
              <!--End of step 3-->

              <!--Step 3-->
              <v-stepper-content step="4">
                <h3>
                  Congratulations, you have successfully created an add
                  campaign!
                </h3>
              </v-stepper-content>
              <!--End of step 3-->
            </v-stepper-items>
          </v-stepper>
        </v-card-text>
      </v-card>
    </v-col>
    <v-spacer></v-spacer>
  </v-row>
</template>

<script>
export default {
  data() {
    return {
      e1: 1,
      snackbar: false,
      snackbarText: "",
      loading: false,
      campaign: {
        isStory: false,
        exposureTime: null,
        interestGroup: {
          maximumAge: null,
          minimumAge: null,
          gender: null,
          userCategory: null,
        },
        post: {
          title: "",
          location_name: "",
          hashtags: "",
          description: "",
          is_add: false,
          add_link: "/",
          content: [],
        },
      },
    };
  },
  methods: {
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

      this.axios
        .post(
          process.env.VUE_APP_BACKEND_URL + process.env.VUE_APP_FILE_ENDPOINT,
          fd
        )
        .then((res) => {
          console.log(res);
          this.campaign.post.content = res.data;
        });
    },
    onFileSelected(event) {
      this.selectedFiles = event;
      console.log(this.selectedFiles);
    },
    submit () {
      this.axois
        .post(
          process.env.VUE_APP_BACKEND_URL + "api/private/adds/campaigns/onetime",
          this.campaign,
          {
            headers: {
              Authorization: "Bearer " + localStorage.getItem("JWT-CPIS"),
            },
          }
        )
        .then((res) => {
          console.log(res);
        })
        .catch((error) => console.log(error));
    },
  },
};
</script>