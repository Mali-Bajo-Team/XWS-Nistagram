<template>
  <v-row class="pa-5">
    <v-spacer></v-spacer>
    <v-col sm="8" md="6" lg="4" xl="4" cols="12">
      <v-row>
        <v-col>
          <v-card class="mx-auto" tile>
            <v-tabs v-model="tab">
              <v-tabs-slider></v-tabs-slider>
              <v-tab>Collaborators</v-tab>
              <v-tab>Unaffiliated</v-tab>
            </v-tabs>
            <v-tabs-items v-model="tab">
              <v-tab-item>
                <v-list>
                  <v-list-item-group color="primary">
                    <v-list-item
                      v-for="(user, i) in collaborators"
                      :key="i"
                      @click="userClicked(user)"
                      ><v-list-item-content>
                        <v-list-item-title v-text="user"></v-list-item-title>
                      </v-list-item-content>
                    </v-list-item>
                  </v-list-item-group>
                </v-list>
              </v-tab-item>

              <v-tab-item>
                <v-list>
                  <v-list-item-group color="primary">
                    <v-list-item v-for="(user, i) in unaffiliated" :key="i">
                      <v-list-item-content>
                        <v-list-item-title v-text="user"></v-list-item-title>
                      </v-list-item-content>
                      <v-list-item-action>
                        <v-btn
                          outlined
                          rounded
                          medium
                          color="primary"
                          @click="request(user, i)"
                        >
                          Request collaboration
                        </v-btn>
                      </v-list-item-action>
                    </v-list-item>
                  </v-list-item-group>
                </v-list>
              </v-tab-item>
            </v-tabs-items>
          </v-card>
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
import { getToken } from "./../../../util/token";

export default {
  data() {
    return {
      snackbar: false,
      snackbarText: "",
      loading: false,
      collaborators: [],
      unaffiliated: [],
      tab: null,
    };
  },
  mounted() {
    this.axios
      .get(
        process.env.VUE_APP_BACKEND_URL +
          "api/private/adds/influencers/collaborating",
        {
          headers: {
            Authorization: "Bearer " + getToken(),
          },
        }
      )
      .then((response) => {
        this.collaborators = response.data;
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

    this.axios
      .get(
        process.env.VUE_APP_BACKEND_URL +
          "api/private/adds/influencers/non-collaborating",
        {
          headers: {
            Authorization: "Bearer " + getToken(),
          },
        }
      )
      .then((response) => {
        this.unaffiliated = response.data;
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
  },
  methods: {
    request: function (user, i) {
      this.axios
        .post(
          process.env.VUE_APP_BACKEND_URL +
            "api/private/adds/influencers/" +
            user +
            "/request",
          {},
          {
            headers: {
              Authorization: "Bearer " + getToken(),
            },
          }
        )
        .then(() => {
          this.unaffiliated.splice(i, 1);
          this.snackbarText = "Successfully requested collaboration.";
          this.loading = false;
          this.snackbar = true;
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
    },
  },
};
</script>