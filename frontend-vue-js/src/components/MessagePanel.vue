<template>
  <div>
    <div class="header">
      <status-icon :connected="user.connected" />{{ user.username }}
    </div>
    <ul class="messages" v-if="!declinee && !deletee" >
      <li
        v-for="(message, index) in user.messages"
        :key="index"
        class="message"
      >
        <div v-if="displaySender(message, index)" class="sender">
          {{ message.fromSelf ? "(yourself)" : user.username }}
        </div>
        
       
        <p  v-if="!message.content.includes('story') && !message.content.includes('post') && !message.content.includes('http')" > {{message.content}}</p> 

        <!-- <p  >Methods date call: "{{ getEntirePost('60e5fbef1b8ceffde5005a13') }}"</p> -->
                          <v-dialog  width="600px">
                          <template v-slot:activator="{ on, attrs }">
                            <v-btn
                              v-if="message.content.includes('post')"
                              v-bind="attrs"
                              v-on="on"
                              @click="getEntirePost(message.content.substr(0, message.content.length - 4))"
                              icon
                            
                            >Open post or album
                            <v-icon right> mdi-plus-circle </v-icon>
                            </v-btn>
                          
                          </template>
                          <postComponent
                             v-if="((entirePost && !userPostMessage.isPrivate) || (entirePost && creatorUsername==loggedUser.username) || (entirePost && followed))"
                            :post="entirePost"
                          ></postComponent>
                           <h1
                             v-if="userPostMessage.isPrivate && (creatorUsername!=loggedUser.username) && !followed"
                            > USER IS PRIVATE
                          </h1>
                        </v-dialog>


                        <v-dialog width="600px">
                          <template v-slot:activator="{ on, attrs }">
                            <v-btn
                             v-if="message.content.includes('story')"
                              v-bind="attrs"
                              v-on="on"
                              @click="getEntireStory(message.content.substr(0, message.content.length - 5))"
                              icon
                            >
                            Open story
                              <v-icon right> mdi-plus-circle </v-icon>
                             </v-btn>
                          </template>
                          <storyComponent
                             v-if="(entireStory && !userStoryMessage.isPrivate || (entireStory && creatorUsername==loggedUser.username) || (entireStory && followed))"
                            :post="entireStory"
                          ></storyComponent>
                          <h1
                             v-if="userStoryMessage.isPrivate && (creatorUsername!=loggedUser.username) && !followed"
                            > USER IS PRIVATE
                          </h1>
                        </v-dialog>
 
                        <v-dialog width="600px">
                          <template v-slot:activator="{ on, attrs }">
                            <v-btn
                             v-if="message.content.includes('http') && !message.content.includes('.mp4')"
                              v-bind="attrs"
                              v-on="on"
                              @click="openOneTime()"
                              icon
                            >
                            Open photo one time
                              <v-icon right> mdi-plus-circle </v-icon>
                             </v-btn>
                          </template>
                          <img class="browse-tip" width="200px" v-if="isOpened==1" v-bind:src="message.content" /> 
                          <h1
                             v-if="isOpened!=1"
                            > YOU CAN OPEN IMAGE ONLY ONE TIME
                          </h1>
                        </v-dialog>

                         <v-dialog width="600px">
                          <template v-slot:activator="{ on, attrs }">
                            <v-btn
                             v-if="message.content.includes('.mp4')"
                              v-bind="attrs"
                              v-on="on"
                              @click="openOneTimeVideo()"
                              icon
                            >
                            Open video one time
                              <v-icon right> mdi-plus-circle </v-icon>
                             </v-btn>
                          </template>
                           <video v-if="isOpenedVideo==1" width="450" controls :src="message.content"></video>
                          <h1
                             v-if="isOpenedVideo!=1"
                            > YOU CAN OPEN VIDEO ONLY ONE TIME
                          </h1>
                        </v-dialog>
      </li>
       <p style="color: red" v-if="loggedUser.isPrivate && !followed && !accepte">Your profile if private, not followed account sent you a message!</p>
      <v-btn
      v-if="loggedUser.isPrivate && !followed && !accepte"
      @click="acceptee()">
         ACCEPT 
      </v-btn> 
      <v-btn
      v-if="loggedUser.isPrivate && !followed && !accepte" 
       @click="declineee()">
        DECLINE 
      </v-btn> 
      <v-btn
      v-if="loggedUser.isPrivate && !followed && !accepte"
      @click="deleteee()" >
        DELETE
       </v-btn>
    </ul>

    <form @submit.prevent="onSubmit" class="form">
      <textarea v-model="input" placeholder="Your message..." class="input" />
      <button :disabled="!isValid" class="send-button">Send</button>
    </form>
  </div>
</template>

<script>
import axios from "axios";
import StatusIcon from "./StatusIcon";
import postComponent from "./../components/Post.vue";
import storyComponent from "./../components/Story.vue";
import {getToken, getUsernameFromToken } from "./../util/token";
export default {
  name: "MessagePanel",
  components: {
    StatusIcon,
    postComponent,
    storyComponent
  },
  props: {
    user: Object,
  },
  data() {
    return {
      isOpened: 0,
      isOpenedVideo: 0,
      input: "",
      entirePost: null,
      entireStory: null,
      loggedUser: {},
      userChatMessage: {},
      userStoryMessage: {},
      userPostMessage: {},
      creatorUsername: "",
      isBlocked: null,
      followed: false,
      accepte: false,
      declinee: false,
      deletee: false
    };
  },
  mounted() {
    this.load();
  },
  methods: {
    load: function(){
      this.axios
          .get(
            process.env.VUE_APP_BACKEND_URL +
              process.env.VUE_APP_USER_BY_USERNAME_ENDPOINT +
              getUsernameFromToken()
          )
          .then((response) => {
            this.loggedUser = response.data;
            console.log( response.data)
          })
          .catch((error) => {
            console.log("Error while fetching user",error)
          })

 
       this.axios
          .get(
            process.env.VUE_APP_BACKEND_URL +
              process.env.VUE_APP_USER_BY_USERNAME_ENDPOINT +
              this.user.username
          )
          .then((response) => {
            this.userChatMessage = response.data;
            console.log( response.data)
            
                  this.axios
                      .get(
                        process.env.VUE_APP_BACKEND_URL +
                          process.env.VUE_APP_RELATIONSHIP_ENDPOINT +
                          this.user.username,
                        {
                          headers: {
                            Authorization: "Bearer " + getToken(),
                          },
                        }
                      )
                      .then((response) => {
                        if (response.data === "BLOCKED") this.isBlocked = true;
                        else if (response.data === "NONE") this.followed = false;
                        else this.followed = true;
                          console.log("RELATIONSIHP",response.data )
                      })
                      .catch((error) => {
                         console.log("Error while fetching user relationships",error)
                      });
          })
          .catch((error) => {
            console.log("Error while fetching user",error)
          });


                
    },
    acceptee(){
       this.accepte = true;
    },
    declineee(){
       this.declinee = true;
    },
    deleteee(){
       this.deletee = true;
    },
    openOneTime(){
       this.isOpened = this.isOpened+1;
    },
     openOneTimeVideo(){
       this.isOpenedVideo = this.isOpenedVideo+1;
    },
    onSubmit() {
      this.$emit("input", this.input);
      this.input = "";
    },
    displaySender(message, index) {
      return (
        index === 0 ||
        this.user.messages[index - 1].fromSelf !==
          this.user.messages[index].fromSelf
      );
    },
     getEntireStory(storyID) {
      // get entire post
      axios
        .get(
          process.env.VUE_APP_BACKEND_URL +
            process.env.VUE_APP_CONTENT_STORY +
            storyID,
          {
            headers: {
              Authorization: "Bearer " + localStorage.getItem("JWT-CPIS"),
            },
          }
        )
        .then((res) => {
           this.entireStory = res.data;

           this.axios
              .get(
                process.env.VUE_APP_BACKEND_URL +
                  process.env.VUE_APP_USER_BY_USERNAME_ENDPOINT +
                  this.entireStory.my_post.creator_username
              )
              .then((response) => {
                this.creatorUsername = this.entireStory.my_post.creator_username
                this.userStoryMessage = response.data;
                 this.axios
                      .get(
                        process.env.VUE_APP_BACKEND_URL +
                          process.env.VUE_APP_RELATIONSHIP_ENDPOINT +
                          this.creatorUsername,
                        {
                          headers: {
                            Authorization: "Bearer " + getToken(),
                          },
                        }
                      )
                      .then((response) => {
                        if (response.data === "BLOCKED") this.isBlocked = true;
                        else if (response.data === "NONE") this.followed = false;
                        else this.followed = true;
                          console.log("RELATIONSIHP",response.data )
                      })
                      .catch((error) => {
                         console.log("Error while fetching user relationships",error)
                      });
              })
              .catch((error) => {
                console.log("Error while fetching user",error)
            });
               
        });
    },
     getEntirePost(postID) {
      // get entire post
      axios
        .get(
          process.env.VUE_APP_BACKEND_URL +
            process.env.VUE_APP_CONTENT_POST +
            postID,
          {
            headers: {
              Authorization: "Bearer " + localStorage.getItem("JWT-CPIS"),
            },
          }
        )
        .then((res) => {
          this.entirePost = res.data;
            this.axios
              .get(
                process.env.VUE_APP_BACKEND_URL +
                  process.env.VUE_APP_USER_BY_USERNAME_ENDPOINT +
                  this.entirePost.my_post.creator_username
              )
              .then((response) => {
                this.creatorUsername = this.entirePost.my_post.creator_username
                this.userPostMessage = response.data;
                    this.axios
                      .get(
                        process.env.VUE_APP_BACKEND_URL +
                          process.env.VUE_APP_RELATIONSHIP_ENDPOINT +
                          this.creatorUsername,
                        {
                          headers: {
                            Authorization: "Bearer " + getToken(),
                          },
                        }
                      )
                      .then((response) => {
                        if (response.data === "BLOCKED") this.isBlocked = true;
                        else if (response.data === "NONE") this.followed = false;
                        else this.followed = true;
                          console.log("RELATIONSIHP",response.data )
                      })
                      .catch((error) => {
                         console.log("Error while fetching user relationships",error)
                      });
              })
              .catch((error) => {
                console.log("Error while fetching user",error)
            });
        });
    },
    
  },
  computed: {
    isValid() {
      return this.input.length > 0;
    },
  },
};
</script>

<style scoped>
.header {
  line-height: 40px;
  padding: 10px 20px;
  border-bottom: 1px solid #dddddd;
}

.messages {
  margin: 0;
  padding: 20px;
  margin-left: 70px;
}

.message {
  list-style: none;
}

.sender {
  font-weight: bold;
  margin-top: 5px;
}

.form {
  padding: 10px;
}

.input {
  width: 80%;
  resize: none;
  padding: 10px;
  line-height: 1.5;
  border-radius: 5px;
  border: 1px solid #000;
}

.send-button {
  vertical-align: top;
}
</style>
