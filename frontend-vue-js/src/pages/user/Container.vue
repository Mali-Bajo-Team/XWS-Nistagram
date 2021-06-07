<template>
  <v-app style="background: rgba(0,0,0,0);">
    <navbar v-bind:items="links" v-bind:logout="false"></navbar>
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
        title: "Home",
        to: "/"
      },
      {
        title: "My profile",
        to: "/profile",
      },
      {
        title: "Log in",
        to: "/login",
      },
      {
        title: "Register",
        to: "/register",
      },
      {
        title: "Search",
        children: [
          {
            title: "Users",
            to: "/usersearch"
          }
        ],
      }
    ],
  }),
};
</script>