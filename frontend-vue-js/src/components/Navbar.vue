<template>
  <v-app-bar fixed scroll-threshold="80" app>
    <template v-slot:img="{ props }">
      <v-img
        v-bind="props"
        gradient="to top, rgba(255,255,230,.9), rgba(15,80,80,.1)"
        src="@/assets/social.jpg"
      ></v-img>
    </template>

    <template v-slot:extension>
      <v-tabs align-with-title hide-slider>
        <template v-for="item in items">
          <v-tab v-if="item.to" :key="item.title" :to="item.to">{{
            item.title
          }}</v-tab>
          <v-menu v-else :key="item.title" bottom offset-y left>
            <template v-slot:activator="{ on, attrs }">
              <v-tab v-bind="attrs" v-on="on">
                {{ item.title }}
                <v-icon right> mdi-menu-down </v-icon>
              </v-tab>
            </template>

            <v-list>
              <v-list-item
                v-for="subitem in item.children"
                :key="subitem.title"
                :to="subitem.to"
                :ripple="{ center: true, class: `primary--text` }"
              >
                {{ subitem.title }}
              </v-list-item>
            </v-list>
          </v-menu>
        </template>
        <v-tab v-if="logout" @click="doLogout">Log out</v-tab>
      </v-tabs>
    </template>
  </v-app-bar>
</template>

<script>
import { removeToken } from "../util/token";

export default {
  props: {
    items: {
      type: Array,
      default: function () {
        return [];
      },
    },
    logout: {
      type: Boolean,
      default: true,
    },
  },
  methods: {
    doLogout: function () {
      removeToken();
      this.$router.push("/");
    },
  },
};
</script>