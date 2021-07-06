<template>
  <v-app style="background: rgba(0,0,0,0);">
    <navbar v-bind:items="links" v-bind:logout="true"></navbar>
    <v-main>
      <v-container fluid>
        <router-view></router-view>
      </v-container>
    </v-main>
  </v-app>
</template>

<script>
import navbar from "../../components/Navbar";

export default {
  components: {
    navbar,
  },
  data: () => ({
    links: [
      {
        title: "Feed",
        children: [
          {
            title: "Posts",
            to: "/feed/posts"
          },
          {
            title: "Stories",
            to: "/feed/stories"
          }
        ],
      },
      {
        title: "My profile",
        to: "/profile",
      },
      {
        title: "Messages",
        to: "/messages",
      },
      {
        title: "Search",
        children: [
          {
            title: "Users",
            to: "/usersearch"
          },
          {
            title: "Hashtags",
            to: "/hashtagsearch"
          },
          {
            title: "Locations",
            to: "/locationsearch"
          }
        ],
      }
    ],
  }),
};
</script>