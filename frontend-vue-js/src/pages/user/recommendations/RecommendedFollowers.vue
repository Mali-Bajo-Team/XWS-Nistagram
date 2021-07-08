<template>
     <v-row class="pa-5">
        <v-spacer></v-spacer>

        <v-col sm="8" md="6" lg="4" xl="4" cols="12">
            <v-card class="mx-auto" tile>
              <v-list>
                <v-list-item-group color="primary">
                  <v-list-item
                    v-for="(user, i) in users"
                    :key="i"
                  >
                      <!--Profile picture-->
                      <v-list-item-avatar>
                        <v-img :src="user.profileImagePath"></v-img>
                      </v-list-item-avatar>
                      <!--End of profile picture-->

                      <!--username-->
                      <v-list-item-content>
                        <v-list-item-title
                             v-text="user.username">
                          <br>
                       
                        </v-list-item-title>
                      </v-list-item-content>
                  </v-list-item>


                </v-list-item-group>


              </v-list>
            
            </v-card>
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
      users: [
        {
          username:"anaperisic12",
          profileImagePath:"https://icon-library.com/images/default-user-icon/default-user-icon-4.jpg"
        },
        {
          username:"ivancica188",
          profileImagePath:"https://www.impel.eu/wp-content/uploads/2019/08/mediterranean-sea.jpg"
        }
      ],
    };
  },
  methods: {
  }
};
</script>
