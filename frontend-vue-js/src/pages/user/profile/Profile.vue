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
                        <!--Edit profile fields-->
                        <v-card-title class="headline">
                            Edit your profile
                        </v-card-title>
                        <v-card-text>
                             <v-file-input
                                small-chips
                                accept="image/png, image/jpeg, image/bmp"
                                label="Choose a profile"
                                prepend-icon="mdi-camera"
                            ></v-file-input>
                            <v-text-field
                                prepend-icon="mdi-account"
                                label="Name"
                                
                            ></v-text-field>
                            <v-text-field
                                prepend-icon="mdi-account"
                                label="Surname"
                         
                            ></v-text-field>
                            <v-text-field
                                prepend-icon="mdi-account-circle"
                                label="Username"
                         
                            ></v-text-field>
                            <v-text-field
                                prepend-icon="mdi-email"
                                label="Email"
                         
                            ></v-text-field>
                            <v-text-field
                                prepend-icon="mdi-phone"
                                label="Phone number"
                         
                            ></v-text-field>
                            <v-text-field
                                label="Birthday date"
                                prepend-icon="mdi-cake-variant"
                                readonly
                                
                            ></v-text-field>
                            <v-text-field
                                prepend-icon="mdi-human-male-female"
                                label="Gender"
                         
                            ></v-text-field>
                            <v-text-field
                                prepend-icon="mdi-web"
                                label="Web site"
                         
                            ></v-text-field>
                            <v-textarea
                                outlined
                                name="input-7-4"
                                no-resize
                                rows="5"
                                label="Add your biography"
                                clearable
                                clear-icon="mdi-close-circle"
                                
                            ></v-textarea>
                        
                                                    
                        </v-card-text>
                        <!--End of edit profile fields-->
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
                 <v-tabs   v-model="tabs" icons-and-text>
                    <v-tabs-slider></v-tabs-slider>
                    <v-tab>Regular posts<v-icon>mdi-camera</v-icon></v-tab>
                    <v-tab>Stories<v-icon>mdi-camera-iris</v-icon></v-tab>
                    <v-tab>Close friends<v-icon>mdi-star</v-icon></v-tab>
                 </v-tabs>
                   
                
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
                    <v-tabs-items  v-model="tabs">
                        <!--Tab for adding photo or video-->
                        <v-tab-item>
                            <v-card>
                                <v-card-title class="headline">
                                    Add a new photo or video
                                </v-card-title>
                                <v-card-text>
                                    <!--Stepper-->
                                    <v-stepper v-model="e1">
                                        <v-stepper-header>
                                            <v-stepper-step
                                                :complete="e1 > 1"
                                                step="1"
                                            >
                                                Photo/Video
                                            </v-stepper-step>

                                            <v-divider></v-divider>

                                            <v-stepper-step
                                                :complete="e1 > 2"
                                                step="2"
                                            >
                                                Other information
                                            </v-stepper-step>

                                            <v-divider></v-divider>

                                            <v-stepper-step step="3">
                                                Confirmation
                                            </v-stepper-step>
                                        </v-stepper-header>

                                        <v-stepper-items>
                                            <!--Step 1-->
                                            <v-stepper-content step="1">
                                            
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
                                                
                                                <v-btn
                                                color="primary"
                                                @click="e1 = 2"
                                                >
                                                Continue
                                                </v-btn>

                                                <v-btn text>
                                                Cancel
                                                </v-btn>
                                            </v-stepper-content>
                                            <!--End of step 1-->

                                            <!--Step 2-->
                                            <v-stepper-content step="2">
                                              
                                                
                                                <!--Choose a location-->
                                                <v-combobox
                                                    label="Choose a location"
                                                    prepend-icon="mdi-map-marker-star"
                                                >
                                                </v-combobox>
                                                <!--End of location-->

                                                <!--Tagging-->
                                                <br>
                                                <v-autocomplete
                                                    label="Tagging"
                                                    prepend-icon="mdi-tag"
                                                ></v-autocomplete>
                                                <!--End of tagging-->
                                                        
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
                                                 
                                              
                                                    
                                                <v-btn
                                                    color="primary"
                                                    @click="e1 = 3"
                                                >
                                                    Continue
                                                </v-btn>

                                                <v-btn text>
                                                    Cancel
                                                </v-btn>
                                                   
                                     
                                              
                                                

                                            </v-stepper-content>
                                            <!--End of step 2-->

                                            <!--Step 3-->
                                            <v-stepper-content step="3">
                                                
                                               
                                                    <h3>Are you sure you want to publish a chosen content?</h3>
                                                
                                                
                                                    <v-spacer></v-spacer>
                                                    <br>
                                                    <v-btn
                                                        color="primary"
                                                       
                                                    >
                                                    Confirm
                                                    </v-btn>

                                                    <v-btn text>
                                                        Cancel
                                                    </v-btn>
                                                   
                                                   
                                            </v-stepper-content>
                                            <!--End of step 3-->
                                        </v-stepper-items>


                                    </v-stepper>
                                </v-card-text>  

                                    
                            </v-card>
                        </v-tab-item>
                        <!--End of tab for adding photo or video-->

                        <!--Tab for adding stories-->
                        <v-tab-item>
                            <v-card>
                                <v-card-title class="headline">
                                    Add a new story
                                </v-card-title>
                                <v-card-text>
                                    <!--Stepper-->
                                    <v-stepper v-model="e2">
                                        <v-stepper-header>
                                            <v-stepper-step
                                                :complete="e2 > 1"
                                                step="1"
                                            >
                                                Photo/Video
                                            </v-stepper-step>

                                            <v-divider></v-divider>

                                            <v-stepper-step
                                                :complete="e2 > 2"
                                                step="2"
                                            >
                                                Other information
                                            </v-stepper-step>

                                            <v-divider></v-divider>

                                            <v-stepper-step step="3">
                                                Confirmation
                                            </v-stepper-step>
                                        </v-stepper-header>

                                        <v-stepper-items>
                                            <!--Step 1-->
                                            <v-stepper-content step="1">
                                            
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
                                                
                                                <v-btn
                                                color="primary"
                                                @click="e2 = 2"
                                                >
                                                Continue
                                                </v-btn>

                                                <v-btn text>
                                                Cancel
                                                </v-btn>
                                            </v-stepper-content>
                                            <!--End of step 1-->

                                            <!--Step 2-->
                                            <v-stepper-content step="2">
                                              
                                                
                                                <!--Choose a location-->
                                                <v-combobox
                                                    label="Choose a location"
                                                    prepend-icon="mdi-map-marker-star"
                                                >
                                                </v-combobox>
                                                <!--End of location-->

                                                <!--Tagging-->
                                                <br>
                                                <v-autocomplete
                                                    label="Tagging"
                                                    prepend-icon="mdi-tag"
                                                ></v-autocomplete>
                                                <!--End of tagging-->

                                                <!--Only available to close friends-->
                                                <v-checkbox
                                                     label="Only available to close friends"
                                                >
                                                </v-checkbox>
                                                 
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
                                                 
                                              
                                                    
                                                <v-btn
                                                    color="primary"
                                                    @click="e2 = 3"
                                                >
                                                    Continue
                                                </v-btn>

                                                <v-btn text>
                                                    Cancel
                                                </v-btn>
                                                   
                                     
                                              
                                                

                                            </v-stepper-content>
                                            <!--End of step 2-->

                                            <!--Step 3-->
                                            <v-stepper-content step="3">
                                                
                                               
                                                    <h3>Are you sure you want to publish a chosen content?</h3>
                                                
                                                
                                                    <v-spacer></v-spacer>
                                                    <br>
                                                    <v-btn
                                                        color="primary"
                                                       
                                                    >
                                                    Confirm
                                                    </v-btn>

                                                    <v-btn text>
                                                        Cancel
                                                    </v-btn>
                                                   
                                                   
                                            </v-stepper-content>
                                            <!--End of step 3-->
                                        </v-stepper-items>


                                    </v-stepper>
                                </v-card-text>  

                                    
                            </v-card>
                        </v-tab-item>
                        <!--End of tab for adding stories-->
                       
                       <!--Tab for close friends-->
                        <v-tab-item>
                            <v-card>
                                <v-card-title>
                                    Add a close friend
                                </v-card-title>
                                <v-card-text>
                                    <v-autocomplete
                                        label="Choose a close friend"
                                        prepend-icon="mdi-star-circle"
                                    ></v-autocomplete>
                                    
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
                                        Add
                                    </v-btn>
                                </v-card-actions>
                            </v-card>
                            

                        </v-tab-item>
                        <!--End of tab for close friends-->
                    </v-tabs-items>
                 </v-dialog>
            </v-col>
            <!--End of the column for add content button-->
        </v-row>

        <v-row>
            <v-tabs v-model="tabs2" icons-and-text background-color="transparent">
                <v-tabs-slider></v-tabs-slider>
                <v-tab>Posts<v-icon>mdi-camera</v-icon></v-tab>
                <v-tab>Stories<v-icon>mdi-camera-iris</v-icon></v-tab>
                <v-tab>Saved<v-icon>mdi-check-circle</v-icon></v-tab>
                <v-tab>Tagged<v-icon>mdi-tag</v-icon></v-tab>
            </v-tabs>
            <v-tabs-items v-model="tabs2">
                <!--Tab for photos and videos-->
                <v-tab-item>
                    <!--Dialog for choosing photo-->
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
                        <!--Card for comments-->
                        <v-card>
                            <v-card-title>
                                <v-img
                                    
                                    lazy-src="https://picsum.photos/id/11/10/6"
                                   
                                    src="https://picsum.photos/id/11/500/300"
                                ></v-img>
                            </v-card-title>
                            <v-card-text>
                                <v-row>
                                    <v-col>
                                    <!--The number of likes and comments-->
                                    <v-icon medium>mdi-heart</v-icon>700
                                    <v-icon medium right>mdi-comment</v-icon>10
                                    </v-col>

                                    <v-col class="text-right mr-5 mb-5">

                                        <!--Button for likes-->
                                        <v-btn
                                                    class="mx-2"
                                                    fab
                                                    x-small
                                                    color="primary"
                                                ><v-icon>mdi-thumb-up</v-icon>
                                                
                                        </v-btn>
                                        <!--End of button for likes-->

                                        <!--Button for dislikes-->
                                        <v-btn
                                                    class="mx-2"
                                                    fab
                                                    x-small
                                                    color="primary"
                                                ><v-icon>mdi-thumb-down</v-icon>
                                                
                                                </v-btn>
                                        <!--End of button for dislikes-->

                                        <!--Dialog for adding comment-->
                                        <v-dialog
                                            width="600px"
                                        >

                                    
                                            <template v-slot:activator="{ on, attrs }">
                                                <v-btn
                                                    class="mx-2"
                                                    fab
                                                    x-small
                                                    v-bind="attrs"
                                                    v-on="on"
                                                    color="primary"
                                                ><v-icon>mdi-pencil</v-icon>
                                                
                                                </v-btn>
                                            </template>
                                            <v-card>
                                                <v-card-title>
                                                    Add a new comment to the post
                                                </v-card-title>
                                                <v-card-text>
                                                    <!--Field for comments-->
                                                <v-textarea
                                                            outlined
                                                            name="input-7-4"
                                                            no-resize
                                                            rows="3"
                                                            label="Comment"
                                                            clearable
                                                            clear-icon="mdi-close-circle"

                                                        ></v-textarea>
                                                    <!--End of field for comments-->
                                                </v-card-text>
                                                <v-card-actions>
                                                    <!--Buttons to confirm or cancel action-->
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
                                                            Confirm
                                                        </v-btn>
                                                </v-card-actions>
                                            </v-card>
                                        </v-dialog>
                                        <!--End of dialog for adding comments-->
                                    </v-col>
                                </v-row>


                                
                                <br><br>
                                <!--Expansion panels for showing comments-->
                                <v-expansion-panels>
                                    <v-expansion-panel
                                        v-for="(item,i) in 1"
                                        :key="i"
                                    >
                                    <v-expansion-panel-header>
                                        Show all comments
                                    </v-expansion-panel-header>
                                    <v-expansion-panel-content
                                        v-for="comment in comments"
                                        :key=comment.id
                                    >
                                        <h3>{{comment.username}}</h3>
                                        {{comment.description}}
                                        
                                    </v-expansion-panel-content>
                                    </v-expansion-panel>
                                </v-expansion-panels>
                                <!--End of expansion panels-->
                            </v-card-text>
                    
                   
                        </v-card>

                    </v-dialog>
                    <!--End of dialog for choosing photo-->
                    
                   
                    
   
                </v-tab-item>
                <!--End of tab for photos and videos-->

                <!--Tab for stories-->
                <v-tab-item>
                    STORIES
                </v-tab-item>
                <!--End of tab for stories-->

                <!--Tab for saved/favorites-->
                <v-tab-item>
                    SAVED
                </v-tab-item>
                <!--End of tab for saved/favorites-->

                <!--Tab for tagged-->
                <v-tab-item>
                    TAGGED
                </v-tab-item>
                <!--End of tab for tagged-->
                

            </v-tabs-items>
              
          
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
      tabs: null,
      tabs2:null,
      e1: 1,
      e2: 1,
      pictures:[
          {
              path:"https://picsum.photos/200/300",
          }
      ],
      comments:[
          {
              id:1,
              description:"Amazing photo!<3 <3 <3",
              username:"anaperisic12"
          },
          {
              id:2,
              description:"WOW!Love this!!!",
              username:"pipidugacarapa25"
          }
      ],
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