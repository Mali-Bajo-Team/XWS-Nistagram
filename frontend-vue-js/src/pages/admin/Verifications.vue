<template>
  <v-row class="pa-5">
    <v-spacer></v-spacer>
    <v-col sm="8" md="6" lg="4" xl="4" cols="12">
      <v-row v-for="(request, i) in requests" :key="i">
        <v-col>
          <v-card>
            <v-card-text>
              <v-list>
                <v-list-item-group color="primary">
                  <v-list-item
                    @click="
                      $router.push('/admin/user/' + request.requesterUsername)
                    "
                  >
                    <v-list-item-content>
                      <v-list-item-title
                        v-text="request.requesterUsername"
                      ></v-list-item-title>
                    </v-list-item-content>
                  </v-list-item>
                </v-list-item-group>
              </v-list>
              <v-row>
                <v-col class="pb-5 pl-5 ml-2 pt-0"
                  >{{ request.realName }} {{ request.realSurname }}
                  <v-chip class="ma-2" color="primary">
                    {{ request.category }}
                  </v-chip>
                </v-col>
              </v-row>
              <v-img
                :src="getImageUrlByPATH(request.imageOfOfficialDocument)"
                class="white--text align-end"
                gradient="to bottom, rgba(0,0,0,.1), rgba(0,0,0,.5)"
                height="200px"
              ></v-img>
            </v-card-text>
            <v-card-actions class="pr-5 pb-5 pl-5">
              <v-spacer></v-spacer>
              <v-btn
                @click="acceptVerificationRequest(request)"
                icon
                color="success"
              >
                <v-icon>mdi-check-circle</v-icon>
              </v-btn>
              <v-btn
                @click="rejectVerificationRequest(request)"
                icon
                color="error"
              >
                <v-icon>mdi-delete</v-icon>
              </v-btn>
            </v-card-actions>
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
export default {
  data() {
    return {
      requests: [],
      snackbar: false,
      snackbarText: "",
    };
  },
  mounted() {
    this.axios
      .get(
        process.env.VUE_APP_BACKEND_URL +
          process.env.VUE_APP_VERIFICATION_REQUEST,
        {
          headers: {
            Authorization: "Bearer " + localStorage.getItem("JWT-CPIS"),
          },
        }
      )
      .then((res) => {
        this.requests = res.data;
        if (this.requests.length == 0) {
          this.snackbarText = "No verificationr requests found.";
          this.snackbar = true;
        }
      })
      .catch(() => {
        this.snackbarText = "Error while fetching verification requests.";
        this.snackbar = true;
      });
  },
  methods: {
    acceptVerificationRequest(verification) {
      this.axios
        .post(
          process.env.VUE_APP_BACKEND_URL +
            process.env.VUE_APP_ACCEPT_VERIFICATION_REQUEST +
            verification.id,
          {},
          {
            headers: {
              Authorization: "Bearer " + localStorage.getItem("JWT-CPIS"),
            },
          }
        )
        .then(() => {
          for (let verificationRequest of this.requests) {
            if (verificationRequest.id == verification.id) {
              this.requests.pop(verificationRequest);
              break;
            }
          }
          this.snackbarText = "Successfully accepted verification request.";
          this.snackbar = true;
        })
        .catch(() => {
          this.snackbarText = "Accepting verification request failed.";
          this.snackbar = true;
        });
    },
    rejectVerificationRequest(verification) {
      this.axios
        .post(
          process.env.VUE_APP_BACKEND_URL +
            process.env.VUE_APP_REJECT_VERIFICATION_REQUEST +
            verification.id,
          {},
          {
            headers: {
              Authorization: "Bearer " + localStorage.getItem("JWT-CPIS"),
            },
          }
        )
        .then(() => {
          for (let verificationRequest of this.requests) {
            if (verificationRequest.id == verification.id) {
              this.requests.pop(verificationRequest);
              break;
            }
          }
          this.snackbarText = "Successfully rejected verification request.";
          this.snackbar = true;
        })
        .catch(() => {
          this.snackbarText = "Rejecting verification request failed.";
          this.snackbar = true;
        });
    },
    getImageUrlByPATH(path) {
      if (path)
        return (
          process.env.VUE_APP_BACKEND_URL +
          process.env.VUE_APP_FILE_ENDPOINT +
          path
        );
      else
        return "https://icon-library.com/images/default-user-icon/default-user-icon-4.jpg";
    },
  },
};
</script>