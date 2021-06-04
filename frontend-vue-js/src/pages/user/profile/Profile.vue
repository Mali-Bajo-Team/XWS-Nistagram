<template>
    <v-row class="pa-5">
    <v-spacer></v-spacer>
    
    <v-col sm="8" md="12" lg="4" cols="12">
        <v-row>
                    <!--First column-->
                    <v-col sm="8" md="12" lg="4" cols="12">
                        <!--Profile photo-->
                        <v-img class="rounded-circle" 
                            aspect-ratio="1.1"
                            src="@/assets/profilePhoto.jpg">
                            
                        </v-img>
                        
                        <!--Username and description-->
                            <h3 class="text-justify">ivana_mil_98</h3>
                            <div class="font-weight-medium text-justify"> 
                                Love is in the air.
                                The sun is shining,I am smiling :).
                            </div>
                            
    
                       
                    </v-col>
                    <!--End of the first column-->

                    <!--Column for the number of posts-->
                    <v-col class="pa-4 mt-2">
                        <h3>250</h3>
                        Posts
                    </v-col>
                    <!--End of the number of posts-->

                    <!--Column for the number of followers-->
                    <v-col class="pa-4 mt-2">
                        <h3>1406</h3>
                        Followers
                    </v-col>
                    <!--End of the number of followers-->

                    <!--Column for the number of following-->
                    <v-col class="pa-4 mt-2">
                        <h3>950</h3>
                        Following
                    </v-col>
                    <!--Column for the number of following-->
                
        </v-row>
        <v-row>
            <!--Column for edit profile button-->
            <v-col class="pa-3">
                <!--Dialog and button for editing profile-->
                <v-dialog

                    width="600px"
                >
                    <template v-slot:activator="{ on, attrs }">
                        <v-btn
                            outlined
                            rounded
                            medium
                            color="primary"
                            v-bind="attrs"
                            v-on="on"
                        >
                            <v-icon left>
                                mdi-pencil
                            </v-icon>
                                Edit profile
                        </v-btn>
                    </template>
                    <v-card>
                        <v-card-title class="headline">
                            Edit your profile
                        </v-card-title>
                        <v-card-text>Let Google help apps determine location. This means sending anonymous location data to Google, even when no apps are running.</v-card-text>
                        <v-card-actions>
                            <v-spacer></v-spacer>
                            <v-btn

                                color="error"
                                text
                            >
                                Cancel
                            </v-btn>
                            <v-btn
                                color="primary"
                                text
                            >
                                Save
                            </v-btn>
                        </v-card-actions>
                    </v-card>
                </v-dialog>
            </v-col>
            <!--End of the column for edit profile button-->

            <!--Column for add content button-->
            <v-col class="text-right mr-5 mb-5">
                 <!--Button for adding new content-->
                 <v-dialog
                    width="600px"
                 >
                    <template v-slot:activator="{ on, attrs }">
                        <v-btn 
                            dark
                            small
                            fab
                            color="primary"
                            v-bind="attrs"
                            v-on="on"
                        >
                            <v-icon dark>
                                mdi-plus
                            </v-icon>
                        </v-btn>
                    </template>

                    <v-card>
                        <v-card-title class="headline">
                            Add a new content
                        </v-card-title>
                        <v-card-text>
                            <!--File input for images and videos-->
                            <v-file-input
                                small-chips
                                multiple
                                show-size
                                counter
                                accept="image/png, image/jpeg, image/bmp"
                                label="Choose a photo or video"
                                prepend-icon="mdi-camera"
                            ></v-file-input>
                            <!--End of file input-->

                            <!--Choose a location-->
                             <v-combobox
                                label="Choose a location"
                                prepend-icon="mdi-map-marker-star"
                            >
                            </v-combobox>
                            <!--End of location-->
                            <br>
                            <v-autocomplete
                                label="Tagging"
                                prepend-icon="mdi-tag"
                            ></v-autocomplete>
                            <!--Add description-->
                            <v-textarea
                                outlined
                                name="input-7-4"
                                no-resize
                                rows="3"
                                label="Add description"
                                clearable
                                clear-icon="mdi-close-circle"
                                
                            ></v-textarea>
                            <!--End of description-->

                            

                            
                           
                        </v-card-text>
                        <v-card-actions>
                            <v-spacer></v-spacer>
                            <v-btn

                                color="error"
                                text
                            >
                                Cancel
                            </v-btn>
                            <v-btn
                                color="primary"
                                text
                            >
                                Post
                            </v-btn>
                        </v-card-actions>
                    </v-card>
                 </v-dialog>
            </v-col>
            <!--End of the column for add content button-->
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
                                v-for="i in 12"
                                :key="i"
                                cols="12"
                                md="6"
                            >
                            <v-card>
                                <v-img
                                    :src="`https://picsum.photos/500/300?image=${i * n * 5 + 10}`"
                                    :lazy-src="`https://picsum.photos/10/6?image=${i * n * 5 + 10}`"
                                    aspect-ratio="0.8"
                                ></v-img>
                                <v-card-text class="title">
                                    <v-row>
                                    <v-col>
                                    <v-icon color="red lighten-2" left>mdi-heart</v-icon>
                                    350
                                    </v-col>

                                    <v-col  class="text-right">
                                    <v-icon left>mdi-chat</v-icon>
                                    10
                                    </v-col>
                                    </v-row>
                                </v-card-text>

                            </v-card>
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