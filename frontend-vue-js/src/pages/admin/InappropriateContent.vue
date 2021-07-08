<template>
    <v-row class="pa-5">
        <v-spacer></v-spacer>

        <v-col sm="8" md="12" lg="4" cols="12">
            <v-row>
                <v-col>
                    <v-card>
                        <!--Inappropriate content-->
                        <v-img
                            height="250"
                            src="https://cdn.vuetifyjs.com/images/cards/cooking.png"
                        ></v-img>

                        <!--Information about content-->
                        <v-card-text>
                            The owner of content:
                            <b>ivkica@gmail.com</b>
                            <br>
                           The person who reported this content:<b>ancica@gmail.com</b>
                           <br>
                           Content reporting date: <b>12-05-2021</b>

                        </v-card-text>
                        <v-divider class="mx-4"></v-divider>
                        <v-card-text>
                            The reason of reporting this content as inappropriate:
                            <br>

                            <b>I dont like mentioning nations and racism</b>
                        </v-card-text>
                        <v-card-actions>
                            <v-btn
                                class="ma-2"
                                outlined
                                rounded
                                color="primary"
                            >
                            <v-icon>
                                mdi-delete-circle
                            </v-icon>
                              Remove content
                            </v-btn>

                            <v-btn
                                class="ma-2"
                                outlined
                                rounded
                                color="red"
                            >
                            <v-icon>
                                mdi-delete-circle
                            </v-icon>
                              Remove profile
                            </v-btn>

                            
                        </v-card-actions>

                            
                    </v-card>
                </v-col>

            </v-row>
          

        </v-col>
        <v-spacer></v-spacer>
    </v-row>
</template>