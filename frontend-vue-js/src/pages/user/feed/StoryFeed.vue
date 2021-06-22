<template>
  <v-row class="pa-5">
    <v-spacer></v-spacer>
    <v-col sm="8" md="6" lg="4" xl="4" cols="12">
      <v-row v-for="(post, i) in posts" :key="i">
        <v-col>
          <postComponent :post="post"></postComponent>
        </v-col>
      </v-row>
      <v-row>
        <v-col>
          <v-progress-linear
            v-if="loading"
            v-intersect="loadNextPage"
            indeterminate
            color="primary"
          ></v-progress-linear>
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
import postComponent from "../../../components/Story.vue";

export default {
  components: {
    postComponent,
  },
  data() {
    return {
      snackbar: false,
      snackbarText: "",
      search: "",
      posts: [],
      pageLoaded: -1,
      loading: true,
    };
  },
  methods: {
    async loadNextPage(entries) {
      if (entries[0].isIntersecting && this.loading) {
        var page = this.pageLoaded + 1;
        this.axios
          .get(
            process.env.VUE_APP_BACKEND_URL +
              process.env.VUE_APP_STORY_FEED_ENDPOINT +
              page,
            {
              headers: {
                Authorization: "Bearer " + localStorage.getItem("JWT-CPIS"),
              },
            }
          )
          .then((response) => {
            if (response.data) this.posts.push(...response.data);
            this.pageLoaded = page;
            if (!response.data || response.data.length < 10) {
              this.loading = false;
              this.snackbarText = "No more stories to load.";
              this.snackbar = true;
            }
          })
          .catch(() => {
            this.snackbarText = "Error fetching feed.";
            this.snackbar = true;
          });
      }
    },
  },
};
</script>