<template>
  <v-row class="pa-5">
    <v-spacer></v-spacer>
    <v-col sm="8" md="6" lg="4" xl="4" cols="12">
      <v-row>
        <v-col>
          <v-card elevation="4" class="pa-5">
            <v-form>
              <v-text-field
                v-model="search"
                label="Search posts by location"
              ></v-text-field>

              <v-card-actions class="pa-0">
                <v-btn color="secondary" text @click="reset"> Reset </v-btn>
                <v-spacer></v-spacer>
                <v-btn color="primary" @click="submit"> Search </v-btn>
              </v-card-actions>
            </v-form>

            <v-overlay
              :absolute="true"
              :value="loading"
              :opacity="0.7"
              color="#ffffff"
            >
              <v-progress-circular
                indeterminate
                color="secondary"
              ></v-progress-circular>
            </v-overlay>
          </v-card>
        </v-col>
      </v-row>

      <v-row v-for="(post, i) in posts" :key="i">
        <v-col>
          <postComponent :post="post"></postComponent>
        </v-col>
      </v-row>

      <v-snackbar v-model="snackbar" :timeout="2000" bottom class="mb-5" right>
        {{ snackbarText }}

        <template v-slot:action="{ attrs }">
          <v-btn color="primary" text v-bind="attrs" @click="snackbar = false">
            Close
          </v-btn>
        </template>
      </v-snackbar>
    </v-col>

    <v-spacer></v-spacer>
  </v-row>
</template>

<script>
import postComponent from "../../../components/Post.vue";

export default {
  components: {
    postComponent,
  },
  data() {
    return {
      snackbar: false,
      snackbarText: "",
      loading: false,
      search: "",
      posts: [],
    };
  },
  methods: {
    submit() {
      this.loading = true;
      this.posts = [];
      this.axios
        .get("https://nominatim.openstreetmap.org/search", {
          params: {
            format: "json",
            q: this.search,
          },
        })
        .then((response) => {
          if (response.data.length == 0) {
            this.snackbarText = "Geocoding failed.";
            this.loading = false;
            this.snackbar = true;
          } else {
            this.axios
              .post(
                process.env.VUE_APP_BACKEND_URL +
                  process.env.VUE_APP_SEARCH_LOCATION_ENDPOINT,
                {
                  latitude: parseFloat(response.data[0].lat),
                  longitude: parseFloat(response.data[0].lon),
                }
              )
              .then((response) => {
                if (response.data) this.posts = response.data;
                this.loading = false;
                if (this.posts.length == 0) {
                  this.snackbarText = "No results found.";
                  this.snackbar = true;
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
                this.loading = false;
                this.snackbar = true;
              });
          }
        })
        .catch(() => {
          this.snackbarText = "Geocoding failed.";
          this.loading = false;
          this.snackbar = true;
        });
    },
    reset() {
      this.search = "";
    },
  },
};
</script>