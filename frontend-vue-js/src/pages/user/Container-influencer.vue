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
            to: "/influencer/feed/posts"
          },
          {
            title: "Stories",
            to: "/influencer/feed/stories"
          }
        ],
      },
      {
        title: "My profile",
        to: "/influencer/profile",
      },
      {
        title: "Search",
        children: [
          {
            title: "Users",
            to: "/influencer/usersearch"
          },
          {
            title: "Hashtags",
            to: "/influencer/hashtagsearch"
          },
          {
            title: "Locations",
            to: "/influencer/locationsearch"
          }
        ],
      },
      {
        title:"Recommendations",
        to:"/influencer/recommendations"
      },
      {
        title:"Agent requests",
        to:"/influencer/collab-requests"
      }
    ],
  }),
};
</script>