<template>
  <div>
    <div class="header">
      <status-icon :connected="user.connected" />{{ user.username }}
    </div>

    <ul class="messages">
      <li
        v-for="(message, index) in user.messages"
        :key="index"
        class="message"
      >
        <div v-if="displaySender(message, index)" class="sender">
          {{ message.fromSelf ? "(yourself)" : user.username }}
        </div>
        
        <img width="200px" v-if="message.content.includes('http')" v-bind:src="message.content" /> 

        <p v-if="!message.content.includes('story') && !message.content.includes('post')" > {{message.content}}</p> 

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
                            v-if="entirePost"
                            :post="entirePost"
                          ></postComponent>
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
                            v-if="entireStory && !userStoryMessage.isPrivate"
                            :post="entireStory"
                          ></storyComponent>
                          <h1
                             v-if="userStoryMessage.isPrivate && (creatorUsername!=loggedUser.username)"
                            > USER IS PRIVATE
                          </h1>
                          <storyComponent
                            v-if="creatorUsername==loggedUser.username"
                            :post="entireStory"
                          ></storyComponent>
                        </v-dialog>
      </li>
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
import { getUsernameFromToken } from "./../util/token";
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
      input: "",
      entirePost: null,
      entireStory: null,
      loggedUser: {},
      userChatMessage: {},
      userStoryMessage: {},
      creatorUsername: "",
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
          })
          .catch((error) => {
            console.log("Error while fetching user",error)
          });
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
          console.log(storyID)
    
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
                console.log( response.data)
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
