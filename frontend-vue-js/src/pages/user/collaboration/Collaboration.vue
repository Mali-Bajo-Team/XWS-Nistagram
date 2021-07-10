<template>
  <v-row class="pa-5">
    <v-spacer></v-spacer>
    <v-col sm="8" md="6" lg="4" xl="4" cols="12">
      <v-row>
        <v-col>
          <v-card class="mx-auto" tile>
            <v-list>
              <v-list-item-group color="primary">
                <v-list-item v-for="(request, i) in requests" :key="i">
                  <v-list-item-content>
                    <v-list-item-title
                      v-text="request.requester.username"
                    ></v-list-item-title>
                  </v-list-item-content>
                  <v-list-item-action>
                    <v-btn
                      outlined
                      rounded
                      medium
                      color="success"
                      @click="accept(request, i)"
                    >
                      Accept
                    </v-btn>
                    <v-btn
                      outlined
                      rounded
                      medium
                      color="error"
                      @click="reject(request, i)"
                    >
                      Reject
                    </v-btn>
                  </v-list-item-action>
                </v-list-item>
              </v-list-item-group>
            </v-list>
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
      requests: [],
    };
  },
  mounted() {
    this.axios
      .get(process.env.VUE_APP_BACKEND_URL + "api/private/adds/requests", {
        headers: {
          Authorization: "Bearer " + getToken(),
        },
      })
      .then((response) => {
        this.requests = response.data;
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
    accept: function (request, i) {
      this.axios
        .post(
          process.env.VUE_APP_BACKEND_URL +
            "api/private/adds/requests/" +
            request.id +
            "/accept",
          {},
          {
            headers: {
              Authorization: "Bearer " + getToken(),
            },
          }
        )
        .then(() => {
          this.requests.splice(i, 1);
          this.snackbarText = "Successfully accepted request.";
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
    reject: function (request, i) {
      this.axios
        .post(
          process.env.VUE_APP_BACKEND_URL +
            "api/private/adds/requests/" +
            request.id +
            "/reject",
          {},
          {
            headers: {
              Authorization: "Bearer " + getToken(),
            },
          }
        )
        .then(() => {
          this.requests.splice(i, 1);
          this.snackbarText = "Successfully rejected request.";
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