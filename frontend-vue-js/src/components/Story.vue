<template>
  <v-card>
    <v-snackbar v-model="snackbar" :timeout="2000" bottom class="mb-5" right>
      {{ snackbarText }}

      <template v-slot:action="{ attrs }">
        <v-btn color="primary" text v-bind="attrs" @click="snackbar = false">
          Close
        </v-btn>
      </template>
    </v-snackbar>

    <v-card-title>
      <!--List of photos and videos-->
      <v-carousel
        v-if="post.my_post"
        hide-delimiter-background
        show-arrows-on-hover
        delimiter-icon="mdi-minus"
        height="auto"
      >
        <v-carousel-item v-for="(slide, i) in post.my_post.content" :key="i">
          <video
            v-if="slide.type == 'video'"
            controls
            :src="getImageUrl(slide.path)"
            class="white--text align-end"
            gradient="to bottom, rgba(0,0,0,.1), rgba(0,0,0,.5)"
            width="100%"
          ></video>

          <v-img v-if="post.my_post" :src="getImageUrl(slide.path)"></v-img>
        </v-carousel-item>
      </v-carousel>
      <!--End of list of photos and videos-->
    </v-card-title>
    <v-card-text>
      <v-row>
        <v-col class="text-right">
          <!--Dialog to report inappropriate content-->
          <v-dialog
            width="600px"
            v-model="openedReportInappropriateContentDialog"
          >
            <template v-slot:activator="{ on: dialog }">
              <v-tooltip bottom>
                <template v-slot:activator="{ on: tooltip }">
                  <v-btn
                    class="mx-2"
                    fab
                    x-small
                    v-on="{ ...dialog, ...tooltip }"
                    color="error"
                    v-if="loggedIn"
                    ><v-icon>mdi-alert-octagon</v-icon>
                  </v-btn>
                </template>
                <span>Report as inappropriate</span>
              </v-tooltip>
            </template>
            <!-- Dialog content -->
            <v-card>
              <v-card-title> <br /> </v-card-title>
              <v-card-text>
                <v-row>
                  <v-textarea
                    v-model="reportInappropriateContent.message"
                    outlined
                    name="input-7-4"
                    no-resize
                    label="Reason for report"
                    clearable
                    clear-icon="mdi-close-circle"
                  ></v-textarea>
                </v-row>
                <v-spacer></v-spacer>
                <br />
                <v-btn
                  color="primary"
                  @click="
                    reportInappropriatePost(post),
                      (openedReportInappropriateContentDialog = false)
                  "
                >
                  Confirm
                </v-btn>
                <v-btn
                  text
                  @click="openedReportInappropriateContentDialog = false"
                >
                  Close
                </v-btn>
              </v-card-text>
            </v-card>
            <!-- End of the dialog content -->
          </v-dialog>
          <!--End of dialog for reporting inappropriate content-->
        </v-col>
      </v-row>
    </v-card-text>
  </v-card>
</template>

<script>
import { getToken, getParsedToken } from "../util/token";
import { getTodayDateString } from "../util/dateHandler";

export default {
  props: {
    post: {
      required: true,
      type: Object,
    },
  },
  data() {
    return {
      // TODO - fix model to fetch correct data
      loggedIn: false,
      snackbar: false,
      snackbarText: "",
      user: null,
      comments: [],
      openedReportInappropriateContentDialog: false,
      reportInappropriateContent: {
        story_id: "",
        story_creator_id: "",
        story_reporter_id: "",
        post_id: "",
        post_creator_id: "",
        post_reporter_id: "",
        time_stamp: "",
        message: "",
      },
    };
  },
  mounted() {
    if (getToken()) {
      this.loggedIn = true;

      // TODO: fix endpoints to make this unnecessary
      this.axios
        .get(
          process.env.VUE_APP_BACKEND_URL +
            process.env.VUE_APP_USER +
            process.env.VUE_APP_USER_USERNAME +
            "/" +
            getParsedToken().sub,
          {
            headers: {
              Authorization: "Bearer " + localStorage.getItem("JWT-CPIS"),
            },
          }
        )
        .then((res) => {
          this.user = res.data;
        });

      if (this.post.comments) this.comments = this.post.comments;
    }
  },
  computed: {
    commentCount: function () {
      if (!this.comments) return 0;
      else return this.comments.length;
    },
    likeCount: function () {
      // TODO: fix model so you can actually get this
      return 0;
    },
  },
  methods: {
    getImageUrl(path) {
      if (path)
        return (
          process.env.VUE_APP_BACKEND_URL +
          process.env.VUE_APP_FILE_ENDPOINT +
          path
        );
      else
        return "https://icon-library.com/images/default-user-icon/default-user-icon-4.jpg";
    },
    reportInappropriatePost(post) {
      this.reportInappropriateContent.story_id = post._id;
      this.reportInappropriateContent.story_reporter_id = this.user._id;
      this.reportInappropriateContent.time_stamp = getTodayDateString();

      this.axios
        .post(
          process.env.VUE_APP_BACKEND_URL +
            process.env.VUE_APP_INAPPROPRIATE_POST,
          this.reportInappropriateContent,
          {
            headers: {
              Authorization: "Bearer " + localStorage.getItem("JWT-CPIS"),
            },
          }
        )
        .then(() => {
          this.snackbarText = "Sucessfully reported story as inappropriate.";
          this.snackbar = true;
        })
        .catch(() => {
          this.snackbarText = "Report failed.";
          this.snackbar = true;
        });
    },
  },
};
</script>