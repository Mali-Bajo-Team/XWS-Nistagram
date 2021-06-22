<template>
  <v-card flat>
    <v-row> 
      <v-col v-for="(post, i) in posts" :key="i">
        <postComponent :post="post"></postComponent>
      </v-col>
    </v-row>
  </v-card>
</template>

<script>
import { getToken } from "../util/token";
import postComponent from "./Post.vue";

export default {
  components: {
    postComponent,
  },
  props: {
    reactiontype: {
      required: true,
      type: String,
    },
  },
  data() {
    return {
      posts: [],
    };
  },
  mounted() {
    if (getToken()) {
      this.axios
        .get(
          process.env.VUE_APP_BACKEND_URL +
            process.env.VUE_APP_REACTIONS +
            this.reactiontype,
          {
            headers: {
              Authorization: "Bearer " + getToken(),
            },
          }
        )
        .then((res) => {
          if (res.data) {
            for (let reaction of res.data)
              this.getEntirePost(reaction.post_id);
          }
        })
        .catch((err) => {
          console.log(err);
        });
    }
  },
  methods: {
    getEntirePost(postID) {
      this.axios
        .get(
          process.env.VUE_APP_BACKEND_URL +
            process.env.VUE_APP_CONTENT_POST +
            postID,
          {
            headers: {
              Authorization: "Bearer " + getToken(),
            },
          }
        )
        .then((res) => {
          this.posts.push(res.data)
        });
    },
  },
};
</script>