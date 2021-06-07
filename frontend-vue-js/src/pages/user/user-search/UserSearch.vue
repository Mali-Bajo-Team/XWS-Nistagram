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
            label="Search users"
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

        <v-row>
        <v-col>
        <v-card v-if="users.length > 0"
    class="mx-auto"
    tile
  >
    <v-list>
      <v-list-item-group
        color="primary"
      >
        <v-list-item
          v-for="(user, i) in users"
          :key="i"
          @click="userClicked(user)"
        >
          <v-list-item-avatar>
            <v-img :src="user.profileImagePath"></v-img>
          </v-list-item-avatar>
          <v-list-item-content>
            <v-list-item-title v-text="user.username"></v-list-item-title>
          </v-list-item-content>
        </v-list-item>
      </v-list-item-group>
    </v-list>
  </v-card>
        </v-col>
        </v-row>

        <v-snackbar
          v-model="snackbar"
          :timeout="2000"
          bottom
          class="mb-5"
          right
        >
          {{ snackbarText }}

          <template v-slot:action="{ attrs }">
            <v-btn
              color="primary"
              text
              v-bind="attrs"
              @click="snackbar = false"
            >
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
      snackbar: false,
      snackbarText: "",
      loading: false,
      search: "",
      users: []
    };
  },
  methods: {
    submit() {
        this.loading = true;
        this.users = []
        this.axios
        .get(
          process.env.VUE_APP_BACKEND_URL + process.env.VUE_APP_SEARCH_USERS_ENDPOINT + this.search
        ).then((response) => {
          let users = response.data;
          for (let user of users){
              if (user.profileImagePath) {
                  user.profileImagePath = process.env.VUE_APP_BACKEND_URL +
                                        process.env.VUE_APP_FILE_ENDPOINT +
                                        user.profileImagePath;
              } else {
                  user.profileImagePath = "https://icon-library.com/images/default-user-icon/default-user-icon-4.jpg";
              }
          }
          this.users = users;
          this.loading = false;
          if (users.length == 0) {
            this.snackbarText = "No results found."
            this.snackbar = true;
          }
        }).
        catch((error) => {
          if (error.response && error.response.data && error.response.data.message)
            this.snackbarText = error.response.data.message;
          else if (error.message) 
            this.snackbarText = error.message;
          else
            this.snackbarText = "An unknown error has occured."
          this.loading = false;
          this.snackbar = true;
        });
    },
    reset() {
      this.search = ""
    },
    userClicked(user) {
        this.$router.push('/user/' + user.username)
    }
  }
};
</script>