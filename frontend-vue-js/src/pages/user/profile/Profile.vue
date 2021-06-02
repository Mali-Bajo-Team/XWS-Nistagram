<template>
    <v-row class="pa-5">
    <v-spacer></v-spacer>
    
    <v-col sm="8" md="12" lg="4" cols="12">
        <v-row>
                    <v-col sm="8" md="12" lg="4" cols="12">
                        <v-img class="rounded-circle"
                            src="@/assets/profilePhoto.jpg">
                            
                        </v-img>
                        
                        <v-text  class="text-center justify-center">
                            <h3>ivana_mil_98</h3>    
                        </v-text>
                        <br>

                        <v-btn
                            medium
                            color="success"
                        >
                            <v-icon left>
                                mdi-pencil
                            </v-icon>
                                Edit profile
                        </v-btn>
                    </v-col>

                    <v-col class="pa-4">
                        <b>50</b>
                        <br>
                        Posts
                    </v-col>
                    <v-col class="pa-4">
                        <b>1406</b>
                        Followers
                    </v-col>

                    <v-col class="pa-4">
                        <b>950</b>
                        Following
                    </v-col>
                
        </v-row>

        <v-row>
            <v-tabs icons-and-text background-color="transparent">
                <v-tabs-slider></v-tabs-slider>
                <v-tab>Posts<v-icon>mdi-camera</v-icon></v-tab>
                <v-tabs-slider></v-tabs-slider>
                <v-tab>Saved<v-icon>mdi-check-circle</v-icon></v-tab>
                <v-tab>Tagged<v-icon>mdi-tag</v-icon></v-tab>

                <v-tab-item
            v-for="n in 3"
            :key="n"
        >
            <v-container fluid>
            <v-row>
                <v-col
                v-for="i in 6"
                :key="i"
                cols="12"
                md="4"
                >
                <v-img
                    :src="`https://picsum.photos/500/300?image=${i * n * 5 + 10}`"
                    :lazy-src="`https://picsum.photos/10/6?image=${i * n * 5 + 10}`"
                    aspect-ratio="1"
                ></v-img>
                </v-col>
            </v-row>
            </v-container>
        </v-tab-item>
            </v-tabs>
        </v-row>

    </v-col>
    
    <v-spacer></v-spacer>
    </v-row>
</template>

<script>
export default {
  data() {
    return {
      snackbar: false,
      snackbarText: '',
      loading: false,
      form: {
        email: "",
        password: "",
        showPassword: false,
        repeatPassword: "",
        showRepeatPassword: false,
        name: "",
        surname: "",
        address: "",
        city: "",
        country: "",
        phone: ""
      },
    };
  },
  computed: {
      
  },
  methods: {
    
  },
};
</script>